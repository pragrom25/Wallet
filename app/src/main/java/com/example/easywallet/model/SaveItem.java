package com.example.easywallet.model;

/**
 * Created by Wirasinee on 10-Dec-17.
 */

public class SaveItem {
    public final int id;
    public final String title;
    public final String number;
    public final String picture;
    public final String type;

    //ALT+INS
    public SaveItem(int id, String title, String number, String picture,String type) {
        this.id = id;
        this.title = title;
        this.number = number;
        this.picture = picture;
        this.type = type;
    }

    //ไปactivity_main MainAcitivity [3]
}
