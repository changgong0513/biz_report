package com.ruoyi.web.controller.purchasesale;

import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.purchase.sale.domain.PurchaseSaleOrderInfo;
import com.ruoyi.purchase.sale.service.IPurchaseSaleOrderInfoService;
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
import com.ruoyi.common.core.page.TableDataInfo;

import java.util.List;

/**
 * 采购收货销售发货管理Controller
 * 
 * @author changgong0513
 * @date 2022-11-03
 */
@RestController
@RequestMapping("/purchase/mgr")
public class PurchaseSaleOrderInfoController extends BaseController
{
    @Autowired
    private IPurchaseSaleOrderInfoService purchaseSaleOrderInfoService;

    /**
     * 查询采购收货销售发货管理列表
     */
    @PreAuthorize("@ss.hasPermi('purchasesale:purchasesale:list')")
    @GetMapping("/list")
    public TableDataInfo list(PurchaseSaleOrderInfo purchaseSaleOrderInfo) {
        startPage();
        List<PurchaseSaleOrderInfo> list = purchaseSaleOrderInfoService.selectPurchaseSaleOrderInfoUnionList(purchaseSaleOrderInfo);
        return getDataTable(list);
    }

    /**
     * 导出采购收货销售发货管理列表
     */
    @PreAuthorize("@ss.hasPermi('purchasesale:purchasesale:export')")
    @Log(title = "采购收货销售发货管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PurchaseSaleOrderInfo purchaseSaleOrderInfo)
    {
        List<PurchaseSaleOrderInfo> list = purchaseSaleOrderInfoService.selectPurchaseSaleOrderInfoList(purchaseSaleOrderInfo);
        ExcelUtil<PurchaseSaleOrderInfo> util = new ExcelUtil<PurchaseSaleOrderInfo>(PurchaseSaleOrderInfo.class);
        util.exportExcel(response, list, "采购收货销售发货管理数据");
    }

    /**
     * 获取采购收货销售发货管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('purchasesale:purchasesale:query')")
    @GetMapping(value = "/{contractId}")
    public AjaxResult getInfo(@PathVariable("contractId") String contractId) {
        return AjaxResult.success(purchaseSaleOrderInfoService.selectPurchaseSaleOrderInfoByContractId(contractId));
    }

    /**
     * 新增采购收货销售发货管理
     */
    @PreAuthorize("@ss.hasPermi('purchasesale:purchasesale:add')")
    @Log(title = "采购收货销售发货管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PurchaseSaleOrderInfo purchaseSaleOrderInfo) {
        purchaseSaleOrderInfo.setBizVersion(1L);
        purchaseSaleOrderInfo.setCreateTime(DateUtils.getNowDate());
        purchaseSaleOrderInfo.setUpdateTime(DateUtils.getNowDate());
        purchaseSaleOrderInfo.setCreateBy(SecurityUtils.getUsername());
        purchaseSaleOrderInfo.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(purchaseSaleOrderInfoService.insertPurchaseSaleOrderInfo(purchaseSaleOrderInfo));
    }

    /**
     * 修改采购收货销售发货管理
     */
    @PreAuthorize("@ss.hasPermi('purchasesale:purchasesale:edit')")
    @Log(title = "采购收货销售发货管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PurchaseSaleOrderInfo purchaseSaleOrderInfo) {
        purchaseSaleOrderInfo.setBizVersion(1L);
        purchaseSaleOrderInfo.setCreateTime(DateUtils.getNowDate());
        purchaseSaleOrderInfo.setUpdateTime(DateUtils.getNowDate());
        purchaseSaleOrderInfo.setCreateBy(SecurityUtils.getUsername());
        purchaseSaleOrderInfo.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(purchaseSaleOrderInfoService.updatePurchaseSaleOrderInfo(purchaseSaleOrderInfo));
    }

    /**
     * 删除采购收货销售发货管理
     */
    @PreAuthorize("@ss.hasPermi('purchasesale:purchasesale:remove')")
    @Log(title = "采购收货销售发货管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{orderIds}")
    public AjaxResult remove(@PathVariable String[] orderIds)
    {
        return toAjax(purchaseSaleOrderInfoService.deletePurchaseSaleOrderInfoByOrderIds(orderIds));
    }
}
