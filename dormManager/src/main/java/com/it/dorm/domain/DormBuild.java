package com.it.dorm.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.it.dorm.common.pojo.BaseEntity;
import lombok.Data;

/**
 * 宿舍楼对象 dorm_build
 */
@Data
@TableName("dorm_build")
public class DormBuild extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 宿舍楼号码
     */
    private Long buildId;

    /**
     * 宿舍楼名称
     */
    private String buildName;

    /**
     * 宿舍楼备注
     */
    private String buildDetail;

}
