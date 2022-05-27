package com.ejlchina.data.fastjson;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ejlchina.data.Array;
import com.ejlchina.data.Mapper;
import com.ejlchina.data.TypeRef;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

public class FastjsonMapper implements Mapper {

	private final JSONObject json;
	
	public FastjsonMapper(JSONObject json) {
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
			return new FastjsonMapper(subJson);
		}
		return null;
	}

	@Override
	public Array getArray(String key) {
		JSONArray subJson = json.getJSONArray(key);
		if (subJson != null) {
			return new FastjsonArray(subJson);
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

	@Override
	public Map<String, Object> toMap() {
		return json;
	}

}
