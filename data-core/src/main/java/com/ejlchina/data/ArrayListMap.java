package com.ejlchina.data;

import java.util.ArrayList;


/**
 * 基于 {@link ArrayList } 实现的 {@link ListMap }
 * @author troy zhou
 * @since v1.4.0
 * @param <V> 泛型 值
 */
public class ArrayListMap<V> extends BaseListMap<V> {

    public ArrayListMap() {
        this(0);
    }

    public ArrayListMap(int initSize) {
        super(new ArrayList<>(initSize));
    }

}
