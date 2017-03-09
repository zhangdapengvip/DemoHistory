package com.yijia.patient.ui.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.yijia.patient.R;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.uibase.DefaultAdapter;
import com.zero.library.base.utils.UtilsListView;
import com.zero.library.base.view.AppEmptyDialog;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by ZeroAries on 2016/6/23.
 * 多选弹出框
 */
public class MultiChoiceDialog extends TextView {
    public static final String SPLIT_STRING = ",";
    public static HashMap<String, List<DialogItem>> spinnersMap = new HashMap<>();
    private ListView lvItems;
    private String valueString;
    private String keyString;
    private List<DialogItem> mDialogItems;

    public MultiChoiceDialog(Context context) {
        super(context);
    }

    public MultiChoiceDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MultiChoiceDialog);
        String sourceKey = typedArray.getString(R.styleable.MultiChoiceDialog_source_key);
        int multiSize = typedArray.getInt(R.styleable.MultiChoiceDialog_multi_size, -1);
        typedArray.recycle();
        initMappingContent();
        mDialogItems = spinnersMap.get(sourceKey);
        if (null == mDialogItems || mDialogItems.size() <= 0) {
        } else {
            setOnClickListener(v -> showChoiceDialog(context, multiSize));
        }
    }

    public String getKeyString() {
        return keyString;
    }

    public void setKeyString(String keyString) {
        if (!TextUtils.isEmpty(keyString)) {
            List<String> choiceList = Arrays.asList(keyString.split(SPLIT_STRING));
            for (DialogItem info : mDialogItems) {
                info.setCheck(choiceList.contains(info.getKey()));
            }
            initKeyAndValue();
        }
        this.keyString = keyString;
    }

    public String getValueString() {
        return valueString;
    }

    private void showChoiceDialog(Context context, int multiSize) {
        AppEmptyDialog dialog = new AppEmptyDialog((Activity) context, 2);
        dialog.setBtnVisibility(1 == multiSize ? View.GONE : View.VISIBLE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        //初始化选中
        if (!TextUtils.isEmpty(keyString)) {
            List<String> choiceList = Arrays.asList(keyString.split(SPLIT_STRING));
            for (DialogItem info : mDialogItems) {
                info.setCheck(choiceList.contains(info.getKey()));
            }
        } else {
            for (DialogItem info : mDialogItems) {
                info.setCheck(false);
            }
        }

        View view = View.inflate(context, R.layout.dialog_multi_choice, null);
        lvItems = (ListView) view.findViewById(R.id.lv_items);
        DefaultAdapter<DialogItem> adapter = new DefaultAdapter<DialogItem>((Activity) context, lvItems,
                mDialogItems) {

            @Override
            protected AppBaseHolder getHolder() {
                return new AppBaseHolder<DialogItem>((Activity) context) {

                    private CheckBox mChecked;
                    private TextView mTextContent;

                    @Override
                    protected int getResLayout() {
                        return R.layout.holder_multi_choice;
                    }

                    @Override
                    public void refreshView(int position) {
                        DialogItem data = getData();
                        mTextContent.setText(data.getValue());
                        mChecked.setChecked(data.isCheck());
                    }

                    @Override
                    public void initView(View view) {
                        mTextContent = (TextView) view.findViewById(R.id.tv_content);
                        mChecked = (CheckBox) view.findViewById(R.id.cb_auto_login);
                    }
                };
            }

            @Override
            public void onItemClickInner(int position) {
                DialogItem item = mDialogItems.get(position);
                int checkCount = 0;
                for (DialogItem info : mDialogItems) {
                    if (1 == multiSize && !item.isCheck()) info.setCheck(false);
                }
                for (DialogItem info : mDialogItems) {
                    if (info.isCheck()) checkCount++;
                }
                if (multiSize == checkCount) {
                    addInfo(dialog);
                    return;
                }
                item.setCheck(!item.isCheck);
                checkCount = 0;
                for (DialogItem info : mDialogItems) {
                    if (info.isCheck()) checkCount++;
                }
                if (multiSize == checkCount) {
                    addInfo(dialog);
                }
                notifyDataSetChanged();
            }
        };
        dialog.setBtnOnClickListener((dialog1, view1) -> {
            if (AppEmptyDialog.BtnView.RIGHT == view1) {
                addInfo(dialog);
            } else {
                dialog.dismiss();
            }
        });
        lvItems.setAdapter(adapter);
        UtilsListView.setListViewMaxHeight(lvItems, 5);
        dialog.setView(view);
        dialog.show();
    }

    private void addInfo(AppEmptyDialog dialog) {
        initKeyAndValue();
        dialog.dismiss();
    }

    private void initKeyAndValue() {
        StringBuilder sbKey = new StringBuilder();
        StringBuilder sbValue = new StringBuilder();
        for (DialogItem info : mDialogItems) {
            if (info.isCheck()) {
                if (sbKey.length() > 0) sbKey.append(SPLIT_STRING);
                if (sbValue.length() > 0) sbValue.append(SPLIT_STRING);
                sbKey.append(info.getKey());
                sbValue.append(info.getValue());
            }
        }
        valueString = sbValue.toString();
        keyString = sbKey.toString();
        setText(valueString);
    }

    public MultiChoiceDialog(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initMappingContent() {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            MappingHandler handler = new MappingHandler();
            InputStream is = (getResources().getAssets().open("data/dialog_mapping.xml"));
            parser.parse(is, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class MappingHandler extends DefaultHandler {
        private List<DialogItem> mappingList;
        private String name;

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            mappingList = new ArrayList<>();
        }

        @Override
        public void startElement(String uri, String localName, String qName,
                                 Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            if (localName.equals("mapping")) {
                mappingList = new ArrayList<>();
                name = attributes.getValue("name");
            } else if (localName.equals("item")) {
                String key = attributes.getValue("key");
                String value = attributes.getValue("value");
                mappingList.add(new DialogItem(key, value));
            }
        }

        @Override
        public void characters(char[] ch, int start, int length)
                throws SAXException {
            super.characters(ch, start, length);
        }

        @Override
        public void endElement(String uri, String localName, String qName)
                throws SAXException {
            super.endElement(uri, localName, qName);
            if (localName.equals("mapping")) {
                spinnersMap.put(name, mappingList);
            }
        }
    }

    class DialogItem {
        private String key;
        private String value;
        private boolean isCheck;

        public DialogItem(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
