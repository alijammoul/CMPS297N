package com.example.lenovo.cmps297n;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    private EditText name,email,password,password2;
    private String sname,semail,spassword,spassword2;
    private ProgressDialog d;
    private FirebaseAuth fba;
    FirebaseUser user;
    private Spinner spinner;
    DatabaseReference dr;
    private int job=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(spinner.getContext(),
                R.array.job, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        d=new ProgressDialog(this);
        fba = FirebaseAuth.getInstance();
        dr = FirebaseDatabase.getInstance().getReference();
        Button button1 = (Button) findViewById(R.id.btn_signup);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                d.setMessage("Creating Account... :/");
                d.show();
                Signup();

            }

        });
    }

    public void Signup(){

        name=(EditText) findViewById(R.id.input_name) ;
        email = (EditText) findViewById(R.id.input_email);
        password = (EditText) findViewById(R.id.input_password);
        password2 = (EditText) findViewById(R.id.input_password2);
        sname = name.getText().toString().trim();
        semail = email.getText().toString().trim();
        spassword = password.getText().toString().trim();
        spassword2= password2.getText().toString().trim();
        job=spinner.getSelectedItemPosition();


        if(semail.isEmpty()||spassword.isEmpty()||sname.isEmpty()||spassword2.isEmpty()){//signup fails
            d.hide();
            Toast.makeText(SignupActivity.this,"Please Enter all input",Toast.LENGTH_SHORT).show();
        }else{//signs successfully
            if(spassword2.equals(spassword)&&spassword.length()>6) {

                fba.createUserWithEmailAndPassword(semail,spassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        CompanyWorker m = new CompanyWorker(sname,semail,false);
                        if(task.isSuccessful()){
                            if(job==0){
                                dr.child("company").child("managers").push().setValue(m);
                            }else if(job==1){
                                dr.child("company").child("employees").push().setValue(m);
                            }

                            d.cancel();
                            Toast.makeText(SignupActivity.this,"Account Created Successfully",Toast.LENGTH_SHORT).show();
                            fba.signOut();
                            //add logout code
                            Intent myIntent = new Intent(SignupActivity.this, LoginActivity.class);
                            startActivity(myIntent);
                            finish();
                        }else{
                            d.cancel();
                            Toast.makeText(SignupActivity.this,"Failed to Register Account",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else{
                d.hide();
                Toast.makeText(SignupActivity.this,"Passwords(must be > 6) dont match",Toast.LENGTH_SHORT).show();
            }
        }

    }
    public void perform_action(View v){
        TextView button2 = (TextView) findViewById(R.id.link_login);
        Intent myIntent = new Intent(SignupActivity.this,LoginActivity.class);
        startActivity(myIntent);
        finish();
    }
}