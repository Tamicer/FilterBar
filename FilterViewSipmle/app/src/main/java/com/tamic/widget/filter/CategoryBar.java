package com.tamic.widget.filter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;


import java.util.HashMap;

import widget.tamic.com.filterviewsipmle.R;


/**
 * Created by tamic on 2017-08-03.
 *
 * @link {https://github.com/Tamicer/FilterBar}
 */
public class CategoryBar extends FrameLayout implements ConditionContainer.Controller, ConditionContainer.FiterModeCallback {

    static final int MODE_LIST = 0;
    static final int MODE_MAP = 1;
    static final int MODE_BRAND_LIST = 2;
    static final int MODE_ED = 2;

    public static final String FILTER_ROOT = "filterRoot";
    public static final String REGION_ROOT = "regionRoot";
    public static final String SORTER_ROOT = "sorterRoot";


    private FilterMode filterMode;
    private FiterModeCallback mCallback;
    private int mMode = MODE_LIST;
    CategoryButton mRegion;
    CategoryButton mFilter;
    CategoryButton mSorter;
    PopupWindow mPopup;

    boolean isShowRegion = true;
    boolean isShowFilter = true;
    boolean isShowSorter = true;

    @Override
    public boolean isShown() {
        return super.isShown();
    }

    HashMap<CategoryCheckable, ConditionContainer> mBtnMap = new HashMap<>();

    private ControllerListener mControllerListener;

    public CategoryBar(Context context) {
        super(context);
        init();
    }

    public CategoryBar(Context context, FiterModeCallback callback) {
        super(context);
        mCallback = callback;
        init();
    }

    public CategoryBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        LayoutInflater.from(getContext()).inflate(R.layout.layout_category_bar_mode_brand_list, this);
        mRegion = (CategoryButton) findViewById(R.id.btn_region_category_bar);

        if (mRegion != null) {
            mRegion.setText("区域");
            mRegion.setOnCheckedChangedListener(mButtonOnChecked);
            mRegionContainer = new RegionContainer(getContext(), geFiterMode());
            mRegionContainer.setController(this);
            mRegionContainer.setModeCallback(this);
            mBtnMap.put(mRegion, mRegionContainer);
        }

        mFilter = (CategoryButton) findViewById(R.id.btn_filter_category_bar);

        if (mFilter != null) {
            mFilter.setText("筛选");
            mFilter.setOnCheckedChangedListener(mButtonOnChecked);
            mFilterContainer = new FilterContainer(getContext());
            mFilterContainer.setController(this);
            mBtnMap.put(mFilter, mFilterContainer);
        }

        mSorter = (CategoryButton) findViewById(R.id.btn_sorter_category_bar);

        if (mSorter != null) {
            mSorter.setText("排序");
            mSorter.setOnCheckedChangedListener(mButtonOnChecked);
            mSorterContainer = new SorterContainer(getContext());
            mSorterContainer.setController(this);
            mBtnMap.put(mSorter, mSorterContainer);
        }
        if (!isShowSorter) {
            mSorter.setVisibility(GONE);
        }

