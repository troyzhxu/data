package cn.zhxu.data.fastjson;

import cn.zhxu.data.Array;
import cn.zhxu.data.DataConvertor;
import cn.zhxu.data.Mapper;
import com.alibaba.fastjson.JSON;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.List;

public class FastjsonDataConvertor implements DataConvertor {

	@Override
	public Mapper toMapper(InputStream in, Charset charset) {
		return toMapper(toString(in, charset));
	}

	@Override
	public Mapper toMapper(String in) {
		return new FastjsonMapper(JSON.parseObject(in));
	}

	@Override
	public Array toArray(InputStream in, Charset charset) {
		return toArray(toString(in, charset));
	}

	@Override
	public Array toArray(String in) {
		return new FastjsonArray(JSON.parseArray(in));
	}

	@Override
	public byte[] serialize(Object object, Charset charset) {
		return serialize(object).getBytes(charset);
	}

	@Override
	public String serialize(Object object) {
		if (object instanceof FastjsonMapper || object instanceof FastjsonArray) {
			return object.toString();
		}
		return JSON.toJSONString(object);
	}

	@Override
	public <T> T toBean(Type type, InputStream in, Charset charset) {
		try {
			return JSON.parseObject(in, charset, type);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
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
