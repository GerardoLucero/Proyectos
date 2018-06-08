package com.example.gerardo.firebase;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;


public class MainActivity extends AppCompatActivity {
    TextView textprueba;
    private static final String TAG = "MyGcmListenerService";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textprueba = (TextView)findViewById(R.id.button);



    }

    @Override
    public void onTokenRefresh() {

        //Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        Log.d(TAG, "Refreshed token: " + refreshedToken);
        if(refreshedToken != null && !refreshedToken.equals("")){

            sendRegistrationToServer(refreshedToken);

        }
    }
}
