package com.example.topitzin.arabp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class ReporteIndivudual extends FragmentActivity implements OnMapReadyCallback{

    private DrawerLayout drawerLayout;
    private ViewPager viewPager;
    Bundle bundle;
    DBHandler Physalus;
    SQLiteDatabase db;
    FloatingActionButton fab, fab3;
    GoogleMap mMap;
    CoordinatorLayout mCoordinatorLayout;
    SupportMapFragment sMapFragment;
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    AppBarLayout appBarLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_indivudual);

        /*LinearLayout root = (LinearLayout)findViewById(android.R.id.list).getParent().getParent().getParent();*/
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_content);
        appBarLayout = ( AppBarLayout) mCoordinatorLayout.findViewById(R.id.appBar);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) appBarLayout.findViewById(R.id.collapsing_toolbar);
        android.support.v4.app.FragmentManager afm = getSupportFragmentManager();
        sMapFragment = (SupportMapFragment) afm.findFragmentById(R.id.mapFragment); //mCollapsingToolbarLayout.findViewById(R.id.mapFragment);

        viewPager = (ViewPager)findViewById(R.id.tab_viewpager);
        if (viewPager != null){
            setupViewPager(viewPager);
        }

        Physalus = new DBHandler(getApplicationContext());
        db = Physalus.getWritableDatabase();

        fab = (FloatingActionButton) findViewById(R.id.fab2);
        sMapFragment.getMapAsync(this);

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(ReporteIndivudual.this);
                builder1.setMessage(getApplicationContext().getResources().getString(R.string.delete));
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Si",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String id_Ava = control();
                                String[] arreglillo = bundle.getStringArray("reporte");

                                try {
                                    int fallo = Physalus.actualizarAvistamiento(bundle.getString("rfc"), "0", id_Ava,Double.valueOf(arreglillo[1]),Double.valueOf(arreglillo[2]),arreglillo[3],arreglillo[4],arreglillo[5],arreglillo[6],arreglillo[7],arreglillo[8],Integer.valueOf(arreglillo[9]),arreglillo[10],arreglillo[11]);
                                    if (fallo > 0) {
                                        Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.erased), Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ReporteIndivudual.this, Whales.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else
                                    { Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.error), Toast.LENGTH_SHORT).show(); }
                                }
                                catch (Exception w){}
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //dialog.cancel();

                            }
                        });

                android.support.v7.app.AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

    }

    public String control (){
        String[] arreglillo = bundle.getStringArray("reporte");
        return arreglillo[0];
    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        bundle = getIntent().getExtras();
        VersionModel.data = bundle.getStringArray("processReport");
        adapter.addFrag(new CoordinatorFragment(), "Reporte");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap = googleMap;
        String [] array =  bundle.getStringArray("reporte");
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(array[2]),  Double.parseDouble(array[1])), 4));
        mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(array[2]),  Double.parseDouble(array[1]))));//.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
    }


    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager){
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position){
            return mFragmentTitleList.get(position);
        }
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        switch (id){
            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }

                return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
