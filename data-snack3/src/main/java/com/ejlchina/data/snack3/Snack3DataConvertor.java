package com.ejlchina.data.snack3;

import com.ejlchina.data.Array;
import com.ejlchina.data.DataConvertor;
import com.ejlchina.data.Mapper;
import org.noear.snack.ONode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author noear
 * @since 2022.07.07
 * */
public class Snack3DataConvertor implements DataConvertor {

	@Override
	public Mapper toMapper(InputStream in, Charset charset) {
		return toMapper(toString(in, charset));
	}

	@Override
	public Mapper toMapper(String in) {
		return new Snack3Mapper(ONode.load(in));
	}

	@Override
	public Array toArray(InputStream in, Charset charset) {
		return toArray(toString(in, charset));
	}

	@Override
	public Array toArray(String in) {
		return new Snack3Array(ONode.load(in));
	}

	@Override
	public byte[] serialize(Object object, Charset charset) {
		return serialize(object).getBytes(charset);
	}

	@Override
	public String serialize(Object object) {
		if (object instanceof Snack3Mapper || object instanceof Snack3Array) {
			return object.toString();
		}
		return ONode.stringify(object);
	}

	@Override
	public <T> T toBean(Type type, InputStream in, Charset charset) {
		try {
			return ONode.load(toString(in, charset)).toObject(type);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public <T> T toBean(Type type, String in) {
		return ONode.load(in).toObject(type);
	}

	@Override
	public <T> List<T> toList(Class<T> type, InputStream in, Charset charset) {
		return toList(type, toString(in, charset));
	}

	@Override
	public <T> List<T> toList(Class<T> type, String in) {
		return ONode.load(in).toObjectList(type);
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