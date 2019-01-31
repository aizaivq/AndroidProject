LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := libelmoaf
#LOCAL_SRC_FILES := com_via_elmoaf_AFManager.c \
#                    afmanager.c
LOCAL_SRC_FILES := afmanager.c
LOCAL_LDLIBS +=  -lm -llog


include $(BUILD_SHARED_LIBRARY)
