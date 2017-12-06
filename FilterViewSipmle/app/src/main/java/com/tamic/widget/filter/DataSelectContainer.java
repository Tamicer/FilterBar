package com.tamic.widget.filter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import widget.tamic.com.filterviewsipmle.R;

/**
 * Created by Tamic on 2017-07-28
 */
public class DataSelectContainer extends ConditionContainer {

    public DataSelectContainer(Context context) {
        super(context);
        init();
    }

    private TextView mTvTitle;
    private CompoundButton mTvDateStart;
    private CompoundButton mTvDateEnd;
    private int mLastStart;
    private int mLastEnd;
    private static final String DATA_FORMAT = "yyyy/MM/dd";

    private void init() {
        setContentView(R.layout.layout_condition_data_select);
        mTvTitle = (TextView) findViewById(R.id.tv_info_condition_tilte);
        mTvDateStart = (CompoundButton) findViewById(R.id.tv_title_condition_data_start);
        mTvDateEnd = (CompoundButton) findViewById(R.id.tv_info_condition_data_end);


        mTvDateEnd.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mTvDateStart.setText("6546465465");
                mItem.end = 43434343;
                Toast.makeText(getContext(), "DateEnd", Toast.LENGTH_SHORT).show();
                ((GridContainer) getParent().getParent().getParent()).reSet();
            }
        });
        mTvDateStart.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "DateStart", Toast.LENGTH_SHORT).show();
                mTvDateEnd.setText("454545");
                mItem.start = 787834;
                ((GridContainer) getParent().getParent().getParent()).reSet();
            }
        });


    }

    public View setContentView(@LayoutRes int id) {
        return LayoutInflater.from(getContext()).inflate(id, this);
    }

    public void reSet() {
        mTvDateStart.setText("开始时间");
        mTvDateEnd.setText("结束时间");
    }

    private DateSelectConditionItem mItem;

    @Override
    public NewConditionItem getConditionItem() {
        return mItem;
    }

    @Override
    public void setConditionItem(NewConditionItem item) {
        mItem = (DateSelectConditionItem) item;
        mTvTitle.setText(mItem.name);
        mLastStart = mItem.start;
        mLastEnd = mItem.end;
        if (mLastStart == 0 && mLastEnd == 0) {
            reSet();
            return;
        }
        if (mLastStart == 0) {
            mTvDateStart.setText("开始时间");
        } else {
            mTvDateStart.setText("" + mLastStart);
        }
        if (mLastEnd == 0) {
            mTvDateEnd.setText("结束时间");
        } else {
            mTvDateEnd.setText("" + mLastEnd);
        }
    }

    @Override
    public NewConditionItem create(List<BaseFilterItem> itemList) {
        //todo create you ConditionItem, Usually not youself  implement it!
        return null;
    }

    @Override
    public void addContentView(View view) {
        //todo add you childView to rootView , Usually not youself  layout the View!
        addView(view);
    }

    static private int adjust(int base, int step, int value) {
        int steps = (value - base) / step;
        float r = (float) ((value - base) % step) / (float) step;
        return base + steps * step + Math.round(r) * step;
    }

}
