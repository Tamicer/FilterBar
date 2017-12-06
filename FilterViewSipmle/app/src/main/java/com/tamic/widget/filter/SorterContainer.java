package com.tamic.widget.filter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import java.util.List;

import widget.tamic.com.filterviewsipmle.R;

/**
 * Created by tamic on 2017-08-03.
 *
 * @link {https://github.com/Tamicer/FilterBar}
 */
public class SorterContainer extends ConditionContainer implements View.OnClickListener {

    public SorterContainer(Context context) {
        super(context);
        init();
    }

    public SorterContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_condition_sorter, this);
        mList = (LinearLayout) findViewById(R.id.list_condition_sorter);
        findViewById(R.id.blank_condition).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mController.cancel(SorterContainer.this);
            }
        });
    }

    private NewConditionItem mRoot;
    private LinearLayout mList;

    @Override
    public NewConditionItem getConditionItem() {
        return mRoot;
    }

    @Override
    public NewConditionItem create(List<BaseFilterItem> sorterBean) {

        NewConditionItem sorterRoot = new NewConditionItem(null, 0, CategoryBar.SORTER_ROOT, true);
        sorterRoot.processor = NewConditionItem.RADIO_LIST;
        if (sorterBean != null) {
            for (BaseFilterItem item : sorterBean) {
                new NewConditionItem(sorterRoot, item.getId(), item.getValue());
            }
        }
        sorterRoot.reset();
        return sorterRoot;
    }

    @Override
    public void addContentView(View view) {
        addView(view);
    }

    @Override
    public void setConditionItem(NewConditionItem item) {
        mRoot = item;
        mList.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (NewConditionItem subItem : mRoot.subItems) {
            CompoundButton cb = (CompoundButton) inflater.inflate(
                    R.layout.item_condition_1, mList, false);
            cb.setTag(subItem);
            cb.setText(subItem.name);
            cb.setChecked(subItem.selected);
            cb.setOnClickListener(this);
            mList.addView(cb);

            if (subItem != mRoot.subItems.get(mRoot.subItems.size() - 1)) {
                inflater.inflate(R.layout.layout_condition_divider, mList);
            }
        }
    }

    @Override
    public void onClick(View v) {
        CompoundButton cb = (CompoundButton) v;
        NewConditionItem item = (NewConditionItem) cb.getTag();
        boolean refresh = item.parent.processSubItems(item, cb.isChecked());
        cb.setChecked(item.selected);
        if (refresh) {
            setConditionItem(mRoot);
            this.postDelayed(new Runnable() {

                @Override
                public void run() {
                    mController.confirm(SorterContainer.this);
                }
            }, 20);
        }
    }
}
