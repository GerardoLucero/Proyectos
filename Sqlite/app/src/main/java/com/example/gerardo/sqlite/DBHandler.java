package com.example.gerardo.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "Escuela";
    // Contacts table name
    private static final String TABLE_AL = "Alumnos";
    // Shops Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SR= "surname";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_AL + "("
        + KEY_ID + " INTEGER ," + KEY_NAME + " TEXT,"
        + KEY_SR + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_AL);
        // Creating tables again
        onCreate(db);
    }

    public void addAlumno(Alumno a) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, a.NoControl );
        values.put(KEY_NAME, a.Nombre ); // Shop Name
        values.put(KEY_SR, a.Apellido ); // Shop Phone Number

        db.insert(TABLE_AL, null, values);
        db.close(); // Closing database connection
    }

    public int updateAlumno(Alumno a, int i) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, a.NoControl );
        values.put(KEY_NAME, a.Nombre);
        values.put(KEY_SR, a.Apellido);

        return db.update(TABLE_AL, values, KEY_ID + " = ?",
        new String[]{String.valueOf(i )});
    }


    public List<Alumno> getAlumnos() {
        List<Alumno> AList = new ArrayList<Alumno>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_AL;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Alumno a = new Alumno();
                a.NoControl = (Integer.parseInt(cursor.getString(0)));
                a.Nombre = (cursor.getString(1));
                a.Apellido =(cursor.getString(2));
        // Adding contact to list
                AList.add(a);
            } while (cursor.moveToNext());
        }
        // return contact list
        return AList;
    }

    public void deleteAlumno(Alumno a) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_AL, KEY_ID + " = ?",
                new String[] { String.valueOf(a.NoControl) });
        db.close();
    }

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_AL);
        db.close();
    }



}