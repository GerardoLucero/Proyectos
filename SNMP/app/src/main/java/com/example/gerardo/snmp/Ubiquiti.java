package com.example.gerardo.snmp;

/**
 * Created by Gerardo on 08/02/2017.
 */

public class Ubiquiti {

    private String IP;
    private Boolean Estado;
    private String Hora;


    public Ubiquiti(String ip){
        IP = ip;
        Estado = true;
    }

    public Boolean getEstado() {
        return Estado;
    }

    public String getIP() {
        return IP;
    }

    public void setEstado(Boolean estado) {
        Estado = estado;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }
}
