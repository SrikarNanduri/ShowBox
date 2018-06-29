package com.example.android.show_boxstage2.Database;

import android.arch.persistence.room.TypeConverter;

import com.example.android.show_boxstage2.Database.Models.GenreModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class GenreConverter {
    @TypeConverter
    public static String encodeGenre(List<GenreModel> genreModelList) {

        Gson gson = new Gson();
        return gson.toJson(genreModelList);
    }

    @TypeConverter
    public static List<GenreModel> decodeGenre(String value) {

        Gson gson = new Gson();
        Type listType = new TypeToken<List<GenreModel>>() {
        }.getType();
        return gson.fromJson(value, listType);
    }
}
