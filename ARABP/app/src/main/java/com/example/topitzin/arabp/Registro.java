package com.example.topitzin.arabp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Registro extends AppCompatActivity {

    ImageButton cancel, acc;
    TextView rfc, name, surname, pass, pass2;
    String Nombre, Apellido,RFC,contraseña, contraseña2 = "";
    DBHandler Physalus;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        Physalus = new DBHandler(this.getApplicationContext());
        cancel = (ImageButton) findViewById(R.id.cancel);
        acc = (ImageButton) findViewById(R.id.accept);
        rfc = (TextView) findViewById(R.id.RFC);
        name = (TextView) findViewById(R.id.name);
        surname = (TextView)findViewById(R.id.surname);
        pass = (TextView)findViewById(R.id.password);
        pass2 = (TextView)findViewById(R.id.passwordC);



        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registro.this, Whales.class);
                try {
                    Nombre = name.getText().toString();
                    Apellido = surname.getText().toString();
                    RFC = rfc.getText().toString();
                    db = Physalus.getWritableDatabase();
                    contraseña = pass.getText().toString();
                    contraseña2 = pass2.getText().toString();
                    if (!Nombre.matches("") && !contraseña.matches("") && !RFC.matches("") && !contraseña.matches("") && !contraseña2.matches("")) {
                        if (contraseña.matches(contraseña2)){
                            if(RFC.length() == 10) {

                                int valor = Physalus.existeInvestigador(RFC, db);

                                if (valor <= 0 /*El RFC no existe en la base de datos*/) {
                                    /* empezar la activity si no mostrar mensaje.*/

                                    try {
                                        Physalus.anadirInvestigador(RFC, contraseña, Nombre, Apellido);
                                        intent.putExtra("rfc", RFC);
                                        startActivity(intent);
                                        finish();
                                    }
                                    catch (Exception e){
                                        Snackbar.make(v,  getApplicationContext().getResources().getString(R.string.error), Snackbar.LENGTH_LONG).show();
                                    }
                                }
                                else { Snackbar.make(v,  getApplicationContext().getResources().getString(R.string.rfcUsed), Snackbar.LENGTH_LONG).show(); }

                            }
                            else
                            { Snackbar.make(v,   getApplicationContext().getResources().getString(R.string.noUser), Snackbar.LENGTH_LONG).show(); }
                        }
                        else
                        { Snackbar.make(v,  getApplicationContext().getResources().getString(R.string.passMatch), Snackbar.LENGTH_LONG).show(); }
                    }
                    else
                    { Snackbar.make(v,  getApplicationContext().getResources().getString( R.string.fillBlanks), Snackbar.LENGTH_LONG).show(); }


                } catch (Exception e) {

                }

            }
        });
    }

}
