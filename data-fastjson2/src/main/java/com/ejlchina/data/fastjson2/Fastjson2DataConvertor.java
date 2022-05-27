package com.ejlchina.data.fastjson2;

import com.alibaba.fastjson2.JSON;
import com.ejlchina.data.Array;
import com.ejlchina.data.DataConvertor;
import com.ejlchina.data.Mapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author Troy.Zhou @ 2017-03-20
 * @since v1.5.0
 * */
public class Fastjson2DataConvertor implements DataConvertor {

    @Override
    public Mapper toMapper(InputStream in, Charset charset) {
        return toMapper(toString(in, charset));
    }

    @Override
    public Mapper toMapper(String in) {
        return new Fastjson2Mapper(JSON.parseObject(in));
    }

    @Override
    public Array toArray(InputStream in, Charset charset) {
        return toArray(toString(in, charset));
    }

    @Override
    public Array toArray(String in) {
        return new Fastjson2Array(JSON.parseArray(in));
    }

    @Override
    public byte[] serialize(Object object, Charset charset) {
        return serialize(object).getBytes(charset);
    }

    @Override
    public String serialize(Object object) {
        if (object instanceof Fastjson2Mapper || object instanceof Fastjson2Array) {
            return object.toString();
        }
        return JSON.toJSONString(object);
    }

    @Override
    public <T> T toBean(Type type, InputStream in, Charset charset) {
        return JSON.parseObject(in, charset, type);
    }

    @Override
    public <T> T toBean(Type type, String in) {
        return JSON.parseObject(in, type);
    }

    @Override
    public <T> List<T> toList(Class<T> type, InputStream in, Charset charset) {
        return toList(type, toString(in, charset));
    }

    @Override
    public <T> List<T> toList(Class<T> type, String in) {
        return JSON.parseArray(in, type);
    }

    protected String toString(InputStream in, Charset charset) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buff = new byte[512];
        try {
            int len;
            while ((len = in.read(buff)) > 0) {
                output.write(buff, 0, len);
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        try {
            return output.toString(charset.name());
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

}
