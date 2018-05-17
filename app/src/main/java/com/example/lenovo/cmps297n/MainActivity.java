package com.example.lenovo.cmps297n;


import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.lenovo.cmps297n.Posts.Checks;
import com.example.lenovo.cmps297n.Posts.CompanyWorker;
import com.example.lenovo.cmps297n.Posts.PostInfo;
import com.example.lenovo.cmps297n.Posts.RecyclerAdapter;
import com.google.android.gms.common.ConnectionResult;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    private FirebaseAuth fba;
    FirebaseUser user;
    DatabaseReference dr;
    private RecyclerView v;
    private RecyclerView.Adapter va;
    //private List<PostInfo> list;
    boolean CanCheckIn;
    boolean checkin, quite;
    CompanyWorker current;
    Snackbar bar;
    FragmentManager fm;
    CheckFragment cf;
    String id="",workerid,name;

    private boolean Detectmylocationpressed;
    protected static final String TAG = "MainActivity";
    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    double latitude;
    double longitude;
    ProgressBar d;

    @Override
    protected void onStart() {
        super.onStart();
        Detectmylocationpressed=false;
        buildGoogleApiClient();
       // if(mGoogleApiClient!=null)
       // mGoogleApiClient.connect();
       //  d=new ProgressBar(this);
      //  d=(ProgressBar) findViewById(R.id.simpleProgressBar);

      //  d.setIndeterminate(true);
       // d.setBackgroundColor(Color.rgb(3,169,244));
      //  d.setVisibility(View.VISIBLE);


        LinearLayout L = (LinearLayout) findViewById(R.id.ll);
        L.setVisibility(View.VISIBLE);

        v = (RecyclerView) findViewById(R.id.rv);
        v.setHasFixedSize(true);
        v.setLayoutManager(new LinearLayoutManager(this));

        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
               // list.clear();


                        List<PostInfo> list = new ArrayList<>();
                         list.clear();
                        for (DataSnapshot posts : dataSnapshot.child("posts").getChildren()) {
                            PostInfo p = posts.getValue(PostInfo.class);
                            list.add(p);
                        }



                        va = new RecyclerAdapter(MainActivity.this,list);
                        v.setAdapter(va);






            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //--------------------------------------------------------------------------------------------------------------------------------------
        //add code to identify in manager or employee
        dr = FirebaseDatabase.getInstance().getReference().child("company");
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("change? detection","login employee search");
                for (DataSnapshot cs : dataSnapshot.child("employees").getChildren()) {
                    CompanyWorker m = cs.getValue(CompanyWorker.class);
                    if((m.getUseremail().equals(user.getEmail()))){
                        workerid="employees";
                        name=m.getName();
                        quite=true;
                        Toast.makeText(MainActivity.this,"Welcome " + name,Toast.LENGTH_SHORT).show();;
                        Log.d("Login id FOUND ","email "+ " type " +workerid);
                        break;
                    }

                }

                if(quite==false){
                    for (DataSnapshot cs : dataSnapshot.child("managers").getChildren()) {
                        CompanyWorker m = cs.getValue(CompanyWorker.class);
                        if((m.getUseremail().equals(user.getEmail()))){
                            workerid="managers";
                            name=m.getName();
                            quite=true;
                            Toast.makeText(MainActivity.this,"Welcome " + name,Toast.LENGTH_SHORT).show();;
                            Log.d("Login id FOUND ","email "+ " type " +workerid);
                            break;
                        }

                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("FIREBASE ERROR",""+databaseError.getMessage());
            }
        });

