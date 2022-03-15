package com.tencent.wxcloudrun.util;


import org.apache.commons.lang3.RandomUtils;

public class ToolsHelper {

    public static String createRandomStr(Integer length) {
        String str = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < length; ++i) {
            int number = RandomUtils.nextInt(0, str.length() - 1);
            sb.append(str.charAt(number));
        }

        return sb.toString();
    }
}
