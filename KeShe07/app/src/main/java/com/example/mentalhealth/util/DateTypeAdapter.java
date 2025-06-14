package com.example.mentalhealth.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 自定义的 Gson TypeAdapter，用于将 Date 对象序列化为 JSON 字符串，
 * 或将 JSON 字符串反序列化为 Date 对象。
 */
public class DateTypeAdapter extends TypeAdapter<Date> {

    // 使用指定格式化模式和默认区域设置的 SimpleDateFormat 实例
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    /**
     * 将 Date 对象写入 JSON 输出流。
     * 如果 Date 为 null，则写入 JSON null 值。
     *
     * @param out   JSON 输出流
     * @param value 要序列化的 Date 对象
     * @throws IOException 如果写入失败
     */
    @Override
    public void write(JsonWriter out, Date value) throws IOException {
        if (value == null) {
            out.nullValue(); // 写入 null 值
        } else {
            out.value(format.format(value)); // 写入格式化后的日期字符串
        }
    }

    /**
     * 从 JSON 输入流中读取字符串并解析为 Date 对象。
     * 如果解析失败，则返回 null。
     *
     * @param in JSON 输入流
     * @return 解析后的 Date 对象或 null
     * @throws IOException 如果读取失败
     */
    @Override
    public Date read(JsonReader in) throws IOException {
        try {
            return format.parse(in.nextString()); // 解析日期字符串
        } catch (Exception e) {
            return null; // 解析失败返回 null
        }
    }
}
