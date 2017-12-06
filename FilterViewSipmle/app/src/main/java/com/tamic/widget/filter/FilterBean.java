package com.tamic.widget.filter;


import java.util.ArrayList;

/**
 * FilterBean
 */
public class FilterBean<T extends BaseFilterItem> {

    private int id;
    private String name;
    private String sName;
    private ArrayList<T> list;

    public FilterBean() {
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

    public void setName(String name) {
        this.name = name;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public ArrayList<T> getList() {
        return list;
    }

    public void setList(ArrayList<T> list) {
        this.list = list;
    }
}
