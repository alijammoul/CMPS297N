package com.example.lenovo.cmps297n.Posts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.cmps297n.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Lenovo on 17/11/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private Context c;
    private List<PostInfo> list ;

    public RecyclerAdapter(Context c, List<PostInfo> list) {
        this.c = c;
        this.list = list;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {

PostInfo p = list.get(position);
switch(p.getPosition()){
    case 0: holder.shape.setBackgroundResource(R.drawable.fun);
    break;
    case 1: holder.shape.setBackgroundResource(R.drawable.attention);
    break;
    case 2: holder.shape.setBackgroundResource(R.drawable.complaint);
    break;
}

        holder.complaint.setText(p.getComplaint());
        holder.user.setText(p.getUser());
        holder.time.setText(p.getDate());



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView complaint;
        TextView user,time;
        ImageView shape;
        public ViewHolder(View itemView) {
            super(itemView);
            shape = (ImageView)itemView.findViewById(R.id.shape);
            time = (TextView)itemView.findViewById(R.id.time);

            complaint =(TextView)itemView.findViewById(R.id.complaint);
            user=(TextView)itemView.findViewById(R.id.user);
        }
    }
}
