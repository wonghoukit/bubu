package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.Users;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author kit
 * @since 2022-01-28
 */
public interface UsersService extends IService<Users> {

    Users findUserByToken(String token);

    Users findUserByOpenId(String openId);

    Users auth(String openId, String sessionKey);
}
