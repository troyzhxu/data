package com.ejlchina.data.gson;

import com.ejlchina.data.test.JsonTests;
import org.junit.jupiter.api.Test;

/**
 * @author Troy.Zhou @ 2022/5/27
 * @since v
 */
public class GsonTests {

    @Test
    public void doTest() {
        new JsonTests(new GsonDataConvertor()).run();
    }

}
