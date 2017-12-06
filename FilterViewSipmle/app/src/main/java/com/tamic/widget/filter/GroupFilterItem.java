package com.tamic.widget.filter;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * GroupFilterItem
 */
public class GroupFilterItem extends BaseFilterItem implements Parcelable {

    private ArrayList<BaseFilterItem> children;

    public ArrayList<BaseFilterItem> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<BaseFilterItem> children) {
        this.children = children;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        if (null == children || 0 == children.size()) {
            dest.writeInt(0);
        } else {
            dest.writeInt(children.size());
            for (BaseFilterItem item : children) {
                dest.writeParcelable(item, flags);
            }
        }
    }

    public static Creator<GroupFilterItem> CREATOR = new Creator<GroupFilterItem>() {

        @Override
        public GroupFilterItem createFromParcel(Parcel source) {
            GroupFilterItem item = new GroupFilterItem();
            item.setCode(source.readInt());
            item.setName(source.readString());
            int size = source.readInt();
            ArrayList<BaseFilterItem> items = new ArrayList<>();
            if (0 != size) {
                for (int i = 0; i < size; i++) {
                    items.add((BaseFilterItem) source
                            .readParcelable(BaseFilterItem.class
                                    .getClassLoader()));
                }
            }
            item.setChildren(items);
            return item;
        }

        @Override
        public GroupFilterItem[] newArray(int size) {
            return new GroupFilterItem[size];
        }
    };

}
