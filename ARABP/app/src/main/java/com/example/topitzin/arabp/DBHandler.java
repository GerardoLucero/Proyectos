package com.example.topitzin.arabp;

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
    private static final String DATABASE_NAME = "PROYECTPHYSALUS";
    //Avistamientos
    private static final String TABLE_AV = "Avistamientos";
    private static final String KEY_ID = "ID_AVISTAMIENTOS";
    private static final String KEY_LONG = "LON";
    private static final String KEY_LAT = "LAT";
    private static final String FECHA = "FECHA";
    private static final String AVI_HORA = "AVIHORA";
    private static final String AVI_HORAF = "AVIHORAF";
    private static final String MUESTRA = "MUESTRA";
    private static final String TI_PRES = "TIPO_PRESERVACION";
    private static final String NOTAS = "NOTAS";
    private static final String NoBall = "NoBallenas";
    private static final String SecuenciaFotosI = "SecuenciaFotosI";
    private static final String SecuenciaFotosF = "SecuenciaFotosF";
    private static final String Visible = "Visible";


    //Investigador
    private static final String TABLE_INV = "Investigador";
    private static final String ID_INV = "ID_INVESTIGADOR";
    private static final String INV_CONT = "INVCONTRASENA";
    private static final String INV_NOM = "INVNOMBRE";
    private static final String INV_APE = "INVAPELLIDOS";
    private static final String INV_ORG = "INVORGANIZACION";

    //Avistinvestigador
    private static final String TABLE_AVIN = "Avistinvestigador";
    private static final String ID_IN = "ID_INVESTIGADOR";
    private static final String ID_OR = "ID_AVISTAMIENTOS";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE0 = "CREATE TABLE " + TABLE_AV + "("
                + KEY_ID + " TEXT ," + KEY_LONG + " DOUBLE,"
                + KEY_LAT + " DOUBLE ," + FECHA + " TEXT ," + AVI_HORA + " TEXT ,"
                + AVI_HORAF + " TEXT ," + MUESTRA + " TEXT ,"
                + TI_PRES + " TEXT ," + NOTAS + " TEXT ,"
                + NoBall + " INTEGER ," + SecuenciaFotosI + " TEXT ,"
                + SecuenciaFotosF + " TEXT ,"
                + Visible + " TEXT " + ")";
        db.execSQL(CREATE_CONTACTS_TABLE0);

        String CREATE_CONTACTS_TABLE1 = "CREATE TABLE " + TABLE_INV + "("
                + ID_INV + " TEXT ," + INV_CONT + " TEXT,"
                + INV_NOM + " TEXT ," + INV_APE + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE1);

        String CREATE_CONTACTS_TABLE3 = "CREATE TABLE " + TABLE_AVIN + "("
                + ID_IN + " TEXT ," + ID_OR + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE3);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_AV);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_INV);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_AVIN);
        // Creating tables again
        onCreate(db);
    }

    public void anadirAvistamiento(String rfc, String id, double lo, double la, String Fe, String AHI, String AHF, String M, String TP, String NTS, int NoB, String SFI, String SFF, String V) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_ID, id);
        values.put(KEY_LONG, lo);
        values.put(KEY_LAT, la);
        values.put(FECHA, Fe);
        values.put(AVI_HORA, AHI);
        values.put(AVI_HORAF, AHF);
        values.put(MUESTRA, M);
        values.put(TI_PRES, TP);
        values.put(NOTAS, NTS);
        values.put(NoBall, NoB);
        values.put(SecuenciaFotosI, SFI);
        values.put(SecuenciaFotosF, SFF);
        values.put(Visible, V);
        db.insert(TABLE_AV, null, values);
        db.close();

        anadirAvistInvestigador(rfc, id);

    }

    public int countAvistamiento() {
        SQLiteDatabase db = this.getWritableDatabase();
        String countQuery = "SELECT COUNT(*) FROM " + TABLE_AV ;
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        int count= cursor.getInt(0);
        return count;
    }

    public String getNombre(String rfc){
        SQLiteDatabase db = this.getWritableDatabase();
        String countQuery = "SELECT " + INV_NOM +" FROM " + TABLE_INV + " WHERE " + ID_INV +" = '"+rfc+"'";
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        String nombre= cursor.getString(0);
        return nombre;
    }

    public int getid(String dia, SQLiteDatabase db) {
        //SQLiteDatabase db = this.getWritableDatabase();
        String countQuery = "SELECT COUNT(*) FROM " + TABLE_AV + " WHERE " + KEY_ID + " LIKE " + "'%" + dia + "%'";
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        int count= cursor.getInt(0);
        return count;
    }




    public void anadirInvestigador(String id, String InvC, String InvN, String InvA) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID_INV, id);
        values.put(INV_CONT, InvC);
        values.put(INV_NOM, InvN);
        values.put(INV_APE, InvA);
        db.insert(TABLE_INV, null, values);
        db.close(); // Closing database connection
    }

    public void anadirAvistInvestigador(String IN, String OR) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID_IN, IN);
        values.put(ID_OR, OR);

        db.insert(TABLE_AVIN, null, values);
        db.close(); // Closing database connection
    }

    public int actualizarAvistamiento(String rfc, String V, String id, Double lo, Double la, String Fe,String AHI,String AHF,String M,String TP,String NTS,int NoB,String SFI,String SFF) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_ID, id);
        values.put(KEY_LONG, lo);
        values.put(KEY_LAT, la);
        values.put(FECHA, Fe);
        values.put(AVI_HORA, AHI);
        values.put(AVI_HORAF, AHF);
        values.put(MUESTRA, M);
        values.put(TI_PRES, TP);
        values.put(NOTAS, NTS);
        values.put(NoBall, NoB);
        values.put(SecuenciaFotosI, SFI);
        values.put(SecuenciaFotosF, SFF);
        values.put(Visible, V);
        String quien = quienJue(id);
        int a = 0;
        if ( quien.contains(rfc)) {
            a = db.update(TABLE_AV, values, KEY_ID + " = ?",
                    new String[]{String.valueOf(id)});
        }
        else if (quien.contains(rfc) == false)
            a = 0;
        return a;
    }

    public String quienJue(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT "+ID_IN+" FROM "+TABLE_AVIN+" WHERE "+ID_OR+"='"+id+"'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        String count= cursor.getString(0);
        cursor.close();
        return count;
    }

    public int actualizarInvestigador(String id, String InvC, String InvN, String InvA, String InvO, int i) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID_INV, id);
        values.put(INV_CONT, InvC);
        values.put(INV_NOM, InvN);
        values.put(INV_APE, InvA);
        values.put(INV_ORG, InvO);


        return db.update(TABLE_INV, values, KEY_ID + " = ?",
                new String[]{String.valueOf(i)});
    }

    public int actualizarAvistInvestigador(String IN, String OR, int i) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID_IN, IN);
        values.put(ID_OR, OR);


        return db.update(TABLE_INV, values, KEY_ID + " = ?",
                new String[]{String.valueOf(i)});
    }


    public List<String[]> obtenerAvistamientos() {
        List<String[]> AList = new ArrayList<String[]>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_AV;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String[] valores = new String[13];
                valores[0] = (cursor.getString(0));
                valores[1] = (cursor.getString(1));
                valores[2] = (cursor.getString(2));
                valores[3] = (cursor.getString(3));
                valores[4] = (cursor.getString(4));
                valores[5] = (cursor.getString(5));
                valores[6] = (cursor.getString(6));
                valores[7] = (cursor.getString(7));
                valores[8] = (cursor.getString(8));
                valores[9] = (cursor.getString(9));
                valores[10] = (cursor.getString(10));
                valores[11] = (cursor.getString(11));
                valores[12] = (cursor.getString(12));
                // Adding contact to list
                AList.add(valores);
            } while (cursor.moveToNext());
        }
        // return contact list
        return AList;
    }

    public List<String[]> obtenerAvistamientosV() {
        List<String[]> AList = new ArrayList<String[]>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_AV;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String[] valores = new String[13];
                valores[0] = (cursor.getString(0));
                valores[1] = (cursor.getString(1));
                valores[2] = (cursor.getString(2));
                valores[3] = (cursor.getString(3));
                valores[4] = (cursor.getString(4));
                valores[5] = (cursor.getString(5));
                valores[6] = (cursor.getString(6));
                valores[7] = (cursor.getString(7));
                valores[8] = (cursor.getString(8));
                valores[9] = (cursor.getString(9));
                valores[10] = (cursor.getString(10));
                valores[11] = (cursor.getString(11));
                valores[12] = (cursor.getString(12));
                // Adding contact to list
                if ((cursor.getString(12)).contains("1")) {
                    AList.add(valores);
                }
            } while (cursor.moveToNext());
        }
        // return contact list
        return AList;
    }


    public int existeInvestigador(String rfc, SQLiteDatabase db ){
        //List<String[]> AList = new ArrayList<String[]>();
        // Select All Query
        String selectQuery = "SELECT COUNT(*) FROM " + TABLE_INV + " WHERE " + ID_INV + " = '" + rfc + "'";

        //db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        int count= cursor.getInt(0);
        cursor.close();
        // return contact list
        return count;
    }

    public int obternerRFC(SQLiteDatabase db, String RFC, String Pass){

        String selectQuery = "SELECT COUNT(*) FROM " + TABLE_INV + " WHERE " + ID_INV + " = '" + RFC + "' AND " + INV_CONT + " = '" + Pass + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        int count= cursor.getInt(0);
        cursor.close();
        // return contact list
        return count;
    }


    public List<String[]> obtenerInvestigador() {
        List<String[]> AList = new ArrayList<String[]>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_INV;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String[] valores = new String[4];
                valores[0] = (cursor.getString(0));
                valores[1] = (cursor.getString(1));
                valores[2] = (cursor.getString(2));
                valores[3] = (cursor.getString(3));
                // Adding contact to list
                AList.add(valores);
            } while (cursor.moveToNext());
        }
        // return contact list
        return AList;
    }


    public List<String[]> obtenerAvistinvestigador() {
        List<String[]> AList = new ArrayList<String[]>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_AVIN;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String[] valores = new String[2];
                valores[0] = (cursor.getString(0));
                valores[1] = (cursor.getString(1));

                // Adding contact to list
                AList.add(valores);
            } while (cursor.moveToNext());
        }
        // return contact list
        return AList;
    }


    public boolean eliminarAvistamiento(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_AV, KEY_ID + " = ?",
                new String[]{String.valueOf(id)}) > 0;
        //db.close();
    }

    public boolean setInvisibleAvistamiento(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_AV, KEY_ID + " = ?",
                new String[]{String.valueOf(id)}) > 0;
        //db.close();
    }

    public void eliminarInvestigador(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INV, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public void eliminarAvistInvestigador(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_AVIN, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteAvistamientos() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_AV);
        db.close();
    }

    public void deleteInvestigacion() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_INV);
        db.close();
    }

    public void deleteAvistInvestacion() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_AVIN);
        db.close();
    }


}