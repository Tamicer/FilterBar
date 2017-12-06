package com.tamic.widget.filter;

/**
 * Created by tamic on 2016-08-26.
 *
 * @link {https://github.com/Tamicer/FilterBar}
 */
public interface CategoryCheckable {

    interface OnCheckedChangeListener {

        void onCheckedChanged(CategoryCheckable button, boolean isChecked);
    }

    void setChecked(boolean checked);

    boolean isChecked();

    void setText(String text);

    void setOnCheckedChangedListener(OnCheckedChangeListener listener);
}
