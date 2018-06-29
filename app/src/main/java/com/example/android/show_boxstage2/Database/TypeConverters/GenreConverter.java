package com.example.android.show_boxstage2.Database.TypeConverters;

import android.arch.persistence.room.TypeConverter;

import com.example.android.show_boxstage2.Models.Genre_POJO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class GenreConverter {
    @TypeConverter
    public static String encodeGenre(List<Genre_POJO> genreModelList) {

        Gson gson = new Gson();
        return gson.toJson(genreModelList);
    }

    @TypeConverter
    public static List<Genre_POJO> decodeGenre(String value) {

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Genre_POJO>>() {
        }.getType();
        return gson.fromJson(value, listType);
    }
}
