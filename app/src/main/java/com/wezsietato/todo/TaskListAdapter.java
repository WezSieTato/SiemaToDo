package com.wezsietato.todo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by WezSieTato on 24.09.2014.
 */
public class TaskListAdapter extends ArrayAdapter<Task> {

    private Context context;
    private List<Task> taskList = new ArrayList<Task>();
    private TaskerDbHelper db;
    private int layoutResourceId;

    public TaskListAdapter(Context context, int layoutResourceId,
                           List<Task> objects) {
        super(context, layoutResourceId, objects);
        this.layoutResourceId = layoutResourceId;
        this.taskList = objects;
        this.context = context;
    }

    /**
     * This method will DEFINe what the view inside the list view will
     * finally look like Here we are going to code that the checkbox state
     * is the status of task and check box text is the task name
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CheckBox chk = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_inner_view,
                    parent, false);
            chk = (CheckBox) convertView.findViewById(R.id.checkBox1);
            convertView.setTag(chk);
            chk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    Task changeTask = (Task) cb.getTag();
                    changeTask.setStatus(cb.isChecked() == true ? 1 : 0);
                    db.updateTask(changeTask);
                    Toast.makeText(
                            context,
                            "Clicked on Checkbox: " + cb.getText() + " is "
                                    + cb.isChecked(), Toast.LENGTH_LONG)
                            .show();
                }
            });
        } else {
            chk = (CheckBox) convertView.getTag();
        }
        Task current = taskList.get(position);
        chk.setText(current.getTaskName());
        chk.setChecked(current.getStatus() == 1 ? true : false);
        chk.setTag(current);
        Log.d("listener", String.valueOf(current.getId()));
        return convertView;

    }
}