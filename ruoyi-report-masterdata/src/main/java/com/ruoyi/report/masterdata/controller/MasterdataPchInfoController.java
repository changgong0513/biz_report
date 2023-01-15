package com.ruoyi.report.masterdata.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.report.masterdata.domain.MasterdataPchInfo;
import com.ruoyi.report.masterdata.service.IMasterdataPchInfoService;
import com.ruoyi.system.service.ISysDeptService;
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

/**
 * 批次号管理Controller
 * 
 * @author changgong
 * @date 2022-12-04
 */
@RestController
@RequestMapping("/masterdata/pch")
public class MasterdataPchInfoController extends BaseController
{
    @Autowired
    private IMasterdataPchInfoService masterdataPchInfoService;

    @Autowired
    private ISysDeptService sysDeptService;

    /**
     * 查询批次号管理列表
     */
    // @PreAuthorize("@ss.hasPermi('masterdata:pch:list')")
    @GetMapping("/list")
    public TableDataInfo list(MasterdataPchInfo masterdataPchInfo)
    {
        startPage();
        List<MasterdataPchInfo> list = masterdataPchInfoService.selectMasterdataPchInfoList(masterdataPchInfo);
        return getDataTable(list);
    }

    /**
     * 导出批次号管理列表
     */
    // @PreAuthorize("@ss.hasPermi('masterdata:pch:export')")
    @Log(title = "批次号管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MasterdataPchInfo masterdataPchInfo) {

        List<MasterdataPchInfo> list = masterdataPchInfoService.selectMasterdataPchInfoList(masterdataPchInfo);
        list.stream().forEach(elment -> {
            String belongDeptId = elment.getBelongDept();
            if (StringUtils.isNotBlank(belongDeptId)) {
                SysDept dept = sysDeptService.selectDeptById(Long.parseLong(belongDeptId));
                elment.setBelongDeptName(dept.getDeptName());
            }
        });
        ExcelUtil<MasterdataPchInfo> util = new ExcelUtil<MasterdataPchInfo>(MasterdataPchInfo.class);
        util.exportExcel(response, list, "批次号管理数据");
    }

    /**
     * 获取批次号管理详细信息
     */
    // @PreAuthorize("@ss.hasPermi('masterdata:pch:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(masterdataPchInfoService.selectMasterdataPchInfoById(id));
    }

    /**
     * 新增批次号管理
     */
    // @PreAuthorize("@ss.hasPermi('masterdata:pch:add')")
    @Log(title = "批次号管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MasterdataPchInfo masterdataPchInfo) {

        AjaxResult result = AjaxResult.success();
        MasterdataPchInfo params = new MasterdataPchInfo();
        params.setPch(masterdataPchInfo.getPch());
        params.setBelongDept(masterdataPchInfo.getBelongDept());
        int cnt = masterdataPchInfoService.selectPchCounts(params);
        if (cnt == 0) {
            result = toAjax(masterdataPchInfoService.insertMasterdataPchInfo(masterdataPchInfo));
        } else {
            result = AjaxResult.error("新增批次号已存在，请重新输入新的批次号!");
        }
        return result;
    }

    /**
     * 修改批次号管理
     */
    // @PreAuthorize("@ss.hasPermi('masterdata:pch:edit')")
    @Log(title = "批次号管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MasterdataPchInfo masterdataPchInfo)
    {
        return toAjax(masterdataPchInfoService.updateMasterdataPchInfo(masterdataPchInfo));
    }

    /**
     * 删除批次号管理
     */
    // @PreAuthorize("@ss.hasPermi('masterdata:pch:remove')")
    @Log(title = "批次号管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(masterdataPchInfoService.deleteMasterdataPchInfoByIds(ids));
    }
}
