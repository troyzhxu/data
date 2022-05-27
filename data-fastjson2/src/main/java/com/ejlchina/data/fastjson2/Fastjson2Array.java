package com.ejlchina.data.fastjson2;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ejlchina.data.Array;
import com.ejlchina.data.Mapper;

import java.util.List;

public class Fastjson2Array implements Array {

	private final JSONArray json;

	public Fastjson2Array(JSONArray json) {
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
	public Mapper getMapper(int index) {
		JSONObject subJson = json.getJSONObject(index);
		if (subJson != null) {
			return new Fastjson2Mapper(subJson);
		}
		return null;
	}

	@Override
	public Array getArray(int index) {
		JSONArray subJson = json.getJSONArray(index);
		if (subJson != null) {
			return new Fastjson2Array(subJson);
		}
		return null;
	}

	@Override
	public boolean getBool(int index) {
		return json.getBooleanValue(index);
	}

	@Override
	public int getInt(int index) {
		return json.getIntValue(index);
	}

	@Override
	public long getLong(int index) {
		return json.getLongValue(index);
	}
	
	@Override
	public float getFloat(int index) {
		return json.getFloatValue(index);
	}

	@Override
	public double getDouble(int index) {
		return json.getDoubleValue(index);
	}

	@Override
	public String getString(int index) {
		return json.getString(index);
	}

	@Override
	public String toString() {
		return json.toJSONString();
	}

	@Override
	public <T> List<T> toList(Class<T> type) {
		return json.toList(type);
	}

}
