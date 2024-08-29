package cn.zhxu.data.gson;

import cn.zhxu.data.Array;
import cn.zhxu.data.Mapper;
import com.google.gson.*;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.StringWriter;
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
    public String toPretty() {
        try {
            StringWriter stringWriter = new StringWriter();
            JsonWriter jsonWriter = new JsonWriter(stringWriter);
            // Make writer lenient because toString() must not fail, even if for example JsonPrimitive
            // contains NaN
            jsonWriter.setStrictness(Strictness.LENIENT);
            jsonWriter.setFormattingStyle(FormattingStyle.PRETTY);
            Streams.write(json, jsonWriter);
            return stringWriter.toString();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    @Override
    public String toString() {
        return json.toString();
    }

}
