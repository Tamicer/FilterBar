package com.tamic.widget.filter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CompoundButton;

import widget.tamic.com.filterviewsipmle.R;

/**
 * Created by Tamic on 2017-08-02.
 */

public class DateView extends CompoundButton {

    public DateView(Context context) {
        super(context);
        init();
    }


    public DateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DateView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext())
                .inflate(R.layout.item_condition_grid, null);
    }


}
