package com.example.to_do_list;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Notes extends AppCompatActivity {

    private GridView gridView;
    private ArrayList<String> notes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        notes =get_notes();
        gridView = findViewById(R.id.notes_grid);
        final note_gadapter gda = new note_gadapter(this, notes);
        gridView.setAdapter(gda);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String note = notes.get(position);
                Intent i = new Intent(getBaseContext(),complete_note.class);
                i.putExtra("Note_title",note);
                startActivity(i);
            }
        });
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String note = notes.get(position);
                final Databse db = new Databse(getBaseContext());
                builder.setTitle("Do you want to delete this Note..!");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Integer i =db.delete_note(note);
                        if(i>0){
                            Toast.makeText(getBaseContext(),"Note deleted",Toast.LENGTH_SHORT).show();
                            gda.update_data(get_notes());
                            gda.notifyDataSetChanged();
                            gridView.setAdapter(gda);
                        }else
                        {
                            Toast.makeText(getBaseContext(),"Error occurred :(",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        builder.setCancelable(true);
                    }
                });
                builder.show();
                return true;
            }
        });

        FloatingActionButton fab =findViewById(R.id.notes_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),add_new_notes.class);
                Notes.this.finish();
                startActivity(i);
            }
        });
    }
    public ArrayList<String> get_notes(){
        ArrayList<String> notes1= new ArrayList<String>();
        Databse db =new Databse(this);
        Cursor cursor = db.get_noteTitle();
        while(cursor.moveToNext()){
            notes1.add(cursor.getString(0));
            //Log.e("Cursor................>",cursor.getString(0));
        }
        return notes1;
    }
}
