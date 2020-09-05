package cn.ffcs.buffet.model.po;

import io.swagger.annotations.ApiModel;
/**
 * @Description: 地址类
 * @Author: mingfeng.zhu
 * @Date: 2020/9/13 19:47
 */
@ApiModel
public class AddressPO extends BasePO {
    /**
     * 地址id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 收货人姓名
     */
    private String receiverName;

    /**
     * 收货人电话号码
     */
    private String receiverPhone;

    /**
     * 地址
     */
    private String address;

    /**
     * 具体地址 如**街道**小区**幢**号
     */
    private String houseNumber;

    /**
     * 地址编号
     */
    private String areaCode;

    /**
     * 地址删除标记
     */
    private Byte addressDelete;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public Byte getAddressDelete() {
        return addressDelete;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public void setAddressDelete(Byte addressDelete) {
        this.addressDelete = addressDelete;
    }

    @Override
    public String toString() {
        return "AddressPO{" +
                "id=" + id +
                ", userId=" + userId +
                ", receiverName='" + receiverName + '\'' +
                ", receiverPhone='" + receiverPhone + '\'' +
                ", address='" + address + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", addressDelete=" + addressDelete +
                ", createBy='" + createBy + '\'' +
                ", updateBy='" + updateBy + '\'' +
                '}';
    }
}