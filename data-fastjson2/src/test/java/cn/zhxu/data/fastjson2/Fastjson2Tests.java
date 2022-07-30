package cn.zhxu.data.fastjson2;

import cn.zhxu.data.test.JsonTests;
import org.junit.jupiter.api.Test;

/**
 * @author Troy.Zhou @ 2022/5/27
 * @since v
 */
public class Fastjson2Tests {

    @Test
    public void test() {
        new JsonTests(new Fastjson2DataConvertor()).run();
    }

}
