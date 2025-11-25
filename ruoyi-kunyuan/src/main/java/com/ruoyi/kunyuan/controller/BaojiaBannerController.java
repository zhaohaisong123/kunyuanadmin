package com.ruoyi.kunyuan.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.kunyuan.domain.BaojiaBanner;
import com.ruoyi.kunyuan.service.IBaojiaBannerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 轮播图Controller
 * 
 * @author ruoyi
 * @date 2025-11-24
 */
@RestController
@RequestMapping("/kunyuan/banner")
public class BaojiaBannerController extends BaseController
{
    @Autowired
    private IBaojiaBannerService baojiaBannerService;

    /**
     * 查询轮播图列表
     */
    @PreAuthorize("@ss.hasPermi('kunyuan:banner:list')")
    @GetMapping("/list")
    public TableDataInfo list(BaojiaBanner baojiaBanner)
    {
        startPage();
        List<BaojiaBanner> list = baojiaBannerService.selectBaojiaBannerList(baojiaBanner);
        return getDataTable(list);
    }

    /**
     * 导出轮播图列表
     */
    @PreAuthorize("@ss.hasPermi('kunyuan:banner:export')")
    @Log(title = "轮播图", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BaojiaBanner baojiaBanner)
    {
        List<BaojiaBanner> list = baojiaBannerService.selectBaojiaBannerList(baojiaBanner);
        ExcelUtil<BaojiaBanner> util = new ExcelUtil<BaojiaBanner>(BaojiaBanner.class);
        util.exportExcel(response, list, "轮播图数据");
    }

    /**
     * 获取轮播图详细信息
     */
    @PreAuthorize("@ss.hasPermi('kunyuan:banner:query')")
    @GetMapping(value = "/{bannerId}")
    public AjaxResult getInfo(@PathVariable("bannerId") Long bannerId)
    {
        return success(baojiaBannerService.selectBaojiaBannerByBannerId(bannerId));
    }

    /**
     * 新增轮播图
     */
    @PreAuthorize("@ss.hasPermi('kunyuan:banner:add')")
    @Log(title = "轮播图", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BaojiaBanner baojiaBanner)
    {
        return toAjax(baojiaBannerService.insertBaojiaBanner(baojiaBanner));
    }

    /**
     * 修改轮播图
     */
    @PreAuthorize("@ss.hasPermi('kunyuan:banner:edit')")
    @Log(title = "轮播图", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaojiaBanner baojiaBanner)
    {
        return toAjax(baojiaBannerService.updateBaojiaBanner(baojiaBanner));
    }

    /**
     * 删除轮播图
     */
    @PreAuthorize("@ss.hasPermi('kunyuan:banner:remove')")
    @Log(title = "轮播图", businessType = BusinessType.DELETE)
	@DeleteMapping("/{bannerIds}")
    public AjaxResult remove(@PathVariable Long[] bannerIds)
    {
        return toAjax(baojiaBannerService.deleteBaojiaBannerByBannerIds(bannerIds));
    }
}
