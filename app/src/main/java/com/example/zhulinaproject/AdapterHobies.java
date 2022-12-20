package com.example.zhulinaproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class AdapterHobies extends BaseAdapter {

    private final Context mContext;
    List<SelectedsMask> maskList;

    public AdapterHobies(Context mContext, List<SelectedsMask> maskList) {
        this.mContext = mContext;
        this.maskList = maskList;
    }

    @Override
    public int getCount() {
        return maskList.size();
    }

    @Override
    public Object getItem(int i) {
        return maskList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return maskList.get(i).getId_selected();
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    private Bitmap getUserImage(String encodedImg) {
        byte[] bytes = Base64.decode(encodedImg, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.item_hobies, null);
        SelectedsMask mask = maskList.get(position);
        TextView HobiesName = v.findViewById(R.id.HobiesName);
        ImageView Image = v.findViewById(R.id.imageView);
        RatingBar RatingBars = v.findViewById(R.id.ratingBar);


        HobiesName.setText(mask.getNameSlected());
        RatingBars.setRating(Float.parseFloat(Integer.toString(mask.getPersonal_assessment())));

        if (!mask.getPhotoSlected().equals("null")) {
            Image.setImageBitmap(getUserImage(mask.getPhotoSlected()));
        }

        return v;
    }


}
