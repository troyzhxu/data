package cn.zhxu.data.jackson.xml;

import cn.zhxu.data.Array;
import cn.zhxu.data.jackson.JacksonDataConvertor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * @author Troy.Zhou @ 2017-03-20
 * @since v1.5.0
 * */
public class JacksonXmlDataConvertor extends JacksonDataConvertor {

    public static final String LIST_ITEM = "item";

    public JacksonXmlDataConvertor() {
        this(new XmlMapper());
    }

    public JacksonXmlDataConvertor(XmlMapper xmlMapper) {
        super(xmlMapper);
    }

    @Override
    protected Array toArray(JsonNode json) {
        JsonNode arr = json;
        if (json.isObject() && json.size() == 1 && json.has(LIST_ITEM)) {
            arr = json.get(LIST_ITEM);
        }
        return super.toArray(arr);
    }

}
