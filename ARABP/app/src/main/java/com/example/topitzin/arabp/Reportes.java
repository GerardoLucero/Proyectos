package com.example.topitzin.arabp;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Reportes.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class Reportes extends Fragment {

    private OnFragmentInteractionListener mListener;
    DBHandler Physalus;
    SQLiteDatabase db;
    TextView textprueba;
    List<String[]> datos;
    ArrayList<String> Ldatos;
    ListView lista;
    Bundle bundle  = this.getArguments();

    public Reportes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reportes, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lista = (ListView)view.findViewById(R.id.Lista);
        Physalus = new DBHandler(getContext());
        db = Physalus.getWritableDatabase();
        datos = Physalus.obtenerAvistamientosV();
        Ldatos = new ArrayList<>();

        for (int i = 0; i < datos.size(); i++){
            if (datos.get(i)[12].contains("1")) {
                String registro = "";
                registro +=  getContext().getResources().getString(R.string.time_start)+": " + datos.get(i)[4] + "\t ";
                registro +=  getContext().getResources().getString(R.string.date)+": " + datos.get(i)[3] + "\t ";
                registro +=  getContext().getResources().getString(R.string.total_whales) +": " + datos.get(i)[9] + "\t ";
                registro +=  getContext().getResources().getString(R.string.latitude) + ": " + datos.get(i)[1] + "\t ";
                registro +=  getContext().getResources().getString(R.string.longitude) +": " + datos.get(i)[2] + "\t ";
                registro +=  getContext().getResources().getString(R.string.time_end)+": " + datos.get(i)[5] + "\t ";
                Ldatos.add(registro);
            }
        }
        if (datos.size() == 0){
            Toast.makeText(getContext(),  getContext().getResources().getString(R.string.vacio),Toast.LENGTH_SHORT).show();
        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, Ldatos);
        lista.setAdapter(adaptador);
        adaptador.notifyDataSetChanged();
        bundle  = this.getArguments();
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Intent intent = new Intent(getActivity(),ReporteIndivudual.class);
                String [] array = arreglo(position, datos);
                String [] processArray = arregloP(position, datos);

                intent.putExtra("reporte",array);
                if (bundle != null){
                    String string= bundle.getString("rfc");
                    intent.putExtra("rfc",string);
                }
                intent.putExtra("processReport", processArray);
                startActivity(intent);
            }
        });

    }

    private String[] arregloP(int pos, List<String[]> dat) {
//        if (dat.get(pos)[12]== "0"){
//            pos++;
//        }
        String id = "ID:  \t " + dat.get(pos)[0];
        String lat = getContext().getResources().getString(R.string.latitude) + ": \t " + dat.get(pos)[1];
        String lon = getContext().getResources().getString(R.string.longitude)+": \t " + dat.get(pos)[2];
        String fecha = getContext().getResources().getString(R.string.date)+": \t " + dat.get(pos)[3];
        String HI = getContext().getResources().getString(R.string.time_start)+": \t " + dat.get(pos)[4];
        String HF = getContext().getResources().getString(R.string.time_end)+": \t " + dat.get(pos)[5];
        String muestra = getContext().getResources().getString(R.string.sample)+" \t " + dat.get(pos)[6] + " / " + dat.get(pos)[7];
        String notes = getContext().getResources().getString(R.string.notes)+": \t " + dat.get(pos)[8];
        String total = getContext().getResources().getString(R.string.total_whales)+": \t " + dat.get(pos)[9];
        String FI = getContext().getResources().getString(R.string.fotoI)+": \t " + dat.get(pos)[10];
        String FF = getContext().getResources().getString(R.string.fotoF)+": \t " + dat.get(pos)[11];

        String[] array = {id,lat, lon, fecha, HI, HF, muestra, notes, total, FI, FF};
        return  array;
    }

    public String[] arreglo(int pos, List<String[]> dat){
//        if (dat.get(pos)[12]== "0"){
//            pos++;
//        }
        return dat.get(pos);
    }

    public String control (int pos, List<String[]> dat ){
//        if (dat.get(pos)[12]== "0"){
//            pos++;
//        }
        return dat.get(pos)[0];
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*for (int i = 0; i < datos.size(); i++){
            for (int j = 0; j < datos.get(i).length; j++) {
                //Toast.makeText(getContext(), "" + datos.get(i)[j].toString(), Toast.LENGTH_SHORT).show();
                textprueba.append(" "+datos.get(i)[j].toString());
            }

        }*/
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
