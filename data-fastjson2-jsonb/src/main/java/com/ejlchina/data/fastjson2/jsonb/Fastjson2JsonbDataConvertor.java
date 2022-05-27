package com.ejlchina.data.fastjson2.jsonb;

import com.alibaba.fastjson2.JSONB;
import com.alibaba.fastjson2.util.ParameterizedTypeImpl;
import com.ejlchina.data.Array;
import com.ejlchina.data.DataConvertor;
import com.ejlchina.data.Mapper;
import com.ejlchina.data.fastjson2.Fastjson2Array;
import com.ejlchina.data.fastjson2.Fastjson2Mapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Troy.Zhou @ 2017-03-20
 * @since v1.5.0
 * */
public class Fastjson2JsonbDataConvertor implements DataConvertor {

    @Override
    public Mapper toMapper(InputStream in, Charset charset) {
        return new Fastjson2Mapper(JSONB.parseObject(toBytes(in)));
    }

    @Override
    public Mapper toMapper(String in) {
        return new Fastjson2Mapper(JSONB.parseObject(in.getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    public Array toArray(InputStream in, Charset charset) {
        return new Fastjson2Array(JSONB.parseArray(toBytes(in)));
    }

    @Override
    public Array toArray(String in) {
        return new Fastjson2Array(JSONB.parseArray(in.getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    public byte[] serialize(Object object, Charset charset) {
        if (object instanceof Fastjson2Mapper) {
            return ((Fastjson2Mapper) object).toJSONBBytes();
        }
        if (object instanceof Fastjson2Array) {
            return ((Fastjson2Array) object).toJSONBBytes();
        }
        return JSONB.toBytes(object);
    }

    @Override
    public String serialize(Object object) {
        return new String(serialize(object, StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    }

    @Override
    public <T> T toBean(Type type, InputStream in, Charset charset) {
        return JSONB.parseObject(toBytes(in), type);
    }

    @Override
    public <T> T toBean(Type type, String in) {
        return JSONB.parseObject(in.getBytes(StandardCharsets.UTF_8), type);
    }

    @Override
    public <T> List<T> toList(Class<T> type, InputStream in, Charset charset) {
        ParameterizedTypeImpl paramType = new ParameterizedTypeImpl(new Type[]{type}, null, List.class);
        return JSONB.parseObject(toBytes(in), paramType);
    }

    @Override
    public <T> List<T> toList(Class<T> type, String in) {
        ParameterizedTypeImpl paramType = new ParameterizedTypeImpl(new Type[]{type}, null, List.class);
        return JSONB.parseObject(in.getBytes(StandardCharsets.UTF_8), paramType);
    }

    protected byte[] toBytes(InputStream in) {
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
        return output.toByteArray();
    }

}
