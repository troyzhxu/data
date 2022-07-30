package cn.zhxu.data.jackson.xml;

import cn.zhxu.data.jackson.JacksonDataConvertor;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * @author Troy.Zhou @ 2017-03-20
 * @since v1.5.0
 * */
public class JacksonXmlDataConvertor extends JacksonDataConvertor {

    public JacksonXmlDataConvertor() {
        this(new XmlMapper());
    }

    public JacksonXmlDataConvertor(XmlMapper xmlMapper) {
        super(xmlMapper);
    }

}
