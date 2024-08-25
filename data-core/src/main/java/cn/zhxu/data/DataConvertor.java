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
     * @param in JSON/XML 字符串（如果是 JSONB，则传 Base64后的字符串）
     * @return Mapper
     * @since v1.2.0
     * @throws IllegalArgumentException 解析 JSONB 时，if {@code in} is not in valid Base64 scheme
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
     * @param in JSON/XML 字符串（如果是 JSONB，则传 Base64后的字符串）
     * @return Array
     * @since v1.2.0
     * @throws IllegalArgumentException 解析 JSONB 时，if {@code in} is not in valid Base64 scheme
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
    default byte[] serialize(Object object, Charset charset) {
        return serialize(object, charset, false);
    }

    /**
     * 将 Java 对象序列化为字节数组
     * @param object Java 对象
     * @param charset 编码格式
     * @param pretty 格式化输出
     * @return 字节数组
     * @since v1.6.0
     */
    byte[] serialize(Object object, Charset charset, boolean pretty);

    /**
     * 将 Java 对象序列化为字符串
     * @param object Java 对象
     * @return 字符串
     * @since v1.2.0
     */
    default String serialize(Object object) {
        return serialize(object, false);
    }

    /**
     * 将 Java 对象序列化为字符串
     * @param object Java 对象
     * @param pretty 格式化输出
     * @return 字符串
     * @since v1.6.0
     */
    default String serialize(Object object, boolean pretty) {
        return new String(serialize(object, StandardCharsets.UTF_8, pretty), StandardCharsets.UTF_8);
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
     * @param in JSON/XML 字符串（如果是 JSONB，则传 Base64后的字符串）
     * @return Java Bean
     * @throws IllegalArgumentException 解析 JSONB 时，if {@code in} is not in valid Base64 scheme
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
     * @param in JSON/XML 输入流（如果是 JSONB，则传 Base64后的字符串）
     * @return Java List
     * @throws IllegalArgumentException 解析 JSONB 时，if {@code in} is not in valid Base64 scheme
     * @since v1.2.0
     */
    default <T> List<T> toList(Class<T> type, String in) {
        return toList(type, new ByteArrayInputStream(in.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }

}
