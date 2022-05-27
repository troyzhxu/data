package com.ejlchina.data.gson;

import com.google.gson.JsonObject;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Troy.Zhou @ 2022/5/27
 * @since v1.5.0
 */
public class GsonMap extends AbstractMap<String, Object> {

    private final JsonObject json;

    public GsonMap(JsonObject json) {
        this.json = json;
    }

    transient Set<Entry<String, Object>> entrySet;

    @Override
    public Set<Entry<String, Object>> entrySet() {
        if (entrySet == null) {
            entrySet = new AbstractSet<>() {

                @Override
                public Iterator<Entry<String, Object>> iterator() {
                    Iterator<String> keyIt = json.keySet().iterator();
                    return new Iterator<>() {

                        @Override
                        public boolean hasNext() {
                            return keyIt.hasNext();
                        }

                        @Override
                        public Entry<String, Object> next() {
                            String key = keyIt.next();
                            Object value = GsonDataConvertor.toPlainObject(json.get(key));
                            return new SimpleEntry<>(key, value);
                        }
                    };
                }

                @Override
                public int size() {
                    return json.size();
                }
            };
        }
        return entrySet;
    }

}
