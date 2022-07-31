package cn.zhxu.data.test;

import cn.zhxu.data.DataConvertor;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.Map;

/**
 * TODO
 * @author Troy.Zhou @ 2022/5/27
 * @since v1.5.0
 */
public class XmlTests extends Tests {

    public XmlTests(DataConvertor convertor) {
        super(convertor);
    }

    final String user1_0 = "<User><id>101001</id><name>Jack</name><school><id>101</id><name>清华</name></school><deleted>false</deleted></User>";

    final String user2_0 = "<User><id>102002</id><name>Alice</name><school><id>102</id><name>北大</name></school><deleted>true</deleted></User>";

    @Override
    protected String user1Str() {
        return user1_0;
    }

    @Override
    protected boolean checkUser1Str(String serialize) {
        return user1_0.equals(serialize);
    }

    @Override
    protected String user2Str() {
        return user2_0;
    }

    @Override
    protected boolean checkUser2Str(String serialize) {
        return user2_0.equals(serialize);
    }

    @Override
    protected String userListStr() {
//        return "<Users><item><id>101001</id><name>Jack</name><school><id>101</id><name>清华</name></school><deleted>false</deleted></item><item><id>102002</id><name>Alice</name><school><id>102</id><name>北大</name></school><deleted>true</deleted></item></Users>";
        return "<ArrayList><item><id>101001</id><name>Jack</name><school><id>101</id><name>清华</name></school><deleted>false</deleted></item><item><id>102002</id><name>Alice</name><school><id>102</id><name>北大</name></school><deleted>true</deleted></item></ArrayList>";
    }

    @Override
    protected boolean checkUserListStr(String serialize) {
        return userListStr().equals(serialize);
    }

    @Override
    protected String objectListStr() {
        return "<ArrayList>" +
                "<item><id>101001</id><name>Jack</name><school><id>101</id><name>清华</name></school><deleted>false</deleted></item>" +
                "<item><id>102002</id><name>Alice</name><school><id>102</id><name>北大</name></school><deleted>true</deleted></item>" +
                "<item>Hello</item>" +
                "<item>100</item>" +
                "</ArrayList>";
    }

    @Override
    protected boolean checkObjectListStr(String serialize) {
        return objectListStr().equals(serialize);
    }

    @Override
    void assertSchool(Map<String, Object> school, School expected) {
        Assertions.assertFalse(school.isEmpty());
        Assertions.assertEquals(2, school.size());
        Assertions.assertTrue(school.containsKey("id"));
        Assertions.assertTrue(school.containsKey("name"));
        Assertions.assertFalse(school.containsKey("address"));
        Assertions.assertEquals(String.valueOf(expected.getId()), String.valueOf(school.get("id")));
        Assertions.assertEquals(expected.getName(), school.get("name"));
    }

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
        Assertions.assertEquals(String.valueOf(expected.isDeleted()), String.valueOf(user.get("deleted")));
        Map<String, Object> school = (Map<String, Object>) user.get("school");
        assertSchool(school, expected.getSchool());
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

}
