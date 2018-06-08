package com.example.gerardo.viewzone.objetos;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gerardo on 12/08/2017.
 */

public class Zonas {
    String Colonia;
    String Coordenadas;
    Integer Numero_Eventos;
    Integer Numero_Total;
    Double Radio;
    Double Rating;




    public Zonas(String coordenadas, Integer numero_Eventos, Integer numero_Total, Double radio, Double rating) {
        Coordenadas = coordenadas;
        Numero_Eventos = numero_Eventos;
        Numero_Total = numero_Total;
        Radio = radio;
        Rating = rating ;
    }

    public Zonas(){

    }






    public String getColonia() {
        return Colonia;
    }

    public void setColonia(String colonia) {
        Colonia = colonia;
    }

    public String getCoordenadas() {
        return Coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        Coordenadas = coordenadas;
    }

    public Integer getNumero_Eventos() {
        return Numero_Eventos;
    }

    public void setNumero_Eventos(Integer numero_Eventos) {
        Numero_Eventos = numero_Eventos;
    }

    public Integer getNumero_Total() {
        return Numero_Total;
    }

    public void setNumero_Total(Integer numero_Total) {
        Numero_Total = numero_Total;
    }

    public Double getRadio() {
        return Radio;
    }

    public void setRadio(Double radio) {
        Radio = radio;
    }

    public Double getRating() {
        return Rating;
    }

    public void setRaiting(Double raiting) {
        Rating = raiting;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Colonia", Colonia);
        result.put("Coordenadas", Coordenadas);
        result.put("Numero_Eventos", Numero_Eventos);
        result.put("Numero_Total", Numero_Total);
        result.put("Radio", Radio);
        result.put("Rating", Rating);
        return result;

    }

}
