package cn.zhxu.data.snack3;

import cn.zhxu.data.test.JsonTests;
import org.junit.jupiter.api.Test;

/**
 * @author Troy.Zhou @ 2022/5/27
 * @since v
 */
public class Snack3Tests {

    @Test
    public void test() {
        new JsonTests(new Snack3DataConvertor()).run();
    }

}
