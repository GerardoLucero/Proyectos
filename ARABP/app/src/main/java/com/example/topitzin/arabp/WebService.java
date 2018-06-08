package com.example.topitzin.arabp;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

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

    public boolean updateTablaAvistamiento(DBHandler Physalus){
        METHOD_NAME = "Avistamientos";
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("a", "VALORX");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);

        HttpTransportSE ht = new HttpTransportSE(URL);
        Object response = null;
        String cad = "";
        String t = "";
        try {
            ht.call(SOAP_ACTION, envelope);
            response = (Object) envelope.getResponse();
            cad = "";
            if (response != null) {
                Physalus.deleteAvistamientos();
                Physalus.deleteAvistInvestacion();
                cad = response.toString();
                JSONObject json = new JSONObject(cad);
                JSONArray jArray = json.getJSONArray("avistamientos");
                for(int i=0; i<jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    Physalus.anadirAvistamiento(json_data.getString("ID_INVESTIGADOR"),
                                                json_data.getString("ID_AVISTAMIENTOS"),
                                                json_data.getDouble("LON"),
                                                json_data.getDouble("LAT"),
                                                json_data.getString("FECHA"),
                                                json_data.getString("AVIHORA"),
                                                json_data.getString("AVIHORAF"),
                                                json_data.getString("MUESTRA"),
                                                json_data.getString("TIPO_PRESERVACION"),
                                                json_data.getString("NOTAS"),
                                                json_data.getInt("NoBallenas"),
                                                json_data.getString("SecuenciaFotosI"),
                                                json_data.getString("SecuenciaFotosF"),
                                                json_data.getString("Visible"));
                }
                t = "True";
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
        boolean asd = false;
        if(t.contentEquals("True")) {
            asd = true;
        } else if (t.contentEquals("True") == false){
            asd = false;
        }
        return asd;
    }

    public boolean updateTablaInvestigador(DBHandler Physalus){
        METHOD_NAME = "Investigador";
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("a", "VALORX");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);

        HttpTransportSE ht = new HttpTransportSE(URL);
        Object response = null;
        String cad = "";
        String t = "";
        try {
            ht.call(SOAP_ACTION, envelope);
            response = (Object) envelope.getResponse();
            cad = "";
            if (response != null) {
                Physalus.deleteInvestigacion();
                cad = response.toString();
                JSONObject json = new JSONObject(cad);
                JSONArray jArray = json.getJSONArray("investigador");
                for(int i=0; i<jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    Physalus.anadirInvestigador(json_data.getString("ID_INVESTIGADOR"),
                                                json_data.getString("INVCONTRASENA"),
                                                json_data.getString("INVNOMBRE"),
                                                json_data.getString("INVAPELLIDOS"));
                }
                t = "True";
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
        boolean asd = false;
        if(t.contentEquals("True")) {
            asd = true;
        } else if (t.contentEquals("True") == false){
            asd = false;
        }
        return asd;
    }


    public boolean insertTablaAvistamiento(DBHandler Physalus){
        METHOD_NAME = "nuevoAvis";
        List<String[]> AList= Physalus.obtenerAvistamientos();
        String cad = "";
        String t = "True";
        for (int i = 0; i < AList.size(); i++){
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("a",AList.get(i)[0]);
            request.addProperty("b",AList.get(i)[1]);
            request.addProperty("c",AList.get(i)[2]);
            request.addProperty("d",AList.get(i)[3]);
            request.addProperty("e",AList.get(i)[4]);
            request.addProperty("f",AList.get(i)[5]);
            request.addProperty("g",AList.get(i)[6]);
            request.addProperty("i",AList.get(i)[7]);
            request.addProperty("j",AList.get(i)[8]);
            request.addProperty("k",AList.get(i)[9]);
            request.addProperty("l",AList.get(i)[10]);
            request.addProperty("m",AList.get(i)[11]);
            request.addProperty("n",AList.get(i)[12]);
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
        }
        boolean asd = false;
        if(AList.size() == 0 || cad.contentEquals("True")) {
            asd = true;
        } else if (t.contentEquals("True") == false){
            asd = false;
        }
        return asd;
    }
    public boolean insertTablaInvestigador(DBHandler Physalus){
        METHOD_NAME = "nuevoInv";
        List<String[]> AList= Physalus.obtenerInvestigador();
        String cad = "";
        String t = "True";
        for (int i = 0; i < AList.size(); i++){
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("a",AList.get(i)[0]);
            request.addProperty("b",AList.get(i)[1]);
            request.addProperty("c",AList.get(i)[2]);
            request.addProperty("d",AList.get(i)[3]);
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
        }
        boolean asd = false;
        if(AList.size() == 0 || cad.contentEquals("True")) {
            asd = true;
        } else if (t.contentEquals("True") == false){
            asd = false;
        }
        return asd;
    }


    public boolean insertTablaAvistinvestigador(DBHandler Physalus){
        METHOD_NAME = "nuevoAvistin";

        List<String[]> AList= Physalus.obtenerAvistinvestigador();
        String cad = "";
        String t = "true";
        for (int i = 0; i < AList.size(); i++) {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("a", AList.get(i)[1]);
            request.addProperty("b", AList.get(i)[0]);
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
        }
        boolean asd = false;
        if(AList.size() == 0 || cad.contentEquals("True")) {
            asd = true;
        } else if (t.contentEquals("True") == false){
            asd = false;
        }
        return asd;
    }


}
