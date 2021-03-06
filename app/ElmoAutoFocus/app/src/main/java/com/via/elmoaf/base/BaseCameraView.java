package com.via.elmoaf.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.TextureView;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by nick on 17-6-21.
 */

public class BaseCameraView extends TextureView implements TextureView.SurfaceTextureListener {
    protected Camera mCamera;
    private static final String TAG = BaseCameraView.class.getSimpleName();
    public static final String ACTION_SURFACE_CREATED = "ACTION_SURFACE_CREATED";
    public static final String ACTION_SURFACE_DESTROY = "ACTION_SURFACE_DESTROY";
    public static final String ACTION_AUTO_FOCUS = "ACTION_AUTO_FOCUS";
    private static final String ACTION_SET_PREVIEW_SIZE = "ACTION_SET_PREVIEW_SIZE";
    private static final String ACTION_SET_PICTURE_SIZE = "ACTION_SET_PICTURE_SIZE";
    private static final String ACTION_SET_ANTIBANDING = "ACTION_SET_ANTIBANDING";
    private static final String ACTION_SET_COLOR_EFFECT = "ACTION_SET_COLOR_EFFECT";
    private static final String ACTION_SET_FLASH_MODE = "ACTION_SET_FLASH_MODE";
    private static final String ACTION_SET_ROTATION = "ACTION_SET_ROTATION";
    private static final String ACTION_STOP_PREVIEW = "ACTION_STOP_PREVIEW";
    private static final String ACTION_START_PREVIEW = "ACTION_START_PREVIEW";
    private static final String ACTION_SET_SCENE_MODE = "ACTION_SET_SCENE_MODE";
    private static final String ACTION_SET_WHITE_BALANCE = "ACTION_SET_WHITE_BALANCE";
    private static final String ACTION_SET_FOCUS_MODE = "ACTION_SET_FOCUS_MODE";
    private static final String ACTION_SET_ZOOM = "ACTION_SET_ZOOM";
    private static final String ACTION_SET_EXPOSURE = "ACTION_SET_EXPOSURE";
    private static final String ACTION_SET_FOCUS = "ACTION_SET_FOCUS";
    private static final String ACTION_SET_SCAN_QRCODE = "ACTION_SET_SCAN_QRCODE";

    private static final String ACTION_SET_PREVIEW_CALLBACK = "ACTION_SET_PREVIEW_CALLBACK";


    private static final String KEY_WIDTH = "KEY_WIDTH";
    private static final String KEY_HEIGHT = "KEY_HEIGHT";
    private static final String KEY_PARAM = "KEY_PARAM";


    private static List<String> mListWhiteBalance = new ArrayList<>();
    private static List<Camera.Size> mListPreviewSize = new ArrayList<>();
    private static List<Camera.Size> mListPictureSize = new ArrayList<>();
    private static List<String> mListSceneMode = new ArrayList<>();
    private static List<String> mListColorEffect = new ArrayList<>();
    private static List<String> mListFlashMode = new ArrayList<>();
    private static List<String> mListFocusMode = new ArrayList<>();
    private static List<String> mListAntibanding = new ArrayList<>();
    private static List<Integer> mListZoom = new ArrayList<>();


    public static List<Integer> getmListZoom() {
        return mListZoom;
    }


    private static float mExposureCompensationStep;
    private static float mExposureMax;
    private static float mExposureMin;


    private static Camera.Size mSizePreview;
    private static Camera.Size mSizePicture;
    private static String mAntibanding;
    private static String mColorEffect;
    private static String mFlashMode;
    private static String mSceneMode;
    private static String mWhiteBalance;
    private static String mFocusMode;
    private static int mZoom = Integer.MAX_VALUE;
    private static int mExposure = Integer.MAX_VALUE;
    private static int mRotation = 0;

    private static Camera.PreviewCallback mPreviewCallback;

    private static SurfaceTextureListener mSurfaceTextureListener;

    private static SurfaceTextureUpdateListener mSurfaceTextureUpdateListener;


    public static void setmSurfaceTextureListener(SurfaceTextureListener surfaceTextureListener) {
        mSurfaceTextureListener = surfaceTextureListener;
    }

