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
import android.widget.Toast;

import com.example.easywallet.Db.SaveDbHelper;
import com.example.easywallet.adapter.SaveListAdapter;
import com.example.easywallet.model.SaveItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button mSaveAddButton,mSaveExitButton;
    private SaveDbHelper mHelper;
    private SQLiteDatabase mDb;
    private ArrayList<SaveItem> mSaveItemsList = new ArrayList<>();
    TextView mLeftTextView;
    private SaveListAdapter mAdapter;
    String sum="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast toast = Toast.makeText(MainActivity.this,"Welcome to pig wallet appication!",Toast.LENGTH_SHORT);
        toast.show();
        mSaveAddButton = findViewById(R.id.save_add_button);
        mSaveExitButton = findViewById(R.id.save_exit_button);
        mLeftTextView = findViewById(R.id.Left_textView);
        mLeftTextView.setText(sum);
        mHelper = new SaveDbHelper(this);
        mDb = mHelper.getReadableDatabase();
        loadDataFromDb();
        mAdapter = new SaveListAdapter(
                this,
                R.layout.item,
                mSaveItemsList
        );

        ListView iv = findViewById(R.id.list_View);
        iv.setAdapter(mAdapter);

        iv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view,final int position, long l) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                SaveItem item = mSaveItemsList.get(position);
                dialog.setTitle("ยืนยันลบรายการ "+item.title+" "+item.number+" บาท");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SaveItem item = mSaveItemsList.get(position);
                        int phoneId=item.id;

                        mDb.delete(
                                SaveDbHelper.TABLE_NAME,
                                SaveDbHelper.COL_ID+"=?",
                                new String[]{String.valueOf(phoneId)}
                        );
                        loadDataFromDb();
                        mAdapter.notifyDataSetChanged();
                    }

                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }

                });


                dialog.show();
                return true;
            }
        });
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==001){
            if(resultCode==RESULT_OK){
                loadDataFromDb();
                mAdapter.notifyDataSetChanged();
            }
        }
        if(requestCode==002){
            if(resultCode==RESULT_OK){
                loadDataFromDb();
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    private void loadDataFromDb() {
        Cursor cursor =mDb.query(

                SaveDbHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        String s = sum;
        mSaveItemsList.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(SaveDbHelper.COL_ID));
            String title = cursor.getString(cursor.getColumnIndex(SaveDbHelper.COL_TITLE));
            String number = cursor.getString(cursor.getColumnIndex(SaveDbHelper.COL_NUMBER));
            String picture = cursor.getString(cursor.getColumnIndex(SaveDbHelper.COL_PICTURE));
            String type = cursor.getString(cursor.getColumnIndex(SaveDbHelper.COL_TYPE));
            SaveItem item = new SaveItem(id,title,number,picture,type);
            mSaveItemsList.add(item);
            if(type.equals("1")) {
                s = String.valueOf((Integer.parseInt(s) + Integer.parseInt(number)));
            }
            if(type.equals("2")) {
                s = String.valueOf((Integer.parseInt(s) - Integer.parseInt(number)));
            }
            mLeftTextView.setText(s);
        }
    }

}
