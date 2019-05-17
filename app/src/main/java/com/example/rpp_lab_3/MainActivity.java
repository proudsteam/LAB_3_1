package com.example.rpp_lab_3;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    // Database
    DBlite dBlite;
    SQLiteDatabase database;


    // View
    Button replace;
    Button add;
    Button showTable;
    EditText fioET ;

    ArrayList<String> fioList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dBlite = new DBlite(this);
        database = dBlite.getWritableDatabase();
        if(dBlite.VERSION == 2)
        {
            dBlite.onUpgrade(database,1,2);
        }

        fioList = new ArrayList<>();

        replace = findViewById(R.id.replace_button);
        add = findViewById(R.id.add_button);
        showTable = findViewById(R.id.show_button);

        fioET = findViewById(R.id.fio_et);

        //database.delete(dBlite.TABLE_NAME, null, null);

        inputList();

        // Listners
        addClick();
        showClick();
        replaceClick();
    }

    private void replaceClick() {
        replace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date now = new Date();
                Cursor cursor = database.query(dBlite.TABLE_NAME, null, null, null, null, null, null);

                String fio = fioET.getText().toString();
                String date = now.toString();
                String id = "";
                if (cursor.moveToLast()) {
                      id = cursor.getString(cursor.getColumnIndex(DBlite.KEY_ID));
                }

                ContentValues contentValues = new ContentValues();

                contentValues.put(dBlite.KEY_FIO, fio);
                contentValues.put(dBlite.KEY_DATE, date);

                database.update(dBlite.TABLE_NAME,contentValues,dBlite.KEY_ID + "= ?", new String[] {id});
            }
        });
    }

    private void showClick() {
        showTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DbInfoActivity.class);
                startActivity(intent);
            }
        });

    }

    private void addClick() {

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date now = new Date();

                String fio = fioET.getText().toString();
                String date = now.toString();

                ContentValues contentValues = new ContentValues();

                contentValues.put(dBlite.KEY_FIO, fio);
                contentValues.put(dBlite.KEY_DATE, date);

                database.insert(dBlite.TABLE_NAME, null, contentValues);

            }
        });
    }

    private void inputList() {

        Date now = new Date();
        String[] names = {"Иванов Иван Иванович", "Петров Петр Петрович", "Сергеев Сергей Александрович", "Александров Василий Петрович", "Сидоров Генадий Васильевич"};
        ContentValues contentValues = new ContentValues();

        for (int i = 0; i < names.length; i++) {
            if (dBlite.VERSION == 1) {
                Random random = new Random();
                fioList.add(names[random.nextInt(names.length)]);

                String fio = fioList.get(i);
                String date = now.toString();


                contentValues.put(dBlite.KEY_FIO, fio);
                contentValues.put(dBlite.KEY_DATE, date);
            }
            if (dBlite.VERSION == 2) {
                String mainInfo = names[new Random().nextInt(names.length)];
                String fields[] = mainInfo.split(" ");
                contentValues.put("family", fields[0]);
                contentValues.put("name", fields[1]);
                contentValues.put("secondName", fields[2]);
            }
            database.insert(dBlite.TABLE_NAME, null, contentValues);
        }
    }
}
