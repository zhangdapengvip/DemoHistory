package com.zero.library.base.db.local;

import android.content.Context;

import com.zero.library.base.db.base.DBInterfaceImp;

/**
 * Created by ZeroAries on 16/3/20.
 * 本地缓存实现类
 */
public class LocalCatchImp extends DBInterfaceImp<LocalCatch> implements LocalCatchDB {
    public LocalCatchImp(Context context) {
        super(context);
    }
}
