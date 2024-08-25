package cn.zhxu.data.test;

import cn.zhxu.data.Array;
import cn.zhxu.data.DataConvertor;
import cn.zhxu.data.Mapper;
import cn.zhxu.data.TypeRef;
import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Troy.Zhou @ 2022/5/27
 * @since v
 */
public abstract class Tests {

    protected final DataConvertor convertor;

    public Tests(DataConvertor convertor) {
        this.convertor = convertor;
    }

    public void run() {
        test_01_toMapper();
        test_02_toArray_01();
        test_03_toArray_02();
        test_04_toBean();
        test_05_toList();
        test_06_serialize();
    }

    protected abstract String user1Str();
    protected abstract boolean checkUser1Str(String serialize);
    protected abstract boolean checkUser1Str(String serialize, boolean pretty);
    protected abstract String user2Str();
    protected abstract boolean checkUser2Str(String serialize);
    protected abstract boolean checkUser2Str(String serialize, boolean pretty);
    protected abstract String userListStr();
    protected abstract boolean checkUserListStr(String serialize);
    protected abstract boolean checkUserListStr(String serialize, boolean pretty);
    protected abstract String objectListStr();
    protected abstract boolean checkObjectListStr(String serialize);
    protected abstract boolean checkObjectListStr(String serialize, boolean pretty);

    protected static final User user1 = new User(101001, "Jack", new School(101, "清华"), false);
    protected static final User user2 = new User(102002, "Alice", new School(102, "北大"), true);

    protected static final List<User> userList = new ArrayList<>();
    protected static final List<Object> objectList = new ArrayList<>();

    static {
        userList.add(user1);
        userList.add(user2);
        objectList.add(user1);
        objectList.add(user2);
        objectList.add("Hello");
        objectList.add(100);
    }

    void assertUser(Mapper user, User expected) {
        Assertions.assertFalse(user.isEmpty());
        Assertions.assertEquals(4, user.size());
        Assertions.assertTrue(user.has("id"));
        Assertions.assertTrue(user.has("name"));
        Assertions.assertTrue(user.has("school"));
        Assertions.assertTrue(user.has("deleted"));
        Assertions.assertFalse(user.has("age"));
        Assertions.assertEquals(expected.getId(), user.getInt("id"));
        Assertions.assertEquals(expected.getName(), user.getString("name"));
        Assertions.assertEquals(expected.isDeleted(), user.getBool("deleted"));
        Assertions.assertEquals(user.toBean(User.class), expected);
        assertSchool(user.getMapper("school"), expected.getSchool());
        assertUser(user.toMap(), expected);
        User uu = new User();
        AtomicInteger count = new AtomicInteger(0);
        user.forEach((key, data) -> {
            if ("id".equals(key)) {
                uu.setId(data.toInt());
            }
            if ("name".equals(key)) {
                uu.setName(data.toString());
            }
            if ("school".equals(key)) {
                uu.setSchool(data.toMapper().toBean(School.class));
            }
            if ("deleted".equals(key)) {
                uu.setDeleted(data.toBool());
            }
            count.getAndIncrement();
        });
        Assertions.assertEquals(uu, expected);
        Assertions.assertEquals(4, count.intValue());
    }

    @SuppressWarnings("unchecked")
    void assertUser(Map<String, Object> user, User expected) {
        Assertions.assertFalse(user.isEmpty());
        Assertions.assertEquals(4, user.size());
        Assertions.assertTrue(user.containsKey("id"));
        Assertions.assertTrue(user.containsKey("name"));
        Assertions.assertTrue(user.containsKey("school"));
        Assertions.assertTrue(user.containsKey("deleted"));
        Assertions.assertFalse(user.containsKey("age"));
        Assertions.assertEquals(String.valueOf(expected.getId()), String.valueOf(user.get("id")));
        Assertions.assertEquals(expected.getName(), user.get("name"));
        Assertions.assertEquals(expected.isDeleted(), user.get("deleted"));
        Map<String, Object> school = (Map<String, Object>) user.get("school");
        assertSchool(school, expected.getSchool());
    }

    void assertSchool(Mapper school, School expected) {
        Assertions.assertFalse(school.isEmpty());
        Assertions.assertEquals(2, school.size());
        Assertions.assertTrue(school.has("id"));
        Assertions.assertTrue(school.has("name"));
        Assertions.assertFalse(school.has("address"));
        Assertions.assertEquals(expected.getId(), school.getInt("id"));
        Assertions.assertEquals(expected.getName(), school.getString("name"));
        Assertions.assertEquals(expected, school.toBean(School.class));
        assertSchool(school.toMap(), expected);
    }

