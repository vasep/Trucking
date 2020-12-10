package com.example.freightviewer.Dashboard.loadmodel;

public class ListLoad {
    private String loadname;
    String date;

    public String getDate() {
        return date;
    }

    public String getName() {
        return loadname;
    }

    public void setName(String name) {
        this.loadname = name;
    }


    public String getLoadname() {
        return loadname;
    }

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}