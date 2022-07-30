package cn.zhxu.data;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 数据转换器
 * @since 2.5.2
 */
public interface DataConvertor {

    /**
     * 解析 Mapper
     * @param in JSON/XML 输入流
     * @param charset 编码格式
     * @return Mapper
     */
    Mapper toMapper(InputStream in, Charset charset);

    /**
     * 解析 Mapper
     * @param in JSON/XML 字符串
     * @return Mapper
     * @since v1.2.0
     */
    default Mapper toMapper(String in) {
        return toMapper(new ByteArrayInputStream(in.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }

    /**
     * 解析 Array
     * @param in JSON/XML 输入流
     * @param charset 编码格式
     * @return Array
     */
    Array toArray(InputStream in, Charset charset);

    /**
     * 解析 Array
     * @param in JSON/XML 字符串
     * @return Array
     * @since v1.2.0
     */
    default Array toArray(String in) {
        return toArray(new ByteArrayInputStream(in.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }

    /**
     * 将 Java 对象序列化为字节数组
     * @param object Java 对象
     * @param charset 编码格式
     * @return 字节数组
     */
    byte[] serialize(Object object, Charset charset);

    /**
     * 将 Java 对象序列化为字符串
     * @param object Java 对象
     * @return 字符串
     * @since v1.2.0
     */
    default String serialize(Object object) {
        return new String(serialize(object, StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    }

    /**
     * 解析 Java Bean
     * @param <T> 目标泛型
     * @param type 目标类型
     * @param in JSON/XML 输入流
     * @param charset 编码格式
     * @return Java Bean
     */
    <T> T toBean(Type type, InputStream in, Charset charset);

    /**
     * 解析 Java Bean
     * @param <T> 目标泛型
     * @param type 目标类型
     * @param in JSON/XML 字符串
     * @return Java Bean
     * @since v1.2.0
     */
    default <T> T toBean(Type type, String in) {
        return toBean(type, new ByteArrayInputStream(in.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }

    /**
     * 解析为 Java List
     * @param <T> 目标泛型
     * @param type 目标类型
     * @param in JSON/XML 输入流
     * @param charset 编码格式
     * @return Java List
     */
    <T> List<T> toList(Class<T> type, InputStream in, Charset charset);

    /**
     * 解析为 Java List
     * @param <T> 目标泛型
     * @param type 目标类型
     * @param in JSON/XML 输入流
     * @return Java List
     * @since v1.2.0
     */
    default <T> List<T> toList(Class<T> type, String in) {
        return toList(type, new ByteArrayInputStream(in.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }

}
