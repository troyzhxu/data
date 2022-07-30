package cn.zhxu.data.jackson.yaml;

import cn.zhxu.data.test.YamlTests;
import org.junit.jupiter.api.Test;

/**
 * @author Troy.Zhou @ 2022/5/27
 * @since v
 */
public class JacksonYamlTests {

    @Test
    public void test() {
        new YamlTests(new JacksonYamlDataConvertor()).run();
    }

}
