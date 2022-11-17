package com.example.account_keep.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Account implements Parcelable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String description;
    private float amount;


    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel parcel) {
            Account acc = new Account();
            acc.id = parcel.readInt();
            acc.description = parcel.readString();
            acc.amount = parcel.readFloat();

            return acc;
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){ this.description = description; }

    public float getAmount(){
        return amount;
    }

    public void setAmount(float amount){ this.amount = amount; }

    public int getId(){
        return id;
    }

    public void setId(int id){ this.id = id; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(description);
        parcel.writeFloat(amount);
    }
}
