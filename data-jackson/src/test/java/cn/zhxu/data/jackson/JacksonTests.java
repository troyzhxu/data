package cn.zhxu.data.jackson;

import cn.zhxu.data.test.JsonTests;
import org.junit.jupiter.api.Test;

/**
 * @author Troy.Zhou @ 2022/5/27
 * @since v
 */
public class JacksonTests {

    @Test
    public void test() {
        new JsonTests(new JacksonDataConvertor()).run();
    }

}
