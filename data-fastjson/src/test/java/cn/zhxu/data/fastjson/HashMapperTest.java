package cn.zhxu.data.fastjson;

import com.ejlchina.data.DataConvertor;
import com.ejlchina.data.DataSet;
import com.ejlchina.data.HashMapper;
import com.ejlchina.data.fastjson.FastjsonDataConvertor;
import org.junit.jupiter.api.Test;

public class HashMapperTest {

    DataConvertor convertor = new FastjsonDataConvertor();

    @Test
    public void test_01() {
        HashMapper school = new HashMapper();
        school.put("name", "清华大学");
        school.put("address", "北京");
        HashMapper user = new HashMapper();
        user.put("name", "Jack");
        user.put("age", 25);
        user.put("school", school);

        user.doEach((String key, DataSet.Data data) -> {
            System.out.println(key + " = " + data.toString());
        });

        String json = convertor.serialize(user);
        System.out.println(json);
    }

}
