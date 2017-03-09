package com.zero.library.base.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class UtilsListView {

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        int width = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
            if (listItem.getMeasuredWidth() > width) {
                width = listItem.getMeasuredWidth();
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        if (width > 0 && listView.getLayoutParams().width == -2) {
            params.width = width;
        }
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        listView.setLayoutParams(params);
    }

    public static void setListViewMaxHeight(ListView listView, int maxItem) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        int width = 0;
        if (listAdapter.getCount() > maxItem) {
            for (int i = 0; i < maxItem; i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
                if (listItem.getMeasuredWidth() > width) {
                    width = listItem.getMeasuredWidth();
                }
            }
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.height = totalHeight + (listView.getDividerHeight() * (maxItem - 1));
            // listView.getDividerHeight()获取子项间分隔符占用的高度
            listView.setLayoutParams(params);
        }
    }

    public static void setListViewMinHeight(ListView listView, int minItem) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int itemHeight = 0;
        if (listAdapter.getCount() > 0) {
            View listItem = listAdapter.getView(0, null, listView);
            listItem.measure(0, 0);
            itemHeight = listItem.getMeasuredHeight();
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.height = itemHeight * minItem + (listView.getDividerHeight() * (minItem - 1));
            // listView.getDividerHeight()获取子项间分隔符占用的高度
            listView.setLayoutParams(params);
        }
    }
}
