package rs21.billsplitr;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

import rs21.billsplitr.POJOs.Member;

public class MemConverter {

    @TypeConverter
    public String fromOptionValuesList(ArrayList<Member> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        return gson.toJson(optionValues);
    }

    @TypeConverter
    public ArrayList<Member> toOptionValuesList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Member>>() {
        }.getType();
        return gson.fromJson(optionValuesString, type);
    }

}