//--------------------------------------------------------------------------------------------------------------------------------------
//d.setVisibility(View.INVISIBLE);
        L.setVisibility(View.GONE);
    }
    @Override
    protected void onStop() {
        Detectmylocationpressed=false;
        super.onStop();

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
    public void Up(View v){
        cf.setname(user.getEmail(),workerid);
    }
    public void myClickMethod2(View v){




if(Detectmylocationpressed==true) {
    if (CanCheckIn == false) {

        bar = Snackbar.make(v, "YOU CANNOT CHECK IN OR OUT", Snackbar.LENGTH_LONG)
                .setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Handle user action
                        bar.dismiss();
                    }
                });

        bar.show();
    } else {

    //USER CAN CHANGE DATABSASE
    if (checkin == false) {

        //checking in

        Date currentTime = Calendar.getInstance().getTime(); String k = currentTime.toString();


        dr=FirebaseDatabase.getInstance().getReference().child("checks").child(user.getEmail().substring(0,5));//
        Checks c = new Checks("IN",k);
        dr.push().setValue(c);

        dr = FirebaseDatabase.getInstance().getReference("company").child(workerid);
        Log.d("woker ID"," "+workerid);
        current.setCheck_status(true);
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot cs : dataSnapshot.getChildren()) {
                    CompanyWorker m = cs.getValue(CompanyWorker.class);
                    if ((m.getUseremail().equals(user.getEmail()))) {
                        String key = cs.getKey();
                        Log.d("key",key);
                        current.setUseremail(m.getUseremail());
                        current.setName(m.getName());
                        dr.child(key).setValue(current);

                        break;
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("FIREBASE ERROR", "" + databaseError.getMessage());
            }
        } );

        cf.Her(true);
        //add to realm
    } else {
        //checking out
        Date currentTime = Calendar.getInstance().getTime(); String k = currentTime.toString();


        dr=FirebaseDatabase.getInstance().getReference().child("checks").child(user.getEmail().substring(0,5));//
        Checks c = new Checks("OUT",k);
        dr.push().setValue(c);

        dr = FirebaseDatabase.getInstance().getReference("company").child(workerid);
        Log.d("woker ID"," "+workerid);
        current.setCheck_status(false);
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot cs : dataSnapshot.getChildren()) {
                    CompanyWorker m = cs.getValue(CompanyWorker.class);
                    if ((m.getUseremail().equals(user.getEmail()))) {
                        String key = cs.getKey();
                        current.setUseremail(m.getUseremail());
                        current.setName(m.getName());
                        dr.child(key).setValue(current);
                        break;
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("FIREBASE ERROR", "" + databaseError.getMessage());
            }
        } );

        cf.Her(false);
        //add to realm
    }
}
}else{
    bar = Snackbar.make(v, "DETECT YOUR LOCATION FIRST", Snackbar.LENGTH_SHORT)
            .setAction("Dismiss", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle user action
                    bar.dismiss();
                }
            });

    bar.show();
}

    }
    public void myClickMethod(View v) {
        Detectmylocationpressed=true;

        //buildGoogleApiClient(); caused crashh on logout execution
        if(mGoogleApiClient!=null)
            mGoogleApiClient.connect();
        dr = FirebaseDatabase.getInstance().getReference("company").child(workerid);
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot cs : dataSnapshot.getChildren()) {
                    CompanyWorker m = cs.getValue(CompanyWorker.class);
                    if ((m.getUseremail().equals(user.getEmail()))) {
                       checkin =m.getCheck_status();
                       Log.d("check status"," "+checkin);
                        CanCheckIn =cf.Here(latitude,longitude,checkin);
                        break;
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("FIREBASE ERROR", "" + databaseError.getMessage());
            }
        } );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Detectmylocationpressed = false;
        current = new CompanyWorker("","",false);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        setContentView(R.layout.activity_main);
        fba = FirebaseAuth.getInstance();
        dr = FirebaseDatabase.getInstance().getReference();
        if(fba.getCurrentUser()==null){
            Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(myIntent);
            finish();

        }
         user = fba.getCurrentUser();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void doThis(MenuItem item){

        DialogBox cdd=new DialogBox(MainActivity.this);
        cdd.setCancelable(false);
        cdd.show();

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_check) {
            fm = getFragmentManager();
            FragmentTransaction tf =fm.beginTransaction();
            cf = new CheckFragment();
            tf.add(R.id.fragcheck,cf);
            tf.addToBackStack(null);
            tf.commit();

        } else if (id == R.id.nav_warn) {
            Intent myIntent = new Intent(MainActivity.this, WarningActivity.class);
            startActivity(myIntent);

        } else if (id == R.id.nav_off) {
            Intent myIntent = new Intent(MainActivity.this, OffActivity.class);
            startActivity(myIntent);
        }  else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_logout) {
            fba.signOut();
            finish();
            Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(myIntent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onConnected(@Nullable Bundle bundle) {


        Log.d("ENTER CONNECTTECD","LOOOL");
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            latitude=mLastLocation.getLatitude();

            longitude=
                    mLastLocation.getLongitude();

            Toast.makeText(this,"Latitude:" +latitude+" ; Longitude:"+longitude, Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this,"Location not Detected", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }
}
