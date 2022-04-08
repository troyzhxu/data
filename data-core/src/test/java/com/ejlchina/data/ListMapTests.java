package com.ejlchina.data;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ListMapTests {

    @Test
    public void testArrayListMap() {
        doTest(new ArrayListMap<>());
    }

    @Test
    public void testLinkedListMap() {
        doTest(new LinkedListMap<>());
    }

    private void doTest(ListMap<Object> map) {
        test1(map);
        test2(map);
        test3(map);
    }

    private void test1(ListMap<Object> map) {
        Map<String, String> m = new HashMap<>();
        m.put("sex", "male");
        m.put("school", "School");
        map.putAll(m);
        Assert.assertEquals("male", map.get("sex"));
        Assert.assertEquals("School", map.get("school"));
        Assert.assertEquals("male", map.get("Sex", true));
        Assert.assertEquals("School", map.get("School", true));
        Assert.assertEquals(2, map.size());
        map.clear();
        Assert.assertEquals(0, map.size());
        Assert.assertNull(map.get("sex"));
        Assert.assertNull(map.get("school"));
    }

    private void test2(ListMap<Object> map) {
        map.put("name", "Jack");
        map.put("age", 25);
        map.put("name", "Tom");
        map.put("age", 30);
        map.put("name", "Alice");
        map.put("age", 26);

        Assert.assertEquals(6, map.size());
        Assert.assertEquals("Alice", map.get("name"));
        Assert.assertEquals(26, map.get("age"));

        String[] NAMES = new String[] {
                "Jack", "Tom", "Alice"
        };
        int[] AGES = new int[] {
                25, 30, 26
        };

        List<Object> names = map.list("name");
        Assert.assertEquals(3, names.size());
        Assert.assertEquals(NAMES[0], names.get(0));
        Assert.assertEquals(NAMES[1], names.get(1));
        Assert.assertEquals(NAMES[2], names.get(2));

        List<Object> ages = map.list("age");
        Assert.assertEquals(3, ages.size());
        Assert.assertEquals(AGES[0], ages.get(0));
        Assert.assertEquals(AGES[1], ages.get(1));
        Assert.assertEquals(AGES[2], ages.get(2));

        AtomicInteger index = new AtomicInteger(0);

        map.forEach((key, value) -> {
            int i = index.getAndIncrement();
            if (i % 2 == 1) {
                Assert.assertEquals("age", key);
                Assert.assertEquals(AGES[i / 2], value);
            } else {
                Assert.assertEquals("name", key);
                Assert.assertEquals(NAMES[i / 2], value);
            }
        });

        map.clear();
        Assert.assertEquals(0, map.size());
    }

    public void test3(ListMap<Object> map) {
        map.put("name", "Jack");
        map.put("name", "Ketty");
        map.put("name", "Tom");
        map.put("name", "Tom");
        map.put("name", "Alice");
        map.put("age", "30");

        Assert.assertEquals(6, map.size());
        Assert.assertEquals("Alice", map.get("name"));
        Assert.assertEquals("30", map.get("age"));

        Assert.assertEquals("Alice", map.remove("name"));

        Assert.assertEquals(5, map.size());
        Assert.assertEquals("Tom", map.get("name"));

        Assert.assertArrayEquals(new String[] {"Jack", "Ketty", "Tom", "Tom"}, map.removeAll("name").toArray());

        Assert.assertEquals(1, map.size());
        Assert.assertEquals("30", map.get("age"));
        Assert.assertNull(map.get("name"));

        map.clear();
        Assert.assertEquals(0, map.size());
    }


    public void test4(ListMap<Object> map) {
        map.put("name", "Jack");
        map.put("age", 20);
        map.put("name", "Tom");
        map.put("age", 30);
        map.put("name", "Ketty");

        Assert.assertEquals(5, map.size());

        Assert.assertArrayEquals(new String[] {"Jack", "Tom", "Ketty"}, map.list("name").toArray());
        Assert.assertArrayEquals(new String[] {"Jack", "Tom", "Ketty"}, map.list("NAME", true).toArray());

        List<Object> ages = map.list("age");
        Assert.assertEquals(2, ages.size());
        Assert.assertEquals(20, ages.get(0));
        Assert.assertEquals(30, ages.get(1));

        Assert.assertNull(map.replace("Name", "aaa"));
        Assert.assertEquals("Ketty", map.get("name"));
        Assert.assertEquals("Ketty", map.replace("Name", "aaa", true));
        Assert.assertEquals("aaa", map.get("name"));
        Assert.assertEquals("aaa", map.replace("name", "bbb"));
        Assert.assertEquals("bbb", map.get("name"));

        Assert.assertArrayEquals(new String[] {"Jack", "Tom", "bbb"}, map.list("name").toArray());
        Assert.assertArrayEquals(new String[] {"Jack", "Tom", "bbb"}, map.list("NAME", true).toArray());

        Assert.assertEquals(0, map.replaceAll("NAME", "CCC"));

        Assert.assertArrayEquals(new String[] {"Jack", "Tom", "bbb"}, map.list("name").toArray());
        Assert.assertArrayEquals(new String[] {"Jack", "Tom", "bbb"}, map.list("NAME", true).toArray());

        Assert.assertEquals(3, map.replaceAll("NAME", "CCC", true));

        Assert.assertArrayEquals(new String[] {"CCC", "CCC", "CCC"}, map.list("name").toArray());
        Assert.assertArrayEquals(new String[] {"CCC", "CCC", "CCC"}, map.list("NAME", true).toArray());

        Assert.assertEquals(3, map.replaceAll("name", "DD"));

        Assert.assertArrayEquals(new String[] {"DD", "DD", "DD"}, map.list("name").toArray());
        Assert.assertArrayEquals(new String[] {"DD", "DD", "DD"}, map.list("NAME", true).toArray());

        Assert.assertEquals(0, map.removeAll("Age").size());
        Assert.assertEquals(5, map.size());

        Assert.assertEquals(2, map.removeAll("Age", true).size());
        Assert.assertEquals(3, map.size());

        Assert.assertEquals(3, map.removeAll("name").size());
        Assert.assertEquals(0, map.size());
        Assert.assertTrue(map.isEmpty());
    }

}
