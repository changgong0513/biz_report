package com.ruoyi.report.masterdata.controller;

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
import com.ruoyi.report.masterdata.domain.MasterDataKqInfo;
import com.ruoyi.report.masterdata.service.IMasterDataKqInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * WarehouseController
 * 
 * @author changgong0513
 * @date 2022-10-30
 */
@RestController
@RequestMapping("/md/kq")
public class MasterDataKqInfoController extends BaseController
{
    @Autowired
    private IMasterDataKqInfoService masterDataKqInfoService;

    /**
     * 查询仓库库区列表
     */
    @GetMapping("/list")
    public TableDataInfo list(MasterDataKqInfo masterDataKqInfo)
    {
        startPage();
        List<MasterDataKqInfo> list = masterDataKqInfoService.selectMasterDataKqInfoList(masterDataKqInfo);
        return getDataTable(list);
    }

    /**
     * 导出仓库库区列表
     */
    @Log(title = "Warehouse", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MasterDataKqInfo masterDataKqInfo)
    {
        List<MasterDataKqInfo> list = masterDataKqInfoService.selectMasterDataKqInfoList(masterDataKqInfo);
        ExcelUtil<MasterDataKqInfo> util = new ExcelUtil<MasterDataKqInfo>(MasterDataKqInfo.class);
        util.exportExcel(response, list, "Warehouse数据");
    }

    /**
     * 获取仓库库区详细信息
     */
    @GetMapping(value = "/{kqCode}")
    public AjaxResult getInfo(@PathVariable("kqCode") String kqCode)
    {
        return AjaxResult.success(masterDataKqInfoService.selectMasterDataKqInfoByKqCode(kqCode));
    }

    /**
     * 新增仓库库区数据
     */
    @Log(title = "kq", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MasterDataKqInfo masterDataKqInfo)
    {
        masterDataKqInfo.setBizVersion(1L);
        masterDataKqInfo.setCreateTime(DateUtils.getNowDate());
        masterDataKqInfo.setUpdateTime(DateUtils.getNowDate());
        masterDataKqInfo.setCreateBy(SecurityUtils.getUsername());
        masterDataKqInfo.setUpdateBy(SecurityUtils.getUsername());
        System.out.println("------新增仓库库区数据：" + masterDataKqInfo);

        return toAjax(masterDataKqInfoService.insertMasterDataKqInfo(masterDataKqInfo));
    }

    /**
     * 修改仓库库区数据
     */
    @Log(title = "kq", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MasterDataKqInfo masterDataKqInfo) {
        masterDataKqInfo.setBizVersion(1L);
        masterDataKqInfo.setCreateTime(DateUtils.getNowDate());
        masterDataKqInfo.setUpdateTime(DateUtils.getNowDate());
        masterDataKqInfo.setCreateBy(SecurityUtils.getUsername());
        masterDataKqInfo.setUpdateBy(SecurityUtils.getUsername());
        System.out.println("------修改仓库库区数据：" + masterDataKqInfo);
        return toAjax(masterDataKqInfoService.updateMasterDataKqInfo(masterDataKqInfo));
    }

    /**
     * 删除仓库库区数据
     */
    @Log(title = "kq", businessType = BusinessType.DELETE)
	@DeleteMapping("/{kqCodes}")
    public AjaxResult remove(@PathVariable String[] kqCodes)
    {
        return toAjax(masterDataKqInfoService.deleteMasterDataKqInfoByKqCodes(kqCodes));
    }
}
