package com.ejlchina.data;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
        Assertions.assertEquals("male", map.get("sex"));
        Assertions.assertEquals("School", map.get("school"));
        Assertions.assertEquals("male", map.get("Sex", true));
        Assertions.assertEquals("School", map.get("School", true));
        Assertions.assertEquals(2, map.size());
        map.clear();
        Assertions.assertEquals(0, map.size());
        Assertions.assertNull(map.get("sex"));
        Assertions.assertNull(map.get("school"));
    }

    private void test2(ListMap<Object> map) {
        map.put("name", "Jack");
        map.put("age", 25);
        map.put("name", "Tom");
        map.put("age", 30);
        map.put("name", "Alice");
        map.put("age", 26);

        Assertions.assertEquals(6, map.size());
        Assertions.assertEquals("Alice", map.get("name"));
        Assertions.assertEquals(26, map.get("age"));

        String[] NAMES = new String[] {
                "Jack", "Tom", "Alice"
        };
        int[] AGES = new int[] {
                25, 30, 26
        };

        List<Object> names = map.list("name");
        Assertions.assertEquals(3, names.size());
        Assertions.assertEquals(NAMES[0], names.get(0));
        Assertions.assertEquals(NAMES[1], names.get(1));
        Assertions.assertEquals(NAMES[2], names.get(2));

        List<Object> ages = map.list("age");
        Assertions.assertEquals(3, ages.size());
        Assertions.assertEquals(AGES[0], ages.get(0));
        Assertions.assertEquals(AGES[1], ages.get(1));
        Assertions.assertEquals(AGES[2], ages.get(2));

        AtomicInteger index = new AtomicInteger(0);

        map.forEach((key, value) -> {
            int i = index.getAndIncrement();
            if (i % 2 == 1) {
                Assertions.assertEquals("age", key);
                Assertions.assertEquals(AGES[i / 2], value);
            } else {
                Assertions.assertEquals("name", key);
                Assertions.assertEquals(NAMES[i / 2], value);
            }
        });

        map.clear();
        Assertions.assertEquals(0, map.size());
    }

    public void test3(ListMap<Object> map) {
        map.put("name", "Jack");
        map.put("name", "Ketty");
        map.put("name", "Tom");
        map.put("name", "Tom");
        map.put("name", "Alice");
        map.put("age", "30");

        Assertions.assertEquals(6, map.size());
        Assertions.assertEquals("Alice", map.get("name"));
        Assertions.assertEquals("30", map.get("age"));

        Assertions.assertEquals("Alice", map.remove("name"));

        Assertions.assertEquals(5, map.size());
        Assertions.assertEquals("Tom", map.get("name"));

        Assertions.assertArrayEquals(new String[] {"Jack", "Ketty", "Tom", "Tom"}, map.removeAll("name").toArray());

        Assertions.assertEquals(1, map.size());
        Assertions.assertEquals("30", map.get("age"));
        Assertions.assertNull(map.get("name"));

        map.clear();
        Assertions.assertEquals(0, map.size());
    }


    public void test4(ListMap<Object> map) {
        map.put("name", "Jack");
        map.put("age", 20);
        map.put("name", "Tom");
        map.put("age", 30);
        map.put("name", "Ketty");

        Assertions.assertEquals(5, map.size());

        Assertions.assertArrayEquals(new String[] {"Jack", "Tom", "Ketty"}, map.list("name").toArray());
        Assertions.assertArrayEquals(new String[] {"Jack", "Tom", "Ketty"}, map.list("NAME", true).toArray());

        List<Object> ages = map.list("age");
        Assertions.assertEquals(2, ages.size());
        Assertions.assertEquals(20, ages.get(0));
        Assertions.assertEquals(30, ages.get(1));

        Assertions.assertNull(map.replace("Name", "aaa"));
        Assertions.assertEquals("Ketty", map.get("name"));
        Assertions.assertEquals("Ketty", map.replace("Name", "aaa", true));
        Assertions.assertEquals("aaa", map.get("name"));
        Assertions.assertEquals("aaa", map.replace("name", "bbb"));
        Assertions.assertEquals("bbb", map.get("name"));

        Assertions.assertArrayEquals(new String[] {"Jack", "Tom", "bbb"}, map.list("name").toArray());
        Assertions.assertArrayEquals(new String[] {"Jack", "Tom", "bbb"}, map.list("NAME", true).toArray());

        Assertions.assertEquals(0, map.replaceAll("NAME", "CCC"));

        Assertions.assertArrayEquals(new String[] {"Jack", "Tom", "bbb"}, map.list("name").toArray());
        Assertions.assertArrayEquals(new String[] {"Jack", "Tom", "bbb"}, map.list("NAME", true).toArray());

        Assertions.assertEquals(3, map.replaceAll("NAME", "CCC", true));

        Assertions.assertArrayEquals(new String[] {"CCC", "CCC", "CCC"}, map.list("name").toArray());
        Assertions.assertArrayEquals(new String[] {"CCC", "CCC", "CCC"}, map.list("NAME", true).toArray());

        Assertions.assertEquals(3, map.replaceAll("name", "DD"));

        Assertions.assertArrayEquals(new String[] {"DD", "DD", "DD"}, map.list("name").toArray());
        Assertions.assertArrayEquals(new String[] {"DD", "DD", "DD"}, map.list("NAME", true).toArray());

        Assertions.assertEquals(0, map.removeAll("Age").size());
        Assertions.assertEquals(5, map.size());

        Assertions.assertEquals(2, map.removeAll("Age", true).size());
        Assertions.assertEquals(3, map.size());

        Assertions.assertEquals(3, map.removeAll("name").size());
        Assertions.assertEquals(0, map.size());
        Assertions.assertTrue(map.isEmpty());
    }

}
