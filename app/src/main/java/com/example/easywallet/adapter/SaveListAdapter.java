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



public class SaveListAdapter extends ArrayAdapter<SaveItem> {
    private Context mContext;
    private int mLayoutResId;
    private ArrayList<SaveItem> mPhoneItemList;

    public SaveListAdapter(@NonNull Context context, int layoutResId, @NonNull ArrayList<SaveItem> phoneItenList) {
        super(context, layoutResId, phoneItenList);

        this.mContext=context;
        this.mLayoutResId=layoutResId;
        this.mPhoneItemList=phoneItenList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemLayout = inflater.inflate(mLayoutResId,null);

        SaveItem item = mPhoneItemList.get(position);

        ImageView phoneImageView = itemLayout.findViewById(R.id.save_image_view);
        TextView phoneTitleTextView = itemLayout.findViewById(R.id.save_title_text_view);
        TextView phoneNumberTextView = itemLayout.findViewById(R.id.save_number_text_view);
        phoneTitleTextView.setText(item.title);
        phoneNumberTextView.setText(item.number);
        String pictureFileName=item.picture;
        AssetManager am = mContext.getAssets();
        try {
            InputStream stream = am.open(pictureFileName);
            Drawable drawable=Drawable.createFromStream(stream,null);
            phoneImageView.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
            File pictureFile=new File(mContext.getFilesDir(),pictureFileName);
            Drawable drawable = Drawable.createFromPath(pictureFile.getAbsolutePath());
            phoneImageView.setImageDrawable(drawable);
        }
        return itemLayout;
    }
}

