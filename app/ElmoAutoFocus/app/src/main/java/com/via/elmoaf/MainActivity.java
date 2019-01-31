package com.via.elmoaf;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;

import com.via.elmoaf.util.PropUtil;
import com.via.elmoaf.base.BaseCameraView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextureView.SurfaceTextureListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Timer mTimer;
    private TimerTask mTimerTask;
    private boolean mIsCheckDebugFile;
    public static final String PATH_DEGBU_FILE = "/storage/sdcard0/test.yuv";
    private boolean mIsCatchPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BaseCameraView.setmSurfaceTextureListener(this);
        findViewById(R.id.mBTCatchNativePreview).setOnClickListener(this);
        findViewById(R.id.mBTCatchPreview).setOnClickListener(this);
    }

    private void initTimer() {
        if (mTimer == null) {
            mTimer = new Timer();
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    boolean isCatchEnd = PropUtil.get("persist.debug.elmoaf").equals("2") ? true : false;
                    File fileTarget = new File(PATH_DEGBU_FILE);
                    if (mIsCheckDebugFile && fileTarget.exists() && isCatchEnd) {
                        PropUtil.set("persist.debug.elmoaf", "0");
                        Log.i(TAG, "yuv file exists length : " + fileTarget.length());
                        try {
                            FileInputStream fileInputStream = new FileInputStream(fileTarget);
                            byte[] data = new byte[(int) fileTarget.length()];
                            fileInputStream.read(data);
                            PictureActivity.start(MainActivity.this, data, BaseCameraView.getmSizePreview().width, BaseCameraView.getmSizePreview().height, PictureActivity.TYPE_DATA_YV12);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        releaseTimer();

                    }
                }
            };
            mTimer.schedule(mTimerTask, 0, 100);
        }
    }

    private void releaseTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        BaseCameraView.setPreviewCallback(MainActivity.this, new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {
                Log.v(TAG, "onPreviewFrame");
                if (mIsCatchPreview) {
                    mIsCatchPreview = false;
                    PictureActivity.start(MainActivity.this, data, BaseCameraView.getmSizePreview().width, BaseCameraView.getmSizePreview().height, PictureActivity.TYPE_DATA_NV12);
                }
            }
        });
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mBTCatchNativePreview:
                releaseTimer();
                mIsCheckDebugFile = false;
                new File(PATH_DEGBU_FILE).delete();
                initTimer();
                PropUtil.set("persist.debug.elmoaf", "1");
                mIsCheckDebugFile = true;
                break;
            case R.id.mBTCatchPreview:
                mIsCatchPreview = true;
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseTimer();
    }
}
