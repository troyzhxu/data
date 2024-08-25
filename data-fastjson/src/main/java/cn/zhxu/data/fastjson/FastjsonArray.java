package cn.zhxu.data.fastjson;

import cn.zhxu.data.Array;
import cn.zhxu.data.Mapper;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;

public class FastjsonArray implements Array {

	private final JSONArray json;
	
	public FastjsonArray(JSONArray json) {
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
			return new FastjsonMapper(subJson);
		}
		return null;
	}

	@Override
	public Array getArray(int index) {
		JSONArray subJson = json.getJSONArray(index);
		if (subJson != null) {
			return new FastjsonArray(subJson);
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
	public <T> List<T> toList(Class<T> type) {
		return json.toJavaList(type);
	}

	@Override
	public List<Object> toList() {
		return json;
	}

	@Override
	public String toPretty() {
		return json.toString(SerializerFeature.PrettyFormat);
	}

	@Override
	public String toString() {
		return json.toJSONString();
	}

}
