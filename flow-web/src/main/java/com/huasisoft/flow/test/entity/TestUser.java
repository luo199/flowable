package com.huasisoft.flow.test.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * <p>
 * 
 * </p>
 *
 * @author flq
 * @since 2020-07-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("FLOW_TEST_USER")
@ToString
public class TestUser extends Model<TestUser> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户主键
     */
    @TableId("USER_ID")
    private String userId;

    /**
     * 用户姓名
     */
    @TableField("USER_NAME")
    private String userName;

    /**
     * 年龄
     */
    @TableField("USER_AGE")
    private Integer userAge;

    /**
     * 性别
     */
    @TableField("USER_SEX_NAME")
    private String userSexName;

    /**
     * 性别代码
     */
    @TableField("USER_SEX_CODE")
    private Integer userSexCode;


    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

}
