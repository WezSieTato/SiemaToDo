package com.wezsietato.todo;

import java.util.List;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class TasksActivity extends Activity {
    protected TaskerDbHelper db;
    List<Task> list;
    TaskListAdapter adapt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        db = new TaskerDbHelper(this);
        list = db.getAllTasks();
        adapt = new TaskListAdapter(this, R.layout.task_row, list, db);
        ListView listTask = (ListView) findViewById(R.id.listViewTasks);
        listTask.setAdapter(adapt);
    }

    public void addTaskNow(View v) {
        EditText t = (EditText) findViewById(R.id.editTextNewTask);
        String s = t.getText().toString();
        if (s.equalsIgnoreCase("")) {
            Toast.makeText(this, "enter the task description first!!",
            Toast.LENGTH_LONG);
        } else {
            Task task = new Task(s, false);
            db.addTask(task);
            Log.d("tasker", "data added");
            t.setText("");
            adapt.add(task);
            adapt.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tasks, menu);
        return true;
    }

}