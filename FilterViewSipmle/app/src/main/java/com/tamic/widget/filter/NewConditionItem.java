package com.tamic.widget.filter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SUNTAIYI on 2016-08-22.
 */
public class NewConditionItem extends BaseBean {

    public interface SubItemsProcessor {

        boolean check(List<NewConditionItem> items, NewConditionItem selectChanged);

        boolean uncheck(List<NewConditionItem> items, NewConditionItem selectChanged);
    }

    static public final SubItemsProcessor DEFAULT = new SubItemsProcessor() {

        @Override
        public boolean check(List<NewConditionItem> items, NewConditionItem selectChanged) {
            if (selectChanged.isSingled) { //当item的只能独立选中的
                for (NewConditionItem item : items) { //将其他选中的item都clear
                    item.selected = false;
                    item.clear();
                }
            } else {
                for (NewConditionItem item : items) {
                    if (item.isSingled) {
                        item.selected = false;
                        item.clear();
                    }
                }
            }
            selectChanged.selected = true;
            selectChanged.reset();
            return true;
        }

        @Override
        public boolean uncheck(List<NewConditionItem> items, NewConditionItem selectChanged) {
            selectChanged.selected = false;
            selectChanged.clear();
            return true;
        }
    };
    static public final SubItemsProcessor RADIO_LIST = new SubItemsProcessor() {

        @Override
        public boolean check(List<NewConditionItem> items, NewConditionItem selectChanged) {
            if (items.size() == 0) {
                return false;
            }
            if (selectChanged.selected) {
                return false;
            }
            for (NewConditionItem item : items) {
                item.selected = false;
                item.clear();
            }
            selectChanged.selected = true;
            selectChanged.reset();
            return true;
        }

        @Override
        public boolean uncheck(List<NewConditionItem> items, NewConditionItem selectChanged) {
            return false;
        }
    };
    static public final SubItemsProcessor RADIO_LIST_NO_CLEAR = new SubItemsProcessor() {

        @Override
        public boolean check(List<NewConditionItem> items, NewConditionItem selectChanged) {
            if (items.size() == 0) {
                return false;
            }
            if (selectChanged.selected) {
                return false;
            }
            for (NewConditionItem item : items) {
                item.selected = false;
            }
            selectChanged.selected = true;
            selectChanged.reset();
            return true;
        }

        @Override
        public boolean uncheck(List<NewConditionItem> items, NewConditionItem selectChanged) {
            return false;
        }
    };
    static public final SubItemsProcessor RADIO_LIST_UNCHECKABLE = new SubItemsProcessor() {

        @Override
        public boolean check(List<NewConditionItem> items, NewConditionItem selectChanged) {
            if (items.size() == 0) {
                return false;
            }
            if (selectChanged.selected) {
                return false;
            }
            for (NewConditionItem item : items) {
                item.selected = false;
                item.clear();
            }
            selectChanged.selected = true;
            selectChanged.reset();
            return true;
        }

        @Override
        public boolean uncheck(List<NewConditionItem> items, NewConditionItem selectChanged) {
            selectChanged.selected = false;
            selectChanged.clear();
            return true;
        }
    };
    static public final SubItemsProcessor ALL_AT_ZERO = new SubItemsProcessor() {

        @Override
        public boolean check(List<NewConditionItem> items, NewConditionItem selectChanged) {
            if (items.size() == 0) {
                return false;
            }
            if (items.get(0) == selectChanged) {
                for (NewConditionItem item : items) {
                    item.selected = false;
                    item.clear();
                }
                selectChanged.selected = true;
                selectChanged.reset();
                return true;
            } else {
                items.get(0).selected = false;
                items.get(0).clear();
                selectChanged.selected = true;
                selectChanged.reset();
                return true;
            }
        }

        @Override
        public boolean uncheck(List<NewConditionItem> items, NewConditionItem selectChanged) {
            if (items.size() == 0) {
                return false;
            }

            if (items.get(0) != selectChanged) {
                selectChanged.selected = false;
                selectChanged.clear();
                boolean all = true;
                for (NewConditionItem item : items) {
                    if (item != items.get(0)) {
                        all = all && !item.selected;
                    }
                }
                if (all) {
                    items.get(0).selected = true;
                    items.get(0).reset();
                }
                return true;
            }
            return false;
        }
    };
    static public final SubItemsProcessor CLEAR_UNCLE_ON_CHECK = new SubItemsProcessor() {

        @Override
        public boolean check(List<NewConditionItem> items, NewConditionItem selectChanged) {
            selectChanged.selected = true;
            selectChanged.reset();
            NewConditionItem parent = selectChanged.parent;
            if (parent == null) {
                return true;
            }
            NewConditionItem grand = parent.parent;
            if (grand == null) {
                return true;
            }
            for (NewConditionItem uncle : grand.subItems) {
                if (uncle == parent) {
                    continue;
                }
                uncle.clear();
            }
            return true;
        }

        @Override
        public boolean uncheck(List<NewConditionItem> items, NewConditionItem selectChanged) {
            selectChanged.selected = false;
            selectChanged.clear();
            return true;
        }
    };
    static public final int TYPE_LIST = 0;
    static public final int TYPE_GRID = 1;
    static public final int TYPE_RANGE = 2;
    static public final int TYPE_DATA = 3;
    static public final int TYPE_DNA_GRID_STYLE1 = 10;
    static public final int TYPE_DNA_GRID_STYLE2 = 11;
    static public final int TYPE_DNA_GRID_STYLE3 = 12;
    static public final int TYPE_DNA_GRID_STYLE4 = 13;
    static public final int TYPE_DNA_TAB_GRID = 20;
    static public final int TYPE_DNA_MUTUAL_TAB_GRID = 21;
    static public final int TYPE_DNA_ONE_ROW = 22;
    static public final int TYPE_DNA_FAVOR_GRID = 23;
    static public final int TYPE_EMPTY = -1;

