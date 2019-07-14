package com.example.contactssampleapp.models;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

@Entity(tableName = "contact_table")
public class Contact {

    @PrimaryKey
    @ColumnInfo
    @NonNull
    private String name;

    @ColumnInfo
    @NonNull
    private String email;

    @ColumnInfo
    @NonNull
    private int phone_number;

    @ColumnInfo
    @NonNull
    private String image;

    public Contact(String name, String email, int phone_number, String image) {
        this.name = name;
        this.email = email;
        this.phone_number = phone_number;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(int phone_number) {
        this.phone_number = phone_number;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl).apply(new RequestOptions().circleCrop())
                .into(view);
    }
}
