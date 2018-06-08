package com.example.gerardo.sqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Insertar extends AppCompatActivity {

    DBHandler db = new DBHandler(this);
    private EditText et1,et2,et3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar);


        et1=(EditText)findViewById(R.id.editText6);
        et2=(EditText)findViewById(R.id.editText7);
        et3=(EditText)findViewById(R.id.editText8);

        //db.deleteAll();
    }

    public void insert(View v) {
        db.addAlumno(new Alumno(Integer.parseInt(et1.getText().toString()),et2.getText().toString(), et3.getText().toString()));
        et1.setText("");
        et2.setText("");
        et3.setText("");


    }
}
