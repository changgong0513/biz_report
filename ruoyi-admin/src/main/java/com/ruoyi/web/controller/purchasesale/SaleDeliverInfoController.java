package com.ruoyi.web.controller.purchasesale;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
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
import com.ruoyi.purchase.sale.domain.SaleDeliverInfo;
import com.ruoyi.purchase.sale.service.ISaleDeliverInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 发货管理Controller
 * 
 * @author chanagong0513
 * @date 2022-11-05
 */
@RestController
@RequestMapping("/deliver/mgr")
public class SaleDeliverInfoController extends BaseController
{
    @Autowired
    private ISaleDeliverInfoService saleDeliverInfoService;

    /**
     * 查询发货管理列表
     */
    // @PreAuthorize("@ss.hasPermi('purchasesale:purchasesale:list')")
    @GetMapping("/list")
    public TableDataInfo list(SaleDeliverInfo saleDeliverInfo)
    {
        startPage();
        List<SaleDeliverInfo> list = saleDeliverInfoService.selectSaleDeliverInfoList(saleDeliverInfo);
        return getDataTable(list);
    }

    /**
     * 导出发货管理列表
     */
    // @PreAuthorize("@ss.hasPermi('purchasesale:purchasesale:export')")
    @Log(title = "发货管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SaleDeliverInfo saleDeliverInfo)
    {
        List<SaleDeliverInfo> list = saleDeliverInfoService.selectSaleDeliverInfoList(saleDeliverInfo);
        ExcelUtil<SaleDeliverInfo> util = new ExcelUtil<SaleDeliverInfo>(SaleDeliverInfo.class);
        util.exportExcel(response, list, "发货管理数据");
    }

    /**
     * 获取发货管理详细信息
     */
    // @PreAuthorize("@ss.hasPermi('purchasesale:purchasesale:query')")
    @GetMapping(value = "/{deliverId}")
    public AjaxResult getInfo(@PathVariable("deliverId") String deliverId)
    {
        return AjaxResult.success(saleDeliverInfoService.selectSaleDeliverInfoByDeliverId(deliverId));
    }

    /**
     * 新增发货管理
     */
    // @PreAuthorize("@ss.hasPermi('purchasesale:purchasesale:add')")
    @Log(title = "发货管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SaleDeliverInfo saleDeliverInfo) {

        SaleDeliverInfo maxSaleDeliverInfo = saleDeliverInfoService.selectMaxDeliverId();
        if (maxSaleDeliverInfo == null) {
            saleDeliverInfo.setDeliverId("ASN00000000");
        } else {
            String maxDeliverId = maxSaleDeliverInfo.getDeliverId();
            String id = maxDeliverId.substring(2, maxDeliverId.length());
            int maxId = Integer.parseInt(id) + 1;
            saleDeliverInfo.setDeliverId("ASN" + StringUtils.padl(maxId, 8));
        }

        saleDeliverInfo.setBizVersion(1L);
        saleDeliverInfo.setCreateTime(DateUtils.getNowDate());
        saleDeliverInfo.setUpdateTime(DateUtils.getNowDate());
        saleDeliverInfo.setCreateBy(SecurityUtils.getUsername());
        saleDeliverInfo.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(saleDeliverInfoService.insertSaleDeliverInfo(saleDeliverInfo));
    }

    /**
     * 修改发货管理
     */
    // @PreAuthorize("@ss.hasPermi('purchasesale:purchasesale:edit')")
    @Log(title = "发货管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SaleDeliverInfo saleDeliverInfo) {
        saleDeliverInfo.setBizVersion(1L);
        saleDeliverInfo.setCreateTime(DateUtils.getNowDate());
        saleDeliverInfo.setUpdateTime(DateUtils.getNowDate());
        saleDeliverInfo.setCreateBy(SecurityUtils.getUsername());
        saleDeliverInfo.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(saleDeliverInfoService.updateSaleDeliverInfo(saleDeliverInfo));
    }

    /**
     * 删除发货管理
     */
    // @PreAuthorize("@ss.hasPermi('purchasesale:purchasesale:remove')")
    @Log(title = "发货管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{deliverIds}")
    public AjaxResult remove(@PathVariable String[] deliverIds)
    {
        return toAjax(saleDeliverInfoService.deleteSaleDeliverInfoByDeliverIds(deliverIds));
    }
}
