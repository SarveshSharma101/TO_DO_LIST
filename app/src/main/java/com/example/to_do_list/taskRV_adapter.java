package com.example.to_do_list;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Hashtable;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class taskRV_adapter extends RecyclerView.Adapter<taskRV_adapter.taskRv_viewholder> {

    ArrayList<Hashtable<String,String >> sub_task;
    Context context;
    int task_id;

    public taskRV_adapter(@NonNull Context context, ArrayList<Hashtable<String, String>> sub_task, int task_id) {
        this.sub_task=sub_task;
        this.context = context;
        this.task_id = task_id;
    }

    @NonNull
    @Override
    public taskRv_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_item,parent,false);
        taskRv_viewholder taskRv_viewholder = new taskRv_viewholder(view, context,task_id);
        return taskRv_viewholder;
    }



    @Override
    public void onBindViewHolder(@NonNull taskRv_viewholder holder, int position) {

        String stask = (String) sub_task.get(position).keySet().toArray()[0];
        String dt = sub_task.get(position).get(stask);
        holder.t1.setText(dt);
        holder.t2.setText(stask);

    }

    @Override
    public int getItemCount() {
        return sub_task.size();
    }

    public  class taskRv_viewholder extends RecyclerView.ViewHolder{

        TextView t1;
        TextView t2;
        RelativeLayout rl;
        boolean f;
        int lmx = 0;
        float x,y;
        public taskRv_viewholder(@NonNull View itemView, final Context context,int task_id) {
            super(itemView);
            //context = context
            t1 = itemView.findViewById(R.id.task_date_time);
            t2 = itemView.findViewById(R.id.task);
            rl = itemView.findViewById(R.id.slide);
            //task_id = task_id;
            final Context finalContext = context;
            final int finalTask_id = task_id;
            t2.setOnLongClickListener(new View.OnLongClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public boolean onLongClick(View v) {
                    rl.setTranslationZ(100);
                    rl.setTranslationX(50);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(finalContext);
                    builder.setTitle("Did you completed the task ??");
                    builder.setPositiveButton("Yesss I did!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Databse db = new Databse(context);
                            int i= db.complete_task(t2.getText().toString());
                            if(i>0){
                                Toast.makeText(context,"Great!!!",Toast.LENGTH_SHORT).show();
                                to_task to_task = new to_task();
                                taskRV_adapter.this.sub_task.clear();
                                taskRV_adapter.this.sub_task =upatesubtask(finalTask_id);
                                taskRV_adapter.this.notifyDataSetChanged();

                            }else{
                                Toast.makeText(context,"error :(",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                    builder.setNegativeButton("No! not yet", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            rl.setTranslationZ(0);
                            rl.setTranslationX(0);
                            builder.setCancelable(true);
                        }
                    });
                    builder.show();
                    return true;
                }
            });



        }

        public ArrayList<Hashtable<String,String>> upatesubtask(int task_id){
            Databse db = new Databse(context);
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
}
