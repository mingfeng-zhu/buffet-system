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

    /** 数据库影响条数为0 */
    public static final Integer RETURN_DATA_COUNT = 0;

    /** 购物者商品数量达到删除界限0 */
    public static final Integer SHOP_CARD_DELETE_ZERO = 0;

    /** 商品库存为0 **/
    public static final Integer PRODUCT_NUMBER_ZERO = 0;

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

    /**
     * 购物车支付状态
     */
    public enum Shop_Cart_STATUS {
        un_paid("0", "未支付"), paid("1", "已支付");
        private String index;
        private String name;
        // 构造方法
        private Shop_Cart_STATUS(String index, String name) {
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

    /**
     * 用户状态
     */
    public enum USER_STATUS {
        forbidden(0, "禁用"), nomal(1, "正常");
        private Integer index;
        private String name;
        // 构造方法
        private USER_STATUS(Integer index, String name) {
            this.name = name;
            this.index = index;
        }
        // get set 方法
        public String getName() {
            return name;
        }
        public Integer getIndex() {
            return index;
        }
    }
}
