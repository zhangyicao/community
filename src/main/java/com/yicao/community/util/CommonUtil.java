package com.yicao.community.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

/**
 * 提供一些常用的工具方法
 */
public class CommonUtil {

    // generate random uuid
    public static String generateUUID(){
        return java.util.UUID.randomUUID().toString().replaceAll("-", "");
    }

    //MD5加密+salt
    public static String md5(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }
}
