package com.example.topitzin.arabp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class Home extends Fragment {

    private OnFragmentInteractionListener mListener;
    ImageButton Sync;
    SharedPreferences pref;
    Conectivity conexion;
    DBHandler Physalus;
    SQLiteDatabase db;
    myAsyncTask ast;
    ProgressDialog dialog;
    TextView a,b;
    SharedPreferences id;
    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Physalus = new DBHandler(getContext());
        db = Physalus.getWritableDatabase();
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        a = (TextView) view.findViewById(R.id.Usuario);
        b = (TextView) view.findViewById(R.id.No);

        id = PreferenceManager.getDefaultSharedPreferences(getContext());
        String rfc = id.getString("usr2", "");

        String usr = Physalus.getNombre(rfc);
        String n = String.valueOf(Physalus.countAvistamiento());

        String partA_text = String.format(getContext().getResources().getString(R.string.part_a), usr);
        a.setText(partA_text);
        String partB_text = String.format(getContext().getResources().getString(R.string.part_b), n);
        b.setText(partB_text);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Sync = (ImageButton)view.findViewById(R.id.syncrhonize);
        pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        conexion = new Conectivity();

        Sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean d = false;
                if (pref.getBoolean("datos",true)){
                    if (conectadoRedMovil() || conectadoWifi()){
                        d = true;
                    }
                    else {
                        showAlertDialog(getContext(),getContext().getResources().getString(R.string.iConnection),"" + R.string.noConexion,false);
                    }
                }
                else{
                    if (conectadoWifi()){
                        d = true;
                    }
                    else { showAlertDialog(getContext(),getContext().getResources().getString(R.string.iConnection),"" + getContext().getResources().getString(R.string.noConexion),false); }
                }

                if(d){
                    ast = new myAsyncTask();
                    ast.execute();
                }
            }
        });
    }
    protected Boolean conectadoWifi(){
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                return true;
            }
        }
        return false;
        /*ConnectivityManager connectivity = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;*/
    }

    protected Boolean conectadoRedMovil(){
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to wifi
                return true;
            }
        }
        return false;
        /*ConnectivityManager connectivity = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;*/
    }

    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setIcon((status) ? R.drawable.ic_menu_share : R.drawable.warning);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class myAsyncTask extends AsyncTask<Void, Void, Void>
    {
        String mensaje = "";
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Toast.makeText(getContext(),mensaje, Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(getContext() , getContext().getResources().getString(R.string.Syncn), getContext().getResources().getString(R.string.wait), true);

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // protected SoapObject doInBackground(Integer... branchNumber){
            WebService ws = new WebService();
            if(ws.insertTablaAvistamiento(Physalus) &&
                    ws.insertTablaInvestigador(Physalus) &&
                    ws.insertTablaAvistinvestigador(Physalus) &&
                    ws.updateTablaAvistamiento(Physalus) &&
                    ws.updateTablaInvestigador(Physalus)){
                //Mensaje exito
                mensaje = getContext().getResources().getString(R.string.Syncn2);

            }
            else{
                //Mensaje error
                mensaje = getContext().getResources().getString(R.string.fail);
            }
            return null;
        }


        protected void onProgressUpdate(String values) {
            if (values != null) {
                // shows a toast for every value we get
                Toast.makeText(getContext(), values, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
