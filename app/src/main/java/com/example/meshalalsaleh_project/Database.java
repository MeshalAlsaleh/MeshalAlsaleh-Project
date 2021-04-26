package com.example.meshalalsaleh_project;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.os.Build.ID;

public class Database extends AppCompatActivity {
    DatabaseHelper myDB;
    EditText id, name, password;
    LinearLayout updatingLayout;
    private FirebaseDatabase myDatabase;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        Button btnAdd = (Button) findViewById(R.id.btnAddData);
        Button btnView = (Button) findViewById(R.id.btnViewData);
        Button btnDelete = (Button) findViewById(R.id.btnDeleteData);
        Button btnUpdate = (Button) findViewById(R.id.btnUpdateData);
        Button btnBack = (Button) findViewById(R.id.btnBack3);
        Button btnFetch = (Button) findViewById(R.id.btnfetch);

        myDB = new DatabaseHelper(this);

        id = (EditText) findViewById(R.id.dataID);
        name = (EditText) findViewById(R.id.dataName);
        password = (EditText) findViewById(R.id.dataPassword);

        myDatabase = FirebaseDatabase.getInstance();
        myRef = myDatabase.getReference("Meshal's Users");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myDB.AddData(id.getText().toString(), name.getText().toString(), password.getText().toString());

                Toast.makeText(Database.this, "Data Added", Toast.LENGTH_LONG).show();

                id.getText().clear();
                name.getText().clear();
                password.getText().clear();


            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor cur = myDB.ViewList();
                StringBuffer buffer = new StringBuffer();
                Toast.makeText(Database.this, "Item Viewed", Toast.LENGTH_LONG).show();

                while (cur.moveToNext()){

                    buffer.append("id: "+ cur.getString(0)+ "\n");
                    buffer.append("Name: "+ cur.getString(1)+ "\n");
                    buffer.append("password: "+ cur.getString(2)+ "\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder( Database.this);
                builder.setCancelable(true);
                builder.setTitle("Data");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myDB.DeleteData(id.getText().toString());
                Toast.makeText(Database.this, "Item Deleted", Toast.LENGTH_LONG).show();

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDB.dataUpdate(id.getText().toString(),name.getText().toString());
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Database.this, MainActivity.class));
            }
        });

        btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        try {
                            for (DataSnapshot ds : snapshot.getChildren()) {

                                String name = ds.child("fName").getValue().toString();

                                String password=ds.child("fPassword").getValue().toString();

                                String id=ds.child("fID").getValue().toString();

                                myDB.AddData(id,name,password);
                            }

                        }

                        catch (Exception e) {
                            Log.d("Meshal", "for loop exception " + e.toString());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }
}