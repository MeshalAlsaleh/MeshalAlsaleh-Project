package com.example.meshalalsaleh_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class llist extends AppCompatActivity {

    List<String> arr= new ArrayList<String>();//store values
    ArrayAdapter<String> adapter;//connect values with ListView

    ListView lv;

    private FirebaseDatabase myDatabase;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        myDatabase = FirebaseDatabase.getInstance();
        myRef = myDatabase.getReference("Meshal's Users");

        lv=(ListView)findViewById(R.id.list_view);
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arr);
        lv.setAdapter(adapter);





        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("hell","im here");
                try {

                    for (DataSnapshot ds : snapshot.getChildren()) { //for each child in snapshot.getChildren = ds
                        String fID = ds.child("fID").getValue().toString();
                        Log.d("heaver", "im there");

                        String fName=ds.child("fName").getValue().toString();

                        String fPassword=ds.child("fPassword").getValue().toString();

                        String all="ID: +"+fID+"\nName: "+fName+"\nfPasswords: "+fPassword;


                        arr.add(all);
                        adapter.notifyDataSetChanged();

                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}