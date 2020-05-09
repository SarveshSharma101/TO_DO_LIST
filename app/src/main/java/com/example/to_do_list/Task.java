package com.example.to_do_list;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Hashtable;

public class Task extends AppCompatActivity {

    ArrayList<String> task= new ArrayList<>();
    final Databse db = new Databse(this);
    Hashtable<String, Integer> task0 = new Hashtable<>();
    task_gadapter gda;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);


       // Typeface tp = Typeface.createFromAsset(this.getAssets(),"font/PRISTINA.TTF");
       // Typeface tp1 = Typeface.createFromAsset(this.getAssets(),"font/Khinta_Script.ttf");

        TextView name = findViewById(R.id.name);
        //name.setTypeface(tp1);





        final GridView gd = findViewById(R.id.task_grid);
        task = get_task();
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog.Builder builder1 = new AlertDialog.Builder(this);

        gda = new task_gadapter(this, task);
        gd.setAdapter(gda);

        gd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int task_id = task0.get(task.get(position));
                Log.e("Value of id ----->",String.valueOf(task_id));
                Intent i =new Intent(getBaseContext(),to_task.class);
                i.putExtra("Task_title", task.get(position));
                i.putExtra("task_id",task_id);

                startActivity(i);

            }
        });

        gd.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                builder1.setTitle("Do you want to delete this task header?\n All the sub-task will be deleted!!!!");
                builder1.setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int task_id = task0.get(task.get(position));
                        Integer i =db.delete_task(task.get(position), task_id);
                        if(i>0){
                        Toast.makeText(getBaseContext(),"Task deleted",Toast.LENGTH_SHORT).show();
                        gda.update_data(get_task());
                        gda.notifyDataSetChanged();
                        gd.setAdapter(gda);
                    }else
                    {
                        Toast.makeText(getBaseContext(),"Error",Toast.LENGTH_SHORT).show();
                    }
                    }
                });
                builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                         builder1.setCancelable(true) ;
                    }
                });
                builder1.show();
                return true;
            }
        });


        final String[] m_Text = {""};

        FloatingActionButton fab =findViewById(R.id.task_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText input = new EditText(getBaseContext());
                builder.setView(input);
                builder.setTitle("Create a new Task header");
                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text[0] = input.getText().toString();
                        //task.add(input.getText().toString());
                        input.setText("");
                        insert(m_Text[0]);
                        //Log.e("--------->",task.get(0)+task.get(1)+task.get(2)+task.get(3)+task.get(4));
                        //Toast.makeText(getBaseContext(), m_Text[0],Toast.LENGTH_SHORT).show();
                        task =get_task();
                        gda.update_data(task);
                        gda.notifyDataSetChanged();
                        gd.setAdapter(gda);

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
                //Toast.makeText(getBaseContext(),"Yet to complete.....",Toast.LENGTH_SHORT).show();
            }
        });




    }

    public void insert(String value){
        boolean f = db.insert_newtask(value);
        if(f){
            Toast.makeText(this,"New Task header created successfully!!!!",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"Some error occurred :(",Toast.LENGTH_SHORT).show();

        }
    }

    public ArrayList<String> get_task(){
        ArrayList<String> task1= new ArrayList<String>();
        //Hashtable<String ,Integer> task2= new Hashtable<>();
        Databse db =new Databse(this);
        Cursor cursor = db.get_Tasks();
        while(cursor.moveToNext()){
            task1.add(cursor.getString(1));
            task0.put(cursor.getString(1),cursor.getInt(0));
            //Log.e("Cursor................>",cursor.getString(1));
        }
        return task1;
    }

    @Override
    protected void onResume() {
        super.onResume();
        gda.update_data(get_task());
        gda.notifyDataSetChanged();
    }
}

