package com.ejlchina.data.gson;

import com.ejlchina.data.Array;
import com.ejlchina.data.Mapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.util.Arrays;
import java.util.List;

public class GsonArray implements Array {

	private final Gson gson;
	private final JsonArray json;
	
	public GsonArray(Gson gson, JsonArray json) {
		this.gson = gson;
		this.json = json;
	}

	@Override
	public int size() {
		return json.size();
	}

	@Override
	public boolean isEmpty() {
		return json.size() == 0;
	}

	@Override
	public Mapper getMapper(int index) {
		JsonElement subJson = json.get(index);
		if (subJson != null && subJson.isJsonObject()) {
			return new GsonMapper(gson, subJson.getAsJsonObject());
		}
		return null;
	}

	@Override
	public Array getArray(int index) {
		JsonElement subJson = json.get(index);
		if (subJson != null && subJson.isJsonArray()) {
			return new GsonArray(gson, subJson.getAsJsonArray());
		}
		return null;
	}

	@Override
	public boolean getBool(int index) {
		JsonElement val = json.get(index);
		if (val != null && val.isJsonPrimitive()) {
			return val.getAsBoolean();
		}
		return false;
	}

	@Override
	public int getInt(int index) {
		JsonElement val = json.get(index);
		if (val != null && val.isJsonPrimitive()) {
			return val.getAsInt();
		}
		return 0;
	}

	@Override
	public long getLong(int index) {
		JsonElement val = json.get(index);
		if (val != null && val.isJsonPrimitive()) {
			return val.getAsLong();
		}
		return 0;
	}
	
	@Override
	public float getFloat(int index) {
		JsonElement val = json.get(index);
		if (val != null && val.isJsonPrimitive()) {
			return val.getAsFloat();
		}
		return 0;
	}

	@Override
	public double getDouble(int index) {
		JsonElement val = json.get(index);
		if (val != null && val.isJsonPrimitive()) {
			return val.getAsDouble();
		}
		return 0;
	}

	@Override
	public String getString(int index) {
		JsonElement val = json.get(index);
		if (val != null && val.isJsonPrimitive()) {
			return val.getAsString();
		}
		return null;
	}

	@Override
	public <T> List<T> toList(Class<T> type) {
		T[] beans = gson.fromJson(json, TypeToken.getArray(type).getType());
		return Arrays.asList(beans);
	}

	@Override
	public List<Object> toList() {
		return new GsonList(json);
	}

	@Override
	public String toString() {
		return json.toString();
	}

}
