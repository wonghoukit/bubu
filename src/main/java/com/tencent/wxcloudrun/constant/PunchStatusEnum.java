package com.tencent.wxcloudrun.constant;

import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.IntStream;

@Getter
public enum PunchStatusEnum {

    PUNCH_STATUS_NORMAL(1, "正常"),
    PUNCH_STATUS_OVERTIME(2, "超时");

    private final Integer value;
    private final String desc;
    public static final Map<Integer, String> map;

    PunchStatusEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    static {
        PunchStatusEnum[] enums = PunchStatusEnum.values();
        int size = enums.length;
        map = IntStream.range(0, size).collect(LinkedHashMap::new,
                (map, index) -> map.put(enums[index].getValue(), enums[index].getDesc()),
                Map::putAll);
    }
}
