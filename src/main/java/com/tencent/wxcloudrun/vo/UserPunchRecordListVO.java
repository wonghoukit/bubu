package com.tencent.wxcloudrun.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class UserPunchRecordListVO {

    private Map<String, PunchRecordVO> map;

    @Data
    public static class PunchRecordVO {
        private LocalDateTime punchTime;
        private Integer punchStatus;
    }
}
