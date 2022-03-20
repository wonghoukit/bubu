package com.tencent.wxcloudrun.controller;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.tencent.wxcloudrun.bo.AddPunchBO;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.model.PunchRecord;
import com.tencent.wxcloudrun.service.PunchRecordService;
import com.tencent.wxcloudrun.vo.UserPunchRecordListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
        AddPunchBO addPunchBO = new AddPunchBO();
        addPunchBO.setUid(Integer.valueOf(uid));
        addPunchBO.setPunchTime(LocalDateTime.now());
        addPunchBO.setPunchDate(LocalDate.now());

        // 查询是否已经打卡
        PunchRecord record = punchRecordService.checkPunch(addPunchBO);
        if (ObjectUtil.isNotNull(record)) {
            return ApiResponse.ok();
        }

        // 记录打卡
        return ApiResponse.ok(punchRecordService.punch(addPunchBO));
    }

    @RequestMapping("/recordList")
    public ApiResponse recordList() {
        String uid = request.getHeader("x-app-uid");

        UserPunchRecordListVO listVO = new UserPunchRecordListVO();
        List<PunchRecord> recordList = punchRecordService.monthPunchListByUid(uid);
        if (CollectionUtil.isNotEmpty(recordList)) {
            List<Map<String, UserPunchRecordListVO.PunchRecordVO>> list = new ArrayList<>();
            recordList.forEach(r -> {
                Map<String, UserPunchRecordListVO.PunchRecordVO> map = new HashMap<>();
                UserPunchRecordListVO.PunchRecordVO punchRecordVO = new UserPunchRecordListVO.PunchRecordVO();
                punchRecordVO.setPunchTime(r.getPunchTime());
                punchRecordVO.setPunchStatus(r.getPunchStatus());
                map.put(r.getPunchDate().toString(), punchRecordVO);

                list.add(map);
            });
            listVO.setList(list);
        }

        return ApiResponse.ok(listVO);
    }
}