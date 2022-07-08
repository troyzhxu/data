package com.ejlchina.data.fastjson2;

import com.ejlchina.data.test.JsonTests;
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
