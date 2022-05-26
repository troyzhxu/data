package com.ejlchina.data;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * 列表结构的只读数据集
 * Array 接口 类似于 JsonArray
 * 但为什么不取名为 JsonArray 呢，因它不止是 json，它还可以是 xml、yml、protobuf 等任何一种格式的数据
 * @author Troy.Zhou
 * @since 2.0.0
 */
public interface Array extends DataSet {

	/**
	 * @param index 元素下标
	 * @return 子 JsonObj
	 */
	Mapper getMapper(int index);
	
	/**
	 * @param index 元素下标
	 * @return 子 JsonArr
	 */
	Array getArray(int index);
	
	/**
	 * @param index 元素下标
	 * @return boolean 值
	 */
	boolean getBool(int index);

	/**
	 * @param index 元素下标
	 * @return int 值
	 */
	int getInt(int index);
	
	/**
	 * @param index 元素下标
	 * @return long 值
	 */
	long getLong(int index);
	
	/**
	 * @param index 元素下标
	 * @return float 值
	 */
	float getFloat(int index);
	
	/**
	 * @param index 元素下标
	 * @return double 值
	 */
	double getDouble(int index);
	
	/**
	 * @param index 元素下标
	 * @return String 值
	 */
	String getString(int index);

	/**
	 * 遍历 Array
	 * @since 2.5.1
	 * @param consumer 消费者
	 */
	default void doEach(BiConsumer<Integer, Data> consumer) {
		for (int i = 0; i < size(); i++) {
			int index = i;
			consumer.accept(i, new Data() {
				@Override
				public Mapper toMapper() {
					return getMapper(index);
				}

				@Override
				public Array toArray() {
					return getArray(index);
				}

				@Override
				public boolean toBool() {
					return getBool(index);
				}

				@Override
				public int toInt() {
					return getInt(index);
				}

				@Override
				public long toLong() {
					return getLong(index);
				}

				@Override
				public float toFloat() {
					return getFloat(index);
				}

				@Override
				public double toDouble() {
					return getDouble(index);
				}

				@Override
				public String toString() {
					return getString(index);
				}

			});
		}
	}

	/**
	 * @param <T> 目标泛型
	 * @param type 目标类型
	 * @return 转 JavaBean 列表
	 * @since v1.2.0
	 */
	@SuppressWarnings("unchecked")
	default <T> List<T> toList(Class<T> type) {
		int size = size();
		if (type == Boolean.class || type == boolean.class) {
			List<Boolean> list = new ArrayList<>(size);
			doEach((i, data) -> list.add(data.toBool()));
			return (List<T>) list;
		}
		if (type == Integer.class || type == int.class) {
			List<Integer> list = new ArrayList<>(size);
			doEach((i, data) -> list.add(data.toInt()));
			return (List<T>) list;
		}
		if (type == Long.class || type == long.class) {
			List<Long> list = new ArrayList<>(size);
			doEach((i, data) -> list.add(data.toLong()));
			return (List<T>) list;
		}
		if (type == Float.class || type == float.class) {
			List<Float> list = new ArrayList<>(size);
			doEach((i, data) -> list.add(data.toFloat()));
			return (List<T>) list;
		}
		if (type == Double.class || type == double.class) {
			List<Double> list = new ArrayList<>(size);
			doEach((i, data) -> list.add(data.toDouble()));
			return (List<T>) list;
		}
		if (type == String.class) {
			List<String> list = new ArrayList<>(size);
			doEach((i, data) -> list.add(data.toString()));
			return (List<T>) list;
		}
		List<T> list = new ArrayList<>(size);
		for (int index = 0; index < size; index++) {
			Mapper m = getMapper(index);
			list.add(m != null ? m.toBean(type) : null);
		}
		return list;
	}

}
