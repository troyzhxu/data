package cn.zhxu.data.snack3;

import cn.zhxu.data.Array;
import cn.zhxu.data.Mapper;
import org.noear.snack.ONode;
import org.noear.snack.core.Context;
import org.noear.snack.core.DEFAULTS;
import org.noear.snack.core.Feature;
import org.noear.snack.core.Options;

import java.util.List;

/**
 * @author noear
 * @since 2022.07.07
 * */
public class Snack3Array implements Array {

	private final ONode json;

	public Snack3Array(ONode json) {
		if (json.isArray()) {
			this.json = json;
		} else {
			throw new IllegalArgumentException("illegal json array type");
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
	public Mapper getMapper(int index) {
		ONode subJson = json.get(index);
		if (subJson != null && subJson.isObject()) {
			return new Snack3Mapper(subJson);
		}
		return null;
	}

	@Override
	public Array getArray(int index) {
		ONode subJson = json.get(index);
		if (subJson != null && subJson.isArray()) {
			return new Snack3Array(subJson);
		}
		return null;
	}

	@Override
	public boolean getBool(int index) {
		return json.get(index).getBoolean();
	}

	@Override
	public int getInt(int index) {
		return json.get(index).getInt();
	}

	@Override
	public long getLong(int index) {
		return json.get(index).getLong();
	}

	@Override
	public float getFloat(int index) {
		return json.get(index).getFloat();
	}

	@Override
	public double getDouble(int index) {
		return json.get(index).getDouble();
	}

	@Override
	public String getString(int index) {
		return json.get(index).getString();
	}

	@Override
	public <T> List<T> toList(Class<T> type) {
		return json.toObjectList(type);
	}

	@Override
	public List<Object> toList() {
		return json.toObject(List.class);
	}

	@Override
	public String toPretty() {
		Options options = new Options(json.options().getFeatures()).add(Feature.PrettyFormat);
		Context context = new Context(options, json, null).handle(DEFAULTS.DEF_JSON_TOER);
		return (String) context.target;
	}

	@Override
	public String toString() {
		return json.toJson();
	}

}
