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
    String getStringForMapperValuesTest() {
        return "<User><id>1</id><age>20</age></User>";
    }

    @Override
    String getStringForArrayStreamTest() {
        return "<ArrayList><item>id</item><item>age</item><item>name</item></ArrayList>";
    }

    @Override
    protected String user1Str() {
        return user1_0;
    }

    @Override
    protected boolean checkUser1Str(String xml) {
        return user1_0.equals(xml);
    }

    @Override
    protected String user2Str() {
        return user2_0;
    }

    @Override
    protected boolean checkUser2Str(String xml) {
        return user2_0.equals(xml);
    }

    @Override
    protected String userListStr() {
//        return "<Users><item><id>101001</id><name>Jack</name><school><id>101</id><name>清华</name></school><deleted>false</deleted></item><item><id>102002</id><name>Alice</name><school><id>102</id><name>北大</name></school><deleted>true</deleted></item></Users>";
        return "<ArrayList><item><id>101001</id><name>Jack</name><school><id>101</id><name>清华</name></school><deleted>false</deleted></item><item><id>102002</id><name>Alice</name><school><id>102</id><name>北大</name></school><deleted>true</deleted></item></ArrayList>";
    }

    @Override
    protected boolean checkUserListStr(String xml) {
        return userListStr().equals(xml);
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
    protected boolean checkObjectListStr(String xml) {
        return objectListStr().equals(xml);
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

    @Override
    protected boolean checkObjectListStr(String xml, boolean pretty) {
        if (pretty) {
            return ("<ArrayList>\r\n" +
                    "  <item>\r\n" +
                    "    <id>101001</id>\r\n" +
                    "    <name>Jack</name>\r\n" +
                    "    <school>\r\n" +
                    "      <id>101</id>\r\n" +
                    "      <name>清华</name>\r\n" +
                    "    </school>\r\n" +
                    "    <deleted>false</deleted>\r\n" +
                    "  </item>\r\n" +
                    "  <item>\r\n" +
                    "    <id>102002</id>\r\n" +
                    "    <name>Alice</name>\r\n" +
                    "    <school>\r\n" +
                    "      <id>102</id>\r\n" +
                    "      <name>北大</name>\r\n" +
                    "    </school>\r\n" +
                    "    <deleted>true</deleted>\r\n" +
                    "  </item>\r\n" +
                    "  <item>Hello</item>\r\n" +
                    "  <item>100</item>\r\n" +
                    "</ArrayList>\r\n").equals(xml);
        }
        return checkObjectListStr(xml);
    }

    @Override
    protected boolean checkUserListStr(String xml, boolean pretty) {
        if (pretty) {
            return ("<ArrayList>\r\n" +
                    "  <item>\r\n" +
                    "    <id>101001</id>\r\n" +
                    "    <name>Jack</name>\r\n" +
                    "    <school>\r\n" +
                    "      <id>101</id>\r\n" +
                    "      <name>清华</name>\r\n" +
                    "    </school>\r\n" +
                    "    <deleted>false</deleted>\r\n" +
                    "  </item>\r\n" +
                    "  <item>\r\n" +
                    "    <id>102002</id>\r\n" +
                    "    <name>Alice</name>\r\n" +
                    "    <school>\r\n" +
                    "      <id>102</id>\r\n" +
                    "      <name>北大</name>\r\n" +
                    "    </school>\r\n" +
                    "    <deleted>true</deleted>\r\n" +
                    "  </item>\r\n" +
                    "</ArrayList>\r\n").equals(xml);
        }
        return checkUserListStr(xml);
    }

    @Override
    protected boolean checkUser2Str(String xml, boolean pretty) {
        if (pretty) {
            return ("<User>\r\n" +
                    "  <id>102002</id>\r\n" +
                    "  <name>Alice</name>\r\n" +
                    "  <school>\r\n" +
                    "    <id>102</id>\r\n" +
                    "    <name>北大</name>\r\n" +
                    "  </school>\r\n" +
                    "  <deleted>true</deleted>\r\n" +
                    "</User>\r\n").equals(xml);
        }
        return checkUser2Str(xml);
    }

    @Override
    protected boolean checkUser1Str(String xml, boolean pretty) {
        if (pretty) {
            return ("<User>\r\n" +
                    "  <id>101001</id>\r\n" +
                    "  <name>Jack</name>\r\n" +
                    "  <school>\r\n" +
                    "    <id>101</id>\r\n" +
                    "    <name>清华</name>\r\n" +
                    "  </school>\r\n" +
                    "  <deleted>false</deleted>\r\n" +
                    "</User>\r\n").equals(xml);
        }
        return checkUser1Str(xml);
    }

}
