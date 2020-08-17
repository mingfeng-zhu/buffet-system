package cn.ffcs.buffet.common.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Description: MD5加密工具类 这里盐使用user.getUsername()
 * @Author: mingfeng.zhu
 * @Date: 2020/8/16 10:14
 */
public class Md5Util {
    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }


    /**
     *@Description:第一次md5加密，用于网络传输
     *@parms:  [inputPass, salt]密码
     *@return:  java.lang.String 一次加密的String
     *@createBy:  zmf
     */
    public static String inputPassToFormPass(String inputPass, String salt){
        String str = inputPass + salt;
        return md5(str);
    }

    /**
     *@Description:第二次Md5加密，用于存储
     *@parms:  [formPass密码, salt]
     *@return:  java.lang.String 二次加密后的结果
     *@createBy:  zmf
     */
    public static String formPassToDbPass(String formPass, String salt){
        String str = formPass + salt;
        return md5(str);
    }
    /**
     *@Description:合并
     *@parms:  [input, salt] input 密码 salt 盐
     *@return:  java.lang.String 加密后String
     *@createBy:  zmf
     */
    public static String getMd5(String input, String salt) {
        String formPass = inputPassToFormPass(input, salt);
        String dbPass = formPassToDbPass(formPass, salt);
        return dbPass;
    }

}
