package com.ejlchina.data;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class TypeRef<T> {

    final Type type;

    public TypeRef() {
        Type superClass = getClass().getGenericSuperclass();
        this.type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
    }

    public TypeRef(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

}
