package com.ruoyi.report.masterdata.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
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
import com.ruoyi.report.masterdata.domain.MasterDataWarehouseBaseInfo;
import com.ruoyi.report.masterdata.service.IMasterDataWarehouseBaseInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 仓库管理Controller
 *
 * @author changgong0513
 * @date 2022-09-17
 */
@RestController
@RequestMapping("/md/warehouse")
public class MasterDataWarehouseBaseInfoController extends BaseController
{
    @Autowired
    private IMasterDataWarehouseBaseInfoService masterDataWarehouseBaseInfoService;

    /**
     * 查询仓库管理列表
     */
    // @PreAuthorize("@ss.hasPermi('report-masterdata:warehouse:list')")
    @GetMapping("/list")
    public TableDataInfo list(MasterDataWarehouseBaseInfo masterDataWarehouseBaseInfo)
    {
        startPage();
        List<MasterDataWarehouseBaseInfo> list = masterDataWarehouseBaseInfoService.selectMasterDataWarehouseBaseInfoList(masterDataWarehouseBaseInfo);
        return getDataTable(list);
    }

    /**
     * 导出仓库管理列表
     */
    // @PreAuthorize("@ss.hasPermi('report-masterdata:warehouse:export')")
    @Log(title = "仓库管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MasterDataWarehouseBaseInfo masterDataWarehouseBaseInfo)
    {
        List<MasterDataWarehouseBaseInfo> list = masterDataWarehouseBaseInfoService.selectMasterDataWarehouseBaseInfoList(masterDataWarehouseBaseInfo);
        ExcelUtil<MasterDataWarehouseBaseInfo> util = new ExcelUtil<MasterDataWarehouseBaseInfo>(MasterDataWarehouseBaseInfo.class);
        util.exportExcel(response, list, "仓库管理数据");
    }

    /**
     * 获取仓库管理详细信息
     */
    // @PreAuthorize("@ss.hasPermi('report-masterdata:warehouse:query')")
    @GetMapping(value = "/{warehouseId}")
    public AjaxResult getInfo(@PathVariable("warehouseId") String warehouseId)
    {
        return AjaxResult.success(masterDataWarehouseBaseInfoService.selectMasterDataWarehouseBaseInfoByWarehouseId(warehouseId));
    }

    /**
     * 新增仓库管理
     */
    // @PreAuthorize("@ss.hasPermi('report-masterdata:warehouse:add')")
    @Log(title = "仓库管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MasterDataWarehouseBaseInfo masterDataWarehouseBaseInfo)
    {
        masterDataWarehouseBaseInfo.setBizVersion(1L);
        masterDataWarehouseBaseInfo.setCreateTime(DateUtils.getNowDate());
        masterDataWarehouseBaseInfo.setUpdateTime(DateUtils.getNowDate());
        masterDataWarehouseBaseInfo.setCreateBy(SecurityUtils.getUsername());
        masterDataWarehouseBaseInfo.setUpdateBy(SecurityUtils.getUsername());
        System.out.println("------新增仓库管理：" + masterDataWarehouseBaseInfo);
        return toAjax(masterDataWarehouseBaseInfoService.insertMasterDataWarehouseBaseInfo(masterDataWarehouseBaseInfo));
    }

    /**
     * 修改仓库管理
     */
    // @PreAuthorize("@ss.hasPermi('report-masterdata:warehouse:edit')")
    @Log(title = "仓库管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MasterDataWarehouseBaseInfo masterDataWarehouseBaseInfo)
    {
        return toAjax(masterDataWarehouseBaseInfoService.updateMasterDataWarehouseBaseInfo(masterDataWarehouseBaseInfo));
    }

    /**
     * 删除仓库管理
     */
    // @PreAuthorize("@ss.hasPermi('report-masterdata:warehouse:remove')")
    @Log(title = "仓库管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{warehouseIds}")
    public AjaxResult remove(@PathVariable String[] warehouseIds)
    {
        return toAjax(masterDataWarehouseBaseInfoService.deleteMasterDataWarehouseBaseInfoByWarehouseIds(warehouseIds));
    }
}
