#include <jni.h>
#include <iostream>
#include "per_owisho_learn_jni_HelloWorldJNI.h"

// Implementation of native method sayHello() of HelloJNI class
JNIEXPORT void JNICALL Java_per_owisho_learn_jni_HelloWorldJNI_sayHello(JNIEnv *env, jobject thisObj){
    std::cout << "Hello from C++ !!" << std::endl;
    return;
}