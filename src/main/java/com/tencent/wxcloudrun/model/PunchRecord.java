package com.tencent.wxcloudrun.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author kit
 * @since 2022-03-16
 */
@Data
@TableName("punch_record")
public class PunchRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 打卡日期
     */
    private LocalDate punchDate;

    /**
     * 打卡时间
     */
    private LocalDateTime punchTime;

    /**
     * 打卡状态[1-正常 2-超时]
     */
    private Integer punchStatus;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;


}
