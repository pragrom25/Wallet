package com.example.easywallet.Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SaveDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "save.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "save";
    public static final String COL_ID = "_id";

    public static final String COL_TITLE = "title";
    public static final String COL_NUMBER = "number";
    public static final String COL_PICTURE = "picture";
    public static final String COL_TYPE = "type";
    public static final String TABLE_NAME2 = "sum";
    public static final String COL_SUM = "sum";


    private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+"("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_TITLE + " TEXT,"
            + COL_NUMBER + " TEXT,"
            + COL_PICTURE + " TEXT,"
            + COL_TYPE + " TEXT)";
    private static final String CREATE_TABLE2 = "CREATE TABLE "+TABLE_NAME2+"("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_SUM + " TEXT)";

    public SaveDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        insertInitialData(db);
        db.execSQL(CREATE_TABLE2);
        insertInitialData2(db);
    }

    private void insertInitialData2(SQLiteDatabase db) {
        String sum="30004800";
        ContentValues cv = new ContentValues();

        cv.put(COL_SUM,sum);
    }

    private void insertInitialData(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(COL_TITLE,"ค่าหอ");
        cv.put(COL_NUMBER,"6000");
        cv.put(COL_PICTURE,"ic_income.png");
        cv.put(COL_TYPE,"1");
        db.insert(TABLE_NAME,null,cv);
       /* cv = new ContentValues();
        cv.put(COL_TITLE,"ค่ากิน");
        cv.put(COL_NUMBER,"2000");
        cv.put(COL_PICTURE,"ic_expense.png");
        cv.put(COL_TYPE,"2");
        db.insert(TABLE_NAME,null,cv);
        cv = new ContentValues();
        cv.put(COL_TITLE,"ค่าโปรเจค");
        cv.put(COL_NUMBER,"1500");
        cv.put(COL_PICTURE,"ic_expense.png");
        cv.put(COL_TYPE,"2");
        db.insert(TABLE_NAME,null,cv);*/

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
}
