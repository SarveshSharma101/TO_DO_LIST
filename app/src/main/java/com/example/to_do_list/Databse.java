package com.example.to_do_list;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Databse extends SQLiteOpenHelper {

    private static final String database_name = "to_do.db";
    private static final String task_table = "Task_manager";
    private static final String note_table = "note_manager";
    private static final String subtask_tabble = "subtask_Manager";
    private static final String task_c1 = "task_id";
    private static final String task_c2 = "task_Title";
    private static final String subtask_c1 = "task";
    private static final String subtask_c2 = "date";
    private static final String subtask_c3 = "time";
    private static final String subtask_c4 = "task_id";
    private static final String note_c1 = "note_id";
    private static final String note_c2 = "note_Title";
    private static final String note_c3 = "note";

    public Databse(@Nullable Context context) {
        super(context,database_name,null,1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+task_table+"(task_id integer primary key autoincrement, task_Title varchar(20))");
        db.execSQL("create table "+note_table+"(note_id integer primary key autoincrement, note_Title varchar(20), note varchar(500))");
        db.execSQL("create table "+subtask_tabble+"(task_id integer, task varchar(500), date varchar(20),time varchar(20), foreign key(task_id) references Task_manager(task_id))");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+task_table);
        db.execSQL("drop table if exists "+note_table);
        db.execSQL("drop table if exists "+subtask_tabble);

        onCreate(db);
    }


    public boolean insert_newtask(String Task_title){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues cv =new ContentValues();
        cv.put(task_c2,Task_title);

        long result = db.insert(task_table,null,cv);

        if(result ==-1){
            return false;
        }
        else{ return true; }
    }
    public boolean insert_newnote(String note_title, String Note){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues cv =new ContentValues();
        cv.put(note_c2,note_title);
        cv.put(note_c3,Note);

        long result = db.insert(note_table,null,cv);

        if(result ==-1){
            return false;
        }
        else{ return true; }
    }

    public boolean insert_newsubtask(String task,String date, String time, int task_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(subtask_c1,task);
        cv.put(subtask_c2,date);
        cv.put(subtask_c3,time);
        cv.put(subtask_c4,task_id);

        long result = db.insert(subtask_tabble,null,cv);
        if(result ==-1){
            return false;
        }
        else{ return true; }

    }




    public Cursor get_Tasks(){
        SQLiteDatabase db =this.getWritableDatabase();

        Cursor cursor= db.rawQuery("select * from "+task_table,null);
        return cursor;
    }


    public Cursor getsubtask(int task_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+subtask_tabble+" where task_id=='"+task_id+"'",null);

        return cursor;
    }

    public Cursor get_noteTitle(){
        SQLiteDatabase db =this.getWritableDatabase();

        Cursor cursor= db.rawQuery("select "+ note_c2+" from "+note_table,null);
        return cursor;
    }

    public  Cursor get_completeNote(String s){
        SQLiteDatabase db =this.getWritableDatabase();


        Cursor cursor = db.rawQuery("select * from "+note_table+" where note_Title=='"+s.toString()+"'",null);
        return cursor;
    }







    public Integer delete_note(String title){
        SQLiteDatabase db= this.getWritableDatabase();
        return db.delete(note_table,"note_Title=?",new String[]{title});
    }

    public Integer delete_task(String title, int task_id){
        SQLiteDatabase db= this.getWritableDatabase();
        int i = delete_stask(task_id);

        return db.delete(task_table,"task_Title=?",new String[]{title});

    }
    public Integer delete_stask(int task_id){
        SQLiteDatabase db= this.getWritableDatabase();
        return db.delete(subtask_tabble,"task_id=?",new String[]{String.valueOf(task_id)});
    }

    public Integer complete_task(String task_name){
        SQLiteDatabase db= this.getWritableDatabase();
        return db.delete(subtask_tabble,"task=?",new String[]{task_name});
    }

    public Integer update_note(String note_title, String s){

        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues cv =new ContentValues();
        cv.put(note_c2,note_title);
        cv.put(note_c3,s);
        int i =db.update(note_table,cv,"note_Title= ?",new String[]{note_title});
        return i;
    }
}
