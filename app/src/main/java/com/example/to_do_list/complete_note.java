package com.example.to_do_list;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class complete_note extends AppCompatActivity {

    private TextView textTitle, textNote;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_note);
        textTitle= findViewById(R.id.textTitle);
        textNote = findViewById(R.id.textNote);

        String i = getIntent().getStringExtra("Note_title");
        Databse db = new Databse(this);
        Cursor cursor = db.get_completeNote(i);

        while(cursor.moveToNext()){
            textTitle.setText(cursor.getString(1));
            textNote.setText(cursor.getString(2));
        }

        floatingActionButton = findViewById(R.id.editnote_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editnote();
            }
        });

    }
    @SuppressLint("RestrictedApi")
    public void  editnote(){
        final EditText editText = findViewById(R.id.enote);
        final Button b= findViewById(R.id.save_hide);
         b.setVisibility(View.VISIBLE);
        editText.setVisibility(View.VISIBLE);
        editText.setFocusable(true);
        floatingActionButton.setVisibility(View.GONE);
        textNote.setVisibility(View.GONE);
        editText.setText(textNote.getText().toString());
        final Databse db = new Databse(this);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int f = db.update_note(textTitle.getText().toString(), editText.getText().toString());
                if(f>0){
                    Toast.makeText(getBaseContext(),"Note Updated successfully", Toast.LENGTH_SHORT).show();
                    textNote.setText(editText.getText().toString());
                    textNote.setVisibility(View.VISIBLE);
                    floatingActionButton.setVisibility(View.VISIBLE);
                    editText.setVisibility(View.GONE);
                    b.setVisibility(View.GONE);



                }
                else{
                    Toast.makeText(getBaseContext(),"There was some Failure :(", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


}
