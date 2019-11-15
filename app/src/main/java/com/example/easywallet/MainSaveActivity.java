package com.example.easywallet;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.easywallet.Db.SaveDbHelper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class MainSaveActivity extends AppCompatActivity {
    private ImageView mSaveOutImageView;
    private EditText mTitleOutEditText, mNumberOutEditText;
    private Button mSaveOutButton;
    private static final String TAG = MainSaveActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_main);
        mSaveOutImageView=findViewById(R.id.save_out_image_view);
        mTitleOutEditText=findViewById(R.id.title_out_edit_text);
        mNumberOutEditText =findViewById(R.id.number_out_edit_text);
        mSaveOutButton=findViewById(R.id.save_out_button);
        Intent intent = getIntent();//getอินเทนที่ส่งมาจากหน้าก่อนหน้านี
        String type  = intent.getStringExtra("type");
        String pictureFileName;
        if(type.equals("1")) {
            pictureFileName = "ic_income.png";
        }else{
            pictureFileName = "ic_expense.png";
        }
            AssetManager am = getAssets();
            try {
                InputStream stream = am.open(pictureFileName); //เปิดไฟล์โดยใช้AssetManager
                Drawable drawable=Drawable.createFromStream(stream,null);//แปลงInputStreamเป็น Drawable
                //สามารถเอาไปsetให้กับรูปภาพได้แล้ว
                mSaveOutImageView.setImageDrawable(drawable);
            } catch (IOException e) {
                e.printStackTrace();
                //(10)ถ้าเปิดไฟล์ไม่เจอมันอ้างเป็นไฟล์ที่ผู้ใช้แอดมาจึง
                File pictureFile=new File(getFilesDir(),pictureFileName);
                Drawable drawable = Drawable.createFromPath(pictureFile.getAbsolutePath());
                mSaveOutImageView.setImageDrawable(drawable);
            }


        mSaveOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDataToDb();//(5) ออกข้อสอบ
                setResult(RESULT_OK);//(8)

                //(9)ถ้ามีการส่งข้อมูลอื่นๆจากปลายทางไปต้นทางด้วย
            /*Intent intent = new Intent();
            intent.putExtra(//อะไรสักอย่าง);
            setResult(RESULT_OK,intent);
            */
                finish();
            }
        });


    }
    //(5)
    private void saveDataToDb() {
        String title = mTitleOutEditText.getText().toString();
        String number = mNumberOutEditText.getText().toString();
        Intent intent = getIntent();//getอินเทนที่ส่งมาจากหน้าก่อนหน้านี
        String type  = intent.getStringExtra("type");
        String pictureFileName;
        if(type.equals("1")) {
            pictureFileName = "ic_income.png";
        }else{
            pictureFileName = "ic_expense.png";
        }

        //saveลงDBโดย
        ContentValues cv = new ContentValues();
        cv.put(SaveDbHelper.COL_TITLE,title);
        cv.put(SaveDbHelper.COL_NUMBER,number);
        cv.put(SaveDbHelper.COL_TYPE,type);
        cv.put(SaveDbHelper.COL_PICTURE,pictureFileName);

        //อินเซิสเข้าDB
        //สร้างobjของDBHelper
        SaveDbHelper dbHelper = new SaveDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();//รีเทินตัวฐานข้อมูล เป็นตัวแทนของฐานข้อมูล ทำให้เราสามารถเล่นกับdbได้แล้ว
        long result=db.insert(SaveDbHelper.TABLE_NAME,null,cv);//เพิ่มข้อมูลลงdb (ตารางที่จะใส่,null,ข้อมูลที่จะใส่)
        if(result==-1){
            Log.e(TAG,"เกิดerrorตอนsaveข้อมูล");
        }
    }


}
