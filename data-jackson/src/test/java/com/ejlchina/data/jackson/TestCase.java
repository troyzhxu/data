package com.ejlchina.data.jackson;

import com.ejlchina.data.test.JsonTests;
import org.junit.jupiter.api.Test;

/**
 * @author Troy.Zhou @ 2022/5/27
 * @since v
 */
public class TestCase {

    @Test
    public void test() {
        new JsonTests(new JacksonDataConvertor()).run();
    }

}
