package com.tamic.widget.filter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;


/**
 * update by Tamic on 2017-07-26.
 * ----------------------
 */
public abstract class ConditionContainer extends FrameLayout {

    public interface Controller {

        void confirm(ConditionContainer container);

        void cancel(ConditionContainer container);

        void onItemClick(ConditionContainer container, int id);
    }

    public ConditionContainer(Context context) {
        super(context);
    }

    public ConditionContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    abstract public NewConditionItem getConditionItem();

    abstract public void setConditionItem(NewConditionItem item);

    abstract public NewConditionItem create(List<BaseFilterItem> itemList);

    abstract public void addContentView(View view);

    protected Controller mController;
    protected FiterModeCallback mCallback;

    public void setController(Controller controller) {
        mController = controller;
    }

    public void setModeCallback(FiterModeCallback controller) {
        mCallback = controller;
    }

    public void onItemClick(ConditionContainer container) {

    }

    public interface FiterModeCallback {

        FilterMode geFiterMode();
    }


}
