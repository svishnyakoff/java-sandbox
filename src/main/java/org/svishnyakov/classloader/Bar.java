package org.svishnyakov.classloader;

public class Bar {

    public static void main(String[] args) {
        System.out.println("Bar is loaded by classloader: " + Bar.class.getClassLoader());
    }
}
