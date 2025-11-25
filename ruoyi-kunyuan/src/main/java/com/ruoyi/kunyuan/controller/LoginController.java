package com.ruoyi.kunyuan.controller;

import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginBody;
import com.ruoyi.common.core.domain.model.RegisterBody;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.user.CaptchaException;
import com.ruoyi.common.exception.user.CaptchaExpireException;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sign.Md5Utils;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.framework.web.service.SysLoginService;
import com.ruoyi.kunyuan.service.WxLoginService;
import com.ruoyi.system.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;


/**
 * @Author: zhs
 * @Description:
 * @Date: Create in 2023/4/3 18:48
 */
@RestController
@RequestMapping("/kunyuan")
@CrossOrigin
public class LoginController extends BaseController {
    protected final static Logger log = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private SysLoginService SysloginService;
    @Autowired
    private ISysUserService userService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private WxLoginService loginService;


    /**
     * 微信一键登录
     * @param code
     * @return
     */

    @GetMapping("/wxOneLogin/{code}")
    public AjaxResult wxOneLogin(@PathVariable("code")  String code){
        log.error("```````````````````````````"+code);
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token =loginService.wxOneLogin(code);
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }
    /**
     * 新增用户
     * 同步信息至客户信息
     */
//    @PostMapping("/register")
//    public AjaxResult adduser(@RequestBody RegisterBody regis) {
//        // 验证码校验
//        validateCaptcha(regis.getUsername(), regis.getCode(), regis.getUuid());
//        SysUser user = new SysUser();
//        user.setUserName(regis.getUsername());
//        user.setPhonenumber(regis.getUsername());
//        user.setPassword(SecurityUtils.encryptPassword(regis.getPassword()));
//        Long dept_id = regis.getDept_id();
//        user.setDeptId(dept_id == null ? 101l : dept_id);
//        String referral_code = regis.getReferral_code();
//        //判断推荐码是否存在
//        if (!referral_code.equals("")) {
//            SysUser user1 = userService.selectUserByinvitationCode(referral_code);
//            if (StringUtils.isNotNull(user1)) {
//                user.setReferralCode(referral_code);
//            } else {
//                return AjaxResult.error("推荐码不存在");
//            }
//
//        } else {
//            user.setReferralCode("888888");
//        }
//
//        String phoneNumber = regis.getUsername();
//        user.setNickName("用户" + phoneNumber.substring(phoneNumber.length() - 4));
//
//        //判断用户是否存在
//        if (StringUtils.isNotEmpty(user.getPhonenumber()) && !userService.checkPhoneUnique(user))
//            return error("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
//        int rows = userService.insertUser(user);
//        //生成邀请码
//        if (rows > 0) {
//            SysUser sysUser = userService.selectUserByUserName(user.getUserName());
//            sysUser.setInvitationCode(ShareCodeUtil.toSerialCode(sysUser.getUserId()));
//            rows = userService.updateUser(sysUser);
//            if (rows <= 0) {
//                userService.deleteUserById_yes(sysUser.getUserId());
//            }
//
//        }
//
//
//        return toAjax(rows);
//    }

    /**
     * 重置名
     * 同步信息至客户信息
     */
//    @PostMapping("/forgetpassword")
//    public AjaxResult forgetpassword(@RequestBody RegisterBody regis) {
//        // 验证码校验
//        validateCaptcha(regis.getUsername(), regis.getCode(), regis.getUuid());
//
//        SysUser sysUser1 = userService.selectUserByUserName(regis.getUsername());
//        if (StringUtils.isNotNull(sysUser1)) {
//            sysUser1.setPassword(SecurityUtils.encryptPassword(regis.getPassword()));
//            int rows = userService.updateUser(sysUser1);
//            if (rows > 0) {
//                return toAjax(rows);
//            } else {
//                return AjaxResult.error("密码重置失败，请联系管理员");
//            }
//        } else {
//            return AjaxResult.error("用户不存在");
//        }
//    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public void validateCaptcha1(String username, String code, String uuid) {
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null) {
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha)) {
            throw new CaptchaException();
        }
    }



}
