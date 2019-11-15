package com.example.easywallet.Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Wirasinee on 10-Dec-17.
 */

public class SaveDbHelper extends SQLiteOpenHelper { //SHIFT+F6 เปลียนชื่อ

    private static final String DATABASE_NAME = "save.db";
    private static final int DATABASE_VERSION = 1;

    //ชื่อฟิล
    public static final String TABLE_NAME = "save";
    public static final String COL_ID = "_id";

    public static final String COL_TITLE = "title";
    public static final String COL_NUMBER = "number";
    public static final String COL_PICTURE = "picture";
    public static final String COL_TYPE = "type";//1รับ 2 จ่าย
    public static final String TABLE_NAME2 = "sum";
    public static final String COL_SUM = "sum";
    /*
    CRETE TABLE ชื่อเทเบิล (
      _id INTEGER PRIMARY KEY AUTOINCREMENT,
      title TEXT,
      number TEXT,
      picture TEXT
     );
     */

    private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+"("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_TITLE + " TEXT,"
            + COL_NUMBER + " TEXT,"
            + COL_PICTURE + " TEXT,"
            + COL_TYPE + " TEXT)";
    private static final String CREATE_TABLE2 = "CREATE TABLE "+TABLE_NAME2+"("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_SUM + " TEXT)";
    //ALT+INS
    public SaveDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { //ถ้าฐานข้อมูลยังไม่มีก็จะมาทำตรงนี้
        db.execSQL(CREATE_TABLE); //ไปสร้างตาราง
        insertInitialData(db);//ใส่ข้อมูลลงตาราง
        db.execSQL(CREATE_TABLE2); //ไปสร้างตาราง
        insertInitialData2(db);//ใส่ข้อมูลลงตาราง
    }

    private void insertInitialData2(SQLiteDatabase db) {
        String sum="30004800";
        ContentValues cv = new ContentValues();
        //putค่าต่างๆใส่ลงcv
        cv.put(COL_SUM,sum);//ใส่ฟิลไหน,ค่าที่ใส่
    }

    private void insertInitialData(SQLiteDatabase db) {//idไม่ต้องใส่เพราะเดียวandroidทำให้เอง
//ข้อมูล1

        ContentValues cv = new ContentValues();
        //putค่าต่างๆใส่ลงcv
        cv.put(COL_TITLE,"คุณพ่อให้เงิน");//ใส่ฟิลไหน,ค่าที่ใส่
        cv.put(COL_NUMBER,"8000");
        cv.put(COL_PICTURE,"ic_income.png");
        cv.put(COL_TYPE,"1");
        db.insert(TABLE_NAME,null,cv);//ชื่อตาราง,null,cv

//ข้อมูล2
        cv = new ContentValues();
        cv.put(COL_TITLE,"จ่ายค่าหอ");//ใส่ฟิลไหน,ค่าที่ใส่
        cv.put(COL_NUMBER,"2500");
        cv.put(COL_PICTURE,"ic_expense.png");
        cv.put(COL_TYPE,"2");
        db.insert(TABLE_NAME,null,cv);
        //ข้อมูล2
        cv = new ContentValues();
        cv.put(COL_TITLE,"ซื้อล๊อคเตอรี่1ชุด");//ใส่ฟิลไหน,ค่าที่ใส่
        cv.put(COL_NUMBER,"700");
        cv.put(COL_PICTURE,"ic_expense.png");
        cv.put(COL_TYPE,"2");
        db.insert(TABLE_NAME,null,cv);
        //ข้อมูล2
        cv = new ContentValues();
        cv.put(COL_TITLE,"ถูกล็อตเตอรี่รางวัลที่1");//ใส่ฟิลไหน,ค่าที่ใส่
        cv.put(COL_NUMBER,"30000000");
        cv.put(COL_PICTURE,"ic_income.png");
        cv.put(COL_TYPE,"1");
        db.insert(TABLE_NAME,null,cv);
    }
//คลิก แพคเกจย่อย-> สร้างแพคเกจ model-> สร้าง javaclass PhoneItem [2]

    //ไม่ออกข้อสอบ  สมมุติออกแอพไปแล้ว ให้ผู้ใช้ใช้ไปแล้ว พอเราจะทำเวอชันถัดไปแต่เทเบิลไม่พอในเวอชันถัดไป จะมาใช้ตรงนี้
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);//ลบตารางออก
        onCreate(db);
        //แล้วไปปรับ เวอชัน
        //หรือลบแอพทิ้งแล้วรันใหม่ก็ได้
    }
}
