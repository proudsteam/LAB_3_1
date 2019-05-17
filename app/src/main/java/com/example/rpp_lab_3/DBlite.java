package com.example.rpp_lab_3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBlite extends SQLiteOpenHelper {

    public static final int VERSION = 2;

    public static final String DATABASE_NAME = "Lab_3";
    public static final String TABLE_NAME = "Students";

    public static final String KEY_ID = "_id";
    public static final String KEY_FIO = "FIO";
    public static final String KEY_DATE= "Date";


    public DBlite( Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(" + KEY_ID
                + " integer primary key," + KEY_FIO + " text," + KEY_DATE + " text" + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("create table " + "Student2" + " (id integer primary key, family text, name text, secondName text, " + KEY_DATE + " text);"  );

        Cursor cursor = db.query(TABLE_NAME,new String[] {"FIO,DATE"}, null,null,null,null,null);
        ContentValues cv = new ContentValues();
        int fioIndex = cursor.getColumnIndex(KEY_FIO);
        int DateIndex = cursor.getColumnIndex(KEY_DATE);
        if (cursor.getCount()!=0) {
            cursor.moveToFirst();
            do {

                String date = cursor.getString(DateIndex);
                String fioLine = cursor.getString(fioIndex);
                String fields[] = fioLine.split(" ");
                cv.put("family",fields[0]);
                cv.put("name",fields[1]);
                cv.put("secondName",fields[2]);
                cv.put("Date",date);
                db.insert("Student2",null,cv);



            } while (cursor.moveToNext());

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            db.execSQL("ALTER TABLE Student2 RENAME to Student");
        }
        cursor.close();
    }
}
