package com.example.lenovo.cmps297n;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
//this fragment pops up when the user wants to check in or out from the company.
public class CheckFragment extends Fragment implements View.OnClickListener {
View vv;
private Button b,b2;
TextView t,l,textt;
ImageView c1,c2,l1,l2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater lf, ViewGroup v,Bundle bundle){
        vv= lf.inflate(R.layout.activity_check,v,false);

        c1 = (ImageView) lf.inflate(R.layout.activity_check, v, false).findViewById(R.id.c1);


        l1 = (ImageView) lf.inflate(R.layout.activity_check, v, false).findViewById(R.id.l1);
        c2 = (ImageView) lf.inflate(R.layout.activity_check, v, false).findViewById(R.id.c2);


        l2 = (ImageView) lf.inflate(R.layout.activity_check, v, false).findViewById(R.id.l2);
        textt = (TextView) vv.findViewById(R.id.email);
        t = (TextView) vv.findViewById(R.id.status);
        l=(TextView)vv.findViewById(R.id.loc) ;
        c2=(ImageView)vv.findViewById(R.id.c2);
        l2=(ImageView)vv.findViewById(R.id.l2);
       // b2.setVisibility(vv.INVISIBLE);

return vv;
    }

public void setname(String name,String type){
    textt.setTextColor(Color.BLUE);
        textt.setText(name +"  [ Company "+type+" ]");
}
public void Her(boolean setme){

        if(setme==true){
            t.setTextColor(Color.GREEN);
            t.setText("You are Checked in");
        }else{
            t.setTextColor(Color.RED);
            t.setText("You are Checked out");
        }

}

    public boolean Here(double lati,double longi,boolean checkin){
Log.d("checkin in here"," "+checkin);

if(checkin){
    t.setTextColor(Color.GREEN);
    t.setText("You are Checked in");

}else if(!checkin){
    t.setTextColor(Color.RED);
    t.setText("You are Checked out");
}
boolean condition;

        if(/*lati==338&&longi ==354*/true){
            l.setTextColor(Color.GREEN);
            l.setText("Location Valid for Check Ins/Outs");
            l2.setBackgroundResource(R.drawable.cor);
           // b2.setVisibility(vv.VISIBLE);
condition = true;

        }else{
            l.setTextColor(Color.RED);
            l.setText("Location Invalid for Check Ins/Outs");
            l2.setBackgroundResource(R.drawable.err);
           // b2.setVisibility(vv.VISIBLE);
condition=false;
        }
        return condition;
}
    @Override
    public void onClick(View view) {
        Log.d("frag","button click outside");
    }
}
