package com.wezsietato.todo.taskview;

import java.util.List;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fortysevendeg.swipelistview.SwipeListView;
import com.wezsietato.todo.R;
import com.wezsietato.todo.model.Note;
import com.wezsietato.todo.model.Task;
import com.wezsietato.todo.model.TaskerDbHelper;

public class TasksActivity extends Activity {
    private TaskerDbHelper db;
    private TaskListAdapter adapt;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        db = new TaskerDbHelper(this);
        int noteId = getIntent().getIntExtra("Key_note", 0);
        note = db.getNoteById(noteId);

        List<Task> list = note.getTasks();
        SwipeListView listTask = (SwipeListView) findViewById(R.id.listViewTasks);
        adapt = new TaskListAdapter(this, R.layout.task_row, list, db, listTask);
        listTask.setAdapter(adapt);

        setTitle(note.getName());

    }

    public void addTaskNow(View v) {
        EditText t = (EditText) findViewById(R.id.editTextNewTask);
        String s = t.getText().toString();
        if (s.equalsIgnoreCase("")) {
            Toast.makeText(this, "enter the task description first!!",
            Toast.LENGTH_LONG);
        } else {
            Task task = new Task(s, false);
            task.setNote(note);
            db.addTask(task);
            Log.d("tasker", "data added");
            t.setText("");
            adapt.add(task);
            adapt.notifyDataSetChanged();
            SwipeListView listTask = (SwipeListView) findViewById(R.id.listViewTasks);
            listTask.closeOpenedItems();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tasks, menu);
        return true;
    }

}