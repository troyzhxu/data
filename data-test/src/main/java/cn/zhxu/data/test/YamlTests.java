package cn.zhxu.data.test;

import cn.zhxu.data.DataConvertor;

/**
 * @author Troy.Zhou @ 2022/5/27
 * @since v
 */
public class YamlTests extends Tests {

    public YamlTests(DataConvertor convertor) {
        super(convertor);
    }

    final String user1_0 = "{\"id\":101001,\"name\":\"Jack\",\"school\":{\"id\":101,\"name\":\"清华\"},\"deleted\":false}";
    final String user1_1 = "{\"deleted\":false,\"id\":101001,\"name\":\"Jack\",\"school\":{\"id\":101,\"name\":\"清华\"}}";

    final String user2_0 = "{\"id\":102002,\"name\":\"Alice\",\"school\":{\"id\":102,\"name\":\"北大\"},\"deleted\":true}";
    final String user2_1 = "{\"deleted\":true,\"id\":102002,\"name\":\"Alice\",\"school\":{\"id\":102,\"name\":\"北大\"}}";

    @Override
    protected String user1Str() {
        return user1_0;
    }

    @Override
    protected boolean checkUser1Str(String serialize) {
        return user1_0.equals(serialize) || user1_1.equals(serialize);
    }

    @Override
    protected String user2Str() {
        return user2_0;
    }

    @Override
    protected boolean checkUser2Str(String serialize) {
        return user2_0.equals(serialize) || user2_1.equals(serialize);
    }

    @Override
    protected String userListStr() {
        return "[" + user1_0 + "," + user2_0 + "]";
    }

    @Override
    protected boolean checkUserListStr(String serialize) {
        return userListStr().equals(serialize) || ("[" + user1_1 + "," + user2_1 + "]").equals(serialize);
    }

    @Override
    protected String objectListStr() {
        return "[" + user1_0 + "," + user2_0 + ",\"Hello\",100]";
    }

    @Override
    protected boolean checkObjectListStr(String serialize) {
        return objectListStr().equals(serialize) || ("[" + user1_1 + "," + user2_1 + ",\"Hello\",100]").equals(serialize);
    }

}
