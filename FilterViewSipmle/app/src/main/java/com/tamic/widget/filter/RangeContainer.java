package com.tamic.widget.filter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import java.util.List;

import widget.tamic.com.filterviewsipmle.R;

/**
 * Update by Tamic on 2017-07-27
 * https://github.com/Tamicer/FilterBar
 */
public class RangeContainer extends ConditionContainer implements RangeSeekBar.OnRangeSeekBarChangeListener<Integer> {

    public RangeContainer(Context context) {
        super(context);
        init();
    }

    private TextView mTvTitle;
    private TextView mTvInfo;
    private RangeSeekBar<Integer> mBar;
    private int mLastFrom;
    private int mLastTo;

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_condition_range, this);

        mTvTitle = (TextView) findViewById(R.id.tv_title_condition_range);
        mTvInfo = (TextView) findViewById(R.id.tv_info_condition_range);
        mBar = (RangeSeekBar<Integer>) findViewById(R.id.bar_range);
        mBar.setOnRangeSeekBarChangeListener(this);
        mBar.setNotifyWhileDragging(true);

    }

    private RangeConditionItem mItem;

    @Override
    public NewConditionItem getConditionItem() {
        return mItem;
    }

    @Override
    public void setConditionItem(NewConditionItem item) {
        mItem = (RangeConditionItem) item;
        mTvTitle.setText(mItem.name);
        mTvInfo.setText(mItem.getInfo());
        mBar.setRangeValues(mItem.min, mItem.max);
        mBar.setSelectedMinValue(mItem.from);
        mBar.setSelectedMaxValue(mItem.to);
        mLastFrom = mItem.from;
        mLastTo = mItem.to;
    }

    @Override
    public NewConditionItem create(List<BaseFilterItem> itemList) {
        return null;
    }

    @Override
    public void addContentView(View view) {
        addView(view);
    }

    @Override
    public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
        int adjustMin = adjust(mItem.min, mItem.step, minValue);
        int adjustMax = adjust(mItem.min, mItem.step, maxValue);
        if (adjustMin == adjustMax) {
            if (mLastFrom == adjustMin) {
                adjustMax += mItem.step;
            } else if (mLastTo == adjustMax) {
                adjustMin -= mItem.step;
            }
        }
        mItem.from = adjustMin;
        mItem.to = adjustMax;
        mTvInfo.setText(mItem.getInfo());
        mBar.setSelectedMinValue(mItem.from);
        mBar.setSelectedMaxValue(mItem.to);
        mLastFrom = mItem.from;
        mLastTo = mItem.to;
    }

    static private int adjust(int base, int step, int value) {
        int steps = (value - base) / step;
        float r = (float) ((value - base) % step) / (float) step;
        return base + steps * step + Math.round(r) * step;
    }

}
