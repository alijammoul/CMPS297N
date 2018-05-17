package com.example.lenovo.cmps297n;

/**
 * Created by Lenovo on 25/11/2017.
 */


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.lenovo.cmps297n.Posts.PostInfo;
import com.example.lenovo.cmps297n.Posts.RecyclerAdapter;
import com.example.lenovo.cmps297n.Posts.Warning;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class WarningFragment extends Fragment {
    ArrayList<String> list;
    ListView lv;

    View vv;
    FirebaseAuth fba;
    FirebaseUser user;
    DatabaseReference dr;

    public WarningFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        list = new ArrayList<>();
        FillList();
        // Inflate the layout for this fragment
        vv = inflater.inflate(R.layout.fragment_warning, container, false);

       // lv = inflater.inflate(R.layout.activity_check, container, false).findViewById(R.id.list);
        lv = (ListView) vv.findViewById(R.id.list);

        ArrayAdapter<String> allItemsAdapter = new ArrayAdapter<>(getActivity().getBaseContext(), android.R.layout.simple_expandable_list_item_1, list);
            lv.setAdapter(allItemsAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("click", "    " + i);
            }
        });


        return vv;

    }

    private void FillList() {
        fba = FirebaseAuth.getInstance();
        dr = FirebaseDatabase.getInstance().getReference();
        user = fba.getCurrentUser();
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot posts : dataSnapshot.child("warnings").getChildren()) {
                    Warning p = posts.getValue(Warning.class);
                    list.add(p.getSeverity() + " " + p.getToEmployee());

                    // text.setText(p.getContent());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}