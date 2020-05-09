package com.example.to_do_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Databse db = new Databse(this);
    TextView no_task ;
    TextView no_note ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        no_task = findViewById(R.id.no_task);
        no_note = findViewById(R.id.no_note);
        //Typeface tp = Typeface.createFromAsset(this.getAssets(),"font/PRISTINA.TTF");
        //Typeface tp1 = Typeface.createFromAsset(this.getAssets(),"font/Khinta_Script.ttf");
        TextView textView =findViewById(R.id.textView);
        //textView.setTypeface(tp);
        Task task = new Task();
        Cursor ct = db.get_Tasks();
        Cursor cn = db.get_noteTitle();
        //Log.e("Count--------->>>", String.valueOf(ct.getCount()));


        //no_note.setTypeface(tp1);
        no_note.setText(String.valueOf(cn.getCount())+"  notes");

        no_task.setText(String.valueOf(ct.getCount())+"  task");
        //no_task.setTypeface(tp1);

        CardView cd = findViewById(R.id.to_do_card);

        cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),Task.class);
                startActivity(i);
            }
        });


        CardView cdnote = findViewById(R.id.notecard);

        cdnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),Notes.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Cursor ct = db.get_Tasks();
        Cursor cn = db.get_noteTitle();
        //no_note.setTypeface(tp1);
        no_note.setText(String.valueOf(cn.getCount())+"  notes");

        no_task.setText(String.valueOf(ct.getCount())+"  task");
    }
}
