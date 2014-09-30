package com.wezsietato.todo.noteview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fortysevendeg.swipelistview.SwipeListView;
import com.wezsietato.todo.R;
import com.wezsietato.todo.model.Note;
import com.wezsietato.todo.model.Task;
import com.wezsietato.todo.model.TaskerDbHelper;

import java.util.List;

/**
 * Created by WezSieTato on 30.09.2014.
 */
public class NoteListAdapter extends ArrayAdapter<Note> {
    private TaskerDbHelper db;
    private SwipeListView listView;

    public NoteListAdapter(Context context, int layoutResourceId,
                           List<Note> objects, TaskerDbHelper db, SwipeListView listView) {
        super(context, layoutResourceId, objects);
        this.db = db;
        this.listView = listView;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView text = null;

        if(shouldCreateView(convertView)){
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.note_row, parent, false);

            text = (TextView)convertView.findViewById(R.id.noteTextView);
        } else {
            text = (TextView)convertView.findViewById(R.id.noteTextView);
        }

        Note note = getItem(position);
        text.setText(note.getName());

        return convertView;
    }

    public boolean shouldCreateView(View convertView){
        if(convertView == null)
            return true;

        return true;
    }
}

