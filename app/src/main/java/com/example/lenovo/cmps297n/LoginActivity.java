package com.example.lenovo.cmps297n;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.cmps297n.Posts.CompanyWorker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
private EditText email,password;
private String semail,spassword,workerid="",name;
private ProgressDialog d;
private FirebaseAuth fba;
private FirebaseUser user;
private DatabaseReference dr;
private boolean quite = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        d = new ProgressDialog(this);
        fba = FirebaseAuth.getInstance();

        if(fba.getCurrentUser()!=null){
            Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(myIntent);
            finish();
        }

        Button button1 = (Button) findViewById(R.id.btn_login);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                login();

            }

        });
    }


    public void login(){
        d.setMessage("Loging in...");
        d.show();
        email = (EditText) findViewById(R.id.input_email);
        password = (EditText) findViewById(R.id.input_password);
        semail = email.getText().toString().trim();
        spassword = password.getText().toString().trim();

        if(semail.isEmpty()||spassword.isEmpty()){//login fails
            d.hide();
            Toast.makeText(LoginActivity.this,"Please Enter Email and Password",Toast.LENGTH_SHORT).show();
        }else{//logins successfully
            fba.signInWithEmailAndPassword(semail,spassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                 if(task.isSuccessful()){
                     user=fba.getCurrentUser();





                     Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
                     d.cancel();

                     startActivity(myIntent);
                     finish();

                 }   else{
                     d.cancel();
                     Toast.makeText(LoginActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                 }
                }
            });



        }

    }

    public void perform_action(View v){
        TextView button2 = (TextView) findViewById(R.id.link_signup);
        Intent myIntent = new Intent(LoginActivity.this,
                SignupActivity.class);
        startActivity(myIntent);
        finish();
    }
}
