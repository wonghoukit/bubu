package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.bo.AddPunchBO;
import com.tencent.wxcloudrun.model.PunchRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kit
 * @since 2022-03-16
 */
public interface PunchRecordService extends IService<PunchRecord> {

    PunchRecord punch(AddPunchBO punchBO);

    PunchRecord checkPunch(AddPunchBO punchBO);
}
