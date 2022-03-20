package com.tencent.wxcloudrun.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class UserPunchRecordListVO {

    private List<Map<String, PunchRecordVO>> list;

    @Data
    public static class PunchRecordVO {
        private LocalDateTime punchTime;
        private Integer punchStatus;
    }
}
