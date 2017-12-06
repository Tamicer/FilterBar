package com.tamic.widget.filter;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import widget.tamic.com.filterviewsipmle.R;

/**
 *
 * Created by Tamic on 2017-07-27
 */
public class RegionContainer extends ConditionContainer implements View.OnClickListener {

    private FilterMode mFilterMode;

    public static int TYPE_NORMAL = 1000;
    public static int TYPE_EDIT = 1001;
    public static int TYPE_ALL = 1002;


    public RegionContainer(Context context, FilterMode filterMode) {
        super(context);
        mFilterMode = filterMode;
        init();
    }

    public RegionContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.layout_condition_region, this);
        mTabGroup = (LinearLayout) findViewById(R.id.tab_layout_condition_region);
        mPrimaryList = (RecyclerView) findViewById(R.id.list_primary_layout_condition_region);
        mSecondaryList = (RecyclerView) findViewById(R.id.list_secondary_layout_condition_region);
        mConfirmPannel = (LinearLayout) findViewById(R.id.bottom_layout_condition_region);

        if (checkConfimPannelVisibility()) {
            mConfirmPannel.setVisibility(View.VISIBLE);
            this.invalidate();
        }

        mDecoration = new DividerItemDecoration(getContext(), R.drawable.shape_list_divider);
        mPrimaryList.addItemDecoration(mDecoration);

        mPrimaryList.setLayoutManager(new LinearLayoutManager(getContext()));
        mSecondaryList.setLayoutManager(new LinearLayoutManager(getContext()));
        mPrimaryAdapter = new ConditionItemAdapter(R.layout.item_condition_2, mLevelTwoClickListener);
        mSecondaryAdapter = new ConditionItem2Adapter(R.layout.item_condition_2, R.layout.item_condition_3, mLevelThreeClickListener);
        mSecondaryAdapter.setOnItemClickListener(mLevelThreeItemListener);
        mPrimaryList.setAdapter(mPrimaryAdapter);
        mSecondaryList.setAdapter(mSecondaryAdapter);

        mTvReset = (TextView) findViewById(R.id.tv_reset_condition);
        mTvConfirm = (TextView) findViewById(R.id.tv_confirm_condition);
        mTvReset.setOnClickListener(this);
        mTvConfirm.setOnClickListener(this);
        findViewById(R.id.blank_condition).setOnClickListener(this);
    }

    private LinearLayout mTabGroup;
    private LinearLayout mConfirmPannel;
    private RecyclerView mPrimaryList;
    private RecyclerView mSecondaryList;
    private TextView mTvReset;
    private TextView mTvConfirm;
    private ConditionItemAdapter mPrimaryAdapter;
    private ConditionItem2Adapter mSecondaryAdapter;
    private DividerItemDecoration mDecoration;
    private NewConditionItem mRoot;
    private boolean mSetting = false;

    private OnClickListener mLevelOneClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            CompoundButton cb = (CompoundButton) v;
            NewConditionItem item = (NewConditionItem) cb.getTag();
            boolean refresh = item.parent.processSubItems(item, cb.isChecked());
            cb.setChecked(item.selected);
            if (refresh) {
                setLv0Item(item.parent);
            }
        }
    };
    private OnClickListener mLevelTwoClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            CompoundButton cb = (CompoundButton) v;
            NewConditionItem item = (NewConditionItem) cb.getTag();
            boolean refresh = item.parent.processSubItems(item, cb.isChecked());
            cb.setChecked(item.selected);
            if (refresh) {
                item.rootClear();
                setLv1Item(item.parent);
            }

        }
    };

    private OnClickListener mLevelThreeClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            CompoundButton cb = (CompoundButton) v;
            NewConditionItem item = (NewConditionItem) cb.getTag();
            boolean refresh = item.parent.processSubItems(item, cb.isChecked());
            cb.setChecked(item.selected);
            if (refresh) {
                item.rootClear();
                setLv2Item(item.parent);
            }

            if (!checkConfimPannelVisibility()) {
                refreshDrawableState();
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mController.confirm(RegionContainer.this);
                    }
                }, 20);
            }


        }
    };

    private OnItemClickListener mLevelThreeItemListener = new OnItemClickListener() {

        @Override
        public void onEditClick(View v, int viewType) {
            mController.onItemClick(RegionContainer.this, viewType);
        }
    };

    @Override
    public NewConditionItem getConditionItem() {
        return mRoot;
    }

    @Override
    public void setConditionItem(NewConditionItem item) {
        mRoot = item;
        if (item == null) {
            mTabGroup.removeAllViews();
            return;
        }
        if (this.isLayoutRequested()) {
            this.addOnLayoutChangeListener(new OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    v.removeOnLayoutChangeListener(this);
                    mSetting = true;
                    setLv0Item(mRoot);
                    mSetting = false;
                }
            });
        } else {
            mSetting = true;
            setLv0Item(mRoot);
            mSetting = false;
        }
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
    public void onItemClick(ConditionContainer container) {
        super.onItemClick(container);
        mController.confirm(container);
    }

    private void setLv0Item(NewConditionItem item) {
        mTabGroup.removeAllViews();
        for (NewConditionItem subItem : item.subItems) {
            CompoundButton btn = (CompoundButton) LayoutInflater.from(getContext()).inflate(
                    R.layout.item_condition_1, mTabGroup, false);
            btn.setText(subItem.name);
            btn.setChecked(subItem.selected);
            btn.setTag(subItem);
            btn.setOnClickListener(mLevelOneClickListener);
            mTabGroup.addView(btn);
        }
        for (NewConditionItem subItem : item.subItems) {
            if (subItem.selected) {
                setLv1Item(subItem);
                return;
            }
        }
        setLv1Item(null);
    }

    private void setLv1Item(NewConditionItem item) {
        if (item == null) {
            mPrimaryList.setVisibility(View.GONE);
            setLv2Item(null);
            return;
        }
        if (mPrimaryList.getVisibility() != View.VISIBLE) {
            mPrimaryList.setVisibility(View.VISIBLE);
        }
        mPrimaryAdapter.setList(item.subItems);
        mPrimaryAdapter.notifyDataSetChanged();

        for (int i = 0; i < item.subItems.size(); ++i) {
            NewConditionItem subItem = item.subItems.get(i);
            if (subItem.selected) {
                setLv2Item(subItem);
                if (mSetting) {
                    checkSelectedItemVisibility(mPrimaryList, i);
                }
                return;
            }
        }
        setLv2Item(null);
    }

    private void setLv2Item(NewConditionItem item) {
        if (item == null || item.isLeaf()) {
            closeSecondaryList();
            return;
        }
        mSecondaryAdapter.setList(item.subItems);
        mSecondaryAdapter.notifyDataSetChanged();
        for (int i = 0; i < item.subItems.size(); ++i) {
            NewConditionItem subItem = item.subItems.get(i);
            if (subItem.selected) {
                if (mSetting) {
                    checkSelectedItemVisibility(mSecondaryList, i);
                }
            }
        }
        openSecondaryList();
    }

    private boolean mIsAnimating;
    private void closeSecondaryList() {
        final int w = getWidth();
        if (mSetting) {
            ViewGroup.LayoutParams lp;
            lp = mPrimaryList.getLayoutParams();
            lp.width = w;
            mPrimaryList.setLayoutParams(lp);
            lp = mSecondaryList.getLayoutParams();
            lp.width = 0;
            mSecondaryList.setLayoutParams(lp);
        } else {
            if (!mIsAnimating && mSecondaryList.getWidth() != 0) {
                ValueAnimator anime = ValueAnimator.ofInt(w / 2, w);
                anime.setDuration(500);
                anime.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Integer value = (Integer) animation.getAnimatedValue();
                        mPrimaryList.layout(
                                mPrimaryList.getLeft(),
                                mPrimaryList.getTop(),
                                value,
                                mPrimaryList.getBottom()
                        );
                        mSecondaryList.layout(
                                value,
                                mSecondaryList.getTop(),
                                value + w / 2,
                                mSecondaryList.getBottom()
                        );
                    }
                });
                anime.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        mIsAnimating = true;
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ViewGroup.LayoutParams lp;
                        lp = mPrimaryList.getLayoutParams();
                        lp.width = w;
                        mPrimaryList.setLayoutParams(lp);
                        lp = mSecondaryList.getLayoutParams();
                        lp.width = 0;
                        mSecondaryList.setLayoutParams(lp);
                        mIsAnimating = false;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                anime.start();
            }
        }
    }
    private void openSecondaryList() {
        final int w = getWidth();
        if (mSetting) {
            ViewGroup.LayoutParams lp;
            lp = mPrimaryList.getLayoutParams();
            lp.width = w / 2;
            mPrimaryList.setLayoutParams(lp);
            lp = mSecondaryList.getLayoutParams();
            lp.width = w / 2;
            mSecondaryList.setLayoutParams(lp);
        } else {
            if (!mIsAnimating && mSecondaryList.getWidth() == 0) {
                ValueAnimator anime = ValueAnimator.ofInt(w, w / 2);
                anime.setDuration(500);
                anime.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Integer value = (Integer) animation.getAnimatedValue();
                        mPrimaryList.layout(
                                mPrimaryList.getLeft(),
                                mPrimaryList.getTop(),
                                value,
                                mPrimaryList.getBottom()
                        );
                        mSecondaryList.layout(
                                value,
                                mSecondaryList.getTop(),
                                value + w / 2,
                                mSecondaryList.getBottom()
                        );
                    }
                });
                anime.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        mIsAnimating = true;
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ViewGroup.LayoutParams lp;
                        lp = mPrimaryList.getLayoutParams();
                        lp.width = w / 2;
                        mPrimaryList.setLayoutParams(lp);
                        lp = mSecondaryList.getLayoutParams();
                        lp.width = w / 2;
                        mSecondaryList.setLayoutParams(lp);
                        mIsAnimating = false;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                anime.start();
            }
        }
    }

    private void checkSelectedItemVisibility(RecyclerView listView, int position) {
        listView.scrollToPosition(position);
    }

    private boolean checkConfimPannelVisibility() {
    /*    if (mCallback == null) {
            retur;
        }*/
       // return FilterMode.ENTER.ordinal() == mCallback.geFiterMode().ordinal();
        return  FilterMode.ENTER.ordinal() == mFilterMode.ordinal();
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
            mController.confirm(RegionContainer.this);
        } else if (i == R.id.blank_condition) {
            mController.cancel(RegionContainer.this);
        } else {
        }
    }

    private static class ConditionItemAdapter extends RecyclerView.Adapter<VH> {

        static final List<NewConditionItem> EMPTY_LIST = new ArrayList<>();
        private List<NewConditionItem> mList = EMPTY_LIST;
        private int mItemLayoutID;
        private OnClickListener mOnClickListener;

        public ConditionItemAdapter(int itemLayoutID, OnClickListener listener) {
            mItemLayoutID = itemLayoutID;
            mOnClickListener = listener;
        }

        public void setList(List<NewConditionItem> list) {
            if (list == null) {
                list = EMPTY_LIST;
            }
            mList = list;
        }

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            return new VH(LayoutInflater.from(parent.getContext()).inflate(
                    mItemLayoutID, parent, false));
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            NewConditionItem item = mList.get(position);
            holder.tb.setText(item.name);
            holder.tb.setChecked(item.selected);
            holder.tb.setTag(item);
            holder.tb.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

    }


    private static class ConditionItem2Adapter extends RecyclerView.Adapter<RegionContainer.VH> {

        static final List<NewConditionItem> EMPTY_LIST = new ArrayList<>();
        private final int mItemSpecLayoutID;
        private List<NewConditionItem> mList = EMPTY_LIST;
        private int mItemLayoutID;
        private OnClickListener mOnClickListener;
        private OnItemClickListener mOnItemClickListener;

        public ConditionItem2Adapter(int itemLayoutID,int itemSpecLayoutID ,OnClickListener listener) {
            mItemLayoutID = itemLayoutID;
            mItemSpecLayoutID = itemSpecLayoutID;
            mOnClickListener = listener;
        }

        public OnItemClickListener getOnItemClickListener() {
            return mOnItemClickListener;
        }

        public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
            this.mOnItemClickListener = mOnItemClickListener;
        }

        public void setList(List<NewConditionItem> list) {
            if (list == null) {
                list = EMPTY_LIST;
            }
            mList = list;
        }

        @Override
        public RegionContainer.VH onCreateViewHolder(ViewGroup parent, int viewType) {

            if (viewType == TYPE_NORMAL) {
                return new RegionContainer.VH(LayoutInflater.from(parent.getContext()).inflate(
                    mItemLayoutID, parent, false));
            } else if (viewType == TYPE_EDIT) {
                 return new RegionContainer.VH(LayoutInflater.from(parent.getContext()).inflate(
                         mItemSpecLayoutID, parent, false));
            }

            return new RegionContainer.VH(LayoutInflater.from(parent.getContext()).inflate(
                    mItemLayoutID, parent, false));
        }

        @Override
        public void onBindViewHolder(RegionContainer.VH holder, final int position) {
            final NewConditionItem item = mList.get(position);
            if (holder.getItemViewType() == TYPE_EDIT) {
                CompoundButton btnEdit = holder.tb;
                        btnEdit.setText(item.name);
                btnEdit.setChecked(item.selected);
                btnEdit.setTag(item);
                btnEdit.setId(holder.getItemViewType());
                btnEdit.setOnClickListener(mOnClickListener);
                final View finalView = btnEdit;
                final NewConditionItem finalitem =item;
                btnEdit.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener!= null) {
                           mOnItemClickListener.onEditClick(finalView, item.id);
                        }
                    }
                });

            } else {
                CompoundButton btn = holder.tb;
                btn.setText(item.name);
                btn.setChecked(item.selected);
                btn.setTag(item);
                btn.setId(holder.getItemViewType());
                btn.setOnClickListener(mOnClickListener);
            }

        }

        @Override
        public int getItemViewType(int position) {
            if (mList!=null && !mList.isEmpty()) {
                NewConditionItem item = mList.get(position);
                if (item.id == TYPE_EDIT) {
                    return TYPE_EDIT;
                }
            }
            return super.getItemViewType(position);
        }


        @Override
        public int getItemCount() {
            return mList.size();
        }

        @Override
        public void onViewRecycled(RegionContainer.VH holder) {
            super.onViewRecycled(holder);
            CompoundButton btn = (CompoundButton) holder.tb;

        }
    }

    public interface OnItemClickListener {
        void onEditClick(View v, int viewType);
    }

    private static class VH extends RecyclerView.ViewHolder {
        CompoundButton tb;
        public VH(View itemView) {
            super(itemView);
            tb = (CompoundButton) itemView.findViewById(R.id.tb);
        }
    }

    private static class DividerItemDecoration extends RecyclerView.ItemDecoration {

        private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

        private Drawable mDivider;

        /**
         * Custom divider will be used
         */
        public DividerItemDecoration(Context context, int resId) {
            mDivider = ContextCompat.getDrawable(context, resId);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }
}
