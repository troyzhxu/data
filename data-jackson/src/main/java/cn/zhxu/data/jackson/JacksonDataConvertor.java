package cn.zhxu.data.jackson;

import cn.zhxu.data.Array;
import cn.zhxu.data.DataConvertor;
import cn.zhxu.data.DataSet;
import cn.zhxu.data.Mapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class JacksonDataConvertor implements DataConvertor {

    private ObjectMapper objectMapper;

    public JacksonDataConvertor() {
        this(new ObjectMapper());
    }

    public JacksonDataConvertor(ObjectMapper objectMapper) {
        // 自 v1.5.3: 默认可兼容未知属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.objectMapper = objectMapper;
    }

    @Override
    public Mapper toMapper(InputStream in, Charset charset) {
        try {
            return toMapper(objectMapper.readTree(new InputStreamReader(in, charset)));
        } catch (IOException e) {
            throw new IllegalArgumentException("Jackson 解析异常", e);
        }
    }

    @Override
    public Mapper toMapper(String in) {
        try {
            return toMapper(objectMapper.readTree(in));
        } catch (IOException e) {
            throw new IllegalArgumentException("Jackson 解析异常", e);
        }
    }

    private Mapper toMapper(JsonNode json) {
        if (json.isObject()) {
            return new JacksonMapper(objectMapper, (ObjectNode) json);
        }
        if (json.isNull() || json.isMissingNode()) {
            return null;
        }
        throw new IllegalArgumentException("不是 一个 json 对象：" + json);
    }

    @Override
    public Array toArray(InputStream in, Charset charset) {
        try {
            return toArray(objectMapper.readTree(new InputStreamReader(in, charset)));
        } catch (IOException e) {
            throw new IllegalArgumentException("Jackson 解析异常", e);
        }
    }

    @Override
    public Array toArray(String in) {
        try {
            return toArray(objectMapper.readTree(in));
        } catch (IOException e) {
            throw new IllegalArgumentException("Jackson 解析异常", e);
        }
    }

    protected Array toArray(JsonNode json) {
        if (json.isArray()) {
            return new JacksonArray(objectMapper, (ArrayNode) json);
        }
        if (json.isNull() || json.isMissingNode()) {
            return null;
        }
        throw new IllegalStateException("不是数组：" + json);
    }

    @Override
    public byte[] serialize(Object object, Charset charset, boolean pretty) {
        return serialize(object, pretty).getBytes(charset);
    }

    @Override
    public String serialize(Object object, boolean pretty) {
        if (object instanceof Mapper) {
            object = ((Mapper) object).toMap();
		}
        if (object instanceof Array) {
            object = ((Array) object).toList();
        }
        try {
            if (pretty) {
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
            }
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Java Bean [" + object + "] Jackson 序列化异常", e);
        }
    }

    protected <T> TypeReference<T> toTypeRef(Type type) {
        return new TypeReference<T>() {
            @Override
            public Type getType() { return type; }
        };
    }

    @Override
    public <T> T toBean(Type type, InputStream in, Charset charset) {
        try {
            return objectMapper.readValue(new InputStreamReader(in, charset), toTypeRef(type));
        } catch (IOException e) {
            throw new IllegalStateException("Jackson 解析异常", e);
        }
    }

    @Override
    public <T> T toBean(Type type, String in) {
        try {
            return objectMapper.readValue(in, toTypeRef(type));
        } catch (IOException e) {
            throw new IllegalStateException("Jackson 解析异常", e);
        }
    }

    @Override
    public <T> List<T> toList(Class<T> type, InputStream in, Charset charset) {
        try {
            return objectMapper.readValue(new InputStreamReader(in, charset), listType(type));
        } catch (IOException e) {
            throw new IllegalStateException("Jackson 解析异常", e);
        }
    }

    @Override
    public <T> List<T> toList(Class<T> type, String in) {
        try {
            return objectMapper.readValue(in, listType(type));
        } catch (IOException e) {
            throw new IllegalStateException("Jackson 解析异常", e);
        }
    }

    private <T> CollectionType listType(Class<T> type) {
        return objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, type);
    }


    /**
     * 将 JsonElement 转换为正常 List 和 Map 或基本类型的 对象
     * @param value JsonElement
     * @return PlainObject
     * @since v1.5.0
     */
    public static Object toPlainObject(JsonNode value) {
        if (value != null) {
            if (value.isObject()) {
                return new JacksonMap((ObjectNode) value);
            }
            if (value.isArray()) {
                return new JacksonList((ArrayNode) value);
            }
            if (value.isBoolean()) {
                return value.asBoolean();
            }
            if (value.isTextual()) {
                return value.asText();
            }
            if (value.isInt()) {
                return value.asInt();
            }
            if (value.isLong()) {
                return value.asLong();
            }
            if (value.isFloat()) {
                return value.floatValue();
            }
            if (value.isDouble()) {
                return value.asDouble();
            }
            if (value.isBigDecimal()) {
                return value.decimalValue();
            }
            if (value.isBigInteger()) {
                return value.bigIntegerValue();
            }
        }
        return null;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

}
