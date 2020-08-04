package cn.ffcs.buffet.model.dto;

import io.swagger.annotations.ApiModel;

import java.math.BigDecimal;

/**
 * @Description OrderTotalDataDTO
 * @Author huang.zhao
 * @Date 2020/08/04 16:40
 */
public class OrderTotalDataDTO {

    /**
     * 总订单数
     */
    private Integer totalNumber;

    /**
     * 总交易额
     */

    private BigDecimal totalMoney;

    public OrderTotalDataDTO() {
    }

    public OrderTotalDataDTO(Integer totalNumber, BigDecimal totalMoney) {
        this.totalNumber = totalNumber;
        this.totalMoney = totalMoney;
    }

    @Override
    public String toString() {
        return "OrderTotalDataDTO{" +
                "totalNumber=" + totalNumber +
                ", totalMoney=" + totalMoney +
                '}';
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }
}
