package com.example.to_do_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class add_newTask extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);
        Intent i = getIntent();
        final int task_id = i.getIntExtra("task_id",0);
        final TextView set_date = findViewById(R.id.task_date);
        final Context context =this;

        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        String s = DateFormat.getDateInstance().format(c.getTime());
        //set_date.setText(String.valueOf(mDay)+'/'+String.valueOf(mMonth)+'/'+String.valueOf(mYear));
        set_date.setText(s);
        set_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int y = calendar.get(Calendar.YEAR);
                int m = calendar.get(Calendar.MONTH);
                int d = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        String cds= DateFormat.getDateInstance().format(calendar.getTime());
                        set_date.setText(cds);
                    }
                },y,m, d);

                datePickerDialog.show();
            }
        });
        final int minute ,hour;
        minute = c.get(Calendar.MINUTE);
        hour = c.get(Calendar.HOUR_OF_DAY);
        String time = hour+" :"+minute;
        final TextView set_time = findViewById(R.id.textClock);
        set_time.setText(time);

        set_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        set_time.setText(hourOfDay+":"+minute);
                    }
                },hour, minute, false);
                timePickerDialog.show();
            }
        });

        final Databse db = new Databse(this);
        final EditText task = findViewById(R.id.edit_task);

        Button save = findViewById(R.id.task_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean f = db.insert_newsubtask(task.getText().toString(),set_date.getText().toString(),set_time.getText().toString(),task_id);
                if(f){
                    Toast.makeText(getBaseContext(),"Task added to Your List successfully!!!",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getBaseContext(),to_task.class);
                    i.putExtra("task_id",task_id);
                    i.putExtra("Task_title",getIntent().getStringExtra("Task_title"));
                    startActivity(i);
                    add_newTask.this.finish();
                }else{
                    Toast.makeText(getBaseContext(),"Error occurred :(",Toast.LENGTH_SHORT).show();

                }

            }
        });


        Button cancel =findViewById(R.id.task_cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),to_task.class);
                i.putExtra("task_id",task_id);
                startActivity(i);
                add_newTask.this.finish();
            }
        });

    }


}
