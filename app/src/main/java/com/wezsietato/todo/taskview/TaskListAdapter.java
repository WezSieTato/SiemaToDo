package com.wezsietato.todo.taskview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.fortysevendeg.swipelistview.SwipeListView;
import com.wezsietato.todo.R;
import com.wezsietato.todo.model.Task;
import com.wezsietato.todo.model.TaskerDbHelper;

import java.util.List;


/**
 * Created by WezSieTato on 24.09.2014.
 */
public class TaskListAdapter extends ArrayAdapter<Task> {

    private TaskerDbHelper db;
    private SwipeListView listView;

    public TaskListAdapter(Context context, int layoutResourceId,
                           List<Task> objects, TaskerDbHelper db, SwipeListView listView) {
        super(context, layoutResourceId, objects);
        this.db = db;
        this.listView = listView;
    }

    /**
     * This method will DEFINe what the view inside the list view will
     * finally look like Here we are going to code that the checkbox state
     * is the status of task and check box text is the task name
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CheckBox chk = null;
        TextView tv = null;
        Button deleteButton = null;

        if (shouldCreateView(convertView)) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.task_row,
                    parent, false);
            chk = (CheckBox) convertView.findViewById(R.id.taskCheckBox);
            tv = (TextView) convertView.findViewById(R.id.taskTextView);

            chk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    Task changeTask = (Task) cb.getTag();
                    changeTask.setStatus(cb.isChecked());
                    db.updateTask(changeTask);
                    Toast.makeText(
                            getContext(),
                            "Clicked on Checkbox: " + cb.getText() + " is "
                                    + cb.isChecked(), Toast.LENGTH_LONG)
                            .show();
                }
            });

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v.getTag();
                    cb.performClick();
                }
            });

            deleteButton = (Button)convertView.findViewById(R.id.buttonDelete);
            deleteButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Task task = (Task) view.getTag();
                    db.deleteTask(task);
                    remove(task);
                    notifyDataSetChanged();
                    listView.setAnimationTime(0);
                    listView.closeOpenedItems();
//                    listView.setAdapter(listView.getAdapter());

                }
            });


        } else {
            chk = (CheckBox) convertView.findViewById(R.id.taskCheckBox);
            tv = (TextView) convertView.findViewById(R.id.taskTextView);
            deleteButton = (Button)convertView.findViewById(R.id.buttonDelete);
        }
        listView.recycle(convertView, position);
        Task current = getItem(position);
        tv.setText(current.getName());
        chk.setChecked(current.getStatus());
        chk.setTag(current);
        deleteButton.setTag(current);
        return convertView;

    }

    public boolean shouldCreateView(View convertView){
        if(convertView == null)
            return true;
//        Button deleteButton = (Button)convertView.findViewById(R.id.buttonDelete);
//        Task task = (Task) deleteButton.getTag();
//        if(getPosition(task) < 0) {
//            Log.d("Odtwarzam na nowo", task.getName());
//            return true;
//        }

        return true;
    }
}
