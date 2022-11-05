package com.ruoyi.web.controller.purchasesale;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.purchase.sale.domain.PurchaseReceiptInfo;
import com.ruoyi.purchase.sale.service.IPurchaseReceiptInfoService;
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
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 收货管理Controller
 * 
 * @author changgong0513
 * @date 2022-11-05
 */
@RestController
@RequestMapping("/receipt/mgr")
public class PurchaseReceiptInfoController extends BaseController
{
    @Autowired
    private IPurchaseReceiptInfoService purchaseReceiptInfoService;

    /**
     * 查询收货管理列表
     */
    @GetMapping("/list")
    public TableDataInfo list(PurchaseReceiptInfo purchaseReceiptInfo) {

        startPage();
        List<PurchaseReceiptInfo> list = purchaseReceiptInfoService.selectPurchaseReceiptInfoList(purchaseReceiptInfo);
        return getDataTable(list);
    }

    /**
     * 导出收货管理列表
     */
    @Log(title = "收货管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PurchaseReceiptInfo purchaseReceiptInfo)
    {
        List<PurchaseReceiptInfo> list = purchaseReceiptInfoService.selectPurchaseReceiptInfoList(purchaseReceiptInfo);
        ExcelUtil<PurchaseReceiptInfo> util = new ExcelUtil<PurchaseReceiptInfo>(PurchaseReceiptInfo.class);
        util.exportExcel(response, list, "收货管理数据");
    }

    /**
     * 获取收货管理详细信息
     */
    @GetMapping(value = "/{receiptId}")
    public AjaxResult getInfo(@PathVariable("receiptId") String receiptId) {
        return AjaxResult.success(purchaseReceiptInfoService.selectPurchaseReceiptInfoByReceiptId(receiptId));
    }

    /**
     * 新增收货管理
     */
    @Log(title = "收货管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PurchaseReceiptInfo purchaseReceiptInfo) {

        purchaseReceiptInfo.setBizVersion(1L);
        purchaseReceiptInfo.setCreateTime(DateUtils.getNowDate());
        purchaseReceiptInfo.setUpdateTime(DateUtils.getNowDate());
        purchaseReceiptInfo.setCreateBy(SecurityUtils.getUsername());
        purchaseReceiptInfo.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(purchaseReceiptInfoService.insertPurchaseReceiptInfo(purchaseReceiptInfo));
    }

    /**
     * 修改收货管理
     */
    @Log(title = "收货管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PurchaseReceiptInfo purchaseReceiptInfo) {

        purchaseReceiptInfo.setBizVersion(1L);
        purchaseReceiptInfo.setCreateTime(DateUtils.getNowDate());
        purchaseReceiptInfo.setUpdateTime(DateUtils.getNowDate());
        purchaseReceiptInfo.setCreateBy(SecurityUtils.getUsername());
        purchaseReceiptInfo.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(purchaseReceiptInfoService.updatePurchaseReceiptInfo(purchaseReceiptInfo));
    }

    /**
     * 删除收货管理
     */
    @PreAuthorize("@ss.hasPermi('purchasesale:purchasesale:remove')")
    @Log(title = "收货管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{receiptIds}")
    public AjaxResult remove(@PathVariable String[] receiptIds)
    {
        return toAjax(purchaseReceiptInfoService.deletePurchaseReceiptInfoByReceiptIds(receiptIds));
    }
}
