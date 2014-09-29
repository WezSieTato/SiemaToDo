package com.wezsietato.todo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by WezSieTato on 24.09.2014.
 */
public class TaskListAdapter extends ArrayAdapter<Task> {

    private TaskerDbHelper db;
    private int layoutResourceId;

    public TaskListAdapter(Context context, int layoutResourceId,
                           List<Task> objects, TaskerDbHelper db) {
        super(context, layoutResourceId, objects);
        this.layoutResourceId = layoutResourceId;
        this.db = db;
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
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.task_row,
                    parent, false);
            chk = (CheckBox) convertView.findViewById(R.id.taskCheckBox);
            tv = (TextView) convertView.findViewById(R.id.taskTextView);
            View front = (View) convertView.findViewById(R.id.taskRowFront);
            convertView.setTag(chk);

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
                }
            });



        } else {
            chk = (CheckBox) convertView.findViewById(R.id.taskCheckBox);
            tv = (TextView) convertView.findViewById(R.id.taskTextView);
            deleteButton = (Button)convertView.findViewById(R.id.buttonDelete);
        }
        Task current = getItem(position);
        tv.setText(current.getTaskName());
        chk.setChecked(current.getStatus());
        chk.setTag(current);
        deleteButton.setTag(current);
        Log.d("listener", String.valueOf(current.getId()));
        return convertView;

    }
}
