package com.example.gerardo.webserver;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class MainActivity extends Activity {
    public EditText nro1,nro2;
    private TextView tv;
    private Button sumar;
    private String resultado="";
    private TareaSumar tareaSumar = null;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nro1=(EditText) findViewById(R.id.editText2);
        nro2=(EditText) findViewById(R.id.editText3);
        sumar=(Button) findViewById(R.id.button2);
        tv = (TextView) findViewById(R.id.textView5);
        tv.setText("0");
        sumar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                tareaSumar = new TareaSumar();
                tareaSumar.execute();
            }
        });
    }

    private class TareaSumar extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            final String NAMESPACE = "http://webservicelucero.esy.es/soap/localhost/servicio_suma/";
            final String URL = "http://webservicelucero.esy.es/servicio_suma/servicio_suma/server_suma.php?wsdl";
            final String METHOD_NAME = "suma";
            final String SOAP_ACTION = " http://webservicelucero.esy.es/servicio_suma/servicio_suma/server_suma.php/suma";

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            request.addProperty("a", nro1.getText().toString());
            request.addProperty("b", nro2.getText().toString());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE ht = new HttpTransportSE(URL);
            try {
                ht.call(SOAP_ACTION, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                resultado = response.toString();

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        // This gets executed on the UI thread so it can safely modify Views
                        tv.setText(resultado);
                    }
                });

                Log.i("Resultado: ", resultado);
            } catch (Exception e) {

                return false;
            }
            return true;
        }



    }




}