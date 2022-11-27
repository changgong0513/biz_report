package com.ruoyi.web.controller.kcdb;

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
import com.ruoyi.kcdb.domain.KcdbMainInfo;
import com.ruoyi.kcdb.service.IKcdbMainInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 存库调拨Controller
 * 
 * @author changgong0513
 * @date 2022-11-05
 */
@RestController
@RequestMapping("/kcdb/mgr")
public class KcdbMainInfoController extends BaseController
{
    @Autowired
    private IKcdbMainInfoService kcdbMainInfoService;

    /**
     * 查询存库调拨列表
     */
    @PreAuthorize("@ss.hasPermi('kcdb:kcdb:list')")
    @GetMapping("/list")
    public TableDataInfo list(KcdbMainInfo kcdbMainInfo)
    {
        startPage();
        List<KcdbMainInfo> list = kcdbMainInfoService.selectKcdbMainInfoList(kcdbMainInfo);
        return getDataTable(list);
    }

    /**
     * 导出存库调拨列表
     */
    @PreAuthorize("@ss.hasPermi('kcdb:kcdb:export')")
    @Log(title = "存库调拨", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, KcdbMainInfo kcdbMainInfo)
    {
        List<KcdbMainInfo> list = kcdbMainInfoService.selectKcdbMainInfoList(kcdbMainInfo);
        ExcelUtil<KcdbMainInfo> util = new ExcelUtil<KcdbMainInfo>(KcdbMainInfo.class);
        util.exportExcel(response, list, "存库调拨数据");
    }

    /**
     * 获取存库调拨详细信息
     */
    @PreAuthorize("@ss.hasPermi('kcdb:kcdb:query')")
    @GetMapping(value = "/{dh}")
    public AjaxResult getInfo(@PathVariable("dh") String dh)
    {
        return AjaxResult.success(kcdbMainInfoService.selectKcdbMainInfoByDh(dh));
    }

    /**
     * 新增存库调拨
     */
    @PreAuthorize("@ss.hasPermi('kcdb:kcdb:add')")
    @Log(title = "存库调拨", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody KcdbMainInfo kcdbMainInfo) {

        KcdbMainInfo maxKcdbMainInfo = kcdbMainInfoService.selectMaxDhForDc();
        if (maxKcdbMainInfo == null) {
            kcdbMainInfo.setDh("DH00000000");
        } else {
            String maxDcdh = maxKcdbMainInfo.getDh();
            String id = maxDcdh.substring(2, maxDcdh.length());
            int maxId = Integer.parseInt(id) + 1;
            kcdbMainInfo.setDh("DH" + StringUtils.padl(maxId, 8));
        }

        kcdbMainInfo.setRecordFlag("dc"); // 库存调出
        kcdbMainInfo.setBizVersion(1L);
        kcdbMainInfo.setCreateTime(DateUtils.getNowDate());
        kcdbMainInfo.setUpdateTime(DateUtils.getNowDate());
        kcdbMainInfo.setCreateBy(SecurityUtils.getUsername());
        kcdbMainInfo.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(kcdbMainInfoService.insertKcdbMainInfo(kcdbMainInfo));
    }

    /**
     * 修改存库调拨
     */
    @PreAuthorize("@ss.hasPermi('kcdb:kcdb:edit')")
    @Log(title = "存库调拨", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody KcdbMainInfo kcdbMainInfo) {
        kcdbMainInfo.setBizVersion(1L);
        kcdbMainInfo.setCreateTime(DateUtils.getNowDate());
        kcdbMainInfo.setUpdateTime(DateUtils.getNowDate());
        kcdbMainInfo.setCreateBy(SecurityUtils.getUsername());
        kcdbMainInfo.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(kcdbMainInfoService.updateKcdbMainInfo(kcdbMainInfo));
    }

    /**
     * 删除存库调拨
     */
    @PreAuthorize("@ss.hasPermi('kcdb:kcdb:remove')")
    @Log(title = "存库调拨", businessType = BusinessType.DELETE)
	@DeleteMapping("/{dhs}")
    public AjaxResult remove(@PathVariable String[] dhs)
    {
        return toAjax(kcdbMainInfoService.deleteKcdbMainInfoByDhs(dhs));
    }
}
