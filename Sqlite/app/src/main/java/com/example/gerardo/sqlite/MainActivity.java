package com.example.gerardo.sqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void insertar(View view) {
        Intent i = new Intent(this,Insertar.class);
        startActivity(i);
    }

    public void ver(View view) {
        Intent i = new Intent(this,Registros.class);
        startActivity(i);
    }

}
