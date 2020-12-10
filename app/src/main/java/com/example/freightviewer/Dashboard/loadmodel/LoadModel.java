package com.example.freightviewer.Dashboard.loadmodel;

public class LoadModel extends ListLoad{

    private String loadname;
    String date;

    public LoadModel(String loadname, String date){
        this.loadname = loadname;
        this.date = date;
    }

    public String getLoadname() {
        return loadname;
    }

    public String getDate() {
        return date;
    }
}
