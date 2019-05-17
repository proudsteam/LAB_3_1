package com.example.rpp_lab_3;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class DbInfoActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    rvAdapter rvAdapter;

    DBlite dBlite;
    SQLiteDatabase database;

    ArrayList<Students> studentsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_info);

        dBlite = new DBlite(this);
        database = dBlite.getWritableDatabase();


        studentsList = new ArrayList<>();
        recyclerView = findViewById(R.id.info_rv);
        rvAdapter = new rvAdapter(this, studentsList);
        recyclerView.setAdapter(rvAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        inputList();

    }

    public void inputList()
    {
        Cursor cursor = database.query(dBlite.TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int id = cursor.getColumnIndex(dBlite.KEY_ID);
            int family = cursor.getColumnIndex("family");
            int name = cursor.getColumnIndex("name");
            int secondName = cursor.getColumnIndex("secondName");
            int date = cursor.getColumnIndex(dBlite.KEY_DATE);
            do {
                Students student = new Students(cursor.getString(id), cursor.getString(family),cursor.getString(name),cursor.getString(secondName), cursor.getString(date));
                studentsList.add(student);
                rvAdapter.notifyDataSetChanged();
            } while (cursor.moveToNext());
        } else
            Log.d("TimLog","0 rows");
        cursor.close();
    }
}
