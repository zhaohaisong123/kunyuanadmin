package com.ruoyi.kunyuan.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.kunyuan.mapper.BaojiaBannerMapper;
import com.ruoyi.kunyuan.domain.BaojiaBanner;
import com.ruoyi.kunyuan.service.IBaojiaBannerService;

/**
 * 轮播图Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-11-24
 */
@Service
public class BaojiaBannerServiceImpl implements IBaojiaBannerService 
{
    @Autowired
    private BaojiaBannerMapper baojiaBannerMapper;

    /**
     * 查询轮播图
     * 
     * @param bannerId 轮播图主键
     * @return 轮播图
     */
    @Override
    public BaojiaBanner selectBaojiaBannerByBannerId(Long bannerId)
    {
        return baojiaBannerMapper.selectBaojiaBannerByBannerId(bannerId);
    }

    /**
     * 查询轮播图列表
     * 
     * @param baojiaBanner 轮播图
     * @return 轮播图
     */
    @Override
    public List<BaojiaBanner> selectBaojiaBannerList(BaojiaBanner baojiaBanner)
    {
        return baojiaBannerMapper.selectBaojiaBannerList(baojiaBanner);
    }

    /**
     * 新增轮播图
     * 
     * @param baojiaBanner 轮播图
     * @return 结果
     */
    @Override
    public int insertBaojiaBanner(BaojiaBanner baojiaBanner)
    {
        baojiaBanner.setCreateTime(DateUtils.getNowDate());
        return baojiaBannerMapper.insertBaojiaBanner(baojiaBanner);
    }

    /**
     * 修改轮播图
     * 
     * @param baojiaBanner 轮播图
     * @return 结果
     */
    @Override
    public int updateBaojiaBanner(BaojiaBanner baojiaBanner)
    {
        baojiaBanner.setUpdateTime(DateUtils.getNowDate());
        return baojiaBannerMapper.updateBaojiaBanner(baojiaBanner);
    }

    /**
     * 批量删除轮播图
     * 
     * @param bannerIds 需要删除的轮播图主键
     * @return 结果
     */
    @Override
    public int deleteBaojiaBannerByBannerIds(Long[] bannerIds)
    {
        return baojiaBannerMapper.deleteBaojiaBannerByBannerIds(bannerIds);
    }

    /**
     * 删除轮播图信息
     * 
     * @param bannerId 轮播图主键
     * @return 结果
     */
    @Override
    public int deleteBaojiaBannerByBannerId(Long bannerId)
    {
        return baojiaBannerMapper.deleteBaojiaBannerByBannerId(bannerId);
    }
}
