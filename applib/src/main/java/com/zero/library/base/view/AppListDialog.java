package com.zero.library.base.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zero.library.base.utils.UtilsListView;
import com.zero.library.R;

public class AppListDialog extends Dialog {
    private ItemSelectListener onSelectListener;

    public AppListDialog(Context context, String[] arr) {
        super(context, R.style.app_dialog_style);
        setContentView(R.layout.wgt_list_dialog_layout);

        ListView mDialogList = (ListView) findViewById(R.id.dialog_list);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context,
                R.layout.wgt_list_dialog_item, arr);
        mDialogList.setAdapter(arrayAdapter);
        UtilsListView.setListViewHeightBasedOnChildren(mDialogList);
        mDialogList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                onSelectListener.onSelect(position);
            }
        });
    }

    public void setOnSelectListener(ItemSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    public interface ItemSelectListener {
        public void onSelect(int position);
    }

}