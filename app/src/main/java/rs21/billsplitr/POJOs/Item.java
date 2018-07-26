package rs21.billsplitr.POJOs;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
    private String name;
    private double price;
    private double quantity;
    private double amount;
    private double percentage;

    public Item(String name, double price, double quantity, double amount) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.amount = amount;
    }

    protected Item(Parcel in) {
        name = in.readString();
        price = in.readDouble();
        quantity = in.readDouble();
        amount = in.readDouble();
    }

    public Item() {
    }

    public Item(String name, double price, double quantity, double amount, double percentage) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.amount = amount;
        this.percentage = percentage;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(price);
        dest.writeDouble(quantity);
        dest.writeDouble(amount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getAmount() {
        return amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
}
