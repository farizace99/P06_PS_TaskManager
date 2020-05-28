package com.myapplicationdev.android.p06_ps_taskmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VER = 1;
    private static final String DATABASE_NAME = "tasks.db";
    private static final String TABLE_TASK = "task";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TASK_NAME = "task_name";
    private static final String COLUMN_TASK_DESC = "task_description";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSql = "CREATE TABLE " + TABLE_TASK + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TASK_NAME + " TEXT,"
                + COLUMN_TASK_DESC + " TEXT )";
        db.execSQL(createTableSql);
        Log.i("info", "Created tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE " + TABLE_TASK + " ADD COLUMN module_name TEXT ");
    }

    public long insertTask(String task, String desc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_NAME, task);
        values.put(COLUMN_TASK_DESC, desc);
        long a = db.insert(TABLE_TASK, null, values);
        db.close();

        if (a == -1) {
            Log.d("DBHelper", "Insert failed");
        } else {
            Log.d("SQL Insert", "ID:" + a);
        }
        return a;
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> notes = new ArrayList<Task>();

        String selectQuery = "SELECT " + COLUMN_ID + ", "
                + COLUMN_TASK_NAME + ", "
                + COLUMN_TASK_DESC
                + " FROM " + TABLE_TASK;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String taskContent = cursor.getString(1);
                String descContent = cursor.getString(2);
                Task note = new Task(id, taskContent, descContent);
                notes.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notes;
    }

    public int updateTask(Task data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_NAME, data.getName());
        values.put(COLUMN_TASK_DESC, data.getDescription());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_TASK, values, condition, args);
        db.close();
        return result;
    }

    public int deleteTask(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_TASK, condition, args);
        db.close();
        return result;
    }
}