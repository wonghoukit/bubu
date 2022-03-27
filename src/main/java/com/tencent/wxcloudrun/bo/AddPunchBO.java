package com.tencent.wxcloudrun.bo;

import com.tencent.wxcloudrun.constant.PunchStatusEnum;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AddPunchBO {

    private Integer uid;
    private LocalDate punchDate;
    private LocalDateTime punchTime;
    private Integer punchStatus = PunchStatusEnum.PUNCH_STATUS_NORMAL.getValue();
}
