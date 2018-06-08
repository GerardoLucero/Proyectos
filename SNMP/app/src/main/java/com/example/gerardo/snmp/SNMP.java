package com.example.gerardo.snmp;

import java.util.List;

/**
 * Created by Gerardo on 08/02/2017.
 */

public class SNMP{

    public static final String READ_COMMUNITY = "public";

    public static final String OID_SYS_DESCR="1.3.6.1.2.1.1.3.0";

    String batteryCap;
    String strIPAddress;
    Ubiquiti U;
    int i;
    public List<Ubiquiti> CheckStatus(final List<Ubiquiti> ListaU ) throws InterruptedException {
        i = 0;
        for (; i < ListaU.size(); i++){
            U = ListaU.get(i);
            strIPAddress = U.getIP();
            batteryCap = "";
            Thread thread = new Thread(new Runnable(){@Override
                public void run() {
                    try {
                        SNMP4JHelper objSNMP = new SNMP4JHelper();
                        batteryCap =objSNMP.snmpGet(strIPAddress,READ_COMMUNITY,OID_SYS_DESCR);
                        if ( (batteryCap == null) || (batteryCap.equals("")) ) {
                            U.setEstado(false);
                        }
                        else{
                            U.setEstado(true);
                            U.setHora(batteryCap);
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    finally {
                        ListaU.set(i,U);
                    }
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return ListaU;
    }
}
