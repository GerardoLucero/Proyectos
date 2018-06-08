package com.example.gerardo.iter_cliente;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link location.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link location#newInstance} factory method to
 * create an instance of this fragment.
 */
public class location extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button locationb, sendlocation, cerrarsesion;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    private LocationListener listener;
    private LocationManager locationManager;

    String lat, lon, usr;
    Boolean request = false;
    Conectivity connect;

    ProgressDialog dialog;
    private OnFragmentInteractionListener mListener;

    public location() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment location.
     */
    // TODO: Rename and change types and number of parameters
    public static location newInstance(String param1, String param2) {
        location fragment = new location();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_location, container, false);
        locationb = (Button)view.findViewById(R.id.mylocation);
        sendlocation = (Button)view.findViewById(R.id.SendLocation);
        cerrarsesion = (Button)view.findViewById(R.id.CerrarSesion);

        pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = pref.edit();

        locationb.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getActivity(), Mylocation.class);
               startActivity(intent);
           }
        });

        sendlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {

                        lat = "" + location.getLatitude();
                        lon = "" + location.getLongitude();
                        pref = PreferenceManager.getDefaultSharedPreferences(getContext());
                        usr = pref.getString("usr2","");
                        if (connect.isConnectedWifi(getContext()) || connect.isConnectedMobile(getContext()) ) {
                            myAsyncTaskLocation myRequest = new myAsyncTaskLocation();
                            myRequest.execute();
                        }
                        else{
                            Snackbar.make(view, "Sin conexión a internet", Snackbar.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                        Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    }
                };
                locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                //noinspection MissingPermission
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5, listener);
            }
        });

        cerrarsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(getActivity());
                builder1.setMessage("¿Estás seguro de cerrar sesión?");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });

                builder1.setNegativeButton(
                        "Si",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //dialog.cancel();
                                editor.putString("usr", "");
                                editor.putString("usr2", "");
                                editor.commit();

                                Fragment fragment = null;
                                FragmentManager fragmentManager = getFragmentManager();
                                FragmentTransaction transaction = fragmentManager.beginTransaction();
                                fragment = new Login();
                                transaction.replace(R.id.activity_main, fragment).commit();
                            }
                        });

                android.support.v7.app.AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        });

        return view;
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

    private class myAsyncTaskLocation extends AsyncTask<Void, Void, Void>
    {
        String mensaje = "";
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            dialog.dismiss();
            Toast.makeText(getContext(),mensaje, Toast.LENGTH_SHORT).show();

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(getContext() , "Subiendo datos", "Espere..." , true);

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // protected SoapObject doInBackground(Integer... branchNumber){
            WebService ws = new WebService();
            request = ws.insertLocation(usr,lat,lon);
            if(request){
                mensaje = "Operación exitosa";
            }
            else{
                mensaje = "Ha ocurrido un error";
            }
            return null;
        }
    }
}
