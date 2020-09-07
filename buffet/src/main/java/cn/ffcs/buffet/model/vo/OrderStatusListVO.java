package cn.ffcs.buffet.model.vo;

import java.io.Serializable;

/**
 * @Description: 订单状态列表VO
 * @Author: mingfeng.zhu@ffcs.cn
 * @Date: 2020/8/17 17:32
 */
public class OrderStatusListVO implements Serializable {

    /**
     * 状态值
     */
    private String index;

    /**
     * 状态名称
     */
    private String name;

    public OrderStatusListVO() {
    }

    public OrderStatusListVO(String index, String name) {
        this.index = index;
        this.name = name;
    }

    @Override
    public String toString() {
        return "OrderStatusListVO{" +
                "index='" + index + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
