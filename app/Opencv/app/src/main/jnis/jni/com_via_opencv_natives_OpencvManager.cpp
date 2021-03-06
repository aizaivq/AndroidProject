/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
#include<android/log.h>
#include "com_via_opencv_natives_OpencvManager.h"
#include <opencv2/opencv.hpp>
#include <opencv2/imgproc.hpp>
#include <opencv/cv.h>
#include <opencv2/legacy/legacy.hpp>

#include <sys/types.h>
#include <dirent.h>
#include <unistd.h>

#include <iostream>

#define TAG "opencv"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG ,__VA_ARGS__) // 定义LOGD类型
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG ,__VA_ARGS__) // 定义LOGI类型
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,TAG ,__VA_ARGS__) // 定义LOGW类型
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG ,__VA_ARGS__) // 定义LOGE类型
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL,TAG ,__VA_ARGS__) // 定义LOGF类型
using namespace std;

using namespace cv;
VideoCapture capture;
char *ConvertJByteaArrayToChars(JNIEnv *env, jbyteArray bytearray) {
    char *chars = NULL;
    jbyte *bytes;
    bytes = env->GetByteArrayElements(bytearray, 0);
    int chars_len = env->GetArrayLength(bytearray);
    chars = new char[chars_len + 1];
    memset(chars, 0, chars_len + 1);
    memcpy(chars, bytes, chars_len);
    chars[chars_len] = 0;

    env->ReleaseByteArrayElements(bytearray, bytes, 0);

    return chars;
}




JNIEXPORT jfloat JNICALL Java_com_via_opencv_natives_OpencvManager_focusCheck
        (JNIEnv *env, jclass thiz, jstring path) {
    const char *str_path = env->GetStringUTFChars(path, false);
    Mat imageSource = imread(str_path, 1);
    // LOGI("cvLoadImage");
    // IplImage *img = cvLoadImage(str_path, CV_LOAD_IMAGE_COLOR);
    Mat imageGrey;
    int channels = imageSource.channels();
    Size size = imageSource.size();
    int w = size.width;
    int h = size.height;
    cvtColor(imageSource, imageGrey, CV_RGB2GRAY);
    Mat imageSobel;
    Laplacian(imageGrey, imageSobel, CV_16U);
    double meanValue = 0.0;
    meanValue = mean(imageSobel)[0];
    stringstream meanValueStream;
    string meanValueString;
    meanValueStream << meanValue;
    meanValueStream >> meanValueString;
    delete (str_path);
    imageSource.release();
    imageGrey.release();
    imageSobel.release();

    return atof(meanValueString.c_str());
}







































