package cn.ffcs.buffet.model.ao;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;

/**
 * @Description: 生成订单AO
 * @Author:
 * @Date: 2020/9/1 20:02
 */
@ApiModel(value = "/api/order/addOrder")
public class AddOrderAO implements Serializable {

    private Integer[] idList;

    private BigDecimal[] totalMoneyList;

    private Integer[] goodCountList;

    private BigDecimal totalMoney;

    private Integer addressId;

    public AddOrderAO() {
    }

    public AddOrderAO(Integer[] idList, BigDecimal[] totalMoneyList, Integer[] goodCountList,
                      BigDecimal totalMoney, Integer addressId) {
        this.idList = idList;
        this.totalMoneyList = totalMoneyList;
        this.goodCountList = goodCountList;
        this.totalMoney = totalMoney;
        this.addressId = addressId;
    }

    @Override
    public String toString() {
        return "AddOrderAO{" +
                "idList=" + Arrays.toString(idList) +
                ", totalMoneyList=" + Arrays.toString(totalMoneyList) +
                ", goodCountList=" + Arrays.toString(goodCountList) +
                ", totalMoney=" + totalMoney +
                ", addressId=" + addressId +
                '}';
    }

    public Integer[] getIdList() {
        return idList;
    }

    public void setIdList(Integer[] idList) {
        this.idList = idList;
    }

    public BigDecimal[] getTotalMoneyList() {
        return totalMoneyList;
    }

    public void setTotalMoneyList(BigDecimal[] totalMoneyList) {
        this.totalMoneyList = totalMoneyList;
    }

    public Integer[] getGoodCountList() {
        return goodCountList;
    }

    public void setGoodCountList(Integer[] goodCountList) {
        this.goodCountList = goodCountList;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }
}
