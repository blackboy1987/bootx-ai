package com.bootx.util;

/**
 * @author black
 */
public class CodeUtils {
    public static String getCode(int i) {
        StringBuilder code = new StringBuilder();
        for (int j = 0; j < i; j++) {
            code.append((int) (Math.random() * 10));
        }
       return code.toString();
    }
}
