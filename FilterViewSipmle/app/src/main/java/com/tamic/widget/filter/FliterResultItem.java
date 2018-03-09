package com.tamic.widget.filter;


import java.util.ArrayList;

/**
 * Created by LIUYONGKUI726 on 2018-03-06.
 */

public class FliterResultItem {

    public int id;
    public String name;
    private ArrayList<FliterResultItem> subItems;


    public void setSubItems(ArrayList<FliterResultItem> subItems) {
        this.subItems = subItems;
    }

    public boolean isEmpty() {
        return subItems == null || subItems.size() == 0;
    }

    public ArrayList<FliterResultItem> subItems() {
        return subItems;
    }

    public FliterResultItem createResult(NewConditionItem item) {
        FliterResultItem fliterResultItem = null;
        if (item.selected) {
            fliterResultItem = new FliterResultItem();
            fliterResultItem.id = item.id;
            fliterResultItem.name = item.name;
            fliterResultItem.subItems = new ArrayList<>();
            for (NewConditionItem subItem : item.subItems) {
                if (!subItem.selected) {
                    continue;
                }
                fliterResultItem.subItems.add(fliterResultItem.createResult(subItem));
            }
        }
        return fliterResultItem;
    }
}
