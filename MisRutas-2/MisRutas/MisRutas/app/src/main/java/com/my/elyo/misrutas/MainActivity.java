package com.my.elyo.misrutas;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText e1;
    EditText e2;
    Context contextR = this;
    static TextView t;
    ListView l;
    B b;
    String asd="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        t = (TextView) findViewById(R.id.textView);
        l = (ListView) findViewById(R.id.listView);

        b = new B(contextR, "gps") ;
        b.actualizarid(b.getReadableDatabase());


        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemC, int position, long id) {
                TextView t=(TextView) itemC;
            }
        });
    }
    public void servicio(View view) {
        ServicioGPS s = new ServicioGPS(getApplicationContext());
        t.setText("");
    }

    public void verMapa(View view) {
        Intent i = new Intent(this,Mapa.class);
        startActivity(i);
        t.setText("");
    }

    public void registro(View view) {
        actualizarTabla();
        t.setText("");
    }

    public void actualizarTabla() {
        ArrayList<String> ar = new ArrayList<>();
        Cursor cr = b.selectAll(b.getReadableDatabase());
        try {
            if (cr.moveToFirst()) {
                do {
                    ar.add(cr.getString(0) + " - " + cr.getString(2) + ", " + cr.getString(1));
                } while (cr.moveToNext());
            }
        } catch (Exception ex) {
            t.setText(ex.getMessage());
        }
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ar);
        l.setAdapter(adapter);
    }
    public void limpiarbase(View view){
        b.deleteAll(b.getWritableDatabase());
    }
}