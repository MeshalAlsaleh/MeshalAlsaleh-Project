package com.example.meshalalsaleh_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

public class Firebase extends AppCompatActivity {
    private FirebaseDatabase myDatabase;
    private DatabaseReference myRef;
    EditText id, name, password, updateID, dataField, newData;
    String fID, fName, fPassword, fUpdateID, fDataField, fNewData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        id = (EditText) findViewById(R.id.id);
        name = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
        updateID = (EditText) findViewById(R.id.updateID);
        dataField = (EditText) findViewById(R.id.dataField);
        newData = (EditText) findViewById(R.id.newData);

        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        Button btnUpdate = (Button)findViewById(R.id.btnUpdate);
        Button btnView = (Button)findViewById(R.id.btnView);
        Button btnDelete = (Button)findViewById(R.id.btnDelete);
        Button btnBack = (Button)findViewById(R.id.btnBack2);

        myDatabase = FirebaseDatabase.getInstance();
        myRef = myDatabase.getReference("Meshal's Users");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // myDatabase = FirebaseDatabase.getInstance();
              //  myRef = myDatabase.getReference("Meshal's Users");

                try {
                    fID = id.getText().toString();
                    fName = name.getText().toString();
                    fPassword = password.getText().toString();

                    insertUser(fID, fName, fPassword);

                    id.getText().clear();
                    name.getText().clear();
                    password.getText().clear();
                }

                catch (Exception e) {
                    Toast.makeText(Firebase.this, e.toString(), Toast.LENGTH_LONG).show();
                }
            }

            private void insertUser(String fID, String fName, String fPassword) {
                User user = new User(fID, fName, fPassword);
                myRef.child(fID).setValue(user);
              // myRef.child("users").child(fID).setValue(user);
                //myRef.child(fID).setValue(user);

            }
        });



        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fID = id.getText().toString();
                if (TextUtils.isEmpty(fID)){
                    Toast.makeText(Firebase.this, "No ID is Found", Toast.LENGTH_LONG).show();
                }
                else delete(fID);
            }

            private void delete(String fID){

                myRef.child(fID).removeValue();

                Toast.makeText(Firebase.this, "User is Removed", Toast.LENGTH_LONG).show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fUpdateID = updateID.getText().toString();
                fDataField = dataField.getText().toString();
                fNewData = newData.getText().toString();

                updateData(fUpdateID, fDataField, fNewData);

            }

            private void updateData(String fUpdateID, String fDataField, String fNewData){
                myRef.child(fUpdateID).child(fDataField).setValue(fNewData);
                Toast.makeText(Firebase.this, "Updated", Toast.LENGTH_LONG).show();

            }
        });

        //when user press view he will be redirected to a new activity (list)
        //List class will have a refrence from the FireBase data
        //A method in the list class will add items to the array list

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Firebase.this, llist.class));
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Firebase.this, MainActivity.class));
            }
        });






    }


    @IgnoreExtraProperties
    public class User{
        public String fID;
        public String fName;
        public String fPassword;

        public User(){
            // constructor
        }
        public User(String fID, String fName, String fPassword){
            this.fID = fID;
            this.fName = fName;
            this.fPassword = fPassword;
        }
    }

}