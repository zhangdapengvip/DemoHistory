package com.zero.library.base.db.local;

import com.zero.library.base.annotation.db.Column;
import com.zero.library.base.annotation.db.Id;
import com.zero.library.base.annotation.db.TableName;
import com.zero.library.base.db.TableConstants;

/**
 * Created by ZeroAries on 16/3/20.
 * 本地缓存实体
 */
@TableName(TableConstants.TABLE_NAME_LOCALCACHE)
public class LocalCatch {
    @Id(autoincrement = true)
    @Column(TableConstants.TABLE_ID)
    private int id;
    @Column(TableConstants.TABLE_CACHE_KEY)
    private String key;
    @Column(TableConstants.TABLE_CACHE_CONTENT)
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "LocalCatch{" +
                "key='" + key + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
