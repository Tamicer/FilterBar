package com.tamic.widget.filterviewsipmle;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tamic.widget.filter.BaseFilterItem;
import com.tamic.widget.filter.CategoryBar;
import com.tamic.widget.filter.ConditionContainer;
import com.tamic.widget.filter.DateSelectConditionItem;
import com.tamic.widget.filter.FilterContainer;
import com.tamic.widget.filter.FliterResultItem;
import com.tamic.widget.filter.GroupFilterItem;
import com.tamic.widget.filter.ListFilterBean;
import com.tamic.widget.filter.NewConditionItem;
import com.tamic.widget.filter.RegionContainer;
import com.tamic.widget.filter.SorterContainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import widget.tamic.com.filterviewsipmle.R;


/**
 * Created by TAMIC on 2017-07-27
 */
public class MainActivity extends AppCompatActivity {

    private CategoryBar mfilterView;
    protected NewConditionItem mRegionRoot;
    protected NewConditionItem mFilterRoot;
    protected NewConditionItem mSortRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mfilterView = (CategoryBar) findViewById(R.id.ablwf_filter_cfv);
        initFilterView();
        setFilterData();
    }


    public void initFilterView() {
        mfilterView.setFristTabText("地区");
        // mfilterView.addFristContainer(new SorterContainer(MainActivity.this));
        mfilterView.setSorterShow(true);
        mfilterView.setThreeTabText("默认排序");
        mfilterView.setOnConfirmListener(new CategoryBar.ControllerListener() {

            @Override
            public void onConfirm(ConditionContainer container) {

                if (container == mfilterView.getRegionContainer()) {
                    mRegionRoot = container.getConditionItem();
                } else if (container == mfilterView.getFilterContainer()) {
                    mFilterRoot = container.getConditionItem();
                }

                onFilterHandleResult(container);
            }

            @Override
            public void onOpen(ConditionContainer container) {

                if (container == mfilterView.getRegionContainer()) {
                    if (mRegionRoot == null) {
                        return;
                    }
                    container.setConditionItem(mRegionRoot.clone());
                } else if (container == mfilterView.getFilterContainer()) {
                    if (mFilterRoot == null) {
                        return;
                    }
                    container.setConditionItem(mFilterRoot.clone());
                }
            }

            @Override
            public void onCancel(ConditionContainer container) {

            }

            @Override
            public void onSpecial(ConditionContainer container, int id) {


            }
        });
    }

    private void onFilterHandleResult(ConditionContainer container) {

        FliterResultItem item = new FliterResultItem();

        if (container instanceof SorterContainer) {
            mSortRoot = container.getConditionItem();
            NewConditionItem lv0 = mSortRoot.firstSelectedSubItem();
            if (lv0.isEmpty()) {
                return;
            }
            onResult(item.createResult(lv0));
        }

        if (container instanceof FilterContainer) {
            mFilterRoot = container.getConditionItem();

            List<NewConditionItem> lv0 = mFilterRoot.selectedSubItems();
            if (lv0.isEmpty()) {
                return;
            }
            onResult(item.createResult(mFilterRoot));
        }

        if (container instanceof RegionContainer) {
            mRegionRoot = container.getConditionItem();
            if (mRegionRoot.selected) {
                onResult(item.createResult(mRegionRoot));
            }
        }
    }



    protected void onFilterResult(ConditionContainer container) {

        NewConditionItem mRegionRoot;
        NewConditionItem mFilterRoot;
        NewConditionItem mSortRoot;
        if (container == mfilterView.getRegionContainer()) {
            mRegionRoot = container.getConditionItem();
            NewConditionItem lv0 = mRegionRoot.firstSelectedSubItem();
            if (lv0.isEmpty()) {
                return;
            }

            return;

        } else if (container == mfilterView.getSorterContainer()) {
            mSortRoot = container.getConditionItem();
            NewConditionItem lv0 = mSortRoot.firstSelectedSubItem();
            if (lv0.isEmpty()) {
                return;
            }

            return;
        } else if (container == mfilterView.getFilterContainer()) {
            mFilterRoot = container.getConditionItem();
            NewConditionItem lv0 = mFilterRoot.subItemById(0);
            NewConditionItem selectprice = lv0.firstSelectedSubItem();
            if (!lv0.isEmpty()) {
                if (selectprice != null) {

                } else {

                }
            }

            NewConditionItem lv1 = mFilterRoot.subItemById(1);
            List<NewConditionItem> select1 = lv1.selectedSubItems();
            if (!lv1.isEmpty()) {
                if (select1 != null) {
                    for (NewConditionItem child : select1) {

                    }

                } else {

                }
            }

            NewConditionItem lv2 = mFilterRoot.subItemById(2);
            NewConditionItem select2 = lv2.firstSelectedSubItem();
          /*  if (!lv2.isEmpty()) {

                DateSelectConditionItem dataitem = (DateSelectConditionItem) lv2.subItemById(555);
                dataitem.rangeInfo.getInfo(dataitem);

                Toast.makeText(MainActivity.this, dataitem.getInfo(), Toast.LENGTH_SHORT).show();
            }*/
            if (select2 != null) {
                for (NewConditionItem child : select1) {
                    Toast.makeText(MainActivity.this, String.valueOf(child.id), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "被重置了!", Toast.LENGTH_SHORT).show();

            }
            return;
        }

    }



    /**
     * 设置筛选数据
     */
    public void setFilterData() {
        //模擬數據
        ListFilterBean listFilterBean = new ListFilterBean();

        listFilterBean.region = new ListFilterBean.ItemList();
        listFilterBean.region.name = "地铁";
        listFilterBean.region.list = new ArrayList<>();
        listFilterBean.sort = new ListFilterBean.ItemList();

        listFilterBean.sort.name = "其他";
        listFilterBean.sort.list = new ArrayList<>();
        listFilterBean.layout = new ListFilterBean.ItemList();
        listFilterBean.layout.name = "tab2";
        listFilterBean.layout.list = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            ListFilterBean.Item item = new ListFilterBean.Item();
            item.id = i;
            item.name = "item" + i;
            listFilterBean.sort.list.add(item);
        }

        for (int i = 0; i < 8; i++) {
            ListFilterBean.Item item = new ListFilterBean.Item();
            item.children = new ArrayList<>();
            item.id = i;
            item.name = "我的" + i;
            for (int j = 0; j < 4; j++) {
                ListFilterBean.Item childitem = new ListFilterBean.Item();
                if (j == 0) {
                    childitem.name = "不限";
                    childitem.id = i * j;
                } else {
                    childitem.name = "tag" + i + "-" + j;
                    childitem.id = i * j;
                }

                item.children.add(childitem);
            }
            listFilterBean.layout.list.add(item);
        }


        for (int i = 0; i < 10; i++) {
            ListFilterBean.Item item = new ListFilterBean.Item();
            item.children = new ArrayList<>();
            item.id = i;
            item.name = "地铁" + i + "号线";
            for (int j = 0; j < 20; j++) {
                ListFilterBean.Item childitem = new ListFilterBean.Item();
                childitem.name = "tamic" + i + "-" + j;
                childitem.id = i * j;
                item.children.add(childitem);
            }
            listFilterBean.region.list.add(item);
        }
        ListFilterBean.Item item = new ListFilterBean.Item();
        item.children = new ArrayList<>();
        item.id = 5000;
        item.name = "不限";
        listFilterBean.region.list.add(0, item);


        //設置
        mSortRoot = getSorterRoot(listFilterBean);
        mfilterView.getSorterContainer().setConditionItem(mSortRoot);

        //区域
        mRegionRoot = getRegionRoot(listFilterBean);
        mfilterView.getRegionContainer().setConditionItem(mSortRoot);

        //筛选
        mFilterRoot = getFilterRoot(listFilterBean);
        mfilterView.getFilterContainer().setConditionItem(mFilterRoot);

    }


    public NewConditionItem getSorterRoot(ListFilterBean filterBean) {
        NewConditionItem sorterRoot = new NewConditionItem(null, 0, CategoryBar.SORTER_ROOT, true);
        sorterRoot.processor = NewConditionItem.RADIO_LIST;
        if (filterBean.sort != null) {
            mfilterView.setThreeTabText(filterBean.sort.name);
            for (ListFilterBean.Item item : filterBean.sort.list) {
                new NewConditionItem(sorterRoot, item.id, item.name);
            }
        }
        sorterRoot.reset();
        return sorterRoot;
    }

    public NewConditionItem getFilterRoot(ListFilterBean filterBean) {
        NewConditionItem filterRoot = new NewConditionItem(null, 0, CategoryBar.FILTER_ROOT, true);


        if (filterBean != null && filterBean.layout != null) {

      /*      new NewConditionItem(filterRoot, 1111,  "选择区间", true,
                    NewConditionItem.TYPE_RANGE);*/

            for (ListFilterBean.Item item : filterBean.layout.list) {
                NewConditionItem createTimeItem = new NewConditionItem(filterRoot, item.id, item.name, true,
                        NewConditionItem.TYPE_GRID);
                createTimeItem.canReset = false;
                createTimeItem.processor = NewConditionItem.DEFAULT;
                for (ListFilterBean.Item item1 : item.children) {
                    new NewConditionItem(createTimeItem, item1.id, item1.name);
                }
            }


        }
        return filterRoot;
    }

    public NewConditionItem getRegionRoot(ListFilterBean filterBean) {

        NewConditionItem regionRoot = new NewConditionItem(null, 0, "regionRoot", true);
        regionRoot.processor = NewConditionItem.RADIO_LIST_NO_CLEAR;
        if (filterBean.region != null) {
            NewConditionItem lv0 = new NewConditionItem(regionRoot, 0, filterBean.region.name);
            lv0.processor = NewConditionItem.RADIO_LIST;
            for (ListFilterBean.Item item : filterBean.region.list) {
                NewConditionItem lv1 = new NewConditionItem(lv0, item.id, item.name);
                if (item.children == null || item.children.size() == 0) {
                    continue;
                }
                lv1.processor = NewConditionItem.DEFAULT;
                for (ListFilterBean.Item subItem : item.children) {
                    new NewConditionItem(lv1, subItem.id, subItem.name);
                }
            }
        }

        if (filterBean != null && filterBean.layout != null) {
            NewConditionItem lv0 = new NewConditionItem(regionRoot, 1, filterBean.layout.name);
            lv0.processor = NewConditionItem.RADIO_LIST;
            for (ListFilterBean.Item item : filterBean.layout.list) {
                NewConditionItem lv1 = new NewConditionItem(lv0, item.id, item.name);
                if (item.children == null || item.children.size() == 0) {
                    continue;
                }
                lv1.processor = NewConditionItem.DEFAULT;
                for (ListFilterBean.Item subItem : item.children) {
                    new NewConditionItem(lv1, subItem.id, subItem.name);
                }
            }
        }
        regionRoot.reset();
        return regionRoot;
    }

    public static String getFromAssets(Context context, String fileName) {
        BufferedReader reader = null;

        try {
            InputStream e = context.getResources().getAssets().open(fileName);
            reader = new BufferedReader(new InputStreamReader(e));
            char[] buf = new char[1024];
            boolean count = false;
            StringBuffer sb = new StringBuffer(e.available());

            String readData;
            int count1;
            while ((count1 = reader.read(buf)) != -1) {
                readData = String.valueOf(buf, 0, count1);
                sb.append(readData);
            }

            readData = sb.toString();
            return readData;
        } catch (Exception var11) {
            var11.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "";
    }

    protected void onResult(FliterResultItem resultItem) {

        if (resultItem == null) {
        }

        if (resultItem.isEmpty()) {
            Log.d("Tag", "lv0: "+ resultItem.name);
            Toast.makeText(MainActivity.this, String.valueOf(resultItem.id), Toast.LENGTH_SHORT).show();

            return;
        }

        for (FliterResultItem item : resultItem.subItems()) {

            if (item.isEmpty()) {
                continue;
            }
            for (FliterResultItem item1 : item.subItems()) {
                Log.d("Tag", "lv1: "+ item.name);
                Log.d("Tag", "lv1: "+ item1.name);
                Toast.makeText(MainActivity.this, String.valueOf(item.id), Toast.LENGTH_SHORT).show();
                for (FliterResultItem item2 : item1.subItems()) {
                    Log.d("Tag", "lv2: "+ item2.name);
                    Toast.makeText(MainActivity.this, String.valueOf(item.id), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
