package com.study.classapp.util;

import java.util.UUID;

/***
 * 生成唯一ID
 */
public class UUIDCreator {
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
