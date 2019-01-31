package com.via.elmoaf.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.util.Log;

import java.io.ByteArrayOutputStream;

/**
 * Created by nick on 18-12-25.
 */

public class Utils {
    public static int[] NV122RGB(byte[] yuv, int width, int height) {
        int[] rgb = new int[width * height];
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                int yIndex = j * width + i;
                int uIndex;
                if ((i % 2) == 0) {
                    uIndex = width * height + (j / 2) * width + i - 1;
                } else {
                    uIndex = width * height + (j / 2) * width + i - 2;
                }
                int vIndex = uIndex + 1;
                int y = (0xff & yuv[yIndex]);
                int u = (0xff & yuv[uIndex]);
                int v = (0xff & yuv[vIndex]);
                rgb[yIndex] = YUV2RGB(y, u, v);
            }
        }
        return rgb;
    }


    public static int[] YV122RGB(byte[] yuv, int width, int height) {
        int[] rgb = new int[width * height];
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                int yIndex = j * width + i;
                int vIndex = width * height + (j / 2) * width / 2 + i / 2 ;
                int uIndex = vIndex + width * height / 4;
                int y = (0xff & yuv[yIndex]);
                int u = (0xff & yuv[uIndex]);
                int v = (0xff & yuv[vIndex]);
                rgb[yIndex] = YUV2RGB(y, u, v);
            }
        }
        return rgb;
    }

    private static int YUV2RGB(int y, int u, int v) {
        int r = (int) (y + 1.402 * (v - 128));
        int g = (int) (y - 0.34414 * (u - 128) - 0.71414 * (v - 128));
        int b = (int) (y + 1.772 * (u - 128));
        r = av_clip_uint8_c(r); // r = clip_8bit(r);
        g = av_clip_uint8_c(g); // g = clip_8bit(g);
        b = av_clip_uint8_c(b); // b = clip_8bit(b);
        return 0xFF000000 | (r << 16) | (g << 8) | b;
    }

    static int YUV2RGB565(int y, int u, int v) {
        int yy, ug, ub, vg, vr, r, g, b;
        yy = y << 8;
        u = u - 128;
        ug = u * 88;
        ub = u * 454;
        v = v - 128;
        vg = v * 183;
        vr = v * 359;

        r = (yy + vr) >> 8;
        g = (yy - ug - vg) >> 8;
        b = (yy + ub) >> 8;

        r = av_clip_uint8_c(r); // r = clip_8bit(r);
        g = av_clip_uint8_c(g); // g = clip_8bit(g);
        b = av_clip_uint8_c(b); // b = clip_8bit(b);

        // return ((r >> 3) << 11) | ((g >> 2) << 5) | ((b >> 3) << 0);
        return 0xFF000000 | (r << 16) | (g << 8) | b;

    }

    static int av_clip_uint8_c(int a) {
        if ((a & (~0xFF)) != 0)
            return (-a) >> 31;
        else
            return a;
    }

}
