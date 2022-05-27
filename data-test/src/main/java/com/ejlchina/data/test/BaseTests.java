package com.ejlchina.data.test;

import com.ejlchina.data.Array;
import com.ejlchina.data.DataConvertor;
import com.ejlchina.data.Mapper;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author Troy.Zhou @ 2022/5/27
 * @since v
 */
public abstract class BaseTests {

    private final DataConvertor convertor;

    public BaseTests(DataConvertor convertor) {
        this.convertor = convertor;
    }

    public void run() {
        test_01_toMapper();
        test_02_toArray();
        test_03();
        test_04();
    }

    abstract String objStr();

    abstract String listStr();

    abstract String complexObjStr();

    final User user1 = new User(101, "Jack", new School(11, "清华"));
    final User user2 = new User(101, "Alice", new School(11, "北大"));

    void assertObj(Mapper obj) {

    }

    void assertArray(Array arr) {

    }

    private void test_01_toMapper() {
        assertObj(convertor.toMapper(objStr()));
        InputStream in = new ByteArrayInputStream(objStr().getBytes(StandardCharsets.UTF_8));
        assertObj(convertor.toMapper(in, StandardCharsets.UTF_8));
        System.out.println("case 01 passed!");
    }

    private void test_02_toArray() {
        InputStream in = new ByteArrayInputStream(listStr().getBytes(StandardCharsets.UTF_8));
        assertArray(convertor.toArray(in, StandardCharsets.UTF_8));
        assertArray(convertor.toArray(listStr()));
        System.out.println("case 02 passed!");
    }

    private void test_03() {

    }

    private void test_04() {

    }


}
