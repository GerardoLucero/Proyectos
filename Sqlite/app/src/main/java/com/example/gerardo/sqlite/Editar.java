package com.example.gerardo.sqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Editar extends AppCompatActivity {


    DBHandler db = new DBHandler(this);
    private EditText et1,et2,et3;
    int  message0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        Bundle extras = getIntent().getExtras();

        message0 = extras.getInt(Registros.EXTRA_MESSAGE0);
        String message1 = extras.getString(Registros.EXTRA_MESSAGE1);
        String message2 = extras.getString(Registros.EXTRA_MESSAGE2);


        et1=(EditText)findViewById(R.id.editText6);
        et2=(EditText)findViewById(R.id.editText7);
        et3=(EditText)findViewById(R.id.editText8);

        et1.setText(Integer.toString(message0));
        et2.setText(message1);
        et3.setText(message2);
    }

    public void insert(View v) {
        db.updateAlumno(new Alumno(Integer.parseInt(et1.getText().toString()),et2.getText().toString(), et3.getText().toString()), message0);
        this.finish();

    }
}
