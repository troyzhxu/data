package com.ejlchina.data.xml;

import com.ejlchina.data.Array;
import com.ejlchina.data.Mapper;
import org.w3c.dom.Element;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class XmlArray implements Array {

    private final String[] nameKeys;
    private final String[] valueKeys;
    private final List<Element> list;

    public XmlArray(String[] nameKeys, String[] valueKeys, List<Element> list) {
        this.nameKeys = nameKeys;
        this.valueKeys = valueKeys;
        this.list = list;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public Mapper getMapper(int index) {
        if (index < list.size()) {
            return new XmlMapper(nameKeys, valueKeys, list.get(index));
        }
        return null;
    }

    @Override
    public Array getArray(int index) {
        if (index < list.size()) {
            Element node = list.get(index);
            return new XmlArray(nameKeys, valueKeys, XmlUtils.children(node));
        }
        return null;
    }

    @Override
    public boolean getBool(int index) {
        if (index < list.size()) {
            String value = XmlUtils.value(list.get(index), valueKeys);
            return XmlUtils.toBoolean(value);
        }
        return false;
    }

    @Override
    public int getInt(int index) {
        if (index < list.size()) {
            String value = XmlUtils.value(list.get(index), valueKeys);
            return XmlUtils.toInt(value);
        }
        return 0;
    }

    @Override
    public long getLong(int index) {
        if (index < list.size()) {
            String value = XmlUtils.value(list.get(index), valueKeys);
            return XmlUtils.toLong(value);
        }
        return 0;
    }

    @Override
    public float getFloat(int index) {
        if (index < list.size()) {
            String value = XmlUtils.value(list.get(index), valueKeys);
            return XmlUtils.toFloat(value);
        }
        return 0;
    }

    @Override
    public double getDouble(int index) {
        if (index < list.size()) {
            String value = XmlUtils.value(list.get(index), valueKeys);
            return XmlUtils.toDouble(value);
        }
        return 0;
    }

    @Override
    public String getString(int index) {
        if (index < list.size()) {
            return XmlUtils.value(list.get(index), valueKeys);
        }
        return null;
    }

    @Override
    public String toString() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            for (int i = 0; i < list.size(); i++) {
                TransformerFactory.newInstance().newTransformer()
                        .transform(new DOMSource(list.get(i)), new StreamResult(baos));
                if (i < list.size() - 1) {
                    baos.write('\n');
                }
            }
            return baos.toString();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public  <T> List<T> toList(Class<T> type) {
        int size = size();
        if (type == Boolean.class || type == boolean.class) {
            List<Boolean> list = new ArrayList<>(size);
            doEach((i, data) -> list.add(data.toBool()));
            return (List<T>) list;
        }
        if (type == Integer.class || type == int.class) {
            List<Integer> list = new ArrayList<>(size);
            doEach((i, data) -> list.add(data.toInt()));
            return (List<T>) list;
        }
        if (type == Long.class || type == long.class) {
            List<Long> list = new ArrayList<>(size);
            doEach((i, data) -> list.add(data.toLong()));
            return (List<T>) list;
        }
        if (type == Float.class || type == float.class) {
            List<Float> list = new ArrayList<>(size);
            doEach((i, data) -> list.add(data.toFloat()));
            return (List<T>) list;
        }
        if (type == Double.class || type == double.class) {
            List<Double> list = new ArrayList<>(size);
            doEach((i, data) -> list.add(data.toDouble()));
            return (List<T>) list;
        }
        if (type == String.class) {
            List<String> list = new ArrayList<>(size);
            doEach((i, data) -> list.add(data.toString()));
            return (List<T>) list;
        }
        List<T> list = new ArrayList<>(size);
        for (int index = 0; index < size; index++) {
            Mapper m = getMapper(index);
            list.add(m != null ? m.toBean(type) : null);
        }
        return list;
    }

}
