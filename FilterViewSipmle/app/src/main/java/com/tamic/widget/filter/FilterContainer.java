package com.tamic.widget.filter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.List;

import widget.tamic.com.filterviewsipmle.R;

/**
 * Created by SUNTAIYI on 2016-08-24.
 */
public class FilterContainer extends ConditionContainer implements View.OnClickListener {
    public FilterContainer(Context context) {
        super(context);
        init();
    }

    public FilterContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
    );

    private LinearLayout mList;
    private TextView mTvReset;
    private TextView mTvConfirm;

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_condition_filter, this);
        mList = (LinearLayout) findViewById(R.id.list_condition_filter);
        mTvReset = (TextView) findViewById(R.id.tv_reset_condition);
        mTvConfirm = (TextView) findViewById(R.id.tv_confirm_condition);
        mTvReset.setOnClickListener(this);
        mTvConfirm.setOnClickListener(this);
        findViewById(R.id.blank_condition).setOnClickListener(this);
    }

    private NewConditionItem mRoot;

    @Override
    public NewConditionItem getConditionItem() {
        return mRoot;
    }

    @Override
    public void setConditionItem(NewConditionItem item) {
        if (mRoot != item) {
            mRoot = item;
            mList.removeAllViews();
            if (item == null) {
                return;
            }
            LayoutInflater inflater = LayoutInflater.from(getContext());
            for (NewConditionItem subItem : item.subItems) {

               if (subItem.type == NewConditionItem.TYPE_GRID) {
                    ConditionContainer container = new GridContainer(getContext());
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );
                    container.setLayoutParams(lp);
                    container.setTag(subItem);
                    addContentView(container);
                    container.setConditionItem(subItem);

                } /*else if (subItem.type == NewConditionItem.TYPE_GRID) {
                    ConditionContainer container = null;
                    container = new GridContainer(getContext());
                   // RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );
                    container.setLayoutParams(lp);
                   *//* if(subItem.type != NewConditionItem.TYPE_DATA) {
                        for (NewConditionItem subItem2 : subItem.subItems) {
                            if(subItem2.type == NewConditionItem.TYPE_DATA) {
                               ConditionContainer container2 = new DataSelectContainer(getContext());
                               LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT
                                );
                                container2.setLayoutParams(lp2);
                                container2.setTag(subItem2);
                                container2.setConditionItem(subItem2);

                                if (container != null) {
                                    container.addContentView(container2);
                                }

                            } else {
                                container.setTag(subItem);
                                container.setConditionItem(subItem);
                            }

                            container.setTag(subItem);
                           // container.setConditionItem(subItem);
                        }
                    }*//*

                    mList.addView(container);
                    container.setConditionItem(subItem);

                } */else if (subItem.type == NewConditionItem.TYPE_RANGE) {
                    ConditionContainer container = new RangeContainer(getContext());
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );
                    container.setLayoutParams(lp);
                    container.setTag(subItem);
                    addContentView(container);
                    container.setConditionItem(subItem);

                } else if (subItem.type == NewConditionItem.TYPE_DATA) {
                    ConditionContainer container = new DataSelectContainer(getContext());
                    container.setLayoutParams(lp);
                    container.setTag(subItem);
                    addContentView(container);
                    container.setConditionItem(subItem);
                } else {
                    continue;
                }
                if (subItem != item.subItems.get(item.subItems.size() - 1)) {
                    inflater.inflate(R.layout.layout_condition_divider, mList);
                }
            }
            View bottomBlank = new View(getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, UIUtil.dip2px(getContext(), 30));
            bottomBlank.setLayoutParams(lp);
            mList.addView(bottomBlank);
        } else if (mRoot != null) {
            for (int i = 0; i < mRoot.subItems.size(); ++i) {
                NewConditionItem subItem = mRoot.subItems.get(i);
                ConditionContainer container = (ConditionContainer) mList.findViewWithTag(subItem);
                container.setConditionItem(subItem);
            }
        }
    }

    @Override
    public NewConditionItem create(List<BaseFilterItem> itemList) {
        return null;
    }

    @Override
    public void addContentView(View v) {
        v.setLayoutParams(lp);
        mList.addView(v);
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_reset_condition) {
            if (mRoot != null) {
                mRoot.clear();
                mRoot.reset();
                setConditionItem(mRoot);
            }
        } else if (i == R.id.tv_confirm_condition) {
            mController.confirm(FilterContainer.this);
        } else if (i == R.id.blank_condition) {
            mController.cancel(FilterContainer.this);
        } else {
        }
    }
}