    public static void setmSurfaceTextureUpdateListener(SurfaceTextureUpdateListener mSurfaceTextureUpdateListener) {
        BaseCameraView.mSurfaceTextureUpdateListener = mSurfaceTextureUpdateListener;
    }

    public static SurfaceTextureUpdateListener getmSurfaceTextureUpdateListener() {
        return mSurfaceTextureUpdateListener;
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, Intent intent) {
            String action = intent.getAction();
            String strParam;
            int intParam;
            boolean boolParam;
            Camera.Parameters parameters;
            Log.i(TAG, "onReceive action: " + action);
            switch (intent.getAction()) {
                case ACTION_SET_PREVIEW_SIZE:
                    mSizePreview.width = intent.getIntExtra(KEY_WIDTH, mSizePreview.width);
                    mSizePreview.height = intent.getIntExtra(KEY_HEIGHT, mSizePreview.height);
                    mCamera.stopPreview();
                    parameters = mCamera.getParameters();
                    parameters.setPreviewSize(mSizePreview.width, mSizePreview.height);
                    mCamera.setParameters(parameters);
                    mCamera.startPreview();
                    break;
                case ACTION_SET_PICTURE_SIZE:
                    mSizePicture.width = intent.getIntExtra(KEY_WIDTH, mSizePicture.width);
                    mSizePicture.height = intent.getIntExtra(KEY_HEIGHT, mSizePicture.height);
                    mCamera.stopPreview();
                    parameters = mCamera.getParameters();
                    parameters.setPictureSize(mSizePicture.width, mSizePicture.height);
                    mCamera.setParameters(parameters);
                    mCamera.startPreview();
                    break;
                case ACTION_SET_ANTIBANDING:
                    strParam = intent.getStringExtra(KEY_PARAM);
                    mAntibanding = strParam;
                    parameters = mCamera.getParameters();
                    parameters.setAntibanding(strParam);
                    mCamera.setParameters(parameters);
                    break;
                case ACTION_SET_COLOR_EFFECT:
                    strParam = intent.getStringExtra(KEY_PARAM);
                    mColorEffect = strParam;
                    parameters = mCamera.getParameters();
                    parameters.setColorEffect(strParam);
                    mCamera.setParameters(parameters);
                    break;
                case ACTION_SET_FLASH_MODE:
                    strParam = intent.getStringExtra(KEY_PARAM);
                    mFlashMode = strParam;
                    parameters = mCamera.getParameters();
                    parameters.setFlashMode(strParam);
                    mCamera.setParameters(parameters);
                    break;
                case ACTION_SET_SCENE_MODE:
                    strParam = intent.getStringExtra(KEY_PARAM);
                    mSceneMode = strParam;
                    parameters = mCamera.getParameters();
                    parameters.setSceneMode(strParam);
                    mCamera.setParameters(parameters);
                    break;
                case ACTION_SET_WHITE_BALANCE:
                    strParam = intent.getStringExtra(KEY_PARAM);
                    mWhiteBalance = strParam;
                    parameters = mCamera.getParameters();
                    parameters.setWhiteBalance(strParam);
                    mCamera.setParameters(parameters);
                    break;
                case ACTION_SET_FOCUS_MODE:
                    strParam = intent.getStringExtra(KEY_PARAM);
                    mFocusMode = strParam;
                    parameters = mCamera.getParameters();
                    parameters.setFocusMode(strParam);
                    mCamera.setParameters(parameters);
                    break;
                case ACTION_SET_ZOOM:
                    intParam = intent.getIntExtra(KEY_PARAM, mZoom);
                    mZoom = intParam;
                    parameters = mCamera.getParameters();
                    parameters.setZoom(mZoom);
                    mCamera.setParameters(parameters);
                    break;
                case ACTION_SET_EXPOSURE:
                    intParam = intent.getIntExtra(KEY_PARAM, mExposure);
                    mExposure = intParam;
                    parameters = mCamera.getParameters();
                    parameters.setZoom(mExposure);
                    mCamera.setParameters(parameters);
                    break;
                case Intent.ACTION_SCREEN_ON:
                    mCamera.startPreview();
                    break;
                case Intent.ACTION_SCREEN_OFF:
                    mCamera.stopPreview();
                    break;
                case ACTION_SET_ROTATION:
                    intParam = intent.getIntExtra(KEY_PARAM, mRotation);
                    mRotation = intParam;
                    mCamera.setDisplayOrientation(mRotation);
                    break;
                case ACTION_SET_FOCUS:
                    mCamera.startPreview();
                    mCamera.autoFocus(new Camera.AutoFocusCallback() {
                        @Override
                        public void onAutoFocus(boolean success, Camera camera) {
                            Intent focusIntent = new Intent(ACTION_AUTO_FOCUS);
                            focusIntent.putExtra(KEY_PARAM, success);
                            context.sendBroadcast(focusIntent);
                        }
                    });
                    break;
                case ACTION_SET_SCAN_QRCODE:
                    break;
                case ACTION_SET_PREVIEW_CALLBACK:
                    mCamera.setPreviewCallback(mPreviewCallback);
                    break;


            }
        }
    };


    public BaseCameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setSurfaceTextureListener(this);

    }


    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        Log.v(TAG, "surfaceCreated");
        mCamera = Camera.open(0);
        try {
            mCamera.setDisplayOrientation(mRotation);
            mCamera.setPreviewTexture(surface);
            Camera.Parameters parameters = mCamera.getParameters();

            mListWhiteBalance = parameters.getSupportedWhiteBalance();
            mListPreviewSize = parameters.getSupportedPreviewSizes();
            mListPictureSize = parameters.getSupportedPictureSizes();
            mListSceneMode = parameters.getSupportedSceneModes();
            mListColorEffect = parameters.getSupportedColorEffects();
            mListFlashMode = parameters.getSupportedFlashModes();
            mListFocusMode = parameters.getSupportedFocusModes();
            mListAntibanding = parameters.getSupportedAntibanding();
            mListZoom = parameters.getZoomRatios();
            if (mSizePreview == null)
                mSizePreview = getBestPreviewSize();
            if (mSizePicture == null)
                mSizePicture = getBestPictureSize();
            if (mAntibanding != null)
                parameters.setAntibanding(mAntibanding);
            else
                mAntibanding = parameters.getAntibanding();
            if (mColorEffect != null)
                parameters.setColorEffect(mColorEffect);
            else
                mColorEffect = parameters.getColorEffect();
            if (mFlashMode != null)
                parameters.setFlashMode(mFlashMode);
            else
                mFlashMode = parameters.getFlashMode();
            if (mSceneMode != null)
                parameters.setSceneMode(mSceneMode);
            else
                mSceneMode = parameters.getSceneMode();
            if (mWhiteBalance != null)
                parameters.setWhiteBalance(mWhiteBalance);
            else
                mWhiteBalance = parameters.getWhiteBalance();
            if (mFocusMode != null)
                parameters.setFocusMode(mFocusMode);
            else {
                mFocusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE;
                parameters.setFocusMode(mFocusMode);
            }
            if (mZoom != Integer.MAX_VALUE)
                parameters.setZoom(mZoom);
            else
                mZoom = parameters.getZoom();
            if (mExposure != Integer.MAX_VALUE)
                parameters.setExposureCompensation(mExposure);
            else
                mExposure = parameters.getExposureCompensation();
            mExposureCompensationStep = parameters.getExposureCompensationStep();
            mExposureMax = parameters.getMaxExposureCompensation();
            mExposureMin = parameters.getMinExposureCompensation();
            parameters.setPreviewSize(mSizePreview.width, mSizePreview.height);
            parameters.setPictureSize(mSizePicture.width, mSizePicture.height);
            mCamera.setParameters(parameters);
            mCamera.startPreview();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ACTION_SET_SCENE_MODE);
            intentFilter.addAction(ACTION_SET_WHITE_BALANCE);
            intentFilter.addAction(ACTION_SET_COLOR_EFFECT);
            intentFilter.addAction(ACTION_SET_EXPOSURE);
            intentFilter.addAction(ACTION_SET_ANTIBANDING);
            intentFilter.addAction(ACTION_SET_FLASH_MODE);
            intentFilter.addAction(ACTION_SET_FOCUS_MODE);
            intentFilter.addAction(ACTION_SET_PICTURE_SIZE);
            intentFilter.addAction(ACTION_SET_FOCUS);

            intentFilter.addAction(ACTION_STOP_PREVIEW);
            intentFilter.addAction(ACTION_START_PREVIEW);

            intentFilter.addAction(ACTION_SET_PICTURE_SIZE);


            intentFilter.addAction(ACTION_SET_PREVIEW_SIZE);
            intentFilter.addAction(ACTION_SET_ZOOM);
            intentFilter.addAction(Intent.ACTION_SCREEN_ON);
            intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
            intentFilter.addAction(ACTION_SET_ROTATION);
            intentFilter.addAction(ACTION_SET_SCAN_QRCODE);

            intentFilter.addAction(ACTION_SET_PREVIEW_CALLBACK);



            getContext().registerReceiver(mBroadcastReceiver, intentFilter);
            getContext().sendBroadcast(new Intent(ACTION_SURFACE_CREATED));
            if (mSurfaceTextureListener != null)
                mSurfaceTextureListener.onSurfaceTextureAvailable(surface, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        if (mSurfaceTextureListener != null)
            mSurfaceTextureListener.onSurfaceTextureSizeChanged(surface, width, height);
        if (mCamera != null)
            mCamera.startPreview();
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        Log.v(TAG, "surfaceDestroyed");
        if (mSurfaceTextureListener != null)
            mSurfaceTextureListener.onSurfaceTextureDestroyed(surface);
        if (mCamera != null) {
            getContext().sendBroadcast(new Intent(ACTION_SURFACE_DESTROY));
            getContext().unregisterReceiver(mBroadcastReceiver);
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        if (mSurfaceTextureListener != null)
            mSurfaceTextureListener.onSurfaceTextureUpdated(surface);
        if (mSurfaceTextureUpdateListener != null)
            mSurfaceTextureUpdateListener.update(this);
    }


    public static void setPreviewCallback(Context context, Camera.PreviewCallback callback) {
        mPreviewCallback = callback;
        context.sendBroadcast(new Intent(ACTION_SET_PREVIEW_CALLBACK));
    }

    public void takePicture(Camera.PictureCallback pictureCallback) {
        mCamera.takePicture(null, null, pictureCallback);
    }


    private Camera.Size getBestPictureSize() {
        Camera.Parameters parameters = mCamera.getParameters();
        List<Camera.Size> list = parameters.getSupportedPictureSizes();
        int index = 0;
        if (index < 0)
            index = 0;
        Log.i(TAG, "picture size: " + list.get(index).width + "x" + list.get(index).height);
        return list.get(index);
    }

    private Camera.Size getBestPreviewSize() {


        int viewW = getWidth();
        int viewH = getHeight();
        Log.i(TAG, "view size: " + viewW + "x" + viewH);
        Camera.Parameters parameters = mCamera.getParameters();
        List<Camera.Size> list = parameters.getSupportedPreviewSizes();
        int i = 0;
        int j = 0;

        for (; j < list.size(); j++) {
            Log.i(TAG, "for preview size: " + list.get(j).width + "x" + list.get(j).height);
            int w = list.get(j).width;
            int h = list.get(j).height;
            if (viewW == w && h == viewH) {
                break;
            }
        }

        if (j < list.size())
            return list.get(j);

        for (; i < list.size(); i++) {
            Log.i(TAG, "for preview size: " + list.get(i).width + "x" + list.get(i).height);
            int w = list.get(i).width;
            int h = list.get(i).height;
            if (viewW < w || viewH < h) {
                i = i - 1;
                if (i < 0)
                    i = 0;
                break;
            }
        }
            return list.get(i);
    }


    public static void setPreviewSize(Context context, int width, int height) {
        Intent intent = new Intent(ACTION_SET_PREVIEW_SIZE);
        intent.putExtra(KEY_WIDTH, width);
        intent.putExtra(KEY_HEIGHT, height);
        context.sendBroadcast(intent);
    }

    public static void setPictureSize(Context context, int width, int height) {
        Intent intent = new Intent(ACTION_SET_PICTURE_SIZE);
        intent.putExtra(KEY_WIDTH, width);
        intent.putExtra(KEY_HEIGHT, height);
        context.sendBroadcast(intent);
    }

    public static void setAntibanding(Context context, String param) {
        Intent intent = new Intent(ACTION_SET_ANTIBANDING);
        intent.putExtra(KEY_PARAM, param);
        context.sendBroadcast(intent);
    }

    public static void setSceneMode(Context context, String param) {
        Intent intent = new Intent(ACTION_SET_SCENE_MODE);
        intent.putExtra(KEY_PARAM, param);
        context.sendBroadcast(intent);
    }

    public static void setWhiteBalance(Context context, String param) {
        Intent intent = new Intent(ACTION_SET_WHITE_BALANCE);
        intent.putExtra(KEY_PARAM, param);
        context.sendBroadcast(intent);
    }

    public static void setColorEffect(Context context, String param) {
        Intent intent = new Intent(ACTION_SET_COLOR_EFFECT);
        intent.putExtra(KEY_PARAM, param);
        context.sendBroadcast(intent);
    }

    public static void setFlashMode(Context context, String param) {
        Intent intent = new Intent(ACTION_SET_FLASH_MODE);
        intent.putExtra(KEY_PARAM, param);
        context.sendBroadcast(intent);
    }

    public static void setRotation(Context context, int param) {
        Intent intent = new Intent(ACTION_SET_ROTATION);
        intent.putExtra(KEY_PARAM, param);
        context.sendBroadcast(intent);
    }

    public static void setFocusMode(Context context, String param) {
        Intent intent = new Intent(ACTION_SET_FOCUS_MODE);
        intent.putExtra(KEY_PARAM, param);
        context.sendBroadcast(intent);
    }

    public static void setZoom(Context context, int param) {
        Intent intent = new Intent(ACTION_SET_ZOOM);
        intent.putExtra(KEY_PARAM, param);
        context.sendBroadcast(intent);
    }

    public static void setExposure(Context context, int param) {
        Intent intent = new Intent(ACTION_SET_EXPOSURE);
        intent.putExtra(KEY_PARAM, param);
        context.sendBroadcast(intent);
    }

    public static void setFocus(Context context) {
        context.sendBroadcast(new Intent(ACTION_SET_FOCUS));
    }

    public static void stopPreview(Context context) {
        context.sendBroadcast(new Intent(ACTION_STOP_PREVIEW));
    }

    public static void startPreview(Context context) {
        context.sendBroadcast(new Intent(ACTION_START_PREVIEW));
    }


    public static List<String> getmListWhiteBalance() {
        return mListWhiteBalance;
    }

    public static List<Camera.Size> getmListPreviewSize() {
        return mListPreviewSize;
    }

    public static List<Camera.Size> getmListPictureSize() {
        return mListPictureSize;
    }

    public static List<String> getmListSceneMode() {
        return mListSceneMode;
    }

    public static List<String> getmListColorEffect() {
        return mListColorEffect;
    }

    public static List<String> getmListFlashMode() {
        return mListFlashMode;
    }

    public static List<String> getmListFocusMode() {
        return mListFocusMode;
    }

    public static List<String> getmListAntibanding() {
        return mListAntibanding;
    }

    public static Camera.Size getmSizePreview() {
        return mSizePreview;
    }

    public static Camera.Size getmSizePicture() {
        return mSizePicture;
    }

    public static String getmAntibanding() {
        return mAntibanding;
    }

    public static String getmColorEffect() {
        return mColorEffect;
    }

    public static String getmFlashMode() {
        return mFlashMode;
    }

    public static String getmSceneMode() {
        return mSceneMode;
    }

    public static String getmWhiteBalance() {
        return mWhiteBalance;
    }

    public static String getmFocusMode() {
        return mFocusMode;
    }

    public static int getmZoom() {
        return mZoom;
    }

    public static int getmExposure() {
        return mExposure;
    }

    public static int getmRotation() {
        return mRotation;
    }

    public interface SurfaceTextureUpdateListener {
        void update(TextureView textureView);
    }


    public static float getmExposureCompensationStep() {
        return mExposureCompensationStep;
    }

    public static float getmExposureMax() {
        return mExposureMax;
    }

    public static float getmExposureMin() {
        return mExposureMin;
    }
}
