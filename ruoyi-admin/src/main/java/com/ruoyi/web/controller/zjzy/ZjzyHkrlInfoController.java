package com.ruoyi.web.controller.zjzy;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.purchase.sale.domain.PurchaseSaleOrderInfo;
import com.ruoyi.purchase.sale.service.IPurchaseSaleOrderInfoService;
import com.ruoyi.zjzy.domain.ZjzyHkrlInfo;
import com.ruoyi.zjzy.service.IZjzyHkrlInfoService;
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
 * 回款认领Controller
 * 
 * @author changgong0513
 * @date 2022-12-04
 */
@RestController
@RequestMapping("/zjzy/hkrl")
public class ZjzyHkrlInfoController extends BaseController
{
    @Autowired
    private IZjzyHkrlInfoService zjzyHkrlInfoService;

    @Autowired
    private IPurchaseSaleOrderInfoService purchaseSaleOrderInfoService;

    /**
     * 查询回款认领列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ZjzyHkrlInfo zjzyHkrlInfo)
    {
        startPage();
        List<ZjzyHkrlInfo> list = zjzyHkrlInfoService.selectZjzyHkrlInfoList(zjzyHkrlInfo);
        return getDataTable(list);
    }

    /**
     * 查询回款认领列表
     */
    @GetMapping("/htbh/list")
    public TableDataInfo listHtbh(PurchaseSaleOrderInfo purchaseSaleOrderInfo) {
        // 采购合同
        List<PurchaseSaleOrderInfo> listPurchase = purchaseSaleOrderInfoService
                .selectPurchaseOrderInfoUnionList(purchaseSaleOrderInfo);

        // 销售合同
        List<PurchaseSaleOrderInfo> listSale = purchaseSaleOrderInfoService
                .selectSaleOrderInfoUnionList(purchaseSaleOrderInfo);

        List<PurchaseSaleOrderInfo> list = new ArrayList<>();
        list.addAll(listPurchase);
        list.addAll(listSale);

        return getDataTable(list);
    }

    /**
     * 导出回款认领列表
     */
    // @PreAuthorize("@ss.hasPermi('system:hkrl:export')")
    @Log(title = "回款认领", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ZjzyHkrlInfo zjzyHkrlInfo)
    {
        List<ZjzyHkrlInfo> list = zjzyHkrlInfoService.selectZjzyHkrlInfoList(zjzyHkrlInfo);
        ExcelUtil<ZjzyHkrlInfo> util = new ExcelUtil<ZjzyHkrlInfo>(ZjzyHkrlInfo.class);
        util.exportExcel(response, list, "回款认领数据");
    }

    /**
     * 获取回款认领详细信息
     */
    // @PreAuthorize("@ss.hasPermi('system:hkrl:query')")
    @GetMapping(value = "/{hkrlId}")
    public AjaxResult getInfo(@PathVariable("hkrlId") Long hkrlId)
    {
        return AjaxResult.success(zjzyHkrlInfoService.selectZjzyHkrlInfoByHkrlId(hkrlId));
    }

    /**
     * 新增回款认领
     */
    @Log(title = "回款认领", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ZjzyHkrlInfo zjzyHkrlInfo) {
        zjzyHkrlInfo.setBizVersion(1L);
        zjzyHkrlInfo.setCreateTime(DateUtils.getNowDate());
        zjzyHkrlInfo.setUpdateTime(DateUtils.getNowDate());
        zjzyHkrlInfo.setCreateBy(SecurityUtils.getUsername());
        zjzyHkrlInfo.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(zjzyHkrlInfoService.insertZjzyHkrlInfo(zjzyHkrlInfo));
    }

    /**
     * 修改回款认领
     */
    // @PreAuthorize("@ss.hasPermi('system:hkrl:edit')")
    @Log(title = "回款认领", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ZjzyHkrlInfo zjzyHkrlInfo)
    {
        return toAjax(zjzyHkrlInfoService.updateZjzyHkrlInfo(zjzyHkrlInfo));
    }

    /**
     * 删除回款认领
     */
    // @PreAuthorize("@ss.hasPermi('system:hkrl:remove')")
    @Log(title = "回款认领", businessType = BusinessType.DELETE)
	@DeleteMapping("/{hkrlIds}")
    public AjaxResult remove(@PathVariable Long[] hkrlIds)
    {
        return toAjax(zjzyHkrlInfoService.deleteZjzyHkrlInfoByHkrlIds(hkrlIds));
    }

    /**
     * 根据部门编号，取得该部门的回款总额
     *
     * @return
     */
    @GetMapping(value = "/bmbh/total")
    public AjaxResult getHkrlTotalByBmbh() {
        return AjaxResult.success(zjzyHkrlInfoService.getHkrlTotalByBmbh(this.getDeptId()));
    }
}