        mPopup = new PopupWindow();
    }

    public CategoryBar setRegionShow(boolean isShow) {

        if (isShow) {
            mRegion.setVisibility(VISIBLE);
        } else {
            mRegion.setVisibility(GONE);
        }
        return this;
    }

    public CategoryBar setFilterShow(boolean isShow) {

        if (isShow) {
            mFilter.setVisibility(VISIBLE);
        } else {
            mFilter.setVisibility(GONE);
        }
        return this;
    }

    public CategoryBar setSorterShow(boolean isShow) {

        if (isShow) {
            mSorter.setVisibility(VISIBLE);
        } else {
            mSorter.setVisibility(GONE);
        }
        return this;
    }

    public CategoryBar setFristTabText(String text) {
        if (mRegion != null) {
            mRegion.setText(text);
        }
        return this;
    }

    public CategoryBar setSecondTabText(String text) {
        if (mFilter != null) {
            mFilter.setText(text);
        }
        return this;
    }

    public CategoryBar setThreeTabText(String text) {
        if (mSorter != null) {
            mSorter.setText(text);
        }
        return this;
    }

    public void setFilterCallback(FiterModeCallback callback) {
        mCallback = callback;
    }

    private void cancelBtn(CategoryCheckable btn) {
        if (btn != null && btn.isChecked()) {
            btn.setChecked(false);
        }
    }

    final private CategoryCheckable.OnCheckedChangeListener mButtonOnChecked =
            new CategoryCheckable.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CategoryCheckable button, boolean isChecked) {
                    if (isChecked) {
                        CategoryCheckable[] checkables = new CategoryCheckable[] {
                                mRegion, mFilter, mSorter
                        };
                        for (CategoryCheckable checkable : checkables) {
                            if (checkable != button) {
                                cancelBtn(checkable);
                            }
                        }
                    }
                    ConditionContainer container = mBtnMap.get(button);
                    if (container == null) {
                        return;
                    }
                    if (isChecked) {
                        if (mControllerListener != null) {
                            mControllerListener.onOpen(container);
                        }
                        mActive = button;
                        int[] location = new int[2];
                        getLocationInWindow(location);
                        int winWidth = UIUtil.getWindowWidth(getContext());
                        int winHeight = UIUtil.getWindowHeight(getContext());


                        View empty = new View(getContext());
                        ViewGroup.LayoutParams lp = new LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT);
                        empty.setLayoutParams(lp);

                        mPopup.setWidth(winWidth);
                        mPopup.setHeight(winHeight - getHeight() - location[1]);
                        mPopup.setContentView(container);
                        mPopup.setBackgroundDrawable(new ColorDrawable(0xcc000000));
                        mPopup.showAsDropDown(CategoryBar.this);
                    } else {
                        mPopup.dismiss();
                        mActive = null;
                        if (mControllerListener != null) {
                            mControllerListener.onCancel(container);
                        }
                    }
                }
            };

    private CategoryCheckable mActive;
    private ConditionContainer mRegionContainer;
    private ConditionContainer mFilterContainer;
    private ConditionContainer mSorterContainer;
    private ConditionContainer mDNAContainer;

    public static int getModeList() {
        return MODE_LIST;
    }

    public ConditionContainer getRegionContainer() {
        return mRegionContainer;
    }

    public void addFristContainer(ConditionContainer container) {
        this.mRegionContainer = container;
        mRegionContainer.setController(this);
        mRegionContainer.setModeCallback(this);
        mBtnMap.put(mRegion, mRegionContainer);
    }

    public ConditionContainer getFilterContainer() {
        return mFilterContainer;
    }

    public void addSecondContainer(ConditionContainer container) {
        this.mFilterContainer = container;
        mFilterContainer.setController(this);
        mFilterContainer.setModeCallback(this);
        mBtnMap.put(mFilter, mFilterContainer);
    }

    public void addThreeContainer(ConditionContainer container) {
        this.mSorterContainer = container;
        mSorterContainer.setController(this);
        mSorterContainer.setModeCallback(this);
        mBtnMap.put(mSorter, mSorterContainer);
    }

    public ConditionContainer getSorterContainer() {
        return mSorterContainer;
    }

    public ConditionContainer getDNAContainer() {
        return mDNAContainer;
    }

    public void setDNAContainer(ConditionContainer mDNAContainer) {
        this.mDNAContainer = mDNAContainer;
    }

    private void setHandle(ConditionContainer container) {
        container.setController(this);
        container.setModeCallback(this);
    }

    @Override
    public void confirm(ConditionContainer container) {
        if (mControllerListener != null) {
            mControllerListener.onConfirm(container);
        }
        if (mActive != null) {
            mActive.setChecked(false);
        }
    }

    @Override
    public void cancel(ConditionContainer container) {
        if (mActive != null) {
            mActive.setChecked(false);
        }
    }

    @Override
    public void onItemClick(ConditionContainer container, int id) {

        if (mControllerListener != null) {
            mControllerListener.onSpecial(container, id);
        }
    }

    public void setMode(FilterMode mode) {
        filterMode = mode;
    }

    public void setOnConfirmListener(ControllerListener listener) {
        mControllerListener = listener;
    }

    @Override
    public FilterMode geFiterMode() {
        if (mCallback == null) {
            return FilterMode.ENTER;
        }
        return mCallback.setFiterMode();
    }

    public interface ControllerListener {

        void onConfirm(ConditionContainer container);

        void onOpen(ConditionContainer container);

        void onCancel(ConditionContainer container);

        void onSpecial(ConditionContainer container, int id);
    }

    public interface FiterModeCallback {

        FilterMode setFiterMode();
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility != VISIBLE) {  //需要关闭popup
            mPopup.dismiss();
        }
    }
}
