package com.ruoyi.kunyuan;

import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class WxMpUtil {
    protected final static Logger log = LoggerFactory.getLogger(WxMpUtil.class);
    private static RedisCache redisCache;
//
//    public static final String app_id = "wxa3356018b45546cc";
//    public static final String app_secret = "544f60c467e9d5d3f0399917b516a905";

    public static final String app_id = "wxba4af576e4d81299";
    public static final String app_secret = "7c3ebbd35371c97a6483a1e003077c2f";
    static {
        redisCache = SpringUtils.getBean(RedisCache.class);
    }

    /**
     * null获取失败
     */
    public static String getAccessToken() {
        String accessToken = redisCache.getCacheObject("wx-mp-access-token");
        if (StringUtils.isEmpty(accessToken)) {
            String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential" +
                    "&appid=" + app_id +
                    "&secret=" + app_secret;
            Map<String, Object> res = JSONUtil.parseObj(HttpUtil.get(url));
            accessToken = MapUtil.getStr(res, "access_token");
            if (StringUtils.isEmpty(accessToken)) {
                log.error("WxMpUtil获取access token：错误码：{}"+MapUtil.getStr(res, "errcode"));
                log.error("WxMpUtil获取access token：错误信息：{}"+MapUtil.getStr(res, "errmsg"));
                return null;
            }
            redisCache.setCacheObject("wx-mp-access-token", accessToken, 7000, TimeUnit.SECONDS);
        }
        return accessToken;
    }

}
