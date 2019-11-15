package com.example.easywallet.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easywallet.R;
import com.example.easywallet.model.SaveItem;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Wirasinee on 10-Dec-17.
 */

public class SaveListAdapter extends ArrayAdapter<SaveItem> {
    private Context mContext;
    private int mLayoutResId;
    private ArrayList<SaveItem> mPhoneItemList;

    //ALT+INS แบบที่5
    public SaveListAdapter(@NonNull Context context, int layoutResId, @NonNull ArrayList<SaveItem> phoneItenList) {//layoutResIdือlayoutที่จะให้ใช้
        super(context, layoutResId, phoneItenList);

        this.mContext=context;
        this.mLayoutResId=layoutResId;
        this.mPhoneItemList=phoneItenList;
    }
    //cTRL+O  getView
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemLayout = inflater.inflate(mLayoutResId,null);//แปลงแทคxmlเป็นobj

        SaveItem item = mPhoneItemList.get(position);//ดึงมาจากแหล้งข้อมูล
        //ตอนนี้เราก็จะได้ มาitem 1 แล้ว
        ImageView phoneImageView = itemLayout.findViewById(R.id.save_image_view);//เข้าถึงวิวต่างๆที่อยู่ใน itemLayout
        TextView phoneTitleTextView = itemLayout.findViewById(R.id.save_title_text_view);
        TextView phoneNumberTextView = itemLayout.findViewById(R.id.save_number_text_view);

        //เอาแต่ละฟิวของไอเทมยัดลง
        phoneTitleTextView.setText(item.title);
        phoneNumberTextView.setText(item.number);

//รูป เนื่องจากมันอยู่ในasset ฉะนั้นต้องโหลดมา (ทำแบบR.ดอเวเบิลไม่ได้)
        String pictureFileName=item.picture;
        AssetManager am = mContext.getAssets();
        try {
            InputStream stream = am.open(pictureFileName); //เปิดไฟล์โดยใช้AssetManager
            Drawable drawable=Drawable.createFromStream(stream,null);//แปลงInputStreamเป็น Drawable
            //สามารถเอาไปsetให้กับรูปภาพได้แล้ว
            phoneImageView.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
            //(10)ถ้าเปิดไฟล์ไม่เจอมันอ้างเป็นไฟล์ที่ผู้ใช้แอดมาจึง
            File pictureFile=new File(mContext.getFilesDir(),pictureFileName);
            Drawable drawable = Drawable.createFromPath(pictureFile.getAbsolutePath());
            phoneImageView.setImageDrawable(drawable);
        }
        return itemLayout;
        //อแดปเตอของเราก็จะทำงานกับเลเอาที่เราออกแบบเองได้แล้ว
        //ไปที่mainActivity




    }
}

