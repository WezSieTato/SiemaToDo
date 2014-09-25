package com.wezsietato.todo;

/**
 * Created by WezSieTato on 24.09.2014.
 */
public class Task {
    private String taskName;
    private boolean status;
    private int id;

    public Task() {
        this.taskName = null;
        this.status = false;
    }

    public Task(String taskName, boolean status) {
        super();
        this.taskName = taskName;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
