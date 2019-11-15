package com.example.easywallet;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.easywallet.Db.SaveDbHelper;
import com.example.easywallet.adapter.SaveListAdapter;
import com.example.easywallet.model.SaveItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button mSaveAddButton,mSaveExitButton;
    private SaveDbHelper mHelper;//เข้าถึงdb โดยการสร้างตารางDB
    private SQLiteDatabase mDb;//ตัวอ้างอิงdb
    private ArrayList<SaveItem> mSaveItemsList = new ArrayList<>();
    TextView mLeftTextView;
    private SaveListAdapter mAdapter;
    String sum="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSaveAddButton = findViewById(R.id.save_add_button);
        mSaveExitButton = findViewById(R.id.save_exit_button);
        mLeftTextView = findViewById(R.id.Left_textView);
        mLeftTextView.setText(sum);
        mHelper = new SaveDbHelper(this);
        mDb = mHelper.getReadableDatabase();
        loadDataFromDb();
        mAdapter = new SaveListAdapter(
                this,
                R.layout.item,//<4>layout->new->layout reso->จะได้item.xml  <5>สร้างแพคเกจ adapter -> สร้างPhoneListAdapter.class
                mSaveItemsList
        );

        ListView iv = findViewById(R.id.list_View);
        iv.setAdapter(mAdapter);
        //(11)มีการลบได้เมือกดค้างที่ไอเทม
        iv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view,final int position, long l) {//positionคือตัวที่เราเลือกเช่นเลือกแจ่งเหตุด่วนเหตุร้ายคือ0
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                SaveItem item = mSaveItemsList.get(position);
                dialog.setTitle("ยืนยันลบรายการ "+item.title+" "+item.number+" บาท");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SaveItem item = mSaveItemsList.get(position);
                        int phoneId=item.id;
                        //String[] args = new String[]{String.valueOf(phoneId)};
                        mDb.delete(
                                SaveDbHelper.TABLE_NAME,
                                SaveDbHelper.COL_ID+"=?",//คอลัมเงือนไขตัวที่จะลบ    _id=? AND picture=?"
                                new String[]{String.valueOf(phoneId)}//?คือphoneId ก็คือargsนั้นหละ  (phoneID,"number0001.jpg")
                        );
                        loadDataFromDb();//โหลดDB
                        mAdapter.notifyDataSetChanged();//บอกให้อแดปเตอรู้
                    }

                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //โค้ดที่ต้องการให้ทำงาน เมือปุ่ม OK ใน dialog ถูกคลิค}
                    }

                });


                dialog.show();
                return true;
            }
        });//end (11)มีการลบได้เมือกดค้างที่ไอเทม
        mSaveAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MainSaveActivity.class);
                intent.putExtra("type", "1");
                startActivityForResult(intent, 001);
            }
        });

        mSaveExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MainSaveActivity.class);
                intent.putExtra("type", "2");
                startActivityForResult(intent, 002);
            }
        });

    }

    //(7)เข้ามาเมือหน้าปลายทางมรการส่งค่ากลับมา
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==001){
            if(resultCode==RESULT_OK){
                loadDataFromDb();//โหลดข้อมูลDB(คิวรีข้อมูลจากDBมา)
                mAdapter.notifyDataSetChanged();//บอกให้อแดปเตอรู้ว่าข้อมูลเปลียนไปแล้วนะ
            }
            //ไป(8)ที่ปลายทาง
        }
        if(requestCode==002){
            if(resultCode==RESULT_OK){
                loadDataFromDb();//โหลดข้อมูลDB(คิวรีข้อมูลจากDBมา)
                mAdapter.notifyDataSetChanged();//บอกให้อแดปเตอรู้ว่าข้อมูลเปลียนไปแล้วนะ
            }
            //ไป(8)ที่ปลายทาง
        }
    }

    //CTRL+ALT+M ทำให้สร้างเมธอทจากโค้ดเดิม
    private void loadDataFromDb() {
        Cursor cursor =mDb.query(
                //CTRT+P เอาแบบ3
                SaveDbHelper.TABLE_NAME,
                null,//เอามาทุกคอลัม
                null,//"category=1"  คิวรีเแพาะที่มีค่าแคททากอรีเป็น1เท่ารั้ร
                null,
                null,
                null,
                null
        );
        String s = sum;
        mSaveItemsList.clear();//เคลียข้อมูลเก่าทิ้ง เผือไว้กรณีผู้ใช้แอดข้อมูลเพิ่มมา
//วนลูปเอาข้อมูลออกมา
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(SaveDbHelper.COL_ID));
            String title = cursor.getString(cursor.getColumnIndex(SaveDbHelper.COL_TITLE));  //getมาแต่ละคอลัมของแถวนั้นๆ หรือcursor.getString(1); ช่อง1ตือtitle
            String number = cursor.getString(cursor.getColumnIndex(SaveDbHelper.COL_NUMBER));  //getมาแต่ละคอลัมของแถวนั้นๆ หรือcursor.getString(1); ช่อง1ตือtitle
            String picture = cursor.getString(cursor.getColumnIndex(SaveDbHelper.COL_PICTURE));  //getมาแต่ละคอลัมของแถวนั้นๆ หรือcursor.getString(1); ช่อง1ตือtitle
            String type = cursor.getString(cursor.getColumnIndex(SaveDbHelper.COL_TYPE));  //getมาแต่ละคอลัมของแถวนั้นๆ หรือcursor.getString(1); ช่อง1ตือtitle

            //สร้างโมเดลobj โดยผ่านคอนสตักจอPhoneItem ที่สร้างไว้
            SaveItem item = new SaveItem(id,title,number,picture,type);
            mSaveItemsList.add(item);//ข้อมูลขากdbมาอยู่ในนี้หมดแล้ว
            if(type.equals("1")) {
                s = String.valueOf((Integer.parseInt(s) + Integer.parseInt(number)));
            }
            if(type.equals("2")) {
                s = String.valueOf((Integer.parseInt(s) - Integer.parseInt(number)));
            }
            mLeftTextView.setText(s);
        }






    }

}//end class
