package cn.zhxu.data.gson;

import cn.zhxu.data.DataSet;
import com.google.gson.*;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.StringWriter;

public abstract class GsonDataSet implements DataSet {

    final Gson gson;
    final JsonElement json;

    public GsonDataSet(Gson gson, JsonElement json) {
        this.gson = gson;
        this.json = json;
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
