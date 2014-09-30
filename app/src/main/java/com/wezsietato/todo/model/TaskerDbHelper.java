package com.wezsietato.todo.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WezSieTato on 24.09.2014.
 */
public class TaskerDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "taskerManager";

    // tables name
    private static final String TABLE_TASKS = "tasks";
    private static final String TABLE_NOTES = "notes";


    // tasks Table Columns names
    private static final String KEY_TASK_ID = "id";
    private static final String KEY_TASK_NAME = "taskName";
    private static final String KEY_TASK_STATUS = "status";

    // notes Table Columns name
    private static final String KEY_NOTE_ID = "noteId";
    private static final String KEY_NOTE_NAME = "noteName";

    public TaskerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_TASKS + " ( "
                + KEY_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_TASK_NAME + " TEXT, "
                + KEY_TASK_STATUS + " INTEGER,"
                + KEY_NOTE_ID + " INTEGER"
                + ")";
        sqLiteDatabase.execSQL(sql);

        sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NOTES + " ( "
                + KEY_NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NOTE_NAME + " TEXT "
                + ")";
        sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);

        onCreate(sqLiteDatabase);
    }

    public void addNote(Note note){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOTE_NAME, note.getName());
        db.insert(TABLE_NOTES, null, values);
        db.close();
    }

    public List<Note> getAllNotes(){
        List<Note> noteList = new ArrayList<Note>();

        String selectQuery = "SELECT * FROM " + TABLE_NOTES;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {
                Note note = new Note();
                note.setId(cursor.getInt(0));
                note.setName(cursor.getString(1));
                note.setTasks(getTasksByNote(note));
                noteList.add(note);
            } while (cursor.moveToNext());
        }

        return noteList;
    }

    public Note getNoteById(int id){
        SQLiteDatabase db = getWritableDatabase();
        String selection =  KEY_NOTE_ID + " = ?";
        String[] selectionArgs = new String[] { String.valueOf(id)};
        Cursor cursor = db.query
                (TABLE_NOTES, null, selection, selectionArgs, null, null, null);

        if(cursor.moveToFirst()){
            Note note = new Note();
            note.setId(cursor.getInt(0));
            note.setName(cursor.getString(1));
            note.setTasks(getTasksByNote(note));
            return note;
        } else
            return null;
    }

    public void addTask(Task task){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TASK_NAME, task.getName());
        values.put(KEY_TASK_STATUS, task.getStatus());
        values.put(KEY_NOTE_ID, task.getNote().getId());

        db.insert(TABLE_TASKS, null, values);
        db.close();
    }

    public List<Task> getTasksByNote(Note note){
        List<Task> taskList = new ArrayList<Task>();

//        String selectQuery = "SELECT * FROM " + TABLE_TASKS;

        String selection = KEY_NOTE_ID + " = ?";
        String[] selectionArgs = new String[] {String.valueOf(note.getId())};
        String[] projection = null;

        String sortOrder = null;

        SQLiteDatabase db = getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
        Cursor cursor = db.query
                (TABLE_TASKS, projection, selection, selectionArgs, sortOrder, null, null);

        if(cursor.moveToFirst()){
            do {
                Task task = new Task();
                task.setId(cursor.getInt(0));
                task.setName(cursor.getString(1));
                task.setStatus(cursor.getInt(2) == 1);
                task.setNote(note);
                taskList.add(task);
            } while (cursor.moveToNext());
        }

        return taskList;
    }

    public void updateTask(Task task){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TASK_NAME, task.getName());
        values.put(KEY_TASK_STATUS, task.getStatus());

        db.update(TABLE_TASKS, values, KEY_TASK_ID + " = ?",
                new String[]{String.valueOf(task.getId())}
                );
    }

    public void deleteTask(Task task){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_TASKS, KEY_TASK_ID + "=" + task.getId(), null);
    }
}
