package com.zero.library.base.db.base;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.zero.library.base.annotation.db.Column;
import com.zero.library.base.annotation.db.Id;
import com.zero.library.base.annotation.db.TableName;
import com.zero.library.base.db.DBOpenHelper;
import com.zero.library.base.db.TableConstants;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ZeroAries on 16/3/20.
 * 使用注解完成数据库通用基本操作
 * {@link Column},{@link Id},{@link TableName}
 */
public abstract class DBInterfaceImp<M> implements DBInterface<M> {
    protected Context context;
    protected DBOpenHelper helper;
    protected SQLiteDatabase db;

    public DBInterfaceImp(Context context) {
        super();
        this.context = context;
        helper = new DBOpenHelper(context);
        db = helper.getWritableDatabase();
    }

    /**
     * 插入实体
     *
     * @param m 插入实体
     * @return the row ID of the newly inserted row
     */
    @Override
    public long insert(M m) {
        ContentValues values = new ContentValues();
        fillContentValues(m, values);
        return db.insert(getTableName(), null, values);
    }

    /**
     * 使用事务,插入实体列表对应数据
     *
     * @param list 实体列表
     */
    @Override
    public void insert(List<M> list) {
        try {
            db.beginTransaction();
            for (M m : list) {
                insert(m);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    /**
     * 根据Id删除列
     *
     * @param id 主键
     * @return the number of rows affected if a whereClause is passed in
     */
    @Override
    public int delete(Serializable id) {
        return db.delete(getTableName(), TableConstants.TABLE_ID + " = ?", new String[]{id.toString()});
    }

    /**
     * 使用事务,删除Id列表对应数据
     *
     * @param isList
     */
    @Override
    public void deleteByIdList(List<Serializable> isList) {
        try {
            db.beginTransaction();
            for (Serializable id : isList) {
                delete(id);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    /**
     * 根据实体内容删除,不包含主键
     *
     * @param m
     * @return
     */
    @Override
    public int delete(M m) {
        Map<String, String> filedMap = getFiledMap(m);
        Set<Map.Entry<String, String>> entries = filedMap.entrySet();
        StringBuilder sb = new StringBuilder();
        List<String> value = new ArrayList<>();
        for (Map.Entry<String, String> entry : entries) {
            if (TextUtils.isEmpty(sb.toString())) {
                sb.append(entry.getKey() + " = ?");
            } else {
                sb.append(" AND " + entry.getKey() + " = ?");
            }
            value.add(entry.getValue());
        }
        return db.delete(getTableName(), sb.toString(), value.toArray(new String[]{}));
    }

    /**
     * 使用事务,删除实体列表对应数据
     *
     * @param list
     */
    @Override
    public void delete(List<M> list) {
        try {
            db.beginTransaction();
            for (M m : list) {
                delete(m);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    /**
     * 根据传入实体更新已有条目
     *
     * @param m 实体
     * @return the number of rows affected
     */
    @Override
    public int update(M m) {
        ContentValues values = new ContentValues();
        fillContentValues(m, values);
        return db.update(getTableName(), values, TableConstants.TABLE_ID + " = ?", new String[]{getId(m)});
    }

    /**
     * 获取数据库中全部条目
     *
     * @return 实体列表
     */
    @Override
    public List<M> findAll() {
        List<M> result = null;
        Cursor cursor = db.query(getTableName(), null, null, null, null, null, null);
        if (cursor != null) {
            result = new ArrayList<>();
            while (cursor.moveToNext()) {
                M m = getInstance();
                fillFields(cursor, m);
                result.add(m);
            }
            cursor.close();
        }
        return result;
    }

    /**
     * 条件查询
     *
     * @param columns       A list of which columns to return. Passing null will
     *                      return all columns, which is discouraged to prevent reading
     *                      data from storage that isn't going to be used.
     * @param selection     A filter declaring which rows to return, formatted as an
     *                      SQL WHERE clause (excluding the WHERE itself). Passing null
     *                      will return all rows for the given table.
     * @param selectionArgs You may include ?s in selection, which will be
     *                      replaced by the values from selectionArgs, in order that they
     *                      appear in the selection. The values will be bound as Strings.
     * @param groupBy       A filter declaring how to group rows, formatted as an SQL
     *                      GROUP BY clause (excluding the GROUP BY itself). Passing null
     *                      will cause the rows to not be grouped.
     * @param having        A filter declare which row groups to include in the cursor,
     *                      if row grouping is being used, formatted as an SQL HAVING
     *                      clause (excluding the HAVING itself). Passing null will cause
     *                      all row groups to be included, and is required when row
     *                      grouping is not being used.
     * @param orderBy       How to order the rows, formatted as an SQL ORDER BY clause
     *                      (excluding the ORDER BY itself). Passing null will use the
     *                      default sort order, which may be unordered.
     * @param limit         Limits the number of rows returned by the query,
     *                      formatted as LIMIT clause. Passing null denotes no LIMIT clause.
     * @return 实体列表
     */
    @Override
    public List<M> findByCondition(String[] columns, String selection, String[] selectionArgs, String groupBy, String
            having, String orderBy, String limit) {
        List<M> result = null;
        Cursor cursor = db.query(getTableName(), columns, selection, selectionArgs, groupBy, having, orderBy, limit);
        if (cursor != null) {
            result = new ArrayList<>();
            while (cursor.moveToNext()) {
                M m = getInstance();
                fillFields(cursor, m);
                result.add(m);
            }
            cursor.close();
        }
        return result;
    }

    /**
     * 条件查询
     *
     * @param columns       A list of which columns to return. Passing null will
     *                      return all columns, which is discouraged to prevent reading
     *                      data from storage that isn't going to be used.
     * @param selection     A filter declaring which rows to return, formatted as an
     *                      SQL WHERE clause (excluding the WHERE itself). Passing null
     *                      will return all rows for the given table.
     * @param selectionArgs You may include ?s in selection, which will be
     *                      replaced by the values from selectionArgs, in order that they
     *                      appear in the selection. The values will be bound as Strings.
     * @param orderBy       How to order the rows, formatted as an SQL ORDER BY clause
     *                      (excluding the ORDER BY itself). Passing null will use the
     *                      default sort order, which may be unordered.
     * @param limit         Limits the number of rows returned by the query,
     *                      formatted as LIMIT clause. Passing null denotes no LIMIT clause.
     * @return 实体列表
     */
    @Override
    public List<M> findByCondition(String[] columns, String selection, String[] selectionArgs, String orderBy, String
            limit) {
        return findByCondition(columns, selection, selectionArgs, null, null, orderBy, limit);
    }

    /**
     * 条件查询
     *
     * @param columns       A list of which columns to return. Passing null will
     *                      return all columns, which is discouraged to prevent reading
     *                      data from storage that isn't going to be used.
     * @param selection     A filter declaring which rows to return, formatted as an
     *                      SQL WHERE clause (excluding the WHERE itself). Passing null
     *                      will return all rows for the given table.
     * @param selectionArgs You may include ?s in selection, which will be
     *                      replaced by the values from selectionArgs, in order that they
     *                      appear in the selection. The values will be bound as Strings.
     * @return 实体列表
     */
    @Override
    public List<M> findByCondition(String[] columns, String selection, String[] selectionArgs) {
        return findByCondition(columns, selection, selectionArgs, null, null, null, null);
    }

    /**
     * 条件查询
     *
     * @param columns A list of which columns to return. Passing null will
     *                return all columns, which is discouraged to prevent reading
     *                data from storage that isn't going to be used.
     * @param m       条件实体
     * @return 实体列表
     */
    @Override
    public List<M> findByCondition(String[] columns, M m) {
        Map<String, String> filedMap = getFiledMap(m);
        Set<Map.Entry<String, String>> entries = filedMap.entrySet();
        StringBuilder sb = new StringBuilder();
        List<String> value = new ArrayList<>();
        for (Map.Entry<String, String> entry : entries) {
            if (TextUtils.isEmpty(sb.toString())) {
                sb.append(entry.getKey() + " = ?");
            } else {
                sb.append(" AND " + entry.getKey() + " = ?");
            }
            value.add(entry.getValue());
        }
        return findByCondition(columns, sb.toString(), value.toArray(new String[]{}), null, null, null, null);
    }

    /**
     * 根据类注解{@link TableName}获取表明
     *
     * @return 表名
     */
    protected String getTableName() {
        M m = getInstance();
        TableName tableName = m.getClass().getAnnotation(TableName.class);
        if (tableName != null) {
            return tableName.value();
        } else {
            throw new RuntimeException("Please use @TableName in" + m.getClass().getSimpleName());
        }
    }


    /**
     * 根据注解{@link Column}填充实体内容至{@link ContentValues}
     *
     * @param m
     * @param values
     */
    protected void fillContentValues(M m, ContentValues values) {
        Field[] fields = m.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                try {
                    String key = column.value();
                    String value = field.get(m).toString();
                    Id id = field.getAnnotation(Id.class);
                    if (id == null || !id.autoincrement()) {
                        values.put(key, value);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 根据注解{@link Id}获取数据库主键
     *
     * @param m
     * @return
     */
    protected String getId(M m) {
        Field[] fields = m.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Id id = field.getAnnotation(Id.class);
            if (id != null) {
                try {
                    return field.get(m).toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 根据注解{@link Column}填充对应数据到实体
     *
     * @param cursor
     * @param m
     */
    protected void fillFields(Cursor cursor, M m) {
        Field[] fields = m.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                int columnIndex = cursor.getColumnIndex(column.value());
                String info = cursor.getString(columnIndex);
                try {
                    if (field.getType() == int.class) {
                        field.set(m, Integer.parseInt(info));
                    } else if (field.getType() == long.class) {
                        field.set(m, Long.parseLong(info));
                    } else {
                        field.set(m, info);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 根据实体内容获取map
     *
     * @param m 实体
     * @return
     */
    protected Map<String, String> getFiledMap(M m) {
        Field[] declaredFields = m.getClass().getDeclaredFields();
        Map<String, String> filedMap = new HashMap<>();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            Column column = field.getAnnotation(Column.class);
            if (null != column) {
                try {
                    String key = column.value();
                    Object value = field.get(m);
                    Id id = field.getAnnotation(Id.class);
                    if (id == null || !id.autoincrement()) {
                        if (null != value) {
                            filedMap.put(key, value.toString());
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return filedMap;
    }

    /**
     * 根据附列泛型,获取当前数据库对应对象实体
     *
     * @return
     */
    protected M getInstance() {
        Class clazz = getClass();
        Type type = clazz.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] arguments = ((ParameterizedType) type).getActualTypeArguments();
            try {
                return (M) ((Class) arguments[0]).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
