package com.ruoyi.report.masterdata.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.report.masterdata.domain.MasterDataClientInfo;
import com.ruoyi.report.masterdata.service.IMasterDataClientInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

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
     * 查询主数据管理列表
     */
    @GetMapping("/list")
    public TableDataInfo list(MasterDataClientInfo masterDataClientInfo) {
        startPage();
        List<MasterDataClientInfo> list = masterDataClientInfoService.selectMasterDataClientInfoList(masterDataClientInfo);
        return getDataTable(list);
    }

    /**
     * 导出主数据管理列表
     */
    @Log(title = "主数据管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MasterDataClientInfo masterDataClientInfo)
    {
        List<MasterDataClientInfo> list = masterDataClientInfoService.selectMasterDataClientInfoList(masterDataClientInfo);
        ExcelUtil<MasterDataClientInfo> util = new ExcelUtil<MasterDataClientInfo>(MasterDataClientInfo.class);
        util.exportExcel(response, list, "主数据管理");
    }

    /**
     * 获取主数据管理详细信息
     */
    @GetMapping(value = "/{baseId}")
    public AjaxResult getInfo(@PathVariable("baseId") String baseId)
    {
        return AjaxResult.success(masterDataClientInfoService.selectMasterDataClientInfoByBaseId(baseId));
    }

    /**
     * 新增主数据管理数据
     */
    @Log(title = "主数据管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MasterDataClientInfo masterDataClientInfo) {

        masterDataClientInfo.setBizVersion(1L);
        masterDataClientInfo.setCreateTime(DateUtils.getNowDate());
        masterDataClientInfo.setUpdateTime(DateUtils.getNowDate());
        masterDataClientInfo.setCreateBy(SecurityUtils.getUsername());
        masterDataClientInfo.setUpdateBy(SecurityUtils.getUsername());

        Long recordFLag = masterDataClientInfo.getRecordFlag();
        if (recordFLag ==  1) {
            // 供应商管理-供应商
            Optional<MasterDataClientInfo> optSelMaxSupplierRecord = masterDataClientInfoService.selectMaxSupplierId();
            if (optSelMaxSupplierRecord.isPresent()) {
                String baseId = optSelMaxSupplierRecord.get().getBaseId();
                String currentBaseId = StringUtils.substring(baseId, 3, baseId.length());
                masterDataClientInfo.setBaseId("GYS" + StringUtils.padl(Integer.parseInt(currentBaseId) + 1, 6));
            } else {
                masterDataClientInfo.setBaseId("GYS" + StringUtils.padl(1, 6));
            }
        } else if (recordFLag == 2) {
            // 供应商管理-客户
            Optional<MasterDataClientInfo> optSelMaxClientRecord = masterDataClientInfoService.selectMaxClientId();
            if (optSelMaxClientRecord.isPresent()) {
                String baseId = optSelMaxClientRecord.get().getBaseId();
                String currentBaseId = StringUtils.substring(baseId, 2, baseId.length());
                masterDataClientInfo.setBaseId("KH" + StringUtils.padl(Integer.parseInt(currentBaseId) + 1, 6));
            } else {
                masterDataClientInfo.setBaseId("KH" + StringUtils.padl(1, 6));
            }
        }

        return toAjax(masterDataClientInfoService.insertMasterDataClientInfo(masterDataClientInfo));
    }

    /**
     * 修改主数据管理数据
     */
    @Log(title = "主数据管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MasterDataClientInfo masterDataClientInfo)
    {
        return toAjax(masterDataClientInfoService.updateMasterDataClientInfo(masterDataClientInfo));
    }

    /**
     * 删除主数据管理数据
     */
    @Log(title = "主数据管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{baseIds}")
    public AjaxResult remove(@PathVariable String[] baseIds)
    {
        return toAjax(masterDataClientInfoService.deleteMasterDataClientInfoByBaseIds(baseIds));
    }
}

