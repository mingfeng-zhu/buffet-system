package cn.ffcs.buffet.model.vo;

import io.swagger.annotations.ApiModel;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description OrderDataVO
 * @Author huang.zhao
 * @Date 2020/08/30 16:40
 */
@ApiModel
public class OrderDataVO {

    /**
     * 订单数数据
     */
    private List<Integer> countList;

    /**
     * 交易金额数据
     */
    private List<BigDecimal> moneyList;

    public OrderDataVO() {
    }

    public OrderDataVO(List<Integer> countList, List<BigDecimal> moneyList) {
        this.countList = countList;
        this.moneyList = moneyList;
    }

    @Override
    public String toString() {
        return "OrderDataVO{" +
                "countList=" + countList +
                ", moneyList=" + moneyList +
                '}';
    }

    public List<Integer> getCountList() {
        return countList;
    }

    public void setCountList(List<Integer> countList) {
        this.countList = countList;
    }

    public List<BigDecimal> getMoneyList() {
        return moneyList;
    }

    public void setMoneyList(List<BigDecimal> moneyList) {
        this.moneyList = moneyList;
    }
}
