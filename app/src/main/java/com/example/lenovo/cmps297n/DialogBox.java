package com.example.lenovo.cmps297n;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lenovo.cmps297n.Posts.PostInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class DialogBox extends Dialog implements
        android.view.View.OnClickListener{
    private Spinner spinner;
    private FirebaseAuth fba;
    private DatabaseReference dr;
    EditText comment;
    FirebaseUser user;
    String scomment="";

    public Activity c;

    public Button yes,no;

    public DialogBox(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_box);
        spinner = (Spinner)findViewById(R.id.spinner);
        fba = FirebaseAuth.getInstance();
        dr = FirebaseDatabase.getInstance().getReference();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(spinner.getContext(),
                R.array.post_type, android.R.layout.simple_spinner_item);
//set the spinners adapter to the previously created one.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        no = (Button) findViewById(R.id.btn_no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        yes = (Button) findViewById(R.id.btn_yes);
        yes.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Date currentTime = Calendar.getInstance().getTime();
        String k = currentTime.toString();
        user = fba.getCurrentUser();
        comment = (EditText) findViewById(R.id.comment);
        scomment = comment.getText().toString().trim();
        if(scomment.isEmpty())
        {}//dismiss();

        else
        {

            PostInfo p = new PostInfo(scomment,spinner.getSelectedItemPosition(),user.getEmail(),k.substring(0,20));
            //send to database
            dr.child("posts").push().setValue(p);
            dismiss();

        }
    }
}