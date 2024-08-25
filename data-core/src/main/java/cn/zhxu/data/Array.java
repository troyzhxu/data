package cn.zhxu.data;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * 列表结构的只读数据集
 * Array 接口 类似于 JsonArray
 * 但为什么不取名为 JsonArray 呢，因它不止是 json，它还可以是 xml、yml、protobuf 等任何一种格式的数据
 * @author Troy.Zhou
 * @since 1.0.0
 */
public interface Array extends DataSet, Iterable<DataSet.Data> {

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
	 * @return 此集合中元素上的顺序流
	 * @since v1.6.0
	 */
	default Stream<Data> stream() {
		return StreamSupport.stream(Spliterators.spliterator(iterator(), size(), 0), false);
	}

	/**
	 * @return 迭代器
	 * @since v1.6.0
	 */
	@Override
	default Iterator<Data> iterator() {
		return new Itr(this);
	}

	class Itr implements Iterator<Data> {

		Array array;
		int cursor = 0;

		Itr(Array array) {
			this.array = array;
		}

		public boolean hasNext() {
			return cursor < array.size();
		}

		public Data next() {
			int i = cursor;
			if (i >= array.size())
				throw new NoSuchElementException();
			return new IdxData(array, cursor++);
		}

	}

	class IdxData implements Data {

		private final Array array;
		private final int index;

        public IdxData(Array array, int index) {
			this.array = array;
            this.index = index;
        }

        @Override
		public Mapper toMapper() {
			return array.getMapper(index);
		}

		@Override
		public Array toArray() {
			return array.getArray(index);
		}

		@Override
		public boolean toBool() {
			return array.getBool(index);
		}

		@Override
		public int toInt() {
			return array.getInt(index);
		}

		@Override
		public long toLong() {
			return array.getLong(index);
		}

		@Override
		public float toFloat() {
			return array.getFloat(index);
		}

		@Override
		public double toDouble() {
			return array.getDouble(index);
		}

		@Override
		public String toString() {
			return array.getString(index);
		}

	}

	/**
	 * 遍历 Array
	 * @param consumer 消费者
	 */
	default void forEach(BiConsumer<Integer, Data> consumer) {
		for (int i = 0; i < size(); i++) {
            consumer.accept(i, new IdxData(this, i));
		}
	}

	/**
	 * @param <T> 目标泛型
	 * @param type 目标类型
	 * @return 转 JavaBean 列表
	 * @since v1.2.0
	 */
	<T> List<T> toList(Class<T> type);

	/**
	 * 转换为 List 对象
	 * @return List 对象
	 * @since v1.5.0
	 */
	List<Object> toList();

}
