package com.ejlchina.data.jackson.yaml;

import com.ejlchina.data.test.YamlTests;
import org.junit.jupiter.api.Test;

/**
 * @author Troy.Zhou @ 2022/5/27
 * @since v
 */
public class TestCase {

    @Test
    public void test() {
        new YamlTests(new JacksonYamlDataConvertor()).run();
    }

}
