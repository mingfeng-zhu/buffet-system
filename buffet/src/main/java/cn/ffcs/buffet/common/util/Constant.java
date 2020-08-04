package cn.ffcs.buffet.common.util;

/**
 * 常量类
 * @author huang.zhao
 * @date: 2020/08/04
 */
public class Constant {

    /** 数据删除状态 */
    public static final String DATA_DELETE = "0";

    /** 数据未删除状态 */
    public static final String DATA_UN_DELETE = "1";

    /**
     * 订单状态
     */
    public enum Order_STATUS {
        cancelled("0", "已取消"), wait_pay("1", "待支付"), wait_receive("2", "待接单"),
        in_make("3", "制作中"), in_delivery("4", "配送中"), completed("5", "已完成"),
        cancel("6", "已取消");
        private String index;
        private String name;
        // 构造方法
        private Order_STATUS(String index, String name) {
            this.name = name;
            this.index = index;
        }
        // get set 方法
        public String getName() {
            return name;
        }
        public String getIndex() {
            return index;
        }
    }
}
