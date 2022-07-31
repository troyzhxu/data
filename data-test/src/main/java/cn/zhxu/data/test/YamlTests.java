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

    final String user1_0 = "---\n" +
            "id: 101001\n" +
            "name: \"Jack\"\n" +
            "school:\n" +
            "  id: 101\n" +
            "  name: \"清华\"\n" +
            "deleted: false";
    final String user2_0 = "---\n" +
            "id: 102002\n" +
            "name: \"Alice\"\n" +
            "school:\n" +
            "  id: 102\n" +
            "  name: \"北大\"\n" +
            "deleted: true";

    @Override
    protected String user1Str() {
        return user1_0;
    }

    @Override
    protected boolean checkUser1Str(String serialize) {
        return user1_0.equals(serialize.trim());
    }

    @Override
    protected String user2Str() {
        return user2_0;
    }

    @Override
    protected boolean checkUser2Str(String serialize) {
        return user2_0.equals(serialize.trim());
    }

    @Override
    protected String userListStr() {
        return "---\n" +
                "- id: 101001\n" +
                "  name: \"Jack\"\n" +
                "  school:\n" +
                "    id: 101\n" +
                "    name: \"清华\"\n" +
                "  deleted: false\n" +
                "- id: 102002\n" +
                "  name: \"Alice\"\n" +
                "  school:\n" +
                "    id: 102\n" +
                "    name: \"北大\"\n" +
                "  deleted: true";
    }

    @Override
    protected boolean checkUserListStr(String serialize) {
        return userListStr().equals(serialize.trim());
    }

    @Override
    protected String objectListStr() {
        return "---\n" +
                "- id: 101001\n" +
                "  name: \"Jack\"\n" +
                "  school:\n" +
                "    id: 101\n" +
                "    name: \"清华\"\n" +
                "  deleted: false\n" +
                "- id: 102002\n" +
                "  name: \"Alice\"\n" +
                "  school:\n" +
                "    id: 102\n" +
                "    name: \"北大\"\n" +
                "  deleted: true\n" +
                "- \"Hello\"\n" +
                "- 100";
    }

    @Override
    protected boolean checkObjectListStr(String serialize) {
        return objectListStr().equals(serialize.trim());
    }

}
