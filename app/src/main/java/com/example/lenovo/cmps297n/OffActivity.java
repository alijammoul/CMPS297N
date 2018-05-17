package com.example.lenovo.cmps297n;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.lenovo.cmps297n.Posts.VacStatus;
import com.example.lenovo.cmps297n.Posts.VacType;
import com.example.lenovo.cmps297n.Posts.Vacation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class OffActivity extends AppCompatActivity {
private Calendar myCalendar;
    private Calendar myCalendar2;
private DatePickerDialog.OnDateSetListener date;
    private DatePickerDialog.OnDateSetListener date2;

    private EditText edittext, edittext2,editreason;
    Button b,b2;
    private Spinner spinner;
    FirebaseAuth fba;
    FirebaseUser user;
    DatabaseReference dr;
    String reason;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_off);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(spinner.getContext(),
                R.array.vactype, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
          myCalendar = Calendar.getInstance();
        myCalendar2 = Calendar.getInstance();
       edittext = (EditText) findViewById(R.id.Birthday);
        edittext2 = (EditText) findViewById(R.id.Birthday2);
        editreason = (EditText) findViewById(R.id.reason);



        Log.d("date empty",edittext.getText().toString());

       date  = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();

            }

        };
        date2  = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH, monthOfYear);
                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(OffActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        edittext2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(OffActivity.this, date2, myCalendar2
                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });





         b = (Button) findViewById(R.id.submit);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Log.d("date empty",edittext.getText().toString());
                  boolean validinfo = check();

if(validinfo==true){
    sendtobd();
    Toast.makeText(OffActivity.this, "Time Off Request Submitted", Toast.LENGTH_SHORT).show();
     finish();//bring alert dialog here
    Intent m = new Intent(OffActivity.this,MainActivity.class);
    startActivity(m);
}else{
    Toast.makeText(OffActivity.this, "Missing Information!", Toast.LENGTH_SHORT).show();

}










            }
        });

    }
    private boolean check(){
        if(!editreason.getText().equals("")&&editreason.getText()!=null){
            return true;
        }else{
            return false;
        }
    }

    private void sendtobd() {
        fba = FirebaseAuth.getInstance();
        dr = FirebaseDatabase.getInstance().getReference();
        user= fba.getCurrentUser();
        reason=editreason.getText().toString().trim();
        int position = spinner.getSelectedItemPosition();
        Vacation vac = new Vacation();
        switch (position){
            case 0:  vac.setType(VacType.SICKNESS) ;break;
            case 1: vac.setType( VacType.TRAVEL);break;
            case 2: vac.setType(VacType.FUNERAL);break;
            case 3: vac.setType(VacType.VACATION);break;
            case 4: vac.setType(VacType.EMERGENCY);break;
            case 5: vac.setType(VacType.WEATHER);
        }
        vac.setStatus(VacStatus.PENDING);
       vac.setStartdate(edittext.getText().toString());
       vac.setEnddate(edittext2.getText().toString());
        vac.setEmployee(user.getEmail());
        dr.child("offs").push().setValue(vac);
    }



    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edittext.setText(sdf.format(myCalendar.getTime()));
        edittext2.setText(sdf.format(myCalendar2.getTime()));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            Intent myIntent = new Intent(OffActivity.this, MainActivity.class);
            startActivity(myIntent);
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
