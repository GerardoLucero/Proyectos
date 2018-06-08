package com.example.gerardo.iter_cliente;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by topitzin on 30/11/2016.
 */
public class WebService {
    final String NAMESPACE = "urn:metodoswsdl";
    final String URL = "http://webservicelucero.esy.es/Servicio/servicio.php?wsdl";
    String METHOD_NAME;
    final String SOAP_ACTION = "http://webservicelucero.esy.es/Servicio/servicio.php";

    public boolean LogIn(String RFC, String pass) {
        METHOD_NAME = "login";
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("a", RFC);
        request.addProperty("b", pass);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);

        HttpTransportSE ht = new HttpTransportSE(URL);
        Object response = null;
        String cad = "";
        try {
            ht.call(SOAP_ACTION, envelope);
            response = (Object) envelope.getResponse();
            cad = "";
            if (response != null) {
                cad = response.toString();
            }
        } catch (SoapFault e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {

        }
        if (cad.contains("True")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean insertLocation(String nombre, String latitud, String longitud){
        METHOD_NAME = "insert";
        String cad = "";

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("a",nombre);
            request.addProperty("b",latitud);
            request.addProperty("c",longitud);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            HttpTransportSE ht = new HttpTransportSE(URL);
            Object response = null;

            try {
                ht.call(SOAP_ACTION, envelope);
                response = (Object) envelope.getResponse();
                cad = "";
                if (response != null) {
                    cad = response.toString();
                }
            } catch (SoapFault e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e) {
            }

        if (cad.contains("True")) {
            return true;
        } else {
            return false;
        }
    }





}
