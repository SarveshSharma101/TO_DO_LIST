package com.example.to_do_list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class add_new_notes extends AppCompatActivity {
    private Button note_s, note_c;
    private EditText edit_note_title, edit_note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_notes);

        final Databse db = new Databse(this);
        edit_note_title = findViewById(R.id.edit_note_title);
        edit_note = findViewById(R.id.edit_note);
        note_s = findViewById(R.id.note_save);
        note_c = findViewById(R.id.note_cancel);

        final Intent i = new Intent(this,Notes.class);

        note_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Title = edit_note_title.getText().toString();
                String note = edit_note.getText().toString();
                boolean f = db.insert_newnote(Title,note);
                if (f){
                    Toast.makeText(getBaseContext(),"Note created successfully!!!",Toast.LENGTH_SHORT).show();

                    startActivity(i);
                    add_new_notes.this.finish();
                }else{
                    Toast.makeText(getBaseContext(),"error occurred :(",Toast.LENGTH_SHORT).show();

                }
            }
        });
        note_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
                add_new_notes.this.finish();
            }
        });
    }


}
