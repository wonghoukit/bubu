package com.tencent.wxcloudrun.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tencent.wxcloudrun.model.Users;
import com.tencent.wxcloudrun.dao.UsersMapper;
import com.tencent.wxcloudrun.service.UsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tencent.wxcloudrun.util.ToolsHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author kit
 * @since 2022-01-28
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

    @Override
    public Users findUserByToken(String token) {
        Wrapper<Users> wrapper = Wrappers.<Users>lambdaQuery()
                .eq(Users::getToken, token)
                .last("limit 1");
        return this.getOne(wrapper);
    }

    @Override
    public Users findUserByOpenId(String openId) {
        Wrapper<Users> wrapper = Wrappers.<Users>lambdaQuery()
                .eq(Users::getMinaOpenid, openId)
                .last("limit 1");
        return this.getOne(wrapper);
    }

    @Override
    public Users auth(String openId, String sessionKey) {
        String token = ToolsHelper.createRandomStr(30);

        Users users = new Users();
        users.setToken(token);
        users.setMinaOpenid(openId);
        users.setSessionKey(sessionKey);
        users.setStatus(1);
        users.setCreatedAt(LocalDateTime.now());
        users.setUpdatedAt(LocalDateTime.now());

        this.save(users);
        return users;
    }
}
