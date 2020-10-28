package com.example.freightviewer.Dashboard.loadmodel;

public class LoadModel extends ListLoad{

    private String loadname;
    int date;

    public LoadModel(String loadname, int date){
        this.loadname = loadname;
        this.date = date;
    }

    public String getLoadname() {
        return loadname;
    }

    public int getDate() {
        return date;
    }
}
