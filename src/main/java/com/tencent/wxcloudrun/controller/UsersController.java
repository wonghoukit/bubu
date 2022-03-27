package com.tencent.wxcloudrun.controller;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.tencent.wxcloudrun.bo.AddPunchBO;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.constant.PunchStatusEnum;
import com.tencent.wxcloudrun.dto.UserMonthRecordListDTO;
import com.tencent.wxcloudrun.model.PunchRecord;
import com.tencent.wxcloudrun.service.PunchRecordService;
import com.tencent.wxcloudrun.vo.UserPunchRecordListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author kit
 * @since 2022-01-28
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    PunchRecordService punchRecordService;

    @Autowired
    private HttpServletRequest request;

    @PostMapping("/punch")
    public ApiResponse punch() {
        String uid = request.getHeader("x-app-uid");
        LocalDateTime nowTime = LocalDateTime.now();
        LocalDate nowDate = LocalDate.now();

        LocalDateTime overTime = LocalDateTime.of(
                nowTime.getYear(),
                nowTime.getMonth(),
                nowTime.getDayOfMonth(),
                0,
                30);

        AddPunchBO addPunchBO = new AddPunchBO();
        addPunchBO.setUid(Integer.valueOf(uid));
        addPunchBO.setPunchTime(nowTime);
        // 凌晨3点前都算前一天的打卡
        if (nowTime.getHour() < 3) {
            nowDate = nowDate.minusDays(1);
            // 超时打卡
            if (nowTime.isAfter(overTime)) {
                addPunchBO.setPunchStatus(PunchStatusEnum.PUNCH_STATUS_OVERTIME.getValue());
            }
        }
        addPunchBO.setPunchDate(nowDate);

        // 查询是否已经打卡
        PunchRecord record = punchRecordService.checkPunch(addPunchBO);
        if (ObjectUtil.isNotNull(record)) {
            return ApiResponse.ok();
        }

        // 记录打卡
        return ApiResponse.ok(punchRecordService.punch(addPunchBO));
    }

    @RequestMapping("/recordList")
    public ApiResponse recordList(@RequestBody UserMonthRecordListDTO listDTO) {
        String uid = request.getHeader("x-app-uid");

        UserPunchRecordListVO listVO = new UserPunchRecordListVO();
        LocalDate date = LocalDate.of(Integer.parseInt(listDTO.getYear()), Integer.parseInt(listDTO.getMonth()), 1);
        LocalDate firstDay = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDay = date.with(TemporalAdjusters.lastDayOfMonth());
        List<PunchRecord> recordList = punchRecordService.getUserPunchListByDateRange(uid, firstDay, lastDay);
        if (CollectionUtil.isNotEmpty(recordList)) {
            Map<String, UserPunchRecordListVO.PunchRecordVO> map = new HashMap<>();
            recordList.forEach(r -> {
                UserPunchRecordListVO.PunchRecordVO punchRecordVO = new UserPunchRecordListVO.PunchRecordVO();
                punchRecordVO.setPunchTime(r.getPunchTime());
                punchRecordVO.setPunchStatus(r.getPunchStatus());
                map.put(r.getPunchDate().toString(), punchRecordVO);
            });
            listVO.setMap(map);
        }

        return ApiResponse.ok(listVO);
    }
}