package com.huasisoft.flow.test.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author lb
 * @since 2020-08-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("FLOW_TEST_ADD")
@ApiModel(value="TestAdd对象", description="")
public class TestAdd extends Model<TestAdd> {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

    @TableField("NAME")
    private String name;

    @TableField("SEX")
    private Integer sex;

    @TableField("BIRTHDAY")
    private Date birthday;

    @TableField("ADDRESS")
    private String address;


    public TestAdd(String id, String name, Integer sex, Date birthday, String address) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.address = address;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
