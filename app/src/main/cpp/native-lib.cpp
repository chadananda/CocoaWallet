//
// Created by My on 23-11-2021.
//

#include <jni.h>
#include <iostream>
extern "C" JNIEXPORT jstring JNICALL

MainActivity.stringfromJNI(
JNIENV*env,jobObject})


JNIEXPORT jfloat JNICALL Java_com_jni_example_TemperatureSampler_getTemperature (JNIEnv * env, jobject thisObject) {
    std::cout << "Returning Simple Temperature..." << std::endl;
    return 27.8;
}

JNIEXPORT jobject JNICALL Java_com_jni_example_TemperatureSampler_getDetailedTemperature (JNIEnv * env, jobject thisObject) {

    // Get the TemperatureData and create an instance of it.
    jclass temperatureDataClass = env->FindClass("com/jni/example/TemperatureData");
    jobject temperatureData = env->AllocObject(temperatureDataClass);

    // We only need TemperatureScale class here, enumeration will be created later.
    jclass temperatureScaleClass = env->FindClass("com/jni/example/TemperatureScale");

    // Get fields of TemperatureData
    jfieldID timestamp = env->GetFieldID(temperatureDataClass, "timestamp", "Ljava/lang/String;");
    jfieldID temperature = env->GetFieldID(temperatureDataClass, "temperature", "F");
    jfieldID scale = env->GetFieldID(temperatureDataClass, "scale", "Lcom/jni/example/TemperatureScale;");

    // Get CELCUIS scale from TemperatureScale enum.
    jfieldID scaleEnumID = env->GetStaticFieldID(temperatureScaleClass, "CELCIUS", "Lcom/jni/example/TemperatureScale;");
    jobject celciusScale = env->GetStaticObjectField(temperatureScaleClass, scaleEnumID);

    // Check if CELCIUS is the supported scale.
    jclass callerClass = env->GetObjectClass(thisObject);
    jmethodID preferredScaleMethodID = env->GetMethodID(callerClass, "getPreferredScale", "()Lcom/jni/example/TemperatureScale;");
    jobject preferredScale = env->CallObjectMethod(thisObject, preferredScaleMethodID);

    if (!env->IsSameObject(preferredScale, celciusScale)) {
        std::cout << "Preferred scale is not supported, using CELCIUS instead!" << std::endl;
    }

    // Set TemperatureData fields.
    env->SetObjectField(temperatureData, timestamp, env->NewStringUTF("02-03-2020 17:30:48"));
    env->SetFloatField(temperatureData, temperature, 27.8);
    env->SetObjectField(temperatureData, scale, celciusScale);

    std::cout << "" << std::endl;
    return temperatureData;
}