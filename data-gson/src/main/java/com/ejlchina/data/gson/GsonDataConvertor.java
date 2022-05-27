package com.ejlchina.data.gson;

import com.ejlchina.data.Array;
import com.ejlchina.data.DataConvertor;
import com.ejlchina.data.Mapper;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

public class GsonDataConvertor implements DataConvertor {

	private Gson gson;

	public GsonDataConvertor() {
		this(new Gson());
	}

	public GsonDataConvertor(Gson gson) {
		this.gson = gson;
	}

	@Override
	public Mapper toMapper(InputStream in, Charset charset) {
		return new GsonMapper(gson, gson.fromJson(new InputStreamReader(in, charset), JsonObject.class));
	}

	@Override
	public Mapper toMapper(String in) {
		return new GsonMapper(gson, gson.fromJson(in, JsonObject.class));
	}

	@Override
	public Array toArray(InputStream in, Charset charset) {
		return new GsonArray(gson, gson.fromJson(new InputStreamReader(in, charset), JsonArray.class));
	}

	@Override
	public Array toArray(String in) {
		return new GsonArray(gson, gson.fromJson(in, JsonArray.class));
	}

	@Override
	public byte[] serialize(Object object, Charset charset) {
		return serialize(object).getBytes(charset);
	}

	@Override
	public String serialize(Object object) {
		if (object instanceof GsonMapper || object instanceof GsonArray) {
			return object.toString();
		}
		return gson.toJson(object);
	}

	@Override
	public <T> T toBean(Type type, InputStream in, Charset charset) {
		return gson.fromJson(new InputStreamReader(in, charset), type);
	}

	@Override
	public <T> T toBean(Type type, String in) {
		return gson.fromJson(in, type);
	}

	@Override
	public <T> List<T> toList(Class<T> type, InputStream in, Charset charset) {
		T[] beans = gson.fromJson(new InputStreamReader(in, charset), TypeToken.getArray(type).getType());
		return Arrays.asList(beans);
	}

	@Override
	public <T> List<T> toList(Class<T> type, String in) {
		T[] beans = gson.fromJson(in, TypeToken.getArray(type).getType());
		return Arrays.asList(beans);
	}

	public Gson getGson() {
		return gson;
	}

	public void setGson(Gson gson) {
		this.gson = gson;
	}

	/**
	 * 将 JsonElement 转换为正常 List 和 Map 或基本类型的 对象
	 * @param value JsonElement
	 * @return PlainObject
	 * @since v1.5.0
	 */
	public static Object toPlainObject(JsonElement value) {
		if (value != null) {
			if (value.isJsonObject()) {
				return new GsonMap((JsonObject) value);
			}
			if (value.isJsonArray()) {
				return new GsonList((JsonArray) value);
			}
			if (value.isJsonPrimitive()) {
				JsonPrimitive p = (JsonPrimitive) value;
				if (p.isBoolean()) {
					return p.getAsBoolean();
				}
				if (p.isString()) {
					return p.getAsString();
				}
				if (p.isNumber()) {
					Number num = p.getAsNumber();
					if (num instanceof Integer || num instanceof Long
							|| num instanceof Double || num instanceof Float
							|| num instanceof Short || num instanceof Byte) {
						return num;
					}
					String str = num.toString();
					if (str.indexOf('.') >= 0) {
						if (str.length() > 8) {
							return Double.parseDouble(str);
						}
						return Float.parseFloat(str);
					}
					if (str.length() > 8) {
						return Long.parseLong(str);
					}
					return Integer.parseInt(str);
				}
			}
		}
		return null;
	}

}














