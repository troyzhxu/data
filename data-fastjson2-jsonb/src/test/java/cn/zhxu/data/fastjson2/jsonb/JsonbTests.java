package cn.zhxu.data.fastjson2.jsonb;

import cn.zhxu.data.DataConvertor;
import com.alibaba.fastjson2.JSONB;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * @author Troy.Zhou @ 2022/5/27
 * @since v
 */
public class JsonbTests extends Tests {

    public JsonbTests(DataConvertor convertor) {
        super(convertor);
    }

    final String user1_0 = Base64.getEncoder().encodeToString(JSONB.toBytes(user1));
    final String user2_0 = Base64.getEncoder().encodeToString(JSONB.toBytes(user2));

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
        return Base64.getEncoder().encodeToString(JSONB.toBytes(userList));
    }

    @Override
    protected boolean checkUserListStr(String serialize) {
        return userListStr().equals(serialize);
    }

    @Override
    protected String objectListStr() {
        List<Object> list = new ArrayList<>();
        list.add(user1);
        list.add(user2);
        list.add("Hello");
        list.add(100);
        return Base64.getEncoder().encodeToString(JSONB.toBytes(list));
    }

    @Override
    protected boolean checkObjectListStr(String serialize) {
        return objectListStr().equals(serialize);
    }

}
