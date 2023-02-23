package com.ruoyi.web.controller.zjzy;

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
import com.ruoyi.zjzy.domain.ZjzyFkrlInfo;
import com.ruoyi.zjzy.service.IZjzyFkrlInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 付款认领Controller
 * 
 * @author changgong0513
 * @date 2022-12-07
 */
@RestController
@RequestMapping("/zjzy/fkrl")
public class ZjzyFkrlInfoController extends BaseController
{
    @Autowired
    private IZjzyFkrlInfoService zjzyFkrlInfoService;

    /**
     * 查询付款认领列表
     */
    // @PreAuthorize("@ss.hasPermi('fkrl:fkrl:list')")
    @GetMapping("/list")
    public TableDataInfo list(ZjzyFkrlInfo zjzyFkrlInfo)
    {
        startPage();
        List<ZjzyFkrlInfo> list = zjzyFkrlInfoService.selectZjzyFkrlInfoList(zjzyFkrlInfo);
        return getDataTable(list);
    }

    /**
     * 导出付款认领列表
     */
    // @PreAuthorize("@ss.hasPermi('fkrl:fkrl:export')")
    @Log(title = "付款认领", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ZjzyFkrlInfo zjzyFkrlInfo)
    {
        List<ZjzyFkrlInfo> list = zjzyFkrlInfoService.selectZjzyFkrlInfoList(zjzyFkrlInfo);
        ExcelUtil<ZjzyFkrlInfo> util = new ExcelUtil<ZjzyFkrlInfo>(ZjzyFkrlInfo.class);
        util.exportExcel(response, list, "付款认领数据");
    }

    /**
     * 获取付款认领详细信息
     */
    // @PreAuthorize("@ss.hasPermi('fkrl:fkrl:query')")
    @GetMapping(value = "/{fkrlId}")
    public AjaxResult getInfo(@PathVariable("fkrlId") String fkrlId)
    {
        return AjaxResult.success(zjzyFkrlInfoService.selectZjzyFkrlInfoByFkrlId(fkrlId));
    }

    /**
     * 新增付款认领
     */
    // @PreAuthorize("@ss.hasPermi('fkrl:fkrl:add')")
    @Log(title = "付款认领", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ZjzyFkrlInfo zjzyFkrlInfo) {
        zjzyFkrlInfo.setBizVersion("1");
        zjzyFkrlInfo.setCreateTime(DateUtils.getNowDate());
        zjzyFkrlInfo.setUpdateTime(DateUtils.getNowDate());
        zjzyFkrlInfo.setCreateBy(SecurityUtils.getUsername());
        zjzyFkrlInfo.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(zjzyFkrlInfoService.insertZjzyFkrlInfo(zjzyFkrlInfo));
    }

    /**
     * 修改付款认领
     */
    // @PreAuthorize("@ss.hasPermi('fkrl:fkrl:edit')")
    @Log(title = "付款认领", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ZjzyFkrlInfo zjzyFkrlInfo)
    {
        return toAjax(zjzyFkrlInfoService.updateZjzyFkrlInfo(zjzyFkrlInfo));
    }

    /**
     * 删除付款认领
     */
    // @PreAuthorize("@ss.hasPermi('fkrl:fkrl:remove')")
    @Log(title = "付款认领", businessType = BusinessType.DELETE)
	@DeleteMapping("/{fkrlIds}")
    public AjaxResult remove(@PathVariable String[] fkrlIds)
    {
        return toAjax(zjzyFkrlInfoService.deleteZjzyFkrlInfoByFkrlIds(fkrlIds));
    }


    /**
     * 根据部门编号，取得该部门的付款总额
     *
     * @return
     */
    @GetMapping(value = "/bmbh/total")
    public AjaxResult getFkrlTotalByBmbh() {
        return AjaxResult.success(zjzyFkrlInfoService.getFkrlTotalByBmbh(this.getDeptId()));
    }
}
