package cn.ffcs.buffet.model.ao;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @Description: 生成订单AO
 * @Author:
 * @Date: 2020/9/1 20:02
 */
@ApiModel(value = "/api/order/payOrderAO")
public class PayOrderAO implements Serializable {

    /**
     * 订单id
     */
    private Long id;

    /**
     * 商品规格idList
     */
    private Integer[] idList;

    /**
     * 商品数量集合
     */
    private Integer[] goodCountList;

    public PayOrderAO() {
    }

    public PayOrderAO(Long id, Integer[] idList, Integer[] goodCountList) {
        this.id = id;
        this.idList = idList;
        this.goodCountList = goodCountList;
    }

    @Override
    public String toString() {
        return "PayOrderAO{" +
                "id=" + id +
                ", idList=" + Arrays.toString(idList) +
                ", goodCountList=" + Arrays.toString(goodCountList) +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer[] getIdList() {
        return idList;
    }

    public void setIdList(Integer[] idList) {
        this.idList = idList;
    }

    public Integer[] getGoodCountList() {
        return goodCountList;
    }

    public void setGoodCountList(Integer[] goodCountList) {
        this.goodCountList = goodCountList;
    }
}
