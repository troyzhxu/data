package cn.zhxu.data.jackson;

import cn.zhxu.data.Array;
import cn.zhxu.data.Mapper;
import cn.zhxu.data.TypeRef;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class JacksonMapper implements Mapper {

	private final ObjectMapper om;
	private final ObjectNode json;
	
	public JacksonMapper(ObjectMapper om, ObjectNode json) {
		this.om = om;
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
		JsonNode subJson = json.get(key);
		if (subJson != null && subJson.isObject()) {
			return new JacksonMapper(om, (ObjectNode) subJson);
		}
		return null;
	}

	@Override
	public Array getArray(String key) {
		JsonNode subJson = json.get(key);
		if (subJson != null && subJson.isArray()) {
			return new JacksonArray(om, (ArrayNode) subJson);
		}
		return null;
	}

	@Override
	public boolean getBool(String key) {
		JsonNode subJson = json.get(key);
		if (subJson != null) {
			return subJson.asBoolean(false);
		}
		return false;
	}

	@Override
	public int getInt(String key) {
		JsonNode subJson = json.get(key);
		if (subJson != null) {
			return subJson.asInt(0);
		}
		return 0;
	}

	@Override
	public long getLong(String key) {
		JsonNode subJson = json.get(key);
		if (subJson != null) {
			return subJson.asLong(0);
		}
		return 0;
	}

	@Override
	public float getFloat(String key) {
		JsonNode subJson = json.get(key);
		if (subJson != null && subJson.isNumber()) {
			return subJson.floatValue();
		}
		if (subJson != null) {
			return (float) subJson.asDouble(0);
		}
		return 0;
	}

	@Override
	public double getDouble(String key) {
		JsonNode subJson = json.get(key);
		if (subJson != null) {
			return subJson.asDouble(0);
		}
		return 0;
	}

	@Override
	public String getString(String key) {
		JsonNode subJson = json.get(key);
		if (subJson == null || subJson.isNull()) {
			return null;
		}
		return subJson.asText();
	}

	@Override
	public <T> T toBean(Class<T> type) {
		try {
			return om.treeToValue(json, type);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public <T> T toBean(Type type) {
		try {
			return om.readValue(om.treeAsTokens(json), new TypeReference<T>() {
				@Override
				public Type getType() {
					return type;
				}
			});
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public <T> T toBean(TypeRef<T> type) {
		return toBean(type.getType());
	}

	@Override
	public Map<String, Object> toMap() {
		return new JacksonMap(json);
	}

	@Override
	public boolean has(String key) {
		return json.has(key);
	}

	@Override
	public Set<String> keySet() {
		Iterator<String> it = json.fieldNames();
		return new AbstractSet<String>() {

			@Override
			public Iterator<String> iterator() {
				return it;
			}

			@Override
			public int size() {
				return json.size();
			}
		};
	}

	@Override
	public String toPretty() {
		return json.toPrettyString();
	}

	@Override
	public String toString() {
		return json.toString();
	}

}
