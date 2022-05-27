package com.ejlchina.data.fastjson2;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ejlchina.data.Array;
import com.ejlchina.data.Mapper;
import com.ejlchina.data.TypeRef;

import java.lang.reflect.Type;
import java.util.Set;

public class Fastjson2Mapper implements Mapper {

	private final JSONObject json;

	public Fastjson2Mapper(JSONObject json) {
		this.json = json;
	}

	@Override
	public int size() {
		return json.size();
	}

	@Override
	public boolean isEmpty() {
		return json.isEmpty();
	}

	@Override
	public Mapper getMapper(String key) {
		JSONObject subJson = json.getJSONObject(key);
		if (subJson != null) {
			return new Fastjson2Mapper(subJson);
		}
		return null;
	}

	@Override
	public Array getArray(String key) {
		JSONArray subJson = json.getJSONArray(key);
		if (subJson != null) {
			return new Fastjson2Array(subJson);
		}
		return null;
	}

	@Override
	public boolean getBool(String key) {
		return json.getBooleanValue(key);
	}

	@Override
	public int getInt(String key) {
		return json.getIntValue(key);
	}

	@Override
	public long getLong(String key) {
		return json.getLongValue(key);
	}
	
	@Override
	public float getFloat(String key) {
		return json.getFloatValue(key);
	}

	@Override
	public double getDouble(String key) {
		return json.getDoubleValue(key);
	}

	@Override
	public String getString(String key) {
		return json.getString(key);
	}

	@Override
	public boolean has(String key) {
		return json.containsKey(key);
	}

	@Override
	public Set<String> keySet() {
		return json.keySet();
	}

	@Override
	public String toString() {
		return json.toJSONString();
	}

	@Override
	public <T> T toBean(Type type) {
		return json.toJavaObject(type);
	}

	@Override
	public <T> T toBean(Class<T> type) {
		return json.toJavaObject(type);
	}

	@Override
	public <T> T toBean(TypeRef<T> type) {
		return json.toJavaObject(type.getType());
	}

}