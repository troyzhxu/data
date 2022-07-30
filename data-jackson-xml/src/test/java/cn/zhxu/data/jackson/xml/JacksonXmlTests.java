package cn.zhxu.data.jackson.xml;

import cn.zhxu.data.test.XmlTests;
import org.junit.jupiter.api.Test;

/**
 * @author Troy.Zhou @ 2022/5/27
 * @since v
 */
public class JacksonXmlTests {

    @Test
    public void test() {
        new XmlTests(new JacksonXmlDataConvertor()).run();
    }

}
