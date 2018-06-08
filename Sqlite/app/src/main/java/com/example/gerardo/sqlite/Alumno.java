package com.example.gerardo.sqlite;

/**
 * Created by Gerardo on 06/11/2016.
 */

public class Alumno {


    public int NoControl;
    public String Nombre;
    public String Apellido;


    public Alumno(){
        NoControl = 0;
        Nombre = "";
        Apellido = "";
    }
    public Alumno(int no, String  nb, String ap){

        NoControl = no;
        Nombre = nb;
        Apellido = ap;
    }


}
