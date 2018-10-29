#include <jni.h>
/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
#include <string.h>
#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <android/log.h>
#include <search-interface.h>
#include <sys/types.h>

#define  LOG_TAG    "libplasma"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)
/* This is a trivial JNI example where we use a native method
 * to return a new VM String. See the corresponding Java source
 * file located at:
 *
 *
 *   apps/samples/hello-jni/project/src/com/example/hellojni/HelloJni.java
 */
jboolean Java_com_lettersearch_core_JniSearchCore_searchPinYin(JNIEnv* env,
		jobject thiz, jobjectArray source, jstring input, jintArray colors) {
	return searchPinyinFromJNI(env, thiz, source, input, colors);
}
jboolean Java_com_lettersearch_core_JniSearchCore_searchMorePinYin(JNIEnv* env,
		jobject thiz, jobjectArray source, jstring input, jintArray colors) {
	return searchMorePinyinFromJNI(env, thiz, source, input, colors);
}

jstring Java_com_lettersearch_core_py_JniSearchCore_get(JNIEnv* env,
		jobject thiz) {
	signed char cs[] = { 108 + 1, 105 + 2, 117 + 3, 113 + 4, 105 + 5, 115 + 6,
			104 + 7, 117 + 8, 97 + 9, 105 + 10, 49 + 11, 49 + 12, 49 + 13 };
	signed char* realcs = "��Ҫ������?�����ǲ�������";
	int i = 0;
	for (; i < 13; i++) {
		cs[i] = cs[i] - i - 1;
	}
	return (*env)->NewStringUTF(env, cs);
}