    void assertSchool(Map<String, Object> school, School expected) {
        Assertions.assertFalse(school.isEmpty());
        Assertions.assertEquals(2, school.size());
        Assertions.assertTrue(school.containsKey("id"));
        Assertions.assertTrue(school.containsKey("name"));
        Assertions.assertFalse(school.containsKey("address"));
        Assertions.assertEquals(String.valueOf(expected.getId()), String.valueOf(school.get("id")));
        Assertions.assertEquals(expected.getName(), school.get("name"));
    }

    void assertObjectList(Array array) {
        Assertions.assertFalse(array.isEmpty());
        Assertions.assertEquals(4, array.size());
        assertUser(array.getMapper(0), user1);
        assertUser(array.getMapper(1), user2);
        Assertions.assertEquals("Hello", array.getString(2));
        Assertions.assertEquals(100, array.getInt(3));
        List<Object> list = new ArrayList<>();
        AtomicInteger count = new AtomicInteger(0);
        array.forEach((index, data) -> {
            if (index == 0) {
                assertUser(data.toMapper(), user1);
                list.add(data.toMapper().toMap());
            }
            if (index == 1) {
                assertUser(data.toMapper(), user2);
                list.add(data.toMapper().toMap());
            }
            if (index == 2) {
                Assertions.assertEquals(data.toString(), "Hello");
                list.add(data.toString());
            }
            if (index == 3) {
                Assertions.assertEquals(data.toInt(), 100);
                list.add(data.toInt());
            }
            count.incrementAndGet();
        });
        Assertions.assertEquals(4, count.intValue());
        assertObjectList(list);
        assertObjectList(array.toList());
    }

    void assertUserList(Array array) {
        Assertions.assertFalse(array.isEmpty());
        Assertions.assertEquals(2, array.size());
        assertUser(array.getMapper(0), user1);
        assertUser(array.getMapper(1), user2);
        assertUserList(array.toList(User.class));
    }

    void assertUserList(List<User> array) {
        Assertions.assertArrayEquals(userList.toArray(), array.toArray());
    }

    @SuppressWarnings("unchecked")
    void assertObjectList(List<Object> array) {
        Assertions.assertFalse(array.isEmpty());
        Assertions.assertEquals(4, array.size());
        assertUser((Map<String, Object>) array.get(0), (User) objectList.get(0));
        assertUser((Map<String, Object>) array.get(1), (User) objectList.get(1));
        Assertions.assertEquals(array.get(2), objectList.get(2));
        Assertions.assertEquals(String.valueOf(array.get(3)), String.valueOf(objectList.get(3)));
    }

    protected void test_01_toMapper() {
        Mapper mapper1 = doMapperTest(user1Str(), user1);
        if (this instanceof JsonTests) {
            doMapperTest(mapper1.toString(), user1);
            doMapperTest(mapper1.toPretty(), user1);
            Assertions.assertTrue(checkUser1Str(mapper1.toString()));
            Assertions.assertTrue(checkUser1Str(mapper1.toPretty(), true));
        }
        Mapper mapper2 = doMapperTest(user2Str(), user2);
        if (this instanceof JsonTests) {
            doMapperTest(mapper2.toString(), user2);
            doMapperTest(mapper2.toPretty(), user2);
            Assertions.assertTrue(checkUser2Str(mapper2.toString()));
            Assertions.assertTrue(checkUser2Str(mapper2.toPretty(), true));
        }
        System.out.println("case 01 passed!");
    }

    protected Mapper doMapperTest(String input, User user1) {
        Mapper mapper = convertor.toMapper(input);
        assertUser(mapper, user1);
        InputStream in1 = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        assertUser(convertor.toMapper(in1, StandardCharsets.UTF_8), user1);
        return mapper;
    }

    protected void test_02_toArray_01() {
        InputStream in = new ByteArrayInputStream(userListStr().getBytes(StandardCharsets.UTF_8));
        assertUserList(convertor.toArray(in, StandardCharsets.UTF_8));
        Array array = convertor.toArray(userListStr());
        assertUserList(array);
        if (this instanceof JsonTests) {
            assertUserList(convertor.toArray(array.toString()));
            assertUserList(convertor.toArray(array.toPretty()));
        }
        System.out.println("case 02 passed!");
    }

