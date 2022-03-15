package com.tencent.wxcloudrun.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.config.WxMaConfiguration;
import com.tencent.wxcloudrun.model.Users;
import com.tencent.wxcloudrun.service.UsersService;
import com.tencent.wxcloudrun.vo.LoginVO;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/wx/user/{appid}")
public class WxMaUserController {

    @Autowired
    private UsersService usersService;

    /**
     * 登陆接口
     */
    @GetMapping("/login")
    public ApiResponse login(@PathVariable String appid, String code) {
        if (StringUtils.isBlank(code)) {
            return ApiResponse.error("empty jscode");
        }

        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        try {
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
            log.info(session.getSessionKey());
            log.info(session.getOpenid());
            Users user = usersService.findUserByOpenId(session.getOpenid());
            if (ObjectUtil.isNull(user)) {
                user = usersService.auth(session.getOpenid(), session.getSessionKey());
            }

            LoginVO loginVO = new LoginVO();
            loginVO.setUid(user.getId());
            loginVO.setToken(user.getToken());
            return ApiResponse.ok(loginVO);
        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
            return ApiResponse.error(e.toString());
        }
    }

    /**
     * 获取用户信息接口
     */
    @GetMapping("/info")
    public ApiResponse info(@PathVariable String appid, String sessionKey,
                       String signature, String rawData, String encryptedData, String iv) {
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return ApiResponse.error("user check failed");
        }

        // 解密用户信息
        WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);

        return ApiResponse.ok(userInfo);
    }
}
