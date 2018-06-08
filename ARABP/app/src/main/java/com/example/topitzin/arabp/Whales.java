package com.example.topitzin.arabp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Whales extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FragmentAvistamiento.OnFragmentInteractionListener, Reportes.OnFragmentInteractionListener,
        Home.OnFragmentInteractionListener {


    String rfc;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private static final int MENU_WIFI = Menu.FIRST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whales);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = pref.edit();
        //region listeners
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                Fragment fragment = null;
                boolean FramentTransaction = true;

                if (FramentTransaction){
                    fragment = new Home();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_main, fragment).commit();
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        //endregion



        //region cosas
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Bundle v = getIntent().getExtras();
        rfc =  v.get("rfc").toString();
        //endregion
        //Toast.makeText(getApplicationContext(),"RFC: " + rfc ,Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(Whales.this);
            builder1.setMessage("¿Estás seguro de salir?");
            builder1.setCancelable(true);
            builder1.setPositiveButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
//                            Intent i = new Intent(getApplicationContext(), Whales.class);
//                            i.putExtra("rfc", RFC);
//                            startActivity(i);
                        }
                    });

            builder1.setNegativeButton(
                    "Si",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //dialog.cancel();
                            finish();
                        }
                    });

            android.support.v7.app.AlertDialog alert11 = builder1.create();
            alert11.show();

        }
        return super.onKeyDown(keyCode, event);
    }

    /*@Override
    protected void onDestroy() {

        android.os.Process.killProcess(android.os.Process.myPid());
        super.onDestroy();
    }*/

    protected Boolean isConeccted() {
        if (conectadoWifi()) {
            //showAlertDialog(MainActivity.this, "Conexion a Internet",
            //        "Tu Dispositivo tiene Conexion a Wifi. Se consulto exitosamente.", true);
            return true;
        }else {
            showAlertDialog(Whales.this, "Internet connection",
                    "There's no Internet connection, Local database will be use. Syncronize later.", false);
            return false;
        }
    }

    protected Boolean isConecctedMovil() {
        if (conectadoWifi()) {
            //showAlertDialog(MainActivity.this, "Conexion a Internet",
            //        "Tu Dispositivo tiene Conexion a Wifi. Se consulto exitosamente.", true);
            return true;
        }else {
            if (conectadoRedMovil()){
                return true;
            } else {
                showAlertDialog(Whales.this, "Internet connection",
                        "There's no Internet connection, try later.", false);
                return false;
            }
        }

    }


    protected Boolean isConecctedSync() {
        if (conectadoWifi()) {
            //showAlertDialog(MainActivity.this, "Conexion a Internet",
            //        "Tu Dispositivo tiene Conexion a Wifi. Se consulto exitosamente.", true);
            return true;
        }else {
            showAlertDialog(Whales.this, "Internet connection",
                    "There's no Internet connection, try later.", false);
            return false;
        }
    }

    protected Boolean conectadoWifi(){
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

    protected Boolean conectadoRedMovil(){
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setIcon((status) ? R.drawable.ic_menu_share : R.drawable.warning);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.getItem(0).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
        //return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Fragment fragment = null;
        boolean FramentTransaction = true;

        if (FramentTransaction){
            fragment = new Home();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main, fragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.whales, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        boolean FramentTransaction = false;

        if (id == R.id.nav_new) {
            fragment = new FragmentAvistamiento();
            Bundle bundle = new Bundle();
            bundle.putString("rfc", rfc);
            fragment.setArguments(bundle);
            FramentTransaction = true;
        } else if (id == R.id.nav_report) {
            fragment = new Reportes();
            Bundle bundle = new Bundle();
            bundle.putString("rfc", rfc);
            fragment.setArguments(bundle);
            FramentTransaction = true;
        } else if (id == R.id.nav_setting) {
            Intent intent = new Intent(Whales.this, Preferences.class);
            FramentTransaction = false;
            startActivity(intent);
        }
        else if (id == R.id.nav_logout){
            Intent intent = new Intent(Whales.this, MainActivity.class);
            editor.putString("usr", "");
            editor.putString("usr2", "");
            editor.commit();
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();


            //ActivityCompat.finishAffinity(Whales.this);
        }
        if (FramentTransaction){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main, fragment).commit();
            item.setChecked(true);
            //getSupportActionBar().setTitle(item.getTitle());
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
