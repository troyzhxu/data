package com.ejlchina.data;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * 一个 Key 可以有多个 Value 的 有序 Map 集合，内部使用 {@link List } 实现
 * @author troy zhou
 * @since v1.4.0
 * @param <V> 泛型 值
 */
public interface ListMap<V> extends Map<String, V> {

    /**
     * 向 Map 里放值
     * @param key 键
     * @param value 值
     * @return always null
     */
    V put(String key, V value);

    /**
     * 获取与指定 key 匹配的最后（新）的一个值
     * @param key 键
     * @return 最后（新）的一个值
     */
    V get(Object key);

    /**
     * 获取与指定 key 匹配的最后（新）的一个值
     * @param key 键
     * @param ic 匹配 key 时是否忽略大小写
     * @return 匹配 key 的最后（新）的一个值
     */
    V get(String key, boolean ic);

    /**
     * 获取与指定 key 匹配的所有值列表
     * @param key 键
     * @return List
     */
    default List<V> list(String key) {
        return list(key, false);
    }

    /**
     * 获取与指定 key 匹配的所有值列表
     * @param key 键
     * @param ic 匹配 key 时是否忽略大小写
     * @return List
     */
    List<V> list(String key, boolean ic);

    /**
     * 替换与指定 key 匹配的最后（新）的一个值
     * @param key 键
     * @return 被替换的值
     */
    V replace(String key, V value);

    /**
     * 替换与指定 key 匹配的最后（新）的一个值
     * @param key 键
     * @param ic 匹配 key 时是否忽略大小写
     * @return 被替换的值
     */
    V replace(String key, V value, boolean ic);

    /**
     * 替换与指定 key 匹配的所有值
     * @param key 键
     * @return 被替换的键值对数量
     */
    default int replaceAll(String key, V value) {
        return replaceAll(key, value, false);
    }

    /**
     * 替换与指定 key 匹配的所有值
     * @param key 键
     * @param ic 匹配 key 时是否忽略大小写
     * @return 被替换的键值对数量
     */
    int replaceAll(String key, V value, boolean ic);

    /**
     * 遍历，该方法是为兼容 Android 低版本
     * @param action The action to be performed for each entry
     */
    void forEach(BiConsumer<? super String, ? super V> action);

    /**
     * 移除与指定 key 匹配的最后（新）一个值
     * @param key 键
     * @return the value was removed
     */
    V remove(Object key);

    /**
     * 移除与指定 key 匹配的最后（新）一个值
     * @param key 键
     * @param ic 匹配 key 时是否忽略大小写
     * @return the value was removed
     */
    V remove(String key, boolean ic);

    /**
     * 移除与指定 key 匹配的所有值
     * @param key 键
     * @return the value was removed
     */
    default List<V> removeAll(String key) {
        return removeAll(key, false);
    }

    /**
     * 移除与指定 key 匹配的所有值
     * @param key 键
     * @param ic 匹配 key 时是否忽略大小写
     * @return the value was removed
     */
    List<V> removeAll(String key, boolean ic);

}
