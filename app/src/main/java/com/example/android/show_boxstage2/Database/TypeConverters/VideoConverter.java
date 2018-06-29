package com.example.android.show_boxstage2.Database.TypeConverters;

import android.arch.persistence.room.TypeConverter;

import com.example.android.show_boxstage2.Models.Videos_POJO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class VideoConverter {
    @TypeConverter
    public static String encodeGenre(List<Videos_POJO> videoModelList) {

        Gson gson = new Gson();
        return gson.toJson(videoModelList);
    }

    @TypeConverter
    public static List<Videos_POJO> decodeGenre(String value) {

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Videos_POJO>>() {
        }.getType();
        return gson.fromJson(value, listType);
    }
}
