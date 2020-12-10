package com.example.freightviewer.Dashboard.loadmodel;

import com.yuyang.stickyheaders.StickyHeaderModel;

public class Header extends ListLoad implements StickyHeaderModel {

    private String header;
    public  static String date;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}