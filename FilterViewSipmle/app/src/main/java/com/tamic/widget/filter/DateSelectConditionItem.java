package com.tamic.widget.filter;

/**
 * Created by Tamic on 2017-07-28.
 */
public class DateSelectConditionItem extends NewConditionItem {

    public interface DateRangeInfo {

        String getInfo(DateSelectConditionItem item);
    }

    static public final DateRangeInfo DATE_INFO = new DateRangeInfo() {

        @Override
        public String getInfo(DateSelectConditionItem item) {
            if (item.start == 0 || item.end == 0) {
                return " ";
            }
            return item.start + ":" + item.end;
        }
    };

    public int start, end;
    public int from, to;
    public boolean isRefresh = true;
    public DateRangeInfo rangeInfo = DATE_INFO;


    public DateSelectConditionItem(int type) {
        super(type);
    }

    public DateSelectConditionItem(NewConditionItem parent, int id, String name,
                                   int start, int end) {
        super(parent, id, name, true, TYPE_DATA);
        this.start = start;
        this.end = end;
      /*  if (this.parent != null) {
            this.parent.subItems.add(this);
        }*/
    }


    public NewConditionItem createConditionItem(int type) {
        return new DateSelectConditionItem(type);
    }

    @Override
    public void clear() {
        super.clear();
        if (isRefresh) {
            start = 0;
            end = 0;
        }
        isRefresh = false;

    }

    @Override
    public void reset() {
        super.reset();
        from = -1;
        to = -1;
    }

    public String getInfo() {
        if (rangeInfo == null) {
            return "";
        } else {
            return rangeInfo.getInfo(this);
        }
    }

    @Override
    public NewConditionItem clone() {
        DateSelectConditionItem cloned = new DateSelectConditionItem(type);
        cloned.id = this.id;
        cloned.name = this.name;
        cloned.selected = this.selected;
        cloned.canReset = this.canReset;
        cloned.type = this.type;
        cloned.start = this.start;
        cloned.end = this.end;
        cloned.processor = this.processor;
        cloned.isRefresh = this.isRefresh;
        for (NewConditionItem subItem : this.subItems) {
            NewConditionItem clonedSubItem = subItem.clone();
            clonedSubItem.parent = cloned;
            cloned.subItems.add(clonedSubItem);
        }
        cloned.start = this.start;
        cloned.end = this.end;
        cloned.from = this.from;
        cloned.to = this.to;
        cloned.rangeInfo = this.rangeInfo;
        return cloned;
    }
}
