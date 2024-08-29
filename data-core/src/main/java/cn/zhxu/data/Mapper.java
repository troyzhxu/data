package cn.zhxu.data;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * 映射结构的只读数据集
 * Mapper 接口，类似于 JsonObject
 * 但为什么不取名为 JsonObject 呢? 因它不止是 json，它还可以是 xml、yml、protobuf 等任何一种格式的数据
 * @author Troy.Zhou
 * @since 1.0.0
 */
public interface Mapper extends DataSet {

    /**
     * @param key 键名
     * @return 子 JsonObj
     */
    Mapper getMapper(String key);
    
    /**
     * @param key 键名
     * @return 子 JsonArr
     */
    Array getArray(String key);
    
    /**
     * @param key 键名
     * @return boolean 值
     */
    boolean getBool(String key);

    /**
     * @param key 键名
     * @return int 值
     */
    int getInt(String key);
    
    /**
     * @param key 键名
     * @return long 值
     */
    long getLong(String key);
    
    /**
     * @param key 键名
     * @return float 值
     */
    float getFloat(String key);
    
    /**
     * @param key 键名
     * @return double 值
     */
    double getDouble(String key);
    
    /**
     * @param key 键名
     * @return String 值
     */
    String getString(String key);

    /**
     * @param key 键名
     * @return 是否有该键
     */
    boolean has(String key);
    
    /**
     * @return 键集合
     */
    Set<String> keySet();

    /**
     * @return 值集合
     * @since v1.6.0
     */
    default List<Data> values() {
        List<KeyData> values = keySet().stream()
                .map(k -> new KeyData(this, k))
                .collect(Collectors.toList());
        return Collections.unmodifiableList(values);
    }

    class KeyData implements Data {

        Mapper mapper;
        String key;

        public KeyData(Mapper mapper, String key) {
            this.mapper = mapper;
            this.key = key;
        }

        @Override
        public Mapper toMapper() {
            return mapper.getMapper(key);
        }

        @Override
        public Array toArray() {
            return mapper.getArray(key);
        }

        @Override
        public boolean toBool() {
            return mapper.getBool(key);
        }

        @Override
        public int toInt() {
            return mapper.getInt(key);
        }

        @Override
        public long toLong() {
            return mapper.getLong(key);
        }

        @Override
        public float toFloat() {
            return mapper.getFloat(key);
        }

        @Override
        public double toDouble() {
            return mapper.getDouble(key);
        }

        @Override
        public String toString() {
            return mapper.getString(key);
        }

    }

    /**
     * 遍历 Mapper
     * @since 2.5.1
     * @param consumer 消费者
     */
    default void forEach(BiConsumer<String, Data> consumer) {
        for (String key: keySet()) {
            consumer.accept(key, new KeyData(this, key));
        }
    }

    /**
     * @param <T> 目标泛型
     * @param type 目标类型
     * @return JavaBean
     * @since v1.2.0
     */
    <T> T toBean(Class<T> type);

    /**
     * @param <T> 目标泛型
     * @param type 目标类型
     * @return JavaBean
     * @since v1.2.0
     */
    <T> T toBean(Type type);

    /**
     * @param <T> 目标泛型
     * @param type 目标类型
     * @return JavaBean
     * @since v1.2.0
     */
    <T> T toBean(TypeRef<T> type);

    /**
     * 转换为 Map 对象
     * @return Map 对象
     * @since v1.5.0
     */
    Map<String, Object> toMap();

}
