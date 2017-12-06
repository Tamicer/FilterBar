package com.tamic.widget.filter;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by suntaiyi on 2016-09-21.
 */
public class ListFilterBean {

    static public class Item {

        public int id;
        public String name;
        public String value;
        public List<Item> children = new ArrayList<>();
    }

    static public class ItemList {

        public String name;
        public List<Item> list = new ArrayList<>();
    }

    static public class Range {

        public String name;
        public int min;
        public int max;
        public int step;
        public String unit;
    }


    public ItemList region;
    public ItemList layout;
    public ItemList sort;
}

