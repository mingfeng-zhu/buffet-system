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

    /**
     * 今日交易额
     */

    private Integer currentDayNumber;

    /**
     * 今日交易额
     */

    private BigDecimal currentDayMoney;

    public OrderTotalDataDTO() {
    }

    public OrderTotalDataDTO(Integer totalNumber, BigDecimal totalMoney, Integer currentDayNumber, BigDecimal currentDayMoney) {
        this.totalNumber = totalNumber;
        this.totalMoney = totalMoney;
        this.currentDayNumber = currentDayNumber;
        this.currentDayMoney = currentDayMoney;
    }

    @Override
    public String toString() {
        return "OrderTotalDataDTO{" +
                "totalNumber=" + totalNumber +
                ", totalMoney=" + totalMoney +
                ", currentDayNumber=" + currentDayNumber +
                ", currentDayMoney=" + currentDayMoney +
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

    public Integer getCurrentDayNumber() {
        return currentDayNumber;
    }

    public void setCurrentDayNumber(Integer currentDayNumber) {
        this.currentDayNumber = currentDayNumber;
    }

    public BigDecimal getCurrentDayMoney() {
        return currentDayMoney;
    }

    public void setCurrentDayMoney(BigDecimal currentDayMoney) {
        this.currentDayMoney = currentDayMoney;
    }
}
