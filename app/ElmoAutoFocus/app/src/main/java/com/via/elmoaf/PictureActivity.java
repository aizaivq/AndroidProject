package com.via.elmoaf;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.via.elmoaf.util.Utils;

public class PictureActivity extends AppCompatActivity {
    private static int mWidth;
    private static int mHeight;
    private static int mTypeData;
    private static byte[] mData;
    public static final int TYPE_DATA_YV12 = 0;
    public static final int TYPE_DATA_NV12 = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        ImageView mIVPicture = findViewById(R.id.mIVPicture);
        Bitmap bitmap = createBitmap();
        mIVPicture.setImageBitmap(bitmap);
    }


    public static void start(Context context, byte[] data, int width, int height, int type) {
        mData = data;
        mWidth = width;
        mHeight = height;
        mTypeData = type;
        Intent intent = new Intent(context, PictureActivity.class);
        context.startActivity(intent);
    }

    public Bitmap createBitmap() {
        switch (mTypeData) {
            case TYPE_DATA_NV12:
                return Bitmap.createBitmap(Utils.NV122RGB(mData, mWidth, mHeight), mWidth, mHeight, Bitmap.Config.ARGB_8888);
            case TYPE_DATA_YV12:
                return Bitmap.createBitmap(Utils.YV122RGB(mData, mWidth, mHeight), mWidth, mHeight, Bitmap.Config.ARGB_8888);
        }
        return null;
    }


}
