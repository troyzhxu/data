package cn.zhxu.data.test;

import cn.zhxu.data.DataConvertor;

/**
 * @author Troy.Zhou @ 2022/5/27
 * @since v
 */
public class JsonTests extends Tests {

    public JsonTests(DataConvertor convertor) {
        super(convertor);
    }

    final String user1_0 = "{\"id\":101001,\"name\":\"Jack\",\"school\":{\"id\":101,\"name\":\"清华\"},\"deleted\":false}";
    final String user1_1 = "{\"deleted\":false,\"id\":101001,\"name\":\"Jack\",\"school\":{\"id\":101,\"name\":\"清华\"}}";
    final String user1_2 = "{\"deleted\":false,\"school\":{\"name\":\"清华\",\"id\":101},\"name\":\"Jack\",\"id\":101001}";
    final String user1_0_pretty = "{\n" +
            "\t\"id\":101001,\n" +
            "\t\"name\":\"Jack\",\n" +
            "\t\"school\":{\n" +
            "\t\t\"id\":101,\n" +
            "\t\t\"name\":\"清华\"\n" +
            "\t},\n" +
            "\t\"deleted\":false\n" +
            "}";
    final String user1_1_pretty = "{\n" +
            "\t\"deleted\":false,\n" +
            "\t\"id\":101001,\n" +
            "\t\"name\":\"Jack\",\n" +
            "\t\"school\":{\n" +
            "\t\t\"id\":101,\n" +
            "\t\t\"name\":\"清华\"\n" +
            "\t}\n" +
            "}";
    final String user1_2_pretty = "{\n" +
            "\t\"deleted\":false,\n" +
            "\t\"school\":{\n" +
            "\t\t\"name\":\"清华\",\n" +
            "\t\t\"id\":101\n" +
            "\t},\n" +
            "\t\"name\":\"Jack\",\n" +
            "\t\"id\":101001\n" +
            "}";

    final String user2_0 = "{\"id\":102002,\"name\":\"Alice\",\"school\":{\"id\":102,\"name\":\"北大\"},\"deleted\":true}";
    final String user2_1 = "{\"deleted\":true,\"id\":102002,\"name\":\"Alice\",\"school\":{\"id\":102,\"name\":\"北大\"}}";
    final String user2_2 = "{\"deleted\":true,\"school\":{\"name\":\"北大\",\"id\":102},\"name\":\"Alice\",\"id\":102002}";
    final String user2_0_pretty = "{\n" +
            "\t\"id\":102002,\n" +
            "\t\"name\":\"Alice\",\n" +
            "\t\"school\":{\n" +
            "\t\t\"id\":102,\n" +
            "\t\t\"name\":\"北大\"\n" +
            "\t},\n" +
            "\t\"deleted\":true\n" +
            "}";
    final String user2_1_pretty = "{\n" +
            "\t\"deleted\":true,\n" +
            "\t\"id\":102002,\n" +
            "\t\"name\":\"Alice\",\n" +
            "\t\"school\":{\n" +
            "\t\t\"id\":102,\n" +
            "\t\t\"name\":\"北大\"\n" +
            "\t}\n" +
            "}";
    final String user2_2_pretty = "{\n" +
            "\t\"deleted\":true,\n" +
            "\t\"school\":{\n" +
            "\t\t\"name\":\"北大\",\n" +
            "\t\t\"id\":102\n" +
            "\t},\n" +
            "\t\"name\":\"Alice\",\n" +
            "\t\"id\":102002\n" +
            "}";

    @Override
    protected String user1Str() {
        return user1_0;
    }

    @Override
    protected boolean checkUser1Str(String json) {
        return user1_0.equals(json) || user1_1.equals(json) || user1_2.equals(json);
    }

    @Override
    protected String user2Str() {
        return user2_0;
    }

    @Override
    protected boolean checkUser2Str(String json) {
        return user2_0.equals(json) || user2_1.equals(json) || user2_2.equals(json);
    }

    @Override
    protected String userListStr() {
        return "[" + user1_0 + "," + user2_0 + "]";
    }

    @Override
    protected boolean checkUserListStr(String json) {
        return userListStr().equals(json) ||
                ("[" + user1_1 + "," + user2_1 + "]").equals(json) ||
                ("[" + user1_2 + "," + user2_2 + "]").equals(json);
    }

    @Override
    protected String objectListStr() {
        return "[" + user1_0 + "," + user2_0 + ",\"Hello\",100]";
    }

