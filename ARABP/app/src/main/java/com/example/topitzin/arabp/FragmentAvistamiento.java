package com.example.topitzin.arabp;

//import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class FragmentAvistamiento extends Fragment {


    private OnFragmentInteractionListener mListener;
    private LocationListener listener;
    private LocationManager locationManager;

    ImageButton guardar;
    DBHandler Physalus;
    SQLiteDatabase db;
    SharedPreferences pref;
    EditText tv, noballenas, f1, f2;
    EditText lat, hora, horafi, notes;
    EditText lon;
    TextView lugar;
    Double la;
    Spinner muestra, preservacion;
    Bundle bundle  = this.getArguments();


    public FragmentAvistamiento() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_avistamiento, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //region asignaciones
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        bundle = this.getArguments();
        //bt = (Button)view.findViewById(R.id.btn_calendar);
        Physalus = new DBHandler(getContext());

        lat = (EditText) view.findViewById(R.id.Latitud);
        //db = Physalus.getWritableDatabase();
        lon = (EditText) view.findViewById(R.id.Longitud);


        notes = (EditText) view.findViewById(R.id.Observaciones);
        pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        f1 = (EditText) view.findViewById(R.id.Foto1);
        f2 = (EditText) view.findViewById(R.id.FotoFinal);
        horafi = (EditText) view.findViewById(R.id.timeend);
        noballenas = (EditText) view.findViewById(R.id.NoIndividuos);
        tv = (EditText) view.findViewById(R.id.Fecha);
        hora = (EditText) view.findViewById(R.id.hora);
        tv.setText(date);
        muestra = (Spinner) view.findViewById(R.id.mue);
        preservacion = (Spinner) view.findViewById(R.id.pres);
        lugar = (TextView)view.findViewById(R.id.tv_zona2);
        guardar = (ImageButton) view.findViewById(R.id.Guardar);
