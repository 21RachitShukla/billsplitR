package rs21.billsplitr;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import rs21.billsplitr.POJOs.Item;

public class ItemConverter {

    @TypeConverter
    public String fromOptionValuesList(ArrayList<Item> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        return gson.toJson(optionValues);
    }

    @TypeConverter
    public ArrayList<Item> toOptionValuesList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Item>>() {
        }.getType();
        return gson.fromJson(optionValuesString, type);
    }

}