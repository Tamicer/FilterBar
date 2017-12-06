package com.tamic.widget.filter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * @link {https://github.com/Tamicer/FilterBar}
 */
public class UIUtil {

    public UIUtil() {
    }

    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5F);
    }

    public static int dip2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5F);
    }

    public static void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(-1, -2);
        }

        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, 1073741824);
        } else {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        }

        child.measure(childWidthSpec, childHeightSpec);
    }

    public static List<View> getViewGroupAllLeafs(ViewGroup root) {
        ArrayList ret = new ArrayList();
        if (root.getChildCount() != 0) {
            for (int i = 0; i < root.getChildCount(); ++i) {
                try {
                    ViewGroup e = (ViewGroup) root.getChildAt(i);
                    ret.addAll(getViewGroupAllLeafs(e));
                } catch (Exception var4) {
                    ret.add(root.getChildAt(i));
                }
            }
        }

        return ret;
    }

    public static List<View> getViewGroupAll(ViewGroup root) {
        ArrayList ret = new ArrayList();
        if (root.getChildCount() != 0) {
            for (int i = 0; i < root.getChildCount(); ++i) {
                try {
                    ret.add(root.getChildAt(i));
                    ViewGroup e = (ViewGroup) root.getChildAt(i);
                    ret.addAll(getViewGroupAll(e));
                } catch (Exception var4) {
                    ret.add(root.getChildAt(i));
                }
            }
        }

        return ret;
    }

    public static Bitmap convertViewToBitmap(View view) {
        if (view.getLayoutParams() == null) {
            view.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        }

        measureView(view);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.destroyDrawingCache();
        view.buildDrawingCache();
        return view.getDrawingCache();
    }

    public static int getWindowHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getWindowWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static float getWindowDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static int getListViewMesureHeight(ListView lv) {
        int height = 0;

        try {
            ListAdapter la = lv.getAdapter();
            int count = la.getCount();

            for (int i = 0; i < count; ++i) {
                View listItem = la.getView(i, (View) null, lv);
                listItem.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
                height += listItem.getMeasuredHeight();
            }

            height += lv.getDividerHeight() * (count - 1);
        } catch (Exception var6) {
            ;
        }

        return height;
    }

    public static float px2sp(Context context, float px) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return px / scaledDensity;
    }

    public static float sp2px(Context context, float sp) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return sp * scaledDensity;
    }

    public static View getFirstChildView(Activity activity) {
        return ((ViewGroup) ((ViewGroup) activity.getWindow().getDecorView().findViewById(16908290)).getChildAt(0)).getChildAt(0);
    }
}