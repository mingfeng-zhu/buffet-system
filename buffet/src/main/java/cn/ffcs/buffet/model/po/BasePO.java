package cn.ffcs.buffet.model.po;


import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Description: 共性抽象类
 * @Author: mingfeng.zhu@ffcs.cn
 * @Date: 2020/7/22 21:48
 */
public class BasePO implements Serializable {

    private static final long serialVersionUID = 6712882226985391395L;
    /**
     * 创建时间
     */
    private Timestamp createTime;
    /**
     * 更新时间
     */
    private Timestamp updateTime;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", name = "createBy", required = true, example = "admin")
    @NotEmpty(message = "创建人不能为空")
    protected String createBy;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人", name = "updateBy", required = true, example = "admin")
    @NotEmpty(message = "更新人不能为空")
    protected String updateBy;

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }


    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Override
    public String toString() {
        return "BasePO{" +
                "createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", createBy=" + createBy +
                ", updateBy=" + updateBy +
                '}';
    }
}