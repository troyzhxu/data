package cn.zhxu.data.jackson;

import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.AbstractList;

/**
 * @author Troy.Zhou @ 2022/5/27
 * @since v
 */
public class JacksonList extends AbstractList<Object> {

    private final ArrayNode json;

    public JacksonList(ArrayNode json) {
        this.json = json;
    }

    @Override
    public Object get(int index) {
        return JacksonDataConvertor.toPlainObject(json.get(index));
    }

    @Override
    public int size() {
        return json.size();
    }

}