    @Override
    protected boolean checkObjectListStr(String json) {
        return objectListStr().equals(json) ||
                ("[" + user1_1 + "," + user2_1 + ",\"Hello\",100]").equals(json) ||
                ("[" + user1_2 + "," + user2_2 + ",\"Hello\",100]").equals(json);
    }

    @Override
    protected boolean checkUser1Str(String json, boolean pretty) {
        if (pretty) {
            return prettyJsonEquals(user1_0_pretty, json) || prettyJsonEquals(user1_1_pretty, json) ||
                    prettyJsonEquals(user1_2_pretty, json);
        }
        return checkUser1Str(json);
    }

    @Override
    protected boolean checkUser2Str(String json, boolean pretty) {
        if (pretty) {
            return prettyJsonEquals(user2_0_pretty, json) || prettyJsonEquals(user2_1_pretty, json) ||
                    prettyJsonEquals(user2_2_pretty, json);
        }
        return checkUser2Str(json);
    }

    @Override
    protected boolean checkUserListStr(String json, boolean pretty) {
        if (pretty) {
            return prettyJsonEquals("[\n\t" + user1_0_pretty.replace("\n", "\n\t") + ",\n\t" +
                    user2_0_pretty.replace("\n", "\n\t") + "\n]", json) ||
                    prettyJsonEquals("[\n\t" + user1_1_pretty.replace("\n", "\n\t") + ",\n\t" +
                    user2_1_pretty.replace("\n", "\n\t") + "\n]", json) ||
                    prettyJsonEquals("[\n\t" + user1_2_pretty.replace("\n", "\n\t") + ",\n\t" +
                            user2_2_pretty.replace("\n", "\n\t") + "\n]", json) ||
                    prettyJsonEquals("[ " + user1_0_pretty + ", " + user2_0_pretty + " ]", json) ||
                    prettyJsonEquals("[ " + user1_1_pretty + ", " + user2_1_pretty + " ]", json) ||
                    prettyJsonEquals("[ " + user1_2_pretty + ", " + user2_2_pretty + " ]", json);
        }
        return checkUserListStr(json);
    }

    @Override
    protected boolean checkObjectListStr(String json, boolean pretty) {
        if (pretty) {
            return prettyJsonEquals("[\n\t" + user1_0_pretty.replace("\n", "\n\t") + ",\n\t" +
                     user2_0_pretty.replace("\n", "\n\t") + ",\n\t\"Hello\",\n\t100\n]", json) ||
                    prettyJsonEquals("[\n\t" + user1_1_pretty.replace("\n", "\n\t") + ",\n\t" +
                            user2_1_pretty.replace("\n", "\n\t") + ",\n\t\"Hello\",\n\t100\n]", json) ||
                    prettyJsonEquals("[\n\t" + user1_2_pretty.replace("\n", "\n\t") + ",\n\t" +
                            user2_2_pretty.replace("\n", "\n\t") + ",\n\t\"Hello\",\n\t100\n]", json) ||
                    prettyJsonEquals("[ " + user1_0_pretty + ", " + user2_0_pretty + ", \"Hello\", 100 ]", json) ||
                    prettyJsonEquals("[ " + user1_1_pretty + ", " + user2_1_pretty + ", \"Hello\", 100 ]", json) ||
                    prettyJsonEquals("[ " + user1_2_pretty + ", " + user2_2_pretty + ", \"Hello\", 100 ]", json);
        }
        return checkObjectListStr(json);
    }

    @Override
    String getStringForMapperValuesTest() {
        return "{\"id\":1,\"age\":20}";
    }

    @Override
    String getStringForArrayStreamTest() {
        return "[\"id\",\"age\",\"name\"]";
    }

    private boolean prettyJsonEquals(String expect, String json) {
        return expect.equals(json) || expect.replace("\t", "  ").equals(json) ||
                expect.replace(":", ": ").equals(json) ||
                expect.replace(":", " : ").equals(json) ||
                expect.replace("\t", "  ").replace(":", ": ").equals(json) ||
                expect.replace("\t", "  ").replace(":", " : ").equals(json) ||
                expect.replace("\n", "\r\n").equals(json) ||
                expect.replace("\n", "\r\n").replace("\t", "  ").equals(json) ||
                expect.replace("\n", "\r\n").replace(":", ": ").equals(json) ||
                expect.replace("\n", "\r\n").replace(":", " : ").equals(json) ||
                expect.replace("\n", "\r\n").replace("\t", "  ").replace(":", ": ").equals(json) ||
                expect.replace("\n", "\r\n").replace("\t", "  ").replace(":", " : ").equals(json);
    }

}
