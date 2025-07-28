package com.proshine.shahecommunityhospital.utils;

import java.util.UUID;

/**
 * 主键id工具类
 * @author lenovo
 */
public class PrimaryKeyUtil {

    public static String generatePrimary(boolean needReplace){
        if(needReplace){
            return UUID.randomUUID().toString().replaceAll("-","");
        }
        return UUID.randomUUID().toString();
    }

}
