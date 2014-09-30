package com.wezsietato.todo.model;

import java.util.List;

/**
 * Created by WezSieTato on 30.09.2014.
 */
public class Note {
    private String name;
    private int id;

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    private List< Task > tasks;

    public Note() {
    }

    public Note(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
