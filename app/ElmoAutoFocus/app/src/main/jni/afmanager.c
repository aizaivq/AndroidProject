#include <sys/system_properties.h>
#include <stdio.h>
#include <dlfcn.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include <android/log.h>

#define TAG "AFManager"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG ,__VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG ,__VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,TAG ,__VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG ,__VA_ARGS__)
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL,TAG ,__VA_ARGS__)

#define DEBUG 1

int main(int argv,char * argc[])
{
    return 1;
}

int savePictureDebug(char * data,int width,int height)
{
    LOGI("savePictureDebug start");
    char value[PROP_VALUE_MAX];
    __system_property_get("persist.debug.elmoaf", value);
    if(strncmp(value, "1",1) == 0) {
        int length = width * height * 3 / 2;
        __system_property_set("persist.debug.elmoaf","0");
        int fd = open("/storage/sdcard0/test.yuv",O_CREAT | O_RDWR,0644);
        write(fd,data,length);
        close(fd);
        __system_property_set("persist.debug.elmoaf","2");
        return 1;
    }
    return 0;
}

int handleData(char * data,int width,int height)
{
    LOGI("handleData start");
    if(DEBUG)
    {
        int retSavePictureDebug = savePictureDebug(data,width,height);
        LOGI("retSavePictureDebug: %d",retSavePictureDebug);
    }
    //to use yv12 data
    for(int i = width * height;i < width * height * 3 / 2;i++)
    {
	data[i] = 0x80;
    }

    return 1;
}
