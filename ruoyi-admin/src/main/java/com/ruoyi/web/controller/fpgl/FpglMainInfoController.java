package com.ruoyi.web.controller.fpgl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fpgl.domain.FpglListInfo;
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
import com.ruoyi.fpgl.domain.FpglMainInfo;
import com.ruoyi.fpgl.service.IFpglMainInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 发票管理Controller
 * 
 * @author changgong0513
 * @date 2022-11-06
 */
@RestController
@RequestMapping("/fpgl/mgr")
public class FpglMainInfoController extends BaseController
{
    @Autowired
    private IFpglMainInfoService fpglMainInfoService;

    /**
     * 查询发票管理列表
     */
    @PreAuthorize("@ss.hasPermi('fpgl:main:list')")
    @GetMapping("/list")
    public TableDataInfo list(FpglListInfo fpglListInfo) {
        startPage();
//        List<FpglMainInfo> list = fpglMainInfoService.selectFpglMainInfoList(fpglMainInfo);
        List<FpglListInfo> list = fpglMainInfoService.selectFpglList(fpglListInfo);
        for (FpglListInfo item : list) {
            BigDecimal total = item.getContractTotal();
            BigDecimal kpje = item.getFpglKpje();
            if (kpje.compareTo(total) == 0) {
                item.setFpglFpzt("1");
            } else if (kpje.compareTo(new BigDecimal("0")) == 0) {
                item.setFpglFpzt("3");
            } else {
                item.setFpglFpzt("2");
            }
        }

        List<FpglListInfo> filterList = null;
        if (StringUtils.isNotBlank(fpglListInfo.getFpglFpzt())) {
            filterList = list.stream()
                    .filter(item -> item.getFpglFpzt().equals(fpglListInfo.getFpglFpzt()))
                    .collect(Collectors.toList());
        } else {
            filterList = list;
        }

        return getDataTable(filterList);
    }

    /**
     * 导出发票管理列表
     */
    @PreAuthorize("@ss.hasPermi('fpgl:main:export')")
    @Log(title = "发票管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FpglMainInfo fpglMainInfo)
    {
        List<FpglMainInfo> list = fpglMainInfoService.selectFpglMainInfoList(fpglMainInfo);
        ExcelUtil<FpglMainInfo> util = new ExcelUtil<FpglMainInfo>(FpglMainInfo.class);
        util.exportExcel(response, list, "发票管理数据");
    }

    /**
     * 获取发票管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('fpgl:main:query')")
    @GetMapping(value = "/{fpglId}")
    public AjaxResult getInfo(@PathVariable("fpglId") String fpglId)
    {
        return AjaxResult.success(fpglMainInfoService.selectFpglMainInfoByFpglId(fpglId));
    }

    /**
     * 新增发票管理
     */
    @PreAuthorize("@ss.hasPermi('fpgl:main:add')")
    @Log(title = "发票管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FpglMainInfo fpglMainInfo)
    {
        return toAjax(fpglMainInfoService.insertFpglMainInfo(fpglMainInfo));
    }

    /**
     * 修改发票管理
     */
    @PreAuthorize("@ss.hasPermi('fpgl:main:edit')")
    @Log(title = "发票管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FpglMainInfo fpglMainInfo)
    {
        return toAjax(fpglMainInfoService.updateFpglMainInfo(fpglMainInfo));
    }

    /**
     * 删除发票管理
     */
    @PreAuthorize("@ss.hasPermi('fpgl:main:remove')")
    @Log(title = "发票管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{fpglIds}")
    public AjaxResult remove(@PathVariable String[] fpglIds)
    {
        return toAjax(fpglMainInfoService.deleteFpglMainInfoByFpglIds(fpglIds));
    }
}