//endregion
        //listener for buttons and edittext
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //region campos
                String fecha = "";
                String hor = "";
                Double lati = 0.0;
                Double lot = 0.0;
                String horaf = "";
                String mues = "";
                String prese = "";
                int TW = 0;
                String i = "";
                String photofst = "";
                String photolst = "";
                String note = "";
                String id = "";

                //endregion

                //region asignaciones
                try {
                    fecha = tv.getText().toString();
                    hor = hora.getText().toString();
                    horaf = horafi.getText().toString();
                    lati = Double.valueOf(lat.getText().toString());
                    lot = Double.valueOf(lon.getText().toString());
                    mues = muestra.getSelectedItem().toString();
                    prese = preservacion.getSelectedItem().toString();
                    TW = Integer.parseInt(noballenas.getText().toString());
                    photofst = f1.getText().toString();
                    photolst = f2.getText().toString();
                    note = notes.getText().toString();
                    String[] idthings = fecha.split("-");
                    String fech = idthings[0] + idthings[1] + idthings[2];
                    db = Physalus.getWritableDatabase();
                    int next = Physalus.getid(fech, db);
                    id = idthings[0] + idthings[1] + idthings[2] + next +1;
                    if (bundle != null) {
                         i = bundle.getString("rfc");
                        //Toast.makeText(getContext(), "RFC: " + i, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {

                }
                //endregion
                InputMethodManager inputManager = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                //solo agrega localmente, implementar la conexxion a internet.
                //region
                inputManager.hideSoftInputFromWindow(v.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                try {
                    if (fecha.matches("") || hor.matches("") || horaf.matches("") || lati.toString().matches("") ||
                            lot.toString().matches("") || mues.matches("") || prese.matches("") || TW == 0 || photofst.matches("") ||
                            photolst.matches("") || id.matches("")) {
                        Toast.makeText(getContext(), getContext().getResources().getString(R.string.fillBlanks), Toast.LENGTH_SHORT).show();

                    } else {
                        try {

                            Physalus.anadirAvistamiento(i,id, lot, lati, fecha, hor, horaf, mues, prese, note, TW, photofst, photolst, "1");
                            Toast.makeText(getContext(), getContext().getResources().getString(R.string.added), Toast.LENGTH_SHORT).show();
                            clearText();
                        } catch (Exception e) {
                            Toast.makeText(getContext(),  getContext().getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), getContext().getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
//endregion
            }
        });

        lon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                la = Double.valueOf(lat.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                la = Double.valueOf(lat.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0 && lat.length() != 0){
                    Double lo = Double.valueOf(s.toString());

                    lugar.setText(AsignarRegion(lo,la));
                }
            }
        });


        tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDatePickerDialog(v);
                }
            });

        noballenas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numericPickerDialog();
            }
        });
        hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog();
            }
        });
        horafi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialogEnd();
            }
        });

        Bundle bundle = this.getArguments();

        if (pref.getBoolean("gps", true)) {
            listener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    lat.setText("" + location.getLatitude());
                    lon.setText("" + location.getLongitude());
                    lon.setText("" + location.getLongitude());
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
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 30, listener);
        }
        else{
            if (listener != null)
                listener = null;
            if ( locationManager != null){
                //noinspection MissingPermission
                locationManager.removeUpdates(listener);
                locationManager = null;
            }
            lon.setText("-");

        }
    }

    public String AsignarRegion(double longitud, double latitud)
    {
        if (longitud >= -109.9 && longitud <= -105.18 &&  latitud <= 23.93 && latitud >= 20.77)
        {
            return " "+ getContext().getResources().getString(R.string.sur);
        }
        if (longitud >= -109.8 && longitud <= -107 && latitud <= 28.12 && latitud >= 23.93)
        {
            return " "+ getContext().getResources().getString(R.string.centro);
        }
        else if (longitud >= -114.64 && longitud <= -113 && latitud <= 31.5 && latitud >= 28.12)
        {
            return  " "+getContext().getResources().getString(R.string.norte);
        }
        return " "+ getContext().getResources().getString(R.string.unknow);
    }

    protected Boolean isConeccted() {
        if (conectadoWifi()) {
            //showAlertDialog(MainActivity.this, "Conexion a Internet",
            //        "Tu Dispositivo tiene Conexion a Wifi. Se consulto exitosamente.", true);
            return true;
        }else {
            showAlertDialog(getContext(), getContext().getResources().getString(R.string.iConnection),
                    "There's no Internet connection, Local database will be use. Syncronize later.", false);
            return false;
        }
    }

    protected Boolean isConecctedMovil() {
        if (conectadoWifi()) {
            //showAlertDialog(MainActivity.this, "Conexion a Internet",
            //        "Tu Dispositivo tiene Conexion a Wifi. Se consulto exitosamente.", true);
            return true;
        }else {
            if (conectadoRedMovil()){
                return true;
            } else {
                showAlertDialog(getContext(), "Internet connection",
                        "There's no Internet connection, try later.", false);
                return false;
            }
        }

    }


    protected Boolean isConecctedSync() {
        if (conectadoWifi()) {
            //showAlertDialog(MainActivity.this, "Conexion a Internet",
            //        "Tu Dispositivo tiene Conexion a Wifi. Se consulto exitosamente.", true);
            return true;
        }else {
            showAlertDialog(getContext(), "Internet connection",
                    "There's no Internet connection, try later.", false);
            return false;
        }
    }

    protected Boolean conectadoWifi(){
        ConnectivityManager connectivity = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

    protected Boolean conectadoRedMovil(){
        ConnectivityManager connectivity = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setIcon((status) ? R.drawable.ic_menu_share : R.drawable.warning);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }


    private void clearText() {
        lat.setText("");
        //db = Physalus.getWritableDatabase();
        lon.setText("");
        notes.setText("");
        f1.setText("");
        f2.setText("");
        horafi.setText("");
        noballenas.setText("");
        hora.setText("");
    }

    public void numericPickerDialog(){
        NumberPicker myNumericPicker = new NumberPicker(getContext());
        myNumericPicker.setMaxValue(10);
        myNumericPicker.setMinValue(0);

        NumberPicker.OnValueChangeListener myValChangeListener = new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
               noballenas.setText(""+newVal);
            }
        };

        myNumericPicker.setOnValueChangedListener(myValChangeListener);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setView(myNumericPicker);
        builder.setTitle(getContext().getResources().getString(R.string.numerpicker));
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //noballenas.setText(""+newVal);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    public void timePickerDialog(){
        TimePicker myTimePicker = new TimePicker(getContext());
        //myTimePicker.setIs24HourView(true);
        TimePicker.OnTimeChangedListener myTimeChangeListener = new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                hora.setText(""+hourOfDay +":"+ minute);
            }
        };
        myTimePicker.setOnTimeChangedListener(myTimeChangeListener);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setView(myTimePicker);
        builder.setTitle(getContext().getResources().getString(R.string.time));
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    public void timePickerDialogEnd(){
        TimePicker myTimePicker = new TimePicker(getContext());
        //myTimePicker.setIs24HourView(true);
        TimePicker.OnTimeChangedListener myTimeChangeListener = new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                horafi.setText(""+hourOfDay +":"+ minute);
            }
        };
        myTimePicker.setOnTimeChangedListener(myTimeChangeListener);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setView(myTimePicker);
        builder.setTitle(getContext().getResources().getString(R.string.time));
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
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

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DataFragment();
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
