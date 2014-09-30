package com.wezsietato.todo.noteview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.fortysevendeg.swipelistview.SwipeListViewListener;
import com.wezsietato.todo.R;
import com.wezsietato.todo.model.Note;
import com.wezsietato.todo.model.TaskerDbHelper;
import com.wezsietato.todo.taskview.TaskListAdapter;
import com.wezsietato.todo.taskview.TasksActivity;

import java.util.List;

public class NotesActivity extends Activity {
    protected TaskerDbHelper db;
    NoteListAdapter adapt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        db = new TaskerDbHelper(this);
        List<Note> list = db.getAllNotes();
        SwipeListView listView = (SwipeListView) findViewById(R.id.listViewNotes);
        adapt = new NoteListAdapter(this, R.layout.note_row, list, db, listView);
        listView.setAdapter(adapt);

        listView.setSwipeListViewListener(new BaseSwipeListViewListener(){

            @Override
            public void onClickFrontView(int position) {
                super.onClickFrontView(position);
                Log.d("FrontRow", "Nowy click");
                Note note = adapt.getItem(position);
                Intent i = new Intent(NotesActivity.this , TasksActivity.class);
                i.putExtra("Key_note", note.getId());
                NotesActivity.this.startActivity(i);
            }
        });


    }

    public void addNoteNow(View v) {
        EditText t = (EditText) findViewById(R.id.editTextNewNote);
        String s = t.getText().toString();
        if (s.equalsIgnoreCase("")) {
            Toast.makeText(this, "Enter the note name first!",
                    Toast.LENGTH_LONG);
        } else {
            Note note = new Note(s);
            db.addNote(note);
            Log.d("tasker", "data added");
            t.setText("");
            adapt.add(note);
            adapt.notifyDataSetChanged();
            SwipeListView listTask = (SwipeListView) findViewById(R.id.listViewNotes);
            listTask.closeOpenedItems();

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
