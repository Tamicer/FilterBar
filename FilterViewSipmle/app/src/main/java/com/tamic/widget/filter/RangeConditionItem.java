package com.tamic.widget.filter;

/**
 * Created by SUNTAIYI on 2016-08-24.
 */
public class RangeConditionItem extends NewConditionItem {

    public interface RangeInfo {
        String getInfo(RangeConditionItem item);
    }

    static public final RangeInfo PRICE_INFO = new RangeInfo() {
        @Override
        public String getInfo(RangeConditionItem item) {
            if (item.from == item.min && item.to == item.max) {
                return "不限";
            }
            if (item.from > item.min && item.to == item.max) {
                return String.format("高于%.1f 万元", (float)item.from/10);
            }
            if (item.from == item.min && item.to < item.max) {
                return String.format("低于%.1f 万元", (float)item.to/10);
            }

            return String.format("%.1f 万元 — %.1f 万元", (float)item.from/10, (float)item.to/10);
        }
    };
    static public final RangeInfo AREA_INFO = new RangeInfo() {
        @Override
        public String getInfo(RangeConditionItem item) {
            if (item.from == item.min && item.to == item.max) {
                return "不限";
            }
            if (item.from > item.min && item.to == item.max) {
                return String.format("大于%d㎡", item.from);
            }
            if (item.from == item.min && item.to < item.max) {
                return String.format("小于%d㎡", item.to);
            }

            return String.format("%d㎡ — %d㎡", item.from, item.to);
        }
    };
    static public final RangeInfo RENT_PRICE_INFO = new RangeInfo() {
        @Override
        public String getInfo(RangeConditionItem item) {
            if (item.from == item.min && item.to == item.max) {
                return "不限";
            }
            if (item.from > item.min && item.to == item.max) {
                return String.format("高于%.1f 元", (float)item.from/10);
            }
            if (item.from == item.min && item.to < item.max) {
                return String.format("低于%.1f 元", (float)item.to/10);
            }

            return String.format("%.1f 元 — %.1f 元", (float)item.from/10, (float)item.to/10);
        }
    };

    public int min, max, step;
    public int from, to;
    public RangeInfo rangeInfo = null;

    public RangeConditionItem() {
        super();
    }

    public RangeConditionItem(NewConditionItem parent, int id, String name,
                              int min, int max, int step, int from, int to) {
        super(parent, id, name, true, TYPE_RANGE);
        this.min = min;
        this.max = max;
        this.step = step;
        this.from = from;
        this.to = to;
    }

    @Override
    public void clear() {
        super.clear();
        from = min;
        to = max;
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
        RangeConditionItem cloned = new RangeConditionItem();
        cloned.id = this.id;
        cloned.name = this.name;
        cloned.selected = this.selected;
        cloned.canReset = this.canReset;
        cloned.type = this.type;
        cloned.processor = this.processor;
        for (NewConditionItem subItem : this.subItems) {
            NewConditionItem clonedSubItem = subItem.clone();
            clonedSubItem.parent = cloned;
            cloned.subItems.add(clonedSubItem);
        }
        cloned.min = this.min;
        cloned.max = this.max;
        cloned.step = this.step;
        cloned.from = this.from;
        cloned.to = this.to;
        cloned.rangeInfo = this.rangeInfo;
        return cloned;
    }
}
