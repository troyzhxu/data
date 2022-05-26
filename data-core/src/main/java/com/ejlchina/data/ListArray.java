package com.ejlchina.data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Troy Zhou
 * @since v2.5.2
 */
public class ListArray implements Array {

    private final List<Object> list = new ArrayList<>();

    @Override
    public Mapper getMapper(int index) {
        Object o = list.get(index);
        if (o instanceof Mapper) {
            return (Mapper) o;
        }
        return null;
    }

    @Override
    public Array getArray(int index) {
        Object o = list.get(index);
        if (o instanceof Array) {
            return (Array) o;
        }
        return null;
    }

    @Override
    public boolean getBool(int index) {
        Object o = list.get(index);
        if (o instanceof Boolean) {
            return (Boolean) o;
        }
        return false;
    }

    @Override
    public int getInt(int index) {
        Object o = list.get(index);
        if (o instanceof Number) {
            return ((Number) o).intValue();
        }
        return 0;
    }

    @Override
    public long getLong(int index) {
        Object o = list.get(index);
        if (o instanceof Number) {
            return ((Number) o).longValue();
        }
        return 0;
    }

    @Override
    public float getFloat(int index) {
        Object o = list.get(index);
        if (o instanceof Number) {
            return ((Number) o).floatValue();
        }
        return 0;
    }

    @Override
    public double getDouble(int index) {
        Object o = list.get(index);
        if (o instanceof Number) {
            return ((Number) o).doubleValue();
        }
        return 0;
    }

    @Override
    public String getString(int index) {
        Object o = list.get(index);
        if (o != null) {
            return o.toString();
        }
        return null;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void add(Mapper value) {
        list.add(value);
    }

    public void add(Array value) {
        list.add(value);
    }

    public void add(boolean value) {
        list.add(value);
    }

    public void add(int value) {
        list.add(value);
    }

    public void add(long value) {
        list.add(value);
    }

    public void add(float value) {
        list.add(value);
    }

    public void add(double value) {
        list.add(value);
    }

    public void add(String value) {
        list.add(value);
    }

}
