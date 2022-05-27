package com.ejlchina.data.gson;

import com.google.gson.JsonArray;

import java.util.AbstractList;

/**
 * @author Troy.Zhou @ 2022/5/27
 * @since v
 */
public class GsonList extends AbstractList<Object> {

    private final JsonArray json;

    public GsonList(JsonArray json) {
        this.json = json;
    }

    @Override
    public Object get(int index) {
        return GsonDataConvertor.toPlainObject(json.get(index));
    }

    @Override
    public int size() {
        return json.size();
    }

}
