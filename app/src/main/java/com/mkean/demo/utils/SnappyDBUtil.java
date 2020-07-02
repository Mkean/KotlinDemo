package com.mkean.demo.utils;

import android.animation.ObjectAnimator;
import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import java.util.List;

/**
 * 数据库工具类
 */
public class SnappyDBUtil {

    private Context context;

    public SnappyDBUtil(Context context) {
        this.context = context;
    }

    /**
     * 存入 String 字符串进数据库
     *
     * @param key
     * @param value
     */
    public void putString(String key, String value) {
        try {
            DB db = DBFactory.open(context);
            db.put(key, value);
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public void putStrings(String key, String[] values) {
        try {
            DB db = DBFactory.open(context);
            db.put(key, values);
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public void putInteger(String key, Integer value) {
        try {
            DB db = DBFactory.open(context);
            db.put(key, value);
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public void putObject(String key, Object value) {
        try {
            DB db = DBFactory.open(context);
            db.put(key, value);
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public <T> void putObjectList(String key, List<T> value) {
        T t = null;
        value.add(t);
        try {
            DB db = DBFactory.open(context);
            db.put(key, value);
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 存list进去 ， 通过json 存入list
     *
     * @param key
     * @param list
     * @param <T>
     */
    public <T> void putList(String key, List<T> list) {
        String value = JSON.toJSONString(list);
        try {
            DB db = DBFactory.open(context);
            db.put(key, value);
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 取list 解析json取出列表
     *
     * @param key
     * @param className
     * @param <T>
     * @return
     */
    public <T> List<T> getDbList(String key, Class<T> className) {
        List<T> list = null;
        String s = null;
        try {
            DB db = DBFactory.open(context);
            s = db.get(key);
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        list = JSON.parseArray(s, className);
        return list;
    }


    /**
     * 获取字符换
     *
     * @param key
     * @return
     */
    public String getDbString(String key) {
        String s = null;
        try {
            DB db = DBFactory.open(context);
            s = db.get(key);
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * 获取整形数据
     *
     * @param key
     * @return
     */
    public Integer getDbInteger(String key) {
        Integer i = 0;
        try {
            DB db = DBFactory.open(context);
            i = db.getInt(key);
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * 获取对象
     *
     * @param key
     * @param className
     * @param <T>
     * @return
     */
    public <T> Object getDbObject(String key, Class<T> className) {
        Object o = null;
        try {
            DB db = DBFactory.open(context);
            o = db.getObject(key, className);
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        return o;
    }


//    public <T> T[] getDbObjectList(String key , Class<T> className){
//        T[] objectList = null;
//        String s = getDbString(key);
//
//        return objectList;
//    }

    public <T> T[] getDbObjectList(String key, Class<T> className) {
        T[] objectList = null;
        try {
            DB db = DBFactory.open(context);
            objectList = db.getObjectArray(key, className);
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        return objectList;
    }
}
