package test;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

class Main {
    interface CLibrary extends Library {
        CLibrary INSTANCE = Native.load(Platform.isWindows() ? "msvcrt" : "c", CLibrary.class);

        void printf(String format, Object... args);
    }

    public static void main(String[] args) {
        try {
            CLibrary.INSTANCE.printf("Hello, World\n");
        } catch (Throwable ex) {
            ex.printStackTrace(System.err);
        }
    }
}
