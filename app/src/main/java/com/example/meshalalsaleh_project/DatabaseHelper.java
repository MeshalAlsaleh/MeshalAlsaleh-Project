package com.example.meshalalsaleh_project;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper
{

    private static final String DATABASE_NAME = "database.db";
    public static final String TABLE_NAME = "data";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PASS = "password";

    // create database once this class is called
    public DatabaseHelper(@Nullable Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    //once database is created, create the table
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {

        sqLiteDatabase.execSQL
                (
                        "CREATE TABLE " + TABLE_NAME
                                + "("
                                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                                + COLUMN_NAME + " TEXT NOT NULL,"
                                + COLUMN_PASS +" TEXT NOT NULL"
                                + ")"
                );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void AddData(String id, String name ,String password)
    {
        // create instance to write to the database created earlier
        SQLiteDatabase db = this.getWritableDatabase();

        // insert values that user pass to the method into the table columns
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, id);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PASS, password);
        db.insert(TABLE_NAME, null, values);
    }

    public Cursor ViewList()
    {
        // create instance to write to the database created earlier
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor x = db.rawQuery("SELECT * FROM "+ TABLE_NAME, null);
        return x;

    }

    public Integer DeleteData(String id){

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete((TABLE_NAME), "ID = ?", new String[]{id});
    }

    public void dataUpdate(String id, String name){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COLUMN_NAME,name);
       // contentValues.put(COLUMN_PASS,password);

        db.update(TABLE_NAME,contentValues,"ID=?",new String[]{String.valueOf(id)});

    }
}
