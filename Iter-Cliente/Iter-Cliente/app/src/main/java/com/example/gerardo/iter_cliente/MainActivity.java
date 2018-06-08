package com.example.gerardo.iter_cliente;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

public class MainActivity extends AppCompatActivity
        implements Login.OnFragmentInteractionListener,
                    location.OnFragmentInteractionListener{

    String  usr = "";
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        pref = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());

        Fragment fragment = null;
        boolean FramentTransaction = true;

        String  usr = pref.getString("usr","");

        if (usr == "") {
            if (FramentTransaction){
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                fragment = new Login();
                transaction.add(R.id.activity_main, fragment).commit();
            }
        }
        else{
            if (FramentTransaction){
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                fragment = new location();
                transaction.add(R.id.activity_main, fragment).commit();
            }
        }


    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(MainActivity.this);
            builder1.setMessage("¿Estás seguro de salir?");
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
                            finish();
                        }
                    });

            android.support.v7.app.AlertDialog alert11 = builder1.create();
            alert11.show();

        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
