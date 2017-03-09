package com.zero.library.base.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    private static final String mDBName = "base.db";

    public DBOpenHelper(Context context) {
        super(context, mDBName, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TableConstants.CREATE_LOCALCATCH);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
