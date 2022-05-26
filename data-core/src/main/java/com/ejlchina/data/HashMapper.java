package com.ejlchina.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Troy Zhou
 * @since v2.5.2
 */
public class HashMapper implements Mapper {

    private final Map<String, Object> map = new HashMap<>();

    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public Mapper getMapper(String key) {
        Object o = map.get(key);
        if (o instanceof Mapper) {
            return (Mapper) o;
        }
        return null;
    }

    @Override
    public Array getArray(String key) {
        Object o = map.get(key);
        if (o instanceof Array) {
            return (Array) o;
        }
        return null;
    }

    @Override
    public boolean getBool(String key) {
        Object o = map.get(key);
        if (o instanceof Boolean) {
            return (Boolean) o;
        }
        return false;
    }

    @Override
    public int getInt(String key) {
        Object o = map.get(key);
        if (o instanceof Number) {
            return ((Number) o).intValue();
        }
        return 0;
    }

    @Override
    public long getLong(String key) {
        Object o = map.get(key);
        if (o instanceof Number) {
            return ((Number) o).longValue();
        }
        return 0;
    }

    @Override
    public float getFloat(String key) {
        Object o = map.get(key);
        if (o instanceof Number) {
            return ((Number) o).floatValue();
        }
        return 0;
    }

    @Override
    public double getDouble(String key) {
        Object o = map.get(key);
        if (o instanceof Number) {
            return ((Number) o).doubleValue();
        }
        return 0;
    }

    @Override
    public String getString(String key) {
        Object o = map.get(key);
        if (o != null) {
            return o.toString();
        }
        return null;
    }

    @Override
    public boolean has(String key) {
        return map.containsKey(key);
    }

    public void put(String key, Mapper value) {
        map.put(key, value);
    }

    public void put(String key, Array value) {
        map.put(key, value);
    }

    public void put(String key, boolean value) {
        map.put(key, value);
    }

    public void put(String key, int value) {
        map.put(key, value);
    }

    public void put(String key, long value) {
        map.put(key, value);
    }

    public void put(String key, float value) {
        map.put(key, value);
    }

    public void put(String key, double value) {
        map.put(key, value);
    }

    public void put(String key, String value) {
        map.put(key, value);
    }

}
