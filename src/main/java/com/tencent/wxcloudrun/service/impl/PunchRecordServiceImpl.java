package com.tencent.wxcloudrun.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tencent.wxcloudrun.bo.AddPunchBO;
import com.tencent.wxcloudrun.dao.PunchRecordMapper;
import com.tencent.wxcloudrun.model.PunchRecord;
import com.tencent.wxcloudrun.service.PunchRecordService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kit
 * @since 2022-03-16
 */
@Service
public class PunchRecordServiceImpl extends ServiceImpl<PunchRecordMapper, PunchRecord> implements PunchRecordService {

    @Override
    public PunchRecord punch(AddPunchBO punchBO) {
        PunchRecord record = new PunchRecord();
        BeanUtil.copyProperties(punchBO, record);
        record.setCreatedAt(LocalDateTime.now());
        record.setUpdatedAt(LocalDateTime.now());
        this.baseMapper.insert(record);
        return record;
    }

    @Override
    public PunchRecord checkPunch(AddPunchBO punchBO) {
        Wrapper<PunchRecord> wrapper = Wrappers.<PunchRecord>lambdaQuery()
                .eq(PunchRecord::getUid, punchBO.getUid())
                .eq(PunchRecord::getPunchDate, punchBO.getPunchDate())
                .last("limit 1");
        return this.baseMapper.selectOne(wrapper);
    }

    @Override
    public List<PunchRecord> getUserPunchListByDateRange(String uid, LocalDate firstDay, LocalDate lastDay) {
        Wrapper<PunchRecord> wrapper = Wrappers.<PunchRecord>lambdaQuery()
                .eq(PunchRecord::getUid, uid)
                .between(PunchRecord::getPunchDate, firstDay, lastDay);
        return this.baseMapper.selectList(wrapper);
    }
}
