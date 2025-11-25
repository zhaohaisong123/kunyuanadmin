package com.ruoyi.kunyuan.service;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.exception.base.BaseException;
import com.ruoyi.common.exception.user.CaptchaException;
import com.ruoyi.common.exception.user.CaptchaExpireException;
import com.ruoyi.common.exception.user.UserPasswordNotMatchException;
import com.ruoyi.common.utils.*;
import com.ruoyi.common.utils.ip.IpUtils;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.framework.security.context.AuthenticationContextHolder;
import com.ruoyi.framework.security.wxone.WxOneAuthenticationToken;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.kunyuan.WxMpUtil;
import com.ruoyi.system.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录校验方法
 *
 * @author ruoyi
 */
@Component
public class WxLoginService
{
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ISysUserService userService;






    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId)
    {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        sysUser.setLoginDate(DateUtils.getNowDate());
        userService.updateUserProfile(sysUser);
    }


    /**
     * 根据用户名（手机号）获取登录token
     * @param username
     * @return
     */
    public String getToken(String username){
        //TODO 1、用户验证
        Authentication authentication = null;
        try {
            //TODO 1.1该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            WxOneAuthenticationToken authenticationToken = new WxOneAuthenticationToken(username);
            AuthenticationContextHolder.setContext(authenticationToken);
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new BaseException(e.getMessage());
            }
        }

        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        recordLoginInfo(loginUser.getUser().getUserId());
        // 生成token
        return tokenService.createToken(loginUser);
    }

    /**
     * 微信一键登录验证
     *
     * @param code 微信code
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    public String wxOneLogin(String code) {

        String accessToken = WxMpUtil.getAccessToken();
        if (StrUtil.isEmpty(accessToken)) {
            log.error("微信一键登录：access token获取失败");
            throw new ServiceException("access token获取失败");
//            return AjaxResult.error("access token获取失败");
        }
        String url = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=" + accessToken;
       log.error(url+"--------"+code);
        HashMap<String, String> params = MapUtil.of("code", code);
        Map<String, Object> res = JSONUtil.parseObj(HttpUtil.post(url, JSONUtil.toJsonStr(params)));

        if (MapUtil.getInt(res, "errcode") == 0) {
            Map map = MapUtil.get(res, "phone_info", Map.class);
            String phoneNumber = map.get("phoneNumber").toString();

            isUserExist(phoneNumber);
            return getToken(phoneNumber);
        }else {
            log.error("微信获取手机号失败！code：{}",MapUtil.getInt(res, "errcode"));
            redisCache.setCacheObject("wx-mp-access-token",null);
            throw new ServiceException("微信获取手机号失败！code：{}",MapUtil.getInt(res, "errcode"));
//            wxOneLogin(code);
        }



//        throw new ServiceException("access token获取失败");
    }

    /**
     * @Description: 根据电话判断用户是否存在，不存在则创建用户
     * @param phoneNumber:
     * @return void
     * @author yueyan
     * @date 2022/5/16 21:20
     */

    private void isUserExist(String phoneNumber) {
        SysUser sysUser = userService.selectUserByUserName(phoneNumber);
        if (StringUtils.isNull(sysUser)){
            //todo 没有账号直接注册
            sysUser = new SysUser();
            // TODO: 2022/4/4
            sysUser.setUserName(phoneNumber);
            sysUser.setNickName("用户" + phoneNumber.substring(phoneNumber.length() - 4));
            sysUser.setPhonenumber(phoneNumber);
            String passwprd = phoneNumber.substring(phoneNumber.length() - 6);
            sysUser.setPassword(SecurityUtils.encryptPassword(passwprd));

            sysUser.setDeptId( 101l);

//            sysUser.setReferralCode("888888");
            int rows = userService.insertUser(sysUser);
            //生成邀请码
            if (rows > 0) {
                SysUser sysUser1 = userService.selectUserByUserName(phoneNumber);
//                sysUser1.setInvitationCode(ShareCodeUtil.toSerialCode(sysUser1.getUserId()));
                rows = userService.updateUser(sysUser1);
                if (rows <= 0) {
//                    userService.deleteUserById_yes(sysUser.getUserId());
                }

            }


        }
    }
}
