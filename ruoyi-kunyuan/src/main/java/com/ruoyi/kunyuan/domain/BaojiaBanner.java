package com.ruoyi.kunyuan.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 轮播图对象 baojia_banner
 * 
 * @author ruoyi
 * @date 2025-11-24
 */
public class BaojiaBanner extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long bannerId;

    /** 名称 */
    @Excel(name = "名称")
    private String bannerName;

    /** 图片 */
    @Excel(name = "图片")
    private String bannerImg;

    /** 跳转地址 */
    @Excel(name = "跳转地址")
    private String linkUrl;

    /** 类型 (0=主页, 1=主页广告页, 2=个人中心广告页) */
    @Excel(name = "类型 (0=主页, 1=主页广告页, 2=个人中心广告页)")
    private String bannerType;

    /** 状态 (0=Normal, 1=Disabled) */
    @Excel(name = "状态 (0=Normal, 1=Disabled)")
    private String status;

    public void setBannerId(Long bannerId) 
    {
        this.bannerId = bannerId;
    }

    public Long getBannerId() 
    {
        return bannerId;
    }

    public void setBannerName(String bannerName) 
    {
        this.bannerName = bannerName;
    }

    public String getBannerName() 
    {
        return bannerName;
    }

    public void setBannerImg(String bannerImg) 
    {
        this.bannerImg = bannerImg;
    }

    public String getBannerImg() 
    {
        return bannerImg;
    }

    public void setLinkUrl(String linkUrl) 
    {
        this.linkUrl = linkUrl;
    }

    public String getLinkUrl() 
    {
        return linkUrl;
    }

    public void setBannerType(String bannerType) 
    {
        this.bannerType = bannerType;
    }

    public String getBannerType() 
    {
        return bannerType;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("bannerId", getBannerId())
            .append("bannerName", getBannerName())
            .append("bannerImg", getBannerImg())
            .append("linkUrl", getLinkUrl())
            .append("bannerType", getBannerType())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
