package com.yijia.zkl.test;

import android.app.Application;
import android.content.Context;
import android.os.SystemClock;
import android.test.ApplicationTestCase;

import com.zero.library.base.bean.HardwareInfo;
import com.zero.library.base.db.local.LocalCatch;
import com.zero.library.base.db.local.LocalCatchDB;
import com.zero.library.base.db.local.LocalCatchImp;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;


public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testInsert() throws Exception {
        Context context = getContext();
        LocalCatchDB imp = new LocalCatchImp(context);
        LocalCatch m = new LocalCatch();
        m.setKey("key");
        m.setContent("content key");
        long insert = imp.insert(m);
        Logger.e(insert + "");
    }

    public void testInsertList() {
        Context context = getContext();
        LocalCatchDB imp = new LocalCatchImp(context);
        List<LocalCatch> localList = new ArrayList<>();
        long start = SystemClock.currentThreadTimeMillis();
        for (int i = 0; i < 100; i++) {
            LocalCatch m = new LocalCatch();
            m.setKey("key" + i);
            m.setContent("content key" + i);
            localList.add(m);
        }
        imp.insert(localList);
        Logger.e("use time = " + (SystemClock.currentThreadTimeMillis() - start));
    }

    public void testSelect() throws Exception {
        Context context = getContext();
        LocalCatchDB imp = new LocalCatchImp(context);
        List<LocalCatch> all = imp.findAll();
        Logger.e(all.size() + "");
    }

    public void testDelete() {
        Context context = getContext();
        LocalCatchDB imp = new LocalCatchImp(context);
        LocalCatch m = new LocalCatch();
        m.setKey("key");
        m.setContent("content key");
        int delete = imp.delete(m);
        Logger.e(delete + " line delete");
    }

    public void testDeleteList() {
        Context context = getContext();
        LocalCatchDB imp = new LocalCatchImp(context);
        List<LocalCatch> localList = new ArrayList<>();
        long start = SystemClock.currentThreadTimeMillis();
        for (int i = 0; i < 100; i++) {
            LocalCatch m = new LocalCatch();
            m.setKey("key" + i);
            m.setContent("content key" + i);
            localList.add(m);
        }
        imp.delete(localList);
        Logger.e("use time = " + (SystemClock.currentThreadTimeMillis() - start));
    }

    public void testPhone() {
        HardwareInfo info = new HardwareInfo(getContext());
        Logger.e("" + info.getPhoneNumber());
    }
}