package cn.zhxu.data;

import java.util.*;
import java.util.function.BiConsumer;

/**
 * 一个 Key 可以有多个 Value 的 有序 Map 集合，内部使用 {@link List } 实现
 * @author troy zhou
 * @since v1.4.0
 * @param <V> 泛型 值
 */
public class BaseListMap<V> extends AbstractMap<String, V> implements ListMap<V> {

    transient final List<Entry<String, V>> entries;

    public BaseListMap(List<Entry<String, V>> entries) {
        this.entries = entries;
    }

    /**
     * 键值对集合类
     */
    class EntrySet extends AbstractSet<Entry<String, V>> {

        @Override
        public Iterator<Entry<String, V>> iterator() {
            return entries.iterator();
        }

        @Override
        public int size() {
            return entries.size();
        }

    }

    transient Set<Entry<String, V>> entrySet;

    /**
     * @return 键值对集合
     */
    @Override
    public Set<Entry<String, V>> entrySet() {
        Set<Map.Entry<String,V>> es = entrySet;
        return es == null ? (entrySet = new EntrySet()) : es;
    }

    /**
     * 向 Map 里放值
     * @param key 键
     * @param value 值
     * @return always null
     */
    @Override
    public V put(String key, V value) {
        if (key != null) {
            entries.add(new SimpleEntry<>(key, value));
        }
        return null;
    }

    /**
     * 获取与指定 key 匹配的最后（新）的一个值
     * @param key 键
     * @return 最后（新）的一个值
     */
    @Override
    public V get(Object key) {
        if (key instanceof String) {
            return get((String) key, false);
        }
        return null;
    }

    /**
     * 获取与指定 key 匹配的最后（新）的一个值
     * @param key 键
     * @param ic 匹配 key 时是否忽略大小写
     * @return 匹配 key 的最后（新）的一个值
     */
    public V get(String key, boolean ic) {
        if (key != null) {
            ListIterator<Entry<String, V>> it = entries.listIterator(entries.size());
            while (it.hasPrevious()) {
                Entry<String, V> entry = it.previous();
                if (keyMatch(key, entry.getKey(), ic)) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    private boolean keyMatch(String key1, String key2, boolean ic) {
        return ic && key1.equalsIgnoreCase(key2) || !ic && key1.equals(key2);
    }

    /**
     * 获取与指定 key 匹配的所有值列表
     * @param key 键
     * @param ic 匹配 key 时是否忽略大小写
     * @return List
     */
    public List<V> list(String key, boolean ic) {
        List<V> list = new ArrayList<>();
        if (key != null) {
            for (Entry<String, V> entry : entries) {
                if (keyMatch(key, entry.getKey(), ic)) {
                    list.add(entry.getValue());
                }
            }
        }
        return list;
    }

    public boolean replace(String key, V oldValue, V newValue) {
        throw new UnsupportedOperationException();
    }

    /**
     * 替换与指定 key 匹配的最后（新）的一个值
     * @param key 键
     * @return 被替换的值
     */
    public V replace(String key, V value) {
        return replace(key, value, false);
    }

    /**
     * 替换与指定 key 匹配的最后（新）的一个值
     * @param key 键
     * @param ic 匹配 key 时是否忽略大小写
     * @return 被替换的值
     */
    public V replace(String key, V value, boolean ic) {
        if (key != null) {
            ListIterator<Entry<String, V>> it = entries.listIterator(entries.size());
            while (it.hasPrevious()) {
                Entry<String, V> entry = it.previous();
                if (keyMatch(key, entry.getKey(), ic)) {
                    return entry.setValue(value);
                }
            }
        }
        return null;
    }

    /**
     * 替换与指定 key 匹配的所有值
     * @param key 键
     * @param ic 匹配 key 时是否忽略大小写
     * @return 被替换的键值对数量
     */
    public int replaceAll(String key, V value, boolean ic) {
        int count = 0;
        if (key != null) {
            for (Entry<String, V> entry : entries) {
                if (keyMatch(key, entry.getKey(), ic)) {
                    entry.setValue(value);
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * 遍历，该方法是为兼容 Android 低版本
     * @param action The action to be performed for each entry
     */
    public void forEach(BiConsumer<? super String, ? super V> action) {
        Objects.requireNonNull(action);
        for (Map.Entry<String, V> entry : entries) {
            action.accept(entry.getKey(), entry.getValue());
        }
    }

    public boolean remove(Object key, Object value) {
        throw new UnsupportedOperationException();
    }

    /**
     * 移除与指定 key 匹配的最后（新）一个值
     * @param key 键
     * @return the value was removed
     */
    public V remove(Object key) {
        if (key instanceof String) {
            return remove((String) key, false);
        }
        return null;
    }

    /**
     * 移除与指定 key 匹配的最后（新）一个值
     * @param key 键
     * @param ic 匹配 key 时是否忽略大小写
     * @return the value was removed
     */
    public V remove(String key, boolean ic) {
        if (key != null) {
            ListIterator<Entry<String, V>> it = entries.listIterator(entries.size());
            while (it.hasPrevious()) {
                Entry<String, V> entry = it.previous();
                if (keyMatch(key, entry.getKey(), ic)) {
                    it.remove();
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 移除与指定 key 匹配的所有值
     * @param key 键
     * @param ic 匹配 key 时是否忽略大小写
     * @return the value was removed
     */
    public List<V> removeAll(String key, boolean ic) {
        List<V> list = new ArrayList<>();
        if (key != null) {
            Iterator<Entry<String, V>> it = entries.iterator();
            while (it.hasNext()) {
                Entry<String, V> entry = it.next();
                if (keyMatch(key, entry.getKey(), ic)) {
                    list.add(entry.getValue());
                    it.remove();
                }
            }
        }
        return list;
    }

}
