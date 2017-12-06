package com.tamic.widget.filter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.List;

import widget.tamic.com.filterviewsipmle.R;

/**
 * Update by Tamic on 2017-07-27
 *
 * @link {https://github.com/Tamicer/FilterBar}
 */
public class GridContainer extends ConditionContainer implements CompoundButton.OnCheckedChangeListener, View.OnLayoutChangeListener {


    public GridContainer(Context context) {
        super(context);
        init();
    }

    private TextView mTvTitle;
    private GridLayout mGrid;
    private LinearLayout mLayout;
    private CompoundButton btn;
    private int columnCount = 4;
    private View view;
    private TextView mTvDateStart;
    private TextView mTvDateEnd;
    private RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT);

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_condition_grid, this);
        mTvTitle = (TextView) findViewById(R.id.tv_title_condition_grid);
        mGrid = (GridLayout) findViewById(R.id.grid_condition_grid);
        mGrid.setColumnCount(columnCount);
        mLayout = (LinearLayout) findViewById(R.id.grid_condition_custom_view);
        mGrid.addOnLayoutChangeListener(this);

    }


    public void addFooterView(View view) {
        this.view = view;
        mLayout.removeAllViews();
        mLayout.addView(view);
    }

    public void addHeaderView(View view) {
        addView(view);
    }

    private NewConditionItem mRoot;
    private DateSelectConditionItem item;

    @Override
    public NewConditionItem getConditionItem() {
        return mRoot;
    }

    @Override
    public void setConditionItem(NewConditionItem item) {
        mRoot = item;
        mTvTitle.setText(item.name);
        mGrid.removeAllViews();
        for (NewConditionItem subItem : mRoot.subItems) {
            if (subItem.type != NewConditionItem.TYPE_LIST) {
                if (subItem.type == NewConditionItem.TYPE_DATA) {
                    this.item = (DateSelectConditionItem) subItem;
                    DataSelectContainer container2 = new DataSelectContainer(getContext());
                    LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );
                    container2.setLayoutParams(lp2);
                    container2.setTag(subItem);
                    container2.setConditionItem(subItem);
                    addFooterView(container2);
                }
                continue;

            }
            btn = (CompoundButton) LayoutInflater.from(getContext())
                    .inflate(R.layout.item_condition_grid, mGrid, false);
            btn.setTag(subItem);
            btn.setText(subItem.name);
            btn.setChecked(subItem.selected);
            btn.setOnCheckedChangeListener(this);
            addToGrid(btn);
        }
    }

    @Override
    public NewConditionItem create(List<BaseFilterItem> itemList) {
        return null;
    }

    @Override
    public void addContentView(View view) {
        addFooterView(view);
    }

    private void addToGrid(View v) {
        if (mGrid.getWidth() > 0) {
            ViewGroup.LayoutParams lp = v.getLayoutParams();
            lp.width = mGrid.getWidth() / columnCount;
            v.setLayoutParams(lp);
        }
        mGrid.addView(v);
    }

    public void clear() {
        for (int i = 0; i < mGrid.getChildCount(); i++) {
            CompoundButton btn = (CompoundButton) mGrid.getChildAt(i);
            NewConditionItem item = (NewConditionItem) btn.getTag();
            item.reset();
            setConditionItem(item);
        }
    }

    public void reSet() {
        if (item != null) {
            item.isRefresh = false;
        }
        if (mRoot != null) {
            mRoot.clear();
            mRoot.reset();
            setConditionItem(mRoot);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        NewConditionItem item = (NewConditionItem) buttonView.getTag();
        boolean refresh = item.parent.processSubItems(item, buttonView.isChecked());
        buttonView.setChecked(item.selected);
        if (refresh) {
            setConditionItem(item.parent);
        }

    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom,
                               int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (right - left == oldRight - oldLeft) {
            return;
        }
        int cellWidth = mGrid.getWidth() / columnCount;
        for (int i = 0; i < mGrid.getChildCount(); ++i) {
            View cell = mGrid.getChildAt(i);
            GridLayout.LayoutParams lp = (GridLayout.LayoutParams) cell.getLayoutParams();
            lp.width = cellWidth;
            cell.setLayoutParams(lp);
        }
    }


}
