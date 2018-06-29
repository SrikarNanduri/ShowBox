package com.example.android.show_boxstage2.Database.TypeConverters;

import android.arch.persistence.room.TypeConverter;

import com.example.android.show_boxstage2.Models.Cast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class CastConverter {
    @TypeConverter
    public static String encodeGenre(List<Cast> castModelList) {

        Gson gson = new Gson();
        return gson.toJson(castModelList);
    }

    @TypeConverter
    public static List<Cast> decodeGenre(String value) {

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Cast>>() {
        }.getType();
        return gson.fromJson(value, listType);
    }
}
