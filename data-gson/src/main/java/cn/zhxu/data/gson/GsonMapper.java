package cn.zhxu.data.gson;

import cn.zhxu.data.Array;
import cn.zhxu.data.Mapper;
import cn.zhxu.data.TypeRef;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

public class GsonMapper extends GsonDataSet implements Mapper {

	final JsonObject json;

	public GsonMapper(Gson gson, JsonObject json) {
		super(gson, json);
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
	public Mapper getMapper(String key) {
		JsonElement subJson = json.get(key);
		if (subJson != null && subJson.isJsonObject()) {
			return new GsonMapper(gson, subJson.getAsJsonObject());
		}
		return null;
	}

	@Override
	public Array getArray(String key) {
		JsonElement subJson = json.get(key);
		if (subJson != null && subJson.isJsonArray()) {
			return new GsonArray(gson, subJson.getAsJsonArray());
		}
		return null;
	}

	@Override
	public boolean getBool(String key) {
		JsonElement val = json.get(key);
		if (val != null && val.isJsonPrimitive()) {
			return val.getAsBoolean();
		}
		return false;
	}

	@Override
	public int getInt(String key) {
		JsonElement val = json.get(key);
		if (val != null && val.isJsonPrimitive()) {
			return val.getAsInt();
		}
		return 0;
	}

	@Override
	public long getLong(String key) {
		JsonElement val = json.get(key);
		if (val != null && val.isJsonPrimitive()) {
			return val.getAsLong();
		}
		return 0;
	}

	@Override
	public float getFloat(String key) {
		JsonElement val = json.get(key);
		if (val != null && val.isJsonPrimitive()) {
			return val.getAsFloat();
		}
		return 0;
	}

	@Override
	public double getDouble(String key) {
		JsonElement val = json.get(key);
		if (val != null && val.isJsonPrimitive()) {
			return val.getAsDouble();
		}
		return 0;
	}

	@Override
	public String getString(String key) {
		JsonElement val = json.get(key);
		if (val != null && val.isJsonPrimitive()) {
			return val.getAsString();
		}
		return null;
	}

	@Override
	public <T> T toBean(Type type) {
		return gson.fromJson(json, type);
	}

	@Override
	public <T> T toBean(Class<T> type) {
		return gson.fromJson(json, type);
	}

	@Override
	public <T> T toBean(TypeRef<T> type) {
		return gson.fromJson(json, type.getType());
	}

	@Override
	public Map<String, Object> toMap() {
		return new GsonMap(json);
	}

	@Override
	public boolean has(String key) {
		return json.has(key);
	}

	@Override
	public Set<String> keySet() {
		return json.keySet();
	}

}
