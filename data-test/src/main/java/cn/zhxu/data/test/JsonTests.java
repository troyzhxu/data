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

    final String user2_0 = "{\"id\":102002,\"name\":\"Alice\",\"school\":{\"id\":102,\"name\":\"北大\"},\"deleted\":true}";
    final String user2_1 = "{\"deleted\":true,\"id\":102002,\"name\":\"Alice\",\"school\":{\"id\":102,\"name\":\"北大\"}}";
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

    @Override
    protected String user1Str() {
        return user1_0;
    }

    @Override
    protected boolean checkUser1Str(String json) {
        return user1_0.equals(json) || user1_1.equals(json);
    }

    @Override
    protected String user2Str() {
        return user2_0;
    }

    @Override
    protected boolean checkUser2Str(String json) {
        return user2_0.equals(json) || user2_1.equals(json);
    }

    @Override
    protected String userListStr() {
        return "[" + user1_0 + "," + user2_0 + "]";
    }

    @Override
    protected boolean checkUserListStr(String json) {
        return userListStr().equals(json) || ("[" + user1_1 + "," + user2_1 + "]").equals(json);
    }

    @Override
    protected String objectListStr() {
        return "[" + user1_0 + "," + user2_0 + ",\"Hello\",100]";
    }

    @Override
    protected boolean checkObjectListStr(String json) {
        return objectListStr().equals(json) || ("[" + user1_1 + "," + user2_1 + ",\"Hello\",100]").equals(json);
    }

    @Override
    protected boolean checkUser1Str(String json, boolean pretty) {
        if (pretty) {
            return user1_0_pretty.equals(json) || user1_1_pretty.equals(json);
        }
        return checkUser1Str(json);
    }

    @Override
    protected boolean checkUser2Str(String json, boolean pretty) {
        if (pretty) {
            return user2_0_pretty.equals(json) || user2_1_pretty.equals(json);
        }
        return checkUser2Str(json);
    }

    @Override
    protected boolean checkUserListStr(String json, boolean pretty) {
        if (pretty) {
            return ("[\n\t" + user1_0_pretty.replace("\n", "\n\t") + ",\n\t" +
                    user2_0_pretty.replace("\n", "\n\t") + "\n]").equals(json) ||
                    ("[\n\t" + user1_1_pretty.replace("\n", "\n\t") + ",\n\t" +
                            user2_1_pretty.replace("\n", "\n\t") + "\n]").equals(json);
        }
        return checkUserListStr(json);
    }

    @Override
    protected boolean checkObjectListStr(String json, boolean pretty) {
        if (pretty) {
            return ("[\n\t" + user1_0_pretty.replace("\n", "\n\t") + ",\n\t" +
                    user2_0_pretty.replace("\n", "\n\t") + ",\n\t\"Hello\",\n\t100\n]").equals(json) ||
                    ("[\n\t" + user1_1_pretty.replace("\n", "\n\t") + ",\n\t" +
                            user2_1_pretty.replace("\n", "\n\t") + ",\n\t\"Hello\",\n\t100\n]").equals(json);
        }
        return checkObjectListStr(json);
    }

}
