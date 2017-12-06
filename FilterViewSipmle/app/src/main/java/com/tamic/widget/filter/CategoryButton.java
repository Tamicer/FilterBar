package com.tamic.widget.filter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import widget.tamic.com.filterviewsipmle.R;


/**
 * Created by Tamic on 2016-08-24.
 */
public class CategoryButton extends FrameLayout implements CategoryCheckable {

    public CategoryButton(Context context) {
        super(context);
        init();
    }

    public CategoryButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    static private final int COLOR_TEXT_CHECK = 0xff6281c2;
    static private final int COLOR_TEXT_UNCHECK = 0xff555555;
    static private final int COLOR_ICON_CHECK = 0xff6281c2;
    static private final int COLOR_ICON_UNCHECK = 0xff555555;
    static private final int TEXT_SIZE = 14; //sp

    private View mContent;
    private TextView mTvText;
    private TextView mTvIcon;
    private boolean mChecked = false;
    private String mText;
    private OnCheckedChangeListener mOnCheckedChangedListener;
    private OnClickListener mOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            setChecked(!mChecked);

        }
    };

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_category_button, this);
        mContent = findViewById(R.id.content_layout_category_button);
        mTvText = (TextView) findViewById(R.id.tv_text_layout_category_button);
        mTvIcon = (TextView) findViewById(R.id.tv_icon_layout_category_button);
        mTvText.setTextSize(TEXT_SIZE);
        mTvIcon.setTextSize(TEXT_SIZE);
        mContent.setOnClickListener(mOnClickListener);
        update();

    }

    public void setChecked(boolean checked) {
        if (mChecked != checked) {
            mChecked = checked;
            update();
            if (mOnCheckedChangedListener != null) {
                mOnCheckedChangedListener.onCheckedChanged(this, mChecked);
            }
        }
    }

    public boolean isChecked() {
        return mChecked;
    }

    public void setText(String text) {
        mText = text;
        mTvText.setText(mText);
    }

    public String getText() {
        return mTvText.getText().toString();
    }

    public void setOnCheckedChangedListener(OnCheckedChangeListener listener) {
        mOnCheckedChangedListener = listener;
    }

    private void update() {
        if (mChecked) {
            mTvText.setTextColor(COLOR_TEXT_CHECK);
            mTvIcon.setTextColor(COLOR_ICON_CHECK);
            mTvIcon.setBackground(getContext().getResources().getDrawable(R.drawable.more_unfold));
        } else {
            mTvText.setTextColor(COLOR_TEXT_UNCHECK);
            mTvIcon.setTextColor(COLOR_ICON_UNCHECK);
            mTvIcon.setBackground(getContext().getResources().getDrawable(R.drawable.less));
        }
    }
}
