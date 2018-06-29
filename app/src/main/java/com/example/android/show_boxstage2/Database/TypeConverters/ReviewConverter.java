package com.example.android.show_boxstage2.Database.TypeConverters;

import android.arch.persistence.room.TypeConverter;

import com.example.android.show_boxstage2.Models.Reviews_POJO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ReviewConverter {
    @TypeConverter
    public static String encodeGenre(List<Reviews_POJO> reviewModelList) {

        Gson gson = new Gson();
        return gson.toJson(reviewModelList);
    }

    @TypeConverter
    public static List<Reviews_POJO> decodeGenre(String value) {

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Reviews_POJO>>() {
        }.getType();
        return gson.fromJson(value, listType);
    }
}
