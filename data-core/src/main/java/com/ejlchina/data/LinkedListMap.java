package com.ejlchina.data;

import java.util.LinkedList;

/**
 * 基于 {@link LinkedList } 实现的 {@link ListMap }
 * @author troy zhou
 * @since v1.4.0
 * @param <V> 泛型 值
 */
public class LinkedListMap<V> extends BaseListMap<V> {

    public LinkedListMap() {
        super(new LinkedList<>());
    }

}
