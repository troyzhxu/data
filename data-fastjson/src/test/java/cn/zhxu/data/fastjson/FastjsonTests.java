package cn.zhxu.data.fastjson;

import cn.zhxu.data.test.JsonTests;
import org.junit.jupiter.api.Test;

/**
 * @author Troy.Zhou @ 2022/5/27
 * @since v
 */
public class FastjsonTests {

    @Test
    public void test() {
        new JsonTests(new FastjsonDataConvertor()).run();
    }

}
