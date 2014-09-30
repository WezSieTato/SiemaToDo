package com.wezsietato.todo.model;

/**
 * Created by WezSieTato on 24.09.2014.
 */
public class Task {
    private String name;
    private boolean status;
    private int id;

    Note note;

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public Task() {
        this.name = null;
        this.status = false;
    }

    public Task(String name, boolean status) {
        super();
        this.name = name;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String taskName) {
        this.name = taskName;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
