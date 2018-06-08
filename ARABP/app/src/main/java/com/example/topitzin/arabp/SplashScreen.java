package com.example.topitzin.arabp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen extends AppCompatActivity {

    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        pref = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());

        //preguntar si hay una sesion activa en las preferencias (preference manager)

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                String  RFC = pref.getString("usr","");
                if (RFC == "") {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(SplashScreen.this, Whales.class);
                    intent.putExtra("rfc", RFC);
                    startActivity(intent);
                }

            }
        }, 1000);
    }
}
