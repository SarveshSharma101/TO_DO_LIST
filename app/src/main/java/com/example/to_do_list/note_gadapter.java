package com.example.to_do_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class note_gadapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> notes;
    public note_gadapter(Context context, ArrayList<String> notes){
        this.context = context;
        this.notes=notes;
    }

    public void update_data(ArrayList<String> notes){
        this.notes=notes;
    }
    @Override
    public int getCount() {
        return notes.size();
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
        View v;

        if(convertView== null){
            convertView = inflater.inflate(R.layout.notes_items,null);



        }
        TextView textView = convertView.findViewById(R.id.note_title);
        String s = notes.get(position);
        textView.setText(s);

        return convertView;
    }
}
