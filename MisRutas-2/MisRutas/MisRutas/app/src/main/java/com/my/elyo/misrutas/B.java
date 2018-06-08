package com.my.elyo.misrutas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by elyo_ on 11/11/2016.
 */

public class B extends SQLiteOpenHelper{
    public static String campo1="";
    public static String campo2="";
    public static String campo3="";
    public static String campo4="";
    public static String campo5="";
    public static String campo6="";
    public static String campo7="";
    public static String campo8="";
    public static String campo9="";
    public static String campo10="";
    public static String mensajeerror="";
    public static Integer id=1;

    public String queryTabla="create table lugares (id TEXT not null, lat TEXT not null, lan TEXT  not null);";
    //variar nombre de la tabla para poder usar la clase en cualquier proyecto o base de datos//
    public static String lastQuery ="";

    public B(Context context, String nombrebd) {
        super(context, nombrebd, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(queryTabla);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void actualizarid(SQLiteDatabase d) {
        lastQuery="select * from lugares";
        Cursor cr= d.rawQuery(lastQuery,null);
        try {
            if (cr.moveToFirst()) {
                do {
                    id= Integer.valueOf(cr.getString(0));
                } while (cr.moveToNext());
                id++;
            }
        } catch (Exception ex) {
            mensajeerror=ex.getMessage();
        }

    }

    public boolean insertRow(SQLiteDatabase d, String[] valores, String tabla){
        lastQuery ="insert into "+ tabla + " values "+construirParametros(valores);
        try {
            d.execSQL(lastQuery);
            actualizarid(d);
            return true;
        }
        catch (Exception ex)
        {
            mensajeerror=ex.getMessage().toString();
            return false;
        }
    }
    public String construirParametros(String[] s){
        String f="('"+ id++ +"', '";
        for (byte i=0;i<s.length;i++)
        {
            if(i!=s.length-1)
                f+=s[i]+"', '";
            else
                f+=s[i]+"');";
        }

        return f;
    }
    public Cursor selectAll(SQLiteDatabase d){
        lastQuery="select * from lugares";
        Cursor c= d.rawQuery(lastQuery,null);
        //puede mejorarse para ser para cualquier tabla//
        return c;
    }
    public boolean deleteRow(SQLiteDatabase db,String id) {
        lastQuery = "delete from lugares where id = '" + id + "'";
        try {
            db.execSQL(lastQuery);
            return true;
        } catch (Exception e) {
            mensajeerror = e.getMessage();
            return false;
        }
    }
    public boolean deleteAll(SQLiteDatabase db){
        lastQuery = "delete from lugares where 1=1";
        try {
            db.execSQL(lastQuery);
            return true;
        } catch (Exception e) {
            mensajeerror = e.getMessage();
            return false;
        }
    }
    }
