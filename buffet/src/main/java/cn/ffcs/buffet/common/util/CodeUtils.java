package cn.ffcs.buffet.common.util;

import java.util.Random;

/**
 * 获取验证码
 */
public class CodeUtils {
    private CodeUtils(){

    }

    /**
     * 全数字
     */
    public static final String NUMBER_CODE = "1234567890";

    /**
     * 全英文大写
     */
    public static final String UPPER_CODE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 全英文小写
     */
    public static final String LOWER_CODE = "abcdefghigklmnopqrstuvwxyz";

    /**
     * 全英文
     */
    public static final String STRING_CODE = "abcdefghigklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 英文和数字(剔除了0和1)
     */
    public static final String ALL_CODE = "abcdefghigklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ23456789";


    /**
     * 获取随机字符
     * @param length 获取长度
     * @param codeStr 截取的原字符
     * @return 随机得到的字符
     */
    public static String getCode(Integer length, String codeStr){
        Random random = new Random();

        StringBuilder code = new StringBuilder();
        int codeLength = codeStr.length();
        for (int i = 0; i < length; i++) {
            code.append(codeStr.charAt(random.nextInt(codeLength)));
        }

        return code.toString();
    }

    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(getCode(4, NUMBER_CODE));
        System.out.println(getCode(4, UPPER_CODE));
        System.out.println(getCode(4, LOWER_CODE));
        System.out.println(getCode(4, STRING_CODE));
        System.out.println(getCode(4, ALL_CODE));
        System.out.println(getCode(4, "你好世界这里是测试"));
    }
}
