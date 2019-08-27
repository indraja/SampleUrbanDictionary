package com.test.nike.dictionary.database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NikeTypeConverter {
    @TypeConverter
    public static List<String> fromString(String value) {
        Type listType = new TypeToken<List<String>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(List<String> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public List<Integer> getIntegerListFromString(String price) {
        List<Integer> list = new ArrayList<>();

        String[] array = price.split(",");

        for (String s : array) {
            if (!s.isEmpty()) {
                list.add(Integer.parseInt(s));
            }
        }
        return list;
    }

    @TypeConverter
    public String getStringFromIntegerList(List<Integer> list) {
        StringBuilder price = new StringBuilder();
        for (int i : list) {
            price.append(",").append(i);
        }
        return price.toString();
    }
}