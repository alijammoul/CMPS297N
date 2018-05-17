package com.example.lenovo.cmps297n;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.textclassifier.TextClassifier;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.lenovo.cmps297n.Posts.CompanyWorker;
import com.example.lenovo.cmps297n.Posts.Severity;
import com.example.lenovo.cmps297n.Posts.Warning;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;



public class WarningActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DatabaseReference dr;
    private FirebaseAuth fba;
    FirebaseUser user;
    private Warning a=new Warning();
    private int[] tabIcons = {
            R.drawable.ic_warning_white_24dp,
            R.drawable.ic_assignment_white_24dp,

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning);
       // toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        setupTabIcons();

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


    }
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);

    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new WarningFragment(), null);
        adapter.addFragment(new RecordsFragment(),null);


        viewPager.setAdapter(adapter);
    }
    public void warn(String content,String employee) {

        fba = FirebaseAuth.getInstance();
        user = fba.getCurrentUser();
        dr = FirebaseDatabase.getInstance().getReference();
        a.setFromManager(user.getEmail());
        a.setToEmployee(employee);
        a.setContent(content);
        dr.child("warnings").push().setValue(a);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            Intent m = new Intent(WarningActivity.this,MainActivity.class);
            startActivity(m);
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.warn, menu);
        return true;
    }
    public void doThis(MenuItem item){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle("Issuing Warning");
       // final Spinner spinner = new Spinner (WarningActivity.this);
        final EditText input = new EditText(WarningActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);


        // set dialog message
        alertDialogBuilder
                .setMessage("Reason of warning")
                .setCancelable(false)
                .setIcon(R.drawable.ic_assignment_late_white_24dp)
               .setView(input)
               // .setView(spinner)
                .setPositiveButton("next",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity

                        dialog.cancel();
                       createspinner(input.getText().toString().trim());

                    }
                })
                .setNegativeButton("cancel",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }
    private void createspinner(final String content){


        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Select Severity of Warning");
        String[] types = {"FIRED", "HIGH","MODERATE","LOW"};
        b.setItems(types, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                switch(which){
                    case 0:  a.setSeverity(Severity.FIRED) ;
                        break;
                    case 1:  a.setSeverity(Severity.HIGH) ;
                        break;
                    case 2:  a.setSeverity(Severity.MODERATE) ;
                        break;
                    case 3:  a.setSeverity(Severity.LOW) ;
                        break;
                }
                createspinner2( content);
            }

        });

        b.show();
    }
private void createspinner2(final String content){

    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
            this);

    // set title
    alertDialogBuilder.setTitle("Issuing Warning");
    // final Spinner spinner = new Spinner (WarningActivity.this);
    final EditText input = new EditText(WarningActivity.this);
    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT);
    input.setLayoutParams(lp);
    // spinner.setLayoutParams(lp);
    //

    //  final  ArrayAdapter<CharSequence> adapter = new ArrayAdapter(spinner.getContext(),
    //        android.R.layout.simple_spinner_item,list);
    //  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    //  spinner.setAdapter(adapter);
    //

    // set dialog message
    alertDialogBuilder
            .setMessage("Enter email of employee")
            .setCancelable(false)

            .setView(input)
            // .setView(spinner)
            .setPositiveButton("send",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int id) {
                    // if this button is clicked, close
                    // current activity
                    String employee = input.getText().toString().toLowerCase().trim();
                    warn(content,employee);

                    dialog.cancel();


                }
            })
            .setNegativeButton("cancel",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int id) {
                    // if this button is clicked, just close
                    // the dialog box and do nothing
                    dialog.cancel();
                }
            });

    // create alert dialog
    AlertDialog alertDialog = alertDialogBuilder.create();
    // show it
    alertDialog.show();
}

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
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

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
