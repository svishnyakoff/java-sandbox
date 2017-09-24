package org.svishnyakov.classloader;

import java.lang.reflect.Method;

public class ClassloaderRunner {

    public static void main(String[] args) throws Exception {
        CustomClassLoader classLoader = new CustomClassLoader(ClassloaderRunner.class.getClassLoader());
        Class aClass = classLoader.loadClass("org.svishnyakov.classloader.Bar");
        Method method = aClass.getMethod("main", String[].class);
        method.invoke(null, (Object) (new String[]{"test"}));
    }
}
