package com.ruoyi.report.masterdata.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.report.masterdata.domain.MasterDataMaterialInfo;
import com.ruoyi.report.masterdata.service.IMasterDataMaterialInfoService;
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
 * 主数据管理Controller
 * 
 * @author changgong0513
 * @date 2022-10-28
 */
@RestController
@RequestMapping("/md/material")
public class MasterDataMaterialInfoController extends BaseController
{
    @Autowired
    private IMasterDataMaterialInfoService masterDataMaterialInfoService;

    /**
     * 查询主数据管理列表
     */
    @GetMapping("/list")
    public TableDataInfo list(MasterDataMaterialInfo masterDataMaterialInfo)
    {
        startPage();
        List<MasterDataMaterialInfo> list = masterDataMaterialInfoService.selectMasterDataMaterialInfoList(masterDataMaterialInfo);
        return getDataTable(list);
    }

    /**
     * 导出主数据管理列表
     */
    @Log(title = "主数据管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MasterDataMaterialInfo masterDataMaterialInfo)
    {
        List<MasterDataMaterialInfo> list = masterDataMaterialInfoService.selectMasterDataMaterialInfoList(masterDataMaterialInfo);
        ExcelUtil<MasterDataMaterialInfo> util = new ExcelUtil<MasterDataMaterialInfo>(MasterDataMaterialInfo.class);
        util.exportExcel(response, list, "主数据管理数据");
    }

    /**
     * 获取主数据管理详细信息
     */
    @GetMapping(value = "/{materialId}")
    public AjaxResult getInfo(@PathVariable("materialId") String materialId)
    {
        return AjaxResult.success(masterDataMaterialInfoService.selectMasterDataMaterialInfoByMaterialId(materialId));
    }

    /**
     * 新增物料数据
     */
    @Log(title = "物料管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MasterDataMaterialInfo masterDataMaterialInfo) {
        masterDataMaterialInfo.setBizVersion(1L);
        masterDataMaterialInfo.setCreateTime(DateUtils.getNowDate());
        masterDataMaterialInfo.setUpdateTime(DateUtils.getNowDate());
        masterDataMaterialInfo.setCreateBy(SecurityUtils.getUsername());
        masterDataMaterialInfo.setUpdateBy(SecurityUtils.getUsername());

        return toAjax(masterDataMaterialInfoService.insertMasterDataMaterialInfo(masterDataMaterialInfo));
    }

    /**
     * 修改主数据管理
     */
    @Log(title = "主数据管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MasterDataMaterialInfo masterDataMaterialInfo)
    {
        return toAjax(masterDataMaterialInfoService.updateMasterDataMaterialInfo(masterDataMaterialInfo));
    }

    /**
     * 删除主数据管理
     */
    @Log(title = "主数据管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{materialIds}")
    public AjaxResult remove(@PathVariable String[] materialIds)
    {
        return toAjax(masterDataMaterialInfoService.deleteMasterDataMaterialInfoByMaterialIds(materialIds));
    }
}
