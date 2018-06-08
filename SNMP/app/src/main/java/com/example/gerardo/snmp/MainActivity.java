package com.example.gerardo.snmp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    TextView textprueba;
    String ip = "192.168.1.147";
    SNMP snmptest = new SNMP();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textprueba = (TextView)findViewById(R.id.texttest);
        textprueba.setText("");
        Ubiquiti U0 = new Ubiquiti("10.2.19.147");
        Ubiquiti U1 = new Ubiquiti("10.2.19.148");
        Ubiquiti U2 = new Ubiquiti("10.2.19.149");
        Ubiquiti U3 = new Ubiquiti("10.2.19.150");

        List<Ubiquiti> LisU  = new ArrayList<Ubiquiti>();
        LisU.add(U0);
        LisU.add(U1);
        LisU.add(U2);
        LisU.add(U3);
        try {
            LisU = snmptest.CheckStatus(LisU);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        textprueba.setText(CheckList(LisU));

    }
    public String CheckList(List<Ubiquiti> ListaU ){
        String text = "";
        String Dtext = "";
        for (int i = 0; i < ListaU.size(); i++){
            Ubiquiti t = ListaU.get(i);
            if(t.getEstado()){
                text+= t.getIP() + " Conectado "+ t.getHora() + "\n";
            }
            else{
                text+= t.getIP() + " Desconectado  \n ";
                Dtext+= t.getIP() + " Desconectado  \n";

            }
        }
        if ( (Dtext != null) && (!Dtext.equals("")) ) {
            showNotification(Dtext);
        }
        return  text;
    }
    public void showNotification(String DIP) {

        NotificationManager manager;
        Notification myNotication;
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        long[] pattern = new long[]{500,1000,500,1000,500,1000};
        Intent intent = new Intent("com.example.gerardo.snmp.SECACTIVITY");
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 1, intent, 0);
        Notification.Builder builder = new Notification.Builder(MainActivity.this);

        builder.setAutoCancel(true);
        builder.setTicker("this is ticker text");
        builder.setContentTitle("Alerta Ubiquiti");
        builder.setContentText(DIP);
        builder.setContentIntent(pendingIntent);
        builder.setOngoing(true);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setVibrate(pattern);
        builder.setLights(Color.RED, 1, 0);
        builder.build();
        myNotication = builder.getNotification();
        manager.notify(0, myNotication);

    }
}


