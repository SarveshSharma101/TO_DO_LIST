package com.example.to_do_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Hashtable;

public class to_task extends AppCompatActivity {
    RecyclerView rc;
    ArrayList<Hashtable<String,String >> sub_task = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_task);
        Intent ig = getIntent();
        int task_id = ig.getIntExtra("task_id",0);
        String s = ig.getStringExtra("Task_title");
        TextView textView =findViewById(R.id.title);
        textView.setText(s);
        //Log.e("From totask------->",String.valueOf(task_id));

        getSub_task(task_id);
        rc = findViewById(R.id.task_rc);
        rc.setLayoutManager(new LinearLayoutManager(this));
        rc.setAdapter(new taskRV_adapter(this,sub_task,task_id));





        View v = findViewById(R.id.task_add_view);
        final Intent i =new Intent(this,add_newTask.class);
        i.putExtra("task_id",task_id);
        i.putExtra("Task_title",s);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
                to_task.this.finish();
            }
        });

        ImageView imageView = findViewById(R.id.task_add_img);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
                to_task.this.finish();
            }
        });
    }

    

    public ArrayList<Hashtable<String,String>> getSub_task(int task_id){
        Databse db = new Databse(this);
        Cursor cursor = db.getsubtask(task_id);
        while(cursor.moveToNext()){
            Hashtable<String, String> stdict = new Hashtable<>();
            String dt = cursor.getString(2)+"\n"+cursor.getString(3);
            stdict.put(cursor.getString(1),dt);
            sub_task.add(stdict);
        }

        return sub_task;
    }
}
