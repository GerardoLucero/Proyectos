package com.example.gerardo.iter_cliente;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Login.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Login extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    Button SingIn;
    EditText usrtxt, passtxt;
    String usr = "", password = "";
    Switch rememberme;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    boolean recordar = false;
    boolean logOk = false;
    Conectivity connect;

    ProgressDialog dialog;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Login() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Login.
     */
    // TODO: Rename and change types and number of parameters
    public static Login newInstance(String param1, String param2) {
        Login fragment = new Login();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        connect= new Conectivity();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        SingIn = (Button)view.findViewById(R.id.btn_SingIn);
        usrtxt = (EditText)view.findViewById(R.id.rfc);
        passtxt = (EditText)view.findViewById(R.id.pass);
        rememberme = (Switch)view.findViewById(R.id.remember);
        connect= new Conectivity();

        pref = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        editor = pref.edit();


        rememberme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    recordar = true;
                } else {
                    //do stuff when Switch if OFF
                    recordar = false;
                }
            }
        });

        SingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usr = usrtxt.getText().toString();
                password = passtxt.getText().toString();

                if(!usr.matches("") && !password.matches("")){
                    if (connect.isConnectedWifi(getContext()) || connect.isConnectedMobile(getContext()) ) {

                        myAsyncTaskLogin myRequest = new myAsyncTaskLogin();
                        myRequest.execute();
                    }
                    else{
                        Snackbar.make(v, "Sin conexión a internet", Snackbar.LENGTH_LONG).show();
                    }
                }
                else{
                    Snackbar.make(v, "Termine de llenar los campos", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        return view;


    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


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

    private class myAsyncTaskLogin extends AsyncTask<Void, Void, Void>
    {
        String mensaje = "";
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Toast.makeText(getContext(),mensaje, Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            if(logOk){
                if (recordar) { //si quiere recordar usuario
                    editor.putString("usr", usr);
                    editor.commit();
                }
                Fragment fragment = null;

                editor.putString("usr2", usr);
                editor.commit();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                fragment = new location();
                transaction.replace(R.id.activity_main, fragment).commit();
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(getContext() , "Verificando datos", "Espere..." , true);

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // protected SoapObject doInBackground(Integer... branchNumber){
            WebService ws = new WebService();
            logOk = ws.LogIn(usr,password);
            if(logOk){
                mensaje = "Usuario correcto";
            }
            else{
                mensaje = "Usuario o contraseña invalida";
            }
            return null;
        }
    }

}
