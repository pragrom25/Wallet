package com.example.easywallet.model;

public class SaveItem {
    public final int id;
    public final String title;
    public final String number;
    public final String picture;
    public final String type;


    public SaveItem(int id, String title, String number, String picture,String type) {
        this.id = id;
        this.title = title;
        this.number = number;
        this.picture = picture;
        this.type = type;
    }

}
