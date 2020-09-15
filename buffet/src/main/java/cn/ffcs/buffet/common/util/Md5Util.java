package cn.ffcs.buffet.common.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Description: MD5加密工具类 这里盐使用user.getUserTel()
 * @Author: mingfeng.zhu
 * @Date: 2020/8/16 10:14
 */
public class Md5Util {
    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    /**
     * @Description:合并
     * @parms: [input, salt] input 密码 salt 盐
     * @return: java.lang.String 加密后String
     * @createBy: zmf
     */
    public static String getMd5(String input, String salt) {
        String str = input + salt;
        return md5(str);
    }

}
