package cn.zhxu.data.snack3;

import cn.zhxu.data.Array;
import cn.zhxu.data.Mapper;
import cn.zhxu.data.TypeRef;
import org.noear.snack.ONode;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

/**
 * @author noear
 * @since 2022.07.07
 * */
public class Snack3Mapper implements Mapper {

	private final ONode json;
	
	public Snack3Mapper(ONode json) {
		if (json.isObject()) {
			this.json = json;
		} else {
			throw new IllegalArgumentException("illegal json object type");
		}
	}

	@Override
	public int size() {
		return json.count();
	}

	@Override
	public boolean isEmpty() {
		return json.count() == 0;
	}

	@Override
	public Mapper getMapper(String key) {
		ONode subJson = json.get(key);
		if (subJson != null && subJson.isObject()) {
			return new Snack3Mapper(subJson);
		}
		return null;
	}

	@Override
	public Array getArray(String key) {
		ONode subJson = json.get(key);
		if (subJson != null && subJson.isArray()) {
			return new Snack3Array(subJson);
		}
		return null;
	}

	@Override
	public boolean getBool(String key) {
		return json.get(key).getBoolean();
	}

	@Override
	public int getInt(String key) {
		return json.get(key).getInt();
	}

	@Override
	public long getLong(String key) {
		return json.get(key).getLong();
	}
	
	@Override
	public float getFloat(String key) {
		return json.get(key).getFloat();
	}

	@Override
	public double getDouble(String key) {
		return json.get(key).getDouble();
	}

	@Override
	public String getString(String key) {
		return json.get(key).getString();
	}

	@Override
	public boolean has(String key) {
		return json.contains(key);
	}

	@Override
	public Set<String> keySet() {
		return json.obj().keySet();
	}

	@Override
	public String toString() {
		return json.toJson();
	}

	@Override
	public <T> T toBean(Type type) {
		return json.toObject(type);
	}

	@Override
	public <T> T toBean(Class<T> type) {
		return json.toObject(type);
	}

	@Override
	public <T> T toBean(TypeRef<T> type) {
		return json.toObject(type.getType());
	}

	@Override
	public Map<String, Object> toMap() {
		return json.toObject(Map.class);
	}
}
