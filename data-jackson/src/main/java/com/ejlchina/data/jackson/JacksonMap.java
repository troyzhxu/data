package com.ejlchina.data.jackson;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Troy.Zhou @ 2022/5/27
 * @since v1.5.0
 */
public class JacksonMap extends AbstractMap<String, Object> {

    private final ObjectNode json;

    public JacksonMap(ObjectNode json) {
        this.json = json;
    }

    transient Set<Entry<String, Object>> entrySet;

    @Override
    public Set<Entry<String, Object>> entrySet() {
        if (entrySet == null) {
            entrySet = new AbstractSet<>() {

                @Override
                public Iterator<Entry<String, Object>> iterator() {
                    Iterator<String> keyIt = json.fieldNames();
                    return new Iterator<>() {

                        @Override
                        public boolean hasNext() {
                            return keyIt.hasNext();
                        }

                        @Override
                        public Entry<String, Object> next() {
                            String key = keyIt.next();
                            Object value = JacksonDataConvertor.toPlainObject(json.get(key));
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
