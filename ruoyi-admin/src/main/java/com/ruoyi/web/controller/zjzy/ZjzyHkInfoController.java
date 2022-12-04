package com.ruoyi.web.controller.zjzy;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.zjzy.domain.ZjzyHkInfo;
import com.ruoyi.zjzy.service.IZjzyHkInfoService;
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
@RequestMapping("/system/hkrl")
public class ZjzyHkInfoController extends BaseController
{
    @Autowired
    private IZjzyHkInfoService zjzyHkInfoService;

    /**
     * 查询回款认领列表
     */
    @PreAuthorize("@ss.hasPermi('system:hkrl:list')")
    @GetMapping("/list")
    public TableDataInfo list(ZjzyHkInfo zjzyHkInfo)
    {
        startPage();
        List<ZjzyHkInfo> list = zjzyHkInfoService.selectZjzyHkInfoList(zjzyHkInfo);
        return getDataTable(list);
    }

    /**
     * 导出回款认领列表
     */
    @PreAuthorize("@ss.hasPermi('system:hkrl:export')")
    @Log(title = "回款认领", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ZjzyHkInfo zjzyHkInfo)
    {
        List<ZjzyHkInfo> list = zjzyHkInfoService.selectZjzyHkInfoList(zjzyHkInfo);
        ExcelUtil<ZjzyHkInfo> util = new ExcelUtil<ZjzyHkInfo>(ZjzyHkInfo.class);
        util.exportExcel(response, list, "回款认领数据");
    }

    /**
     * 获取回款认领详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:hkrl:query')")
    @GetMapping(value = "/{hkId}")
    public AjaxResult getInfo(@PathVariable("hkId") String hkId)
    {
        return AjaxResult.success(zjzyHkInfoService.selectZjzyHkInfoByHkId(hkId));
    }

    /**
     * 新增回款认领
     */
    @PreAuthorize("@ss.hasPermi('system:hkrl:add')")
    @Log(title = "回款认领", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ZjzyHkInfo zjzyHkInfo)
    {
        return toAjax(zjzyHkInfoService.insertZjzyHkInfo(zjzyHkInfo));
    }

    /**
     * 修改回款认领
     */
    @PreAuthorize("@ss.hasPermi('system:hkrl:edit')")
    @Log(title = "回款认领", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ZjzyHkInfo zjzyHkInfo)
    {
        return toAjax(zjzyHkInfoService.updateZjzyHkInfo(zjzyHkInfo));
    }

    /**
     * 删除回款认领
     */
    @PreAuthorize("@ss.hasPermi('system:hkrl:remove')")
    @Log(title = "回款认领", businessType = BusinessType.DELETE)
	@DeleteMapping("/{hkIds}")
    public AjaxResult remove(@PathVariable String[] hkIds)
    {
        return toAjax(zjzyHkInfoService.deleteZjzyHkInfoByHkIds(hkIds));
    }
}
