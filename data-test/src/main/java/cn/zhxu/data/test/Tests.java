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

    private final DataConvertor convertor;

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

    abstract String user1Str();
    abstract boolean checkUser1Str(String serialize);
    abstract String user2Str();
    abstract boolean checkUser2Str(String serialize);
    abstract String userListStr();
    abstract boolean checkUserListStr(String serialize);
    abstract String objectListStr();
    abstract boolean checkObjectListStr(String serialize);

    static final User user1 = new User(101001, "Jack", new School(101, "清华"), false);
    static final User user2 = new User(102002, "Alice", new School(102, "北大"), true);

    static final List<User> userList = new ArrayList<>();
    static final List<Object> objectList = new ArrayList<>();

    static {
        userList.add(user1);
        userList.add(user2);
        objectList.add(user1);
        objectList.add(user2);
        objectList.add("Hello");
        objectList.add(100);
    }


//    static final Result<User> userResult = new Result<>(200, "ok", user1);
//    static final Result<List<User>> listResult = new Result<>(200, "ok", userList);

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
        Assertions.assertEquals(expected.getId(), user.get("id"));
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
        Assertions.assertEquals(expected.getId(), school.get("id"));
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
        Assertions.assertEquals(array.get(3), objectList.get(3));
    }

    private void test_01_toMapper() {
        assertUser(convertor.toMapper(user1Str()), user1);
        InputStream in1 = new ByteArrayInputStream(user1Str().getBytes(StandardCharsets.UTF_8));
        assertUser(convertor.toMapper(in1, StandardCharsets.UTF_8), user1);
        assertUser(convertor.toMapper(user2Str()), user2);
        InputStream in2 = new ByteArrayInputStream(user2Str().getBytes(StandardCharsets.UTF_8));
        assertUser(convertor.toMapper(in2, StandardCharsets.UTF_8), user2);
        System.out.println("case 01 passed!");
    }

    private void test_02_toArray_01() {
        InputStream in = new ByteArrayInputStream(userListStr().getBytes(StandardCharsets.UTF_8));
        assertUserList(convertor.toArray(in, StandardCharsets.UTF_8));
        assertUserList(convertor.toArray(userListStr()));
        System.out.println("case 02 passed!");
    }

    private void test_03_toArray_02() {
        InputStream in = new ByteArrayInputStream(objectListStr().getBytes(StandardCharsets.UTF_8));
        assertObjectList(convertor.toArray(in, StandardCharsets.UTF_8));
        assertObjectList(convertor.toArray(objectListStr()));
        System.out.println("case 03 passed!");
    }

    private void test_04_toBean() {
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

    private void test_05_toList() {
        assertUserList(convertor.toList(User.class, userListStr()));
        InputStream in = new ByteArrayInputStream(userListStr().getBytes(StandardCharsets.UTF_8));
        assertUserList(convertor.toList(User.class, in, StandardCharsets.UTF_8));
        System.out.println("case 05 passed!");
    }

    private void test_06_serialize() {
        Assertions.assertTrue(checkUser1Str(convertor.serialize(user1)));
        Assertions.assertTrue(checkUser2Str(convertor.serialize(user2)));
        Assertions.assertTrue(checkUserListStr(convertor.serialize(userList)));
        Assertions.assertTrue(checkObjectListStr(convertor.serialize(objectList)));
        System.out.println("case 06 passed!");
    }

}
