//
// Created by flood on 16/3/9.
//

#include "com_flood_iceframe_jnis_JniUtils.h"
JNIEXPORT jstring JNICALL Java_com_flood_iceframe_jnis_JniUtils_getCLanguageString
        (JNIEnv *env, jobject obj){
    return (*env)->NewStringUTF(env, "I am Jni");
}
