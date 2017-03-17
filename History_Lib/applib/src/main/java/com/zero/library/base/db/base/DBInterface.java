package com.zero.library.base.db.base;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ZeroAries on 16/3/20.
 * 通用实体操作的接口
 */

public interface DBInterface<M> {

    long insert(M m);

    void insert(List<M> list);

    int delete(Serializable id);

    void deleteByIdList(List<Serializable> isList);

    int delete(M m);

    void delete(List<M> list);

    int update(M m);

    List<M> findAll();

    List<M> findByCondition(String[] columns, String selection, String[] selectionArgs, String groupBy, String
            having, String orderBy, String limit);

    List<M> findByCondition(String[] columns, String selection, String[] selectionArgs, String orderBy, String
            limit);

    List<M> findByCondition(String[] columns, String selection, String[] selectionArgs);

    List<M> findByCondition(String[] columns, M m);
}