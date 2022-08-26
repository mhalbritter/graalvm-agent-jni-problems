The GraalVM agent doesn't capture all calls done at runtime.

Reproducer:

1. `./gradlew clean run -Pagent`
2. `./gradlew metadataCopy`
3. `./gradlew nativeRun`

```
JNA: Problems loading core IDs: java.lang.Object
java.lang.UnsatisfiedLinkError: Unsupported JNI version 0x0, required by /home/moe/.cache/JNA/temp/jna10621555043339308097.tmp
Exception in thread "main" java.lang.NoClassDefFoundError: java/lang/Object
        at com.oracle.svm.jni.functions.JNIFunctions.FindClass(JNIFunctions.java:345)
        at com.oracle.svm.jni.JNIOnLoadFunctionPointer.invoke(JNILibraryInitializer.java)
        at com.oracle.svm.jni.JNILibraryInitializer.callOnLoadFunction(JNILibraryInitializer.java:69)
        at com.oracle.svm.jni.JNILibraryInitializer.initialize(JNILibraryInitializer.java:126)
        at com.oracle.svm.core.jdk.NativeLibrarySupport.addLibrary(NativeLibrarySupport.java:186)
        at com.oracle.svm.core.jdk.NativeLibrarySupport.loadLibrary0(NativeLibrarySupport.java:142)
        at com.oracle.svm.core.jdk.NativeLibrarySupport.loadLibraryAbsolute(NativeLibrarySupport.java:101)
        at java.lang.ClassLoader.loadLibrary(ClassLoader.java:54)
        at java.lang.Runtime.load0(Runtime.java:755)
        at java.lang.System.load(System.java:1953)
        at com.sun.jna.Native.loadNativeDispatchLibraryFromClasspath(Native.java:1019)
        at com.sun.jna.Native.loadNativeDispatchLibrary(Native.java:989)
        at com.sun.jna.Native.<clinit>(Native.java:195)
```

When adding `java.lang.Object` to `jni-config.json`, the error message changes to the next type which is missing
in `jni-config.json`. The agent should have picked that up.
