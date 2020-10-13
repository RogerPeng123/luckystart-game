package com.luckystar.utils;

public class SystemUtil {
    /**
     * 判断是否是win系统
     *
     * @return
     */
    public static boolean isWindows() {
        boolean flag = false;
        String osName = System.getProperties().getProperty("os.name").toUpperCase();
        if (osName.contains("WINDOWS")) {
            flag = true;
        }
        return flag;
    }
}
