package com.ruoyi.kunyuan.service;

import java.util.List;
import com.ruoyi.kunyuan.domain.BaojiaBanner;

/**
 * 轮播图Service接口
 * 
 * @author ruoyi
 * @date 2025-11-24
 */
public interface IBaojiaBannerService 
{
    /**
     * 查询轮播图
     * 
     * @param bannerId 轮播图主键
     * @return 轮播图
     */
    public BaojiaBanner selectBaojiaBannerByBannerId(Long bannerId);

    /**
     * 查询轮播图列表
     * 
     * @param baojiaBanner 轮播图
     * @return 轮播图集合
     */
    public List<BaojiaBanner> selectBaojiaBannerList(BaojiaBanner baojiaBanner);

    /**
     * 新增轮播图
     * 
     * @param baojiaBanner 轮播图
     * @return 结果
     */
    public int insertBaojiaBanner(BaojiaBanner baojiaBanner);

    /**
     * 修改轮播图
     * 
     * @param baojiaBanner 轮播图
     * @return 结果
     */
    public int updateBaojiaBanner(BaojiaBanner baojiaBanner);

    /**
     * 批量删除轮播图
     * 
     * @param bannerIds 需要删除的轮播图主键集合
     * @return 结果
     */
    public int deleteBaojiaBannerByBannerIds(Long[] bannerIds);

    /**
     * 删除轮播图信息
     * 
     * @param bannerId 轮播图主键
     * @return 结果
     */
    public int deleteBaojiaBannerByBannerId(Long bannerId);
}
