package cn.zhxu.data.fastjson2.jsonb;

import cn.zhxu.data.test.JsonbTests;
import org.junit.jupiter.api.Test;

/**
 * @author Troy.Zhou @ 2022/5/27
 * @since v
 */
public class Fastjson2JsonbTests {

    @Test
    public void test() {
        new JsonbTests(new Fastjson2JsonbDataConvertor()).run();
    }

}