    protected void test_03_toArray_02() {
        InputStream in = new ByteArrayInputStream(objectListStr().getBytes(StandardCharsets.UTF_8));
        assertObjectList(convertor.toArray(in, StandardCharsets.UTF_8));
        Array array = convertor.toArray(objectListStr());
        assertObjectList(array);
        if (this instanceof JsonTests) {
            assertObjectList(convertor.toArray(array.toString()));
            assertObjectList(convertor.toArray(array.toPretty()));
        }
        System.out.println("case 03 passed!");
    }

    protected void test_04_toBean() {
        Assertions.assertEquals(convertor.toBean(User.class, user1Str()), user1);
        Assertions.assertEquals(convertor.toBean(User.class, user2Str()), user2);
        InputStream in1 = new ByteArrayInputStream(user1Str().getBytes(StandardCharsets.UTF_8));
        Assertions.assertEquals(convertor.toBean(User.class, in1, StandardCharsets.UTF_8), user1);
        InputStream in2 = new ByteArrayInputStream(user2Str().getBytes(StandardCharsets.UTF_8));
        Assertions.assertEquals(convertor.toBean(User.class, in2, StandardCharsets.UTF_8), user2);
        List<User> list = convertor.toBean(new TypeRef<List<User>>(){}.getType(), userListStr());
        assertUserList(list);
        System.out.println("case 04 passed!");
    }

    protected void test_05_toList() {
        assertUserList(convertor.toList(User.class, userListStr()));
        InputStream in = new ByteArrayInputStream(userListStr().getBytes(StandardCharsets.UTF_8));
        assertUserList(convertor.toList(User.class, in, StandardCharsets.UTF_8));
        System.out.println("case 05 passed!");
    }

    protected void test_06_serialize() {
        Assertions.assertTrue(checkUser1Str(convertor.serialize(user1)));
        Assertions.assertTrue(checkUser2Str(convertor.serialize(user2)));
        Assertions.assertTrue(checkUserListStr(convertor.serialize(userList)));
        Assertions.assertTrue(checkObjectListStr(convertor.serialize(objectList)));

        Assertions.assertTrue(checkUser1Str(convertor.serialize(user1, true), true));
        Assertions.assertTrue(checkUser2Str(convertor.serialize(user2, true), true));
        Assertions.assertTrue(checkUserListStr(convertor.serialize(userList, true), true));
        Assertions.assertTrue(checkObjectListStr(convertor.serialize(objectList, true), true));

        Assertions.assertTrue(checkUser1Str(convertor.serialize(user1, false), false));
        Assertions.assertTrue(checkUser2Str(convertor.serialize(user2, false), false));
        Assertions.assertTrue(checkUserListStr(convertor.serialize(userList, false), false));
        Assertions.assertTrue(checkObjectListStr(convertor.serialize(objectList, false), false));

        Assertions.assertTrue(checkUser1Str(new String(convertor.serialize(user1, StandardCharsets.UTF_8))));
        Assertions.assertTrue(checkUser2Str(new String(convertor.serialize(user2, StandardCharsets.UTF_8))));
        Assertions.assertTrue(checkUserListStr(new String(convertor.serialize(userList, StandardCharsets.UTF_8))));
        Assertions.assertTrue(checkObjectListStr(new String(convertor.serialize(objectList, StandardCharsets.UTF_8))));

        Assertions.assertTrue(checkUser1Str(new String(convertor.serialize(user1, StandardCharsets.UTF_8, true)), true));
        Assertions.assertTrue(checkUser2Str(new String(convertor.serialize(user2, StandardCharsets.UTF_8, true)), true));
        Assertions.assertTrue(checkUserListStr(new String(convertor.serialize(userList, StandardCharsets.UTF_8, true)), true));
        Assertions.assertTrue(checkObjectListStr(new String(convertor.serialize(objectList, StandardCharsets.UTF_8, true)), true));

        Assertions.assertTrue(checkUser1Str(new String(convertor.serialize(user1, StandardCharsets.UTF_8, false)), false));
        Assertions.assertTrue(checkUser2Str(new String(convertor.serialize(user2, StandardCharsets.UTF_8, false)), false));
        Assertions.assertTrue(checkUserListStr(new String(convertor.serialize(userList, StandardCharsets.UTF_8, false)), false));
        Assertions.assertTrue(checkObjectListStr(new String(convertor.serialize(objectList, StandardCharsets.UTF_8, false)), false));
        System.out.println("case 06 passed!");
    }

}
