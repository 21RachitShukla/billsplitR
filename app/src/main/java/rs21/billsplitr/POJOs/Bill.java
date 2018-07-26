package rs21.billsplitr.POJOs;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import rs21.billsplitr.ItemConverter;
import rs21.billsplitr.MemConverter;

@Entity(tableName = "MyBills")
public class Bill implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    private String name;
    private String Desc;
    private ArrayList<Item> items;
    private ArrayList<Member> members;
    public boolean status = false;

    public Bill(String name, String desc, ArrayList<Item> items, ArrayList<Member> members) {
        this.name = name;
        Desc = desc;
        this.items = items;
        this.members = members;
    }

    protected Bill(Parcel in) {
        name = in.readString();
        Desc = in.readString();
    }

    public Bill() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(Desc);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Bill> CREATOR = new Creator<Bill>() {
        @Override
        public Bill createFromParcel(Parcel in) {
            return new Bill(in);
        }

        @Override
        public Bill[] newArray(int size) {
            return new Bill[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getDesc() {
        return Desc;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public void setMembers(ArrayList<Member> members) {
        this.members = members;
    }
}
