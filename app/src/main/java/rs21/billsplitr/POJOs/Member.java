package rs21.billsplitr.POJOs;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Member implements Parcelable {
    private String name;
    private double paidAmount;
    private ArrayList<Item> memItems;
    public boolean status = false;

    public Member(String name, double paidAmount) {
        this.name = name;
        this.paidAmount = paidAmount;
    }

    protected Member(Parcel in) {
        name = in.readString();
        paidAmount = in.readDouble();
    }

    public Member() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(paidAmount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Member> CREATOR = new Creator<Member>() {
        @Override
        public Member createFromParcel(Parcel in) {
            return new Member(in);
        }

        @Override
        public Member[] newArray(int size) {
            return new Member[size];
        }
    };

    public double getPaidAmount() {
        return paidAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public ArrayList<Item> getMemItems() {
        return memItems;
    }

    public void setMemItems(ArrayList<Item> memItems) {
        this.memItems = memItems;
    }
}