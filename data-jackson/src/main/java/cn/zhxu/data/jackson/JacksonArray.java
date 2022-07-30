package cn.zhxu.data.jackson;

import cn.zhxu.data.Array;
import cn.zhxu.data.Mapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.util.ArrayList;
import java.util.List;

public class JacksonArray implements Array {

	private final ObjectMapper om;
	private final ArrayNode json;
	
	public JacksonArray(ObjectMapper om, ArrayNode json) {
		this.om = om;
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
		JsonNode subJson = json.get(index);
		if (subJson != null && subJson.isObject()) {
			return new JacksonMapper(om, (ObjectNode) subJson);
		}
		return null;
	}

	@Override
	public Array getArray(int index) {
		JsonNode subJson = json.get(index);
		if (subJson != null && subJson.isArray()) {
			return new JacksonArray(om, (ArrayNode) subJson);
		}
		return null;
	}

	@Override
	public boolean getBool(int index) {
		JsonNode subJson = json.get(index);
		if (subJson != null) {
			return subJson.asBoolean(false);
		}
		return false;
	}

	@Override
	public int getInt(int index) {
		JsonNode subJson = json.get(index);
		if (subJson != null) {
			return subJson.asInt(0);
		}
		return 0;
	}

	@Override
	public long getLong(int index) {
		JsonNode subJson = json.get(index);
		if (subJson != null) {
			return subJson.asLong(0);
		}
		return 0;
	}
	
	@Override
	public float getFloat(int index) {
		JsonNode subJson = json.get(index);
		if (subJson != null && subJson.isNumber()) {
			return subJson.floatValue();
		}
		if (subJson != null) {
			return (float) subJson.asDouble(0);
		}
		return 0;
	}

	@Override
	public double getDouble(int index) {
		JsonNode subJson = json.get(index);
		if (subJson != null) {
			return subJson.asDouble(0);
		}
		return 0;
	}

	@Override
	public String getString(int index) {
		JsonNode subJson = json.get(index);
		if (subJson == null || subJson.isNull()) {
			return null;
		}
		return subJson.asText();
	}

	@Override
	public <T> List<T> toList(Class<T> type) {
		CollectionType listType = om.getTypeFactory().constructCollectionType(ArrayList.class, type);
		try {
			return om.treeToValue(json, listType);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Object> toList() {
		return new JacksonList(json);
	}

	@Override
	public String toString() {
		return json.toString();
	}

}
