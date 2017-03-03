package com.vanxd.baidubar.component;

/**
 * Created by 37343 on 2017-03-03.
 */
public class MessagePool {
    private static String[] sentences;

    private static Integer count;

    public static Integer getCount() {
        return count;
    }

    public static void setCount(Integer count) {
        MessagePool.count = count;
    }

    public static String[] getSentences() {
        return sentences;
    }

    public static void setSentences(String[] sentences) {
        MessagePool.sentences = sentences;
    }
}
