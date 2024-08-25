package cn.zhxu.data;

/**
 * 只读数据集
 * @since 2.5.0
 */
public interface DataSet {

    /**
     * @return 集合内直接子元素的数量
     */
    int size();

    /**
     * @return 数据集是否为空
     */
    boolean isEmpty();

    /**
     * @return 格式化的本文
     * @since v1.6.0
     */
    String toPretty();

    /**
     * 只读数据
     * @since 2.5.1
     */
    interface Data {

        Mapper toMapper();

        Array toArray();

        boolean toBool();

        int toInt();

        long toLong();

        float toFloat();

        double toDouble();

        String toString();

    }

}
