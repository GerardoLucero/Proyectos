package com.example.topitzin.arabp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button SingIn, btn_register;
    EditText rfc, pass;
    DBHandler Physalus;
    SQLiteDatabase db;
    String Rfc, password = "";
    Switch rememberme;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    boolean recordar = false;
    boolean logOk = false;
    Conectivity connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SingIn = (Button)findViewById(R.id.btn_SingIn);
        rfc = (EditText)findViewById(R.id.rfc);
        pass = (EditText)findViewById(R.id.pass);
        rememberme = (Switch)findViewById(R.id.remember);
        btn_register = (Button)findViewById(R.id.registrarse);
        connect= new Conectivity();

        pref = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        Physalus = new DBHandler(this.getApplicationContext());
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
                db = Physalus.getWritableDatabase();
                Intent intent = new Intent(MainActivity.this, Whales.class);
                try {
                    Rfc = rfc.getText().toString();
                    password = pass.getText().toString();
                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);

                    if (!Rfc.matches("") && !password.matches("")){
                        if (pref.getBoolean("datos",true)) {//Quiere usar datos
                            if (connect.isConnectedWifi(MainActivity.this)) { //tiene wifi
                                if (Physalus.obternerRFC(db, Rfc, password) == 1) { //Tiene usuario en BD local
                                    if (recordar) { //si quiere recordar usuario
                                        editor.putString("usr", Rfc);
                                    }
                                    editor.putString("usr2", Rfc);
                                    editor.commit();
                                    intent.putExtra("rfc", Rfc);
                                    startActivity(intent);
                                    finish();
                                } else { //Verificar en la base de datos enlinea WS
                                    myAsyncTask myRequest = new myAsyncTask();
                                    myRequest.execute();
                                }
                            }
                            else if (connect.isConnectedMobile(MainActivity.this)){ //si no tiene wifi pero si datos
                                if (Physalus.obternerRFC(db, Rfc, password) == 1) { //Tiene usuario en BD local
                                    if (recordar) { //si quiere recordar usuario
                                        editor.putString("usr", Rfc);
                                        editor.commit();
                                    }
                                    editor.putString("usr2", Rfc);
                                    editor.commit();
                                    intent.putExtra("rfc", Rfc);
                                    startActivity(intent);
                                    finish();
                                } else { //Verificar en la base de datos enlinea WS
                                    myAsyncTask myRequest = new myAsyncTask();
                                    myRequest.execute();
                                }
                            }
                            else if (Physalus.obternerRFC(db, Rfc, password) == 1) { //Tiene usuario en BD local
                                if (recordar) { //si quiere recordar usuario
                                    editor.putString("usr", Rfc);
                                    editor.commit();
                                }
                                intent.putExtra("rfc", Rfc);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Snackbar.make(v, getApplicationContext().getResources().getString(R.string.noUser), Snackbar.LENGTH_LONG).show();
                            }
                        }
                        else { //Quiere decir que no quiere usar datos
                            if (connect.isConnectedWifi(MainActivity.this)) { //tiene wifi
                                if (Physalus.obternerRFC(db, Rfc, password) == 1) { //Tiene usuario en BD local
                                    if (recordar) { //si quiere recordar usuario
                                        editor.putString("usr", Rfc);
                                        editor.commit();
                                    }
                                    intent.putExtra("rfc", Rfc);
                                    startActivity(intent);
                                    finish();
                                } else { //Verificar en la base de datos enlinea WS
                                    myAsyncTask myRequest = new myAsyncTask();
                                    myRequest.execute();
                                }
                            }
                            else { //Si no tiene Internet
                                if (Physalus.obternerRFC(db, Rfc, password) == 1) { //Tiene usuario en BD local
                                    if (recordar) { //si quiere recordar usuario
                                        editor.putString("usr", Rfc);
                                        editor.commit();
                                    }
                                    intent.putExtra("rfc", Rfc);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    Snackbar.make(v, getApplicationContext().getResources().getString(R.string.noUser), Snackbar.LENGTH_LONG).show();
                                }
                            }
                        }
                    }
                    else
                    { Snackbar.make(v, getApplicationContext().getResources().getString(R.string.fillBlanks), Snackbar.LENGTH_LONG).show(); }

                }
                catch (Exception e)
                {  Snackbar.make(v, getApplicationContext().getResources().getString(R.string.error), Snackbar.LENGTH_LONG).show(); }
            }
        });


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Registro.class);
                startActivity(intent);
            }
        });
    }
    private class myAsyncTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Intent intent = new Intent(MainActivity.this, Whales.class);
            if(logOk){
                if (recordar) { //si quiere recordar usuario
                    editor.putString("usr", Rfc);
                    editor.commit();
                }
                intent.putExtra("rfc", Rfc);
                startActivity(intent);
                finish();
            }
            else {

                Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.noUser),Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // protected SoapObject doInBackground(Integer... branchNumber){
            WebService ws = new WebService();
            logOk = ws.LogIn(Rfc,password);
            return null;
        }
    }
}
