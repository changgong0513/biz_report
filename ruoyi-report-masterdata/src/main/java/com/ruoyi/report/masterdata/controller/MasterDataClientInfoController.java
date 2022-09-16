package com.ruoyi.report.masterdata.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.report.masterdata.domain.MasterDataClientInfo;
import com.ruoyi.report.masterdata.service.IMasterDataClientInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 业务报表Controller
 *
 * @author ruoyi
 * @date 2022-09-16
 */
@RestController
@RequestMapping("/md/client")
public class MasterDataClientInfoController extends BaseController
{
    @Autowired
    private IMasterDataClientInfoService masterDataClientInfoService;

    /**
     * 查询业务报表列表
     */
    @PreAuthorize("@ss.hasPermi('masterdata:xmy-biz-api:list')")
    @GetMapping("/list")
    public TableDataInfo list(MasterDataClientInfo masterDataClientInfo)
    {
        startPage();
        List<MasterDataClientInfo> list = masterDataClientInfoService.selectMasterDataClientInfoList(masterDataClientInfo);
        return getDataTable(list);
    }

    /**
     * 导出业务报表列表
     */
    @PreAuthorize("@ss.hasPermi('masterdata:xmy-biz-api:export')")
    @Log(title = "业务报表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MasterDataClientInfo masterDataClientInfo)
    {
        List<MasterDataClientInfo> list = masterDataClientInfoService.selectMasterDataClientInfoList(masterDataClientInfo);
        ExcelUtil<MasterDataClientInfo> util = new ExcelUtil<MasterDataClientInfo>(MasterDataClientInfo.class);
        util.exportExcel(response, list, "业务报表数据");
    }

    /**
     * 获取业务报表详细信息
     */
    @PreAuthorize("@ss.hasPermi('masterdata:xmy-biz-api:query')")
    @GetMapping(value = "/{baseId}")
    public AjaxResult getInfo(@PathVariable("baseId") Long baseId)
    {
        return AjaxResult.success(masterDataClientInfoService.selectMasterDataClientInfoByBaseId(baseId));
    }

    /**
     * 新增业务报表
     */
    @PreAuthorize("@ss.hasPermi('masterdata:xmy-biz-api:add')")
    @Log(title = "业务报表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MasterDataClientInfo masterDataClientInfo) {

        masterDataClientInfo.setBizVersion(1L);
        masterDataClientInfo.setCreateTime(DateUtils.getNowDate());
        masterDataClientInfo.setUpdateTime(DateUtils.getNowDate());
        masterDataClientInfo.setCreateBy(SecurityUtils.getUsername());
        masterDataClientInfo.setUpdateBy(SecurityUtils.getUsername());
        System.out.println("------新增业务报表大数据：" + masterDataClientInfo);

        return toAjax(masterDataClientInfoService.insertMasterDataClientInfo(masterDataClientInfo));
    }

    /**
     * 修改业务报表
     */
    @PreAuthorize("@ss.hasPermi('masterdata:xmy-biz-api:edit')")
    @Log(title = "业务报表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MasterDataClientInfo masterDataClientInfo)
    {
        return toAjax(masterDataClientInfoService.updateMasterDataClientInfo(masterDataClientInfo));
    }

    /**
     * 删除业务报表
     */
    @PreAuthorize("@ss.hasPermi('masterdata:xmy-biz-api:remove')")
    @Log(title = "业务报表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{baseIds}")
    public AjaxResult remove(@PathVariable Long[] baseIds)
    {
        return toAjax(masterDataClientInfoService.deleteMasterDataClientInfoByBaseIds(baseIds));
    }
}

