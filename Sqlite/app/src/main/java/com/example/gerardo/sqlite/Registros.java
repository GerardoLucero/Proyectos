package com.example.gerardo.sqlite;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Registros extends AppCompatActivity {

    DBHandler db = new DBHandler(this);
    final Context context = this;

    public final static String EXTRA_MESSAGE0 = "com.example.gerardo.sqlit.MESSAGE";
    public final static String EXTRA_MESSAGE1 = "com.example.gerardo.sqlit.NAME";
    public final static String EXTRA_MESSAGE2 = "com.example.gerardo.sqlit.SURNAME";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registros);

        final ListView list = (ListView) findViewById(R.id.ListView1);
        List<Alumno> a = db.getAlumnos();

        final ArrayList<String> l = new ArrayList<String>();
        for (int i = 0; i < a.size() ; ++i) {
            String str = a.get(i).NoControl + " "+ a.get(i).Nombre + " " +a.get(i).Apellido;
            l.add(str);
        }

        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1 ,l);
        list.setAdapter(adapter);



        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {



                final String item = (String) parent.getItemAtPosition(position);
                String [] i = item.split(" ");
                final Alumno d = new Alumno(Integer.parseInt(i[0]),i[1],i[2]);

                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1
                        .setMessage("Desea borrar ese registro?")
                        .setCancelable(false)
                        .setPositiveButton("Si",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, close
                                // current activity
                                view.animate().setDuration(1000).alpha(0)
                                        .withEndAction(new Runnable() {
                                            @Override
                                            public void run() {
                                                l.remove(item);
                                                adapter.notifyDataSetChanged();
                                                view.setAlpha(1);
                                                db.deleteAlumno(d);

                                            }
                                        });
                            }
                        })
                        .setNegativeButton("Modificar",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                Intent i = new Intent(context, Editar.class);
                                Bundle extras = new Bundle();
                                extras.putInt(EXTRA_MESSAGE0, d.NoControl);
                                extras.putString(EXTRA_MESSAGE1, d.Nombre);
                                extras.putString(EXTRA_MESSAGE2, d.Apellido);
                                i.putExtras(extras);
                                startActivity(i);
                            }
                        });

                        AlertDialog alertDialog = builder1.create();
                        alertDialog.show();



            }

        });

    }

    public void refresh(View view){          //refresh is onClick name given to the button
        onRestart();
    }

    @Override
    protected void onRestart() {

        // TODO Auto-generated method stub
        super.onRestart();
        Intent i = new Intent(this, Registros.class);  //your class
        startActivity(i);
        finish();
    }





}


