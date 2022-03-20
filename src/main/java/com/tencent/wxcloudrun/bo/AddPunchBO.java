package com.tencent.wxcloudrun.bo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AddPunchBO {

    private Integer uid;
    private LocalDate punchDate;
    private LocalDateTime punchTime;
    private Integer punchStatus = 1;
}
