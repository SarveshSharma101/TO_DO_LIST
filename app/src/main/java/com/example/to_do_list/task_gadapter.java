package com.example.to_do_list;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class task_gadapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> task ;
    Typeface tp1 ;

    public  task_gadapter(Context context, ArrayList<String> task){
        this.context = context;
        this.task = task;
        tp1= Typeface.createFromAsset(context.getAssets(),"font/Khinta_Script.ttf");
    }
    public void update_data(ArrayList<String> task){
        this.task = task;
    }
    @Override
    public int getCount() {
        return task.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        if(convertView== null){

            if(true){
                convertView = inflater.inflate(R.layout.task_title_item,null);

            }

        }
        TextView textView = convertView.findViewById(R.id.task_title);
        String s = task.get(position);
        //textView.setTypeface(tp1);
        //Log.e("}}}}}}}}}}}}}",String.valueOf(position));
        textView.setText(s);

        return convertView;
    }


}