    public NewConditionItem parent;
    public int id;
    public String name;
    public boolean selected;
    public boolean canReset = true;
    public int type = TYPE_LIST;
    public final List<NewConditionItem> subItems = new ArrayList<>();
    public SubItemsProcessor processor = DEFAULT;
    public boolean isSingled = false;  //此时的item是否是独立选择

    public NewConditionItem() {
        type = TYPE_EMPTY;
    }

    public NewConditionItem(int type) {
        this.type = type;
    }

    public NewConditionItem createConditionItem(int type) {
        return new NewConditionItem(type);
    }

    public NewConditionItem(NewConditionItem parent, int id, String name) {
        this(parent, id, name, false);
    }

    public NewConditionItem(NewConditionItem parent, int id, String name, boolean selected) {
        this(parent, id, name, selected, TYPE_LIST);
    }

    public NewConditionItem(NewConditionItem parent, int id, String name, boolean selected, int type) {
        this.parent = parent;
        this.id = id;
        this.name = name;
        this.selected = selected;
        this.type = type;
        this.isSingled = (id == 0);
        if (this.parent != null) {
            this.parent.subItems.add(this);
        }
    }

    public NewConditionItem subItemById(int id) {
        if (this.type == TYPE_EMPTY) {
            return createConditionItem(TYPE_EMPTY);
        }
        for (NewConditionItem item : subItems) {
            if (item.id == id) {
                return item;
            }
        }
        return createConditionItem(TYPE_EMPTY);
    }

    public List<NewConditionItem> selectedSubItems() {
        List<NewConditionItem> list = new ArrayList<>();
        for (NewConditionItem item : subItems) {
            if (item.selected) {
                list.add(item);
            }
        }
        return list;
    }

    public List<NewConditionItem> unselectedSubItems() {
        List<NewConditionItem> list = new ArrayList<>();
        for (NewConditionItem item : subItems) {
            if (!item.selected) {
                list.add(item);
            }
        }
        return list;
    }

    public NewConditionItem firstSelectedSubItem() {
        for (NewConditionItem item : subItems) {
            if (item.selected) {
                return item;
            }
        }
        return null;
    }

    public NewConditionItem root() {
        NewConditionItem item = this;
        while (item.parent != null) {
            item = item.parent;
        }
        return item;
    }

    public void rootClear() {
        NewConditionItem root = this.root();
        for (NewConditionItem top : root.subItems) {
            if (!top.selected) {
                top.clear();
            }
        }
    }

    public boolean isEmpty() {
        return type == TYPE_EMPTY;
    }

    public boolean isLeaf() {
        return subItems.size() == 0;
    }

    public boolean processSubItems(NewConditionItem selectedChanged, boolean checked) {
        if (processor != null) {
            if (checked) {
                return processor.check(subItems, selectedChanged);
            } else {
                return processor.uncheck(subItems, selectedChanged);
            }
        } else {
            return false;
        }
    }

    public void clear() {
        for (NewConditionItem subItem : subItems) {
            subItem.selected = false;
            subItem.clear();
        }
    }

    public void reset() {
        NewConditionItem selected = this.firstSelectedSubItem();
        if (canReset && selected == null && subItems.size() > 0) {

            if (subItems.get(0).type != NewConditionItem.TYPE_DATA) {
                subItems.get(0).selected = true;
            } else {

            }
            //subItems.subItemById(0).reset();
            for (NewConditionItem subItem : subItems) {
                if (subItem.type == NewConditionItem.TYPE_DATA) {
                    continue;
                }
                subItem.reset();
            }
        }
    }

    public NewConditionItem clone() {
        NewConditionItem cloned = createConditionItem(this.type);
        cloned.id = this.id;
        cloned.name = this.name;
        cloned.selected = this.selected;
        cloned.canReset = this.canReset;
        cloned.type = this.type;
        cloned.processor = this.processor;
        cloned.isSingled = this.isSingled;
        for (NewConditionItem subItem : this.subItems) {
            NewConditionItem clonedSubItem = subItem.clone();
            clonedSubItem.parent = cloned;
            cloned.subItems.add(clonedSubItem);
        }
        return cloned;
    }
}
