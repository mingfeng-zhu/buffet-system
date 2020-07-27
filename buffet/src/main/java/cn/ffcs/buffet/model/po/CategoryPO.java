package cn.ffcs.buffet.model.po;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @Description: 类别类
 * @Author: mingfeng.zhu@ffcs.cn
 * @Date: 2020/7/22 21:50
 */
@ApiModel
public class CategoryPO extends BasePO implements Serializable {

    private static final long serialVersionUID = 8074348819922185915L;
    /**
     * 类别id
     */
    private Integer id;

    /**
     * 类别名称
     */
    @ApiModelProperty(value = "类别名称", name = "name", required = true, example = "汉堡")
    @NotEmpty(message = "类别名不能为null且不为空")
    @Size(min = 1, max = 64, message = "类别名长度需在1至64个字符")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CategoryPO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createBy=" + createBy +
                ", updateBy=" + updateBy +
                '}';
    }
}