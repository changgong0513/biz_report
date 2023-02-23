package com.ruoyi.web.controller.zjzy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.report.masterdata.domain.MasterDataClientInfo;
import com.ruoyi.report.masterdata.domain.MasterDataMaterialInfo;
import com.ruoyi.report.masterdata.service.IMasterDataClientInfoService;
import com.ruoyi.zjzy.domain.ZjzyHkInfo;
import com.ruoyi.zjzy.service.IZjzyHkInfoService;
import com.ruoyi.zjzy.service.IZjzyHkrlInfoService;
import org.apache.xmlbeans.impl.jam.JElement;
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
import org.springframework.web.multipart.MultipartFile;

/**
 * 回款认领Controller
 * 
 * @author changgong0513
 * @date 2022-12-04
 */
@RestController
@RequestMapping("/zjzy/hk")
public class ZjzyHkInfoController extends BaseController
{
    @Autowired
    private IZjzyHkInfoService zjzyHkInfoService;

    @Autowired
    private IZjzyHkrlInfoService zjzyHkrlInfoService;

    @Autowired
    private IMasterDataClientInfoService masterDataClientInfoService;

    /**
     * 查询回款列表
     */
    @PreAuthorize("@ss.hasPermi('zjzy:hk:list')")
    @GetMapping("/list")
    public TableDataInfo list(ZjzyHkInfo zjzyHkInfo)
    {
        startPage();

        List<ZjzyHkInfo> list = zjzyHkInfoService.selectZjzyHkInfoList(zjzyHkInfo);
        list.stream().forEach(hkData -> {

            if (hkData.getHkHkje() != null && hkData.getHkrlJe() != null) {
                BigDecimal hkje = hkData.getHkHkje();
                BigDecimal hkrlje = hkData.getHkrlJe();
                if (hkje.compareTo(hkrlje) == 0) {
                    hkData.setHkHkzt("1");
                } else if (hkje.compareTo(hkrlje) == 1) {
                    hkData.setHkHkzt("2");
                }
            }
        });
        return getDataTable(list);
    }

    /**
     * 导出回款认领列表
     */
    // @PreAuthorize("@ss.hasPermi('system:hkrl:export')")
    @Log(title = "回款认领", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ZjzyHkInfo zjzyHkInfo)
    {
        List<ZjzyHkInfo> list = zjzyHkInfoService.selectZjzyHkInfoList(zjzyHkInfo);
        for (ZjzyHkInfo item: list) {
            item.setHkRealKhmc(item.getHkKhmc());
        }

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
     * 取得回款总金额
     */
    @GetMapping(value = "/total")
    public AjaxResult getHkrlTotal() {

        Long deptId = this.getDeptId();
        if (deptId == 100 || deptId == 103) {
            return AjaxResult.success(zjzyHkInfoService.getHkrlTotal());
        }

        return AjaxResult.success(zjzyHkrlInfoService.getHkrlTotalByBmbh(deptId));
    }

    /**
     * 根据年月分组，取得年月回款总金额
     */
    @GetMapping(value = "/total/ym")
    public TableDataInfo getHkTotalByYearMonth() {
        List<ZjzyHkInfo> list = zjzyHkInfoService.getHkTotalByYearMonth();
        return getDataTable(list);
    }

    /**
     * 新增回款认领
     */
    // @PreAuthorize("@ss.hasPermi('system:hkrl:add')")
    @Log(title = "回款认领", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ZjzyHkInfo zjzyHkInfo)
    {
        return toAjax(zjzyHkInfoService.insertZjzyHkInfo(zjzyHkInfo));
    }

    /**
     * 修改回款认领
     */
    // @PreAuthorize("@ss.hasPermi('system:hkrl:edit')")
    @Log(title = "回款认领", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ZjzyHkInfo zjzyHkInfo)
    {
        return toAjax(zjzyHkInfoService.updateZjzyHkInfo(zjzyHkInfo));
    }

    /**
     * 删除回款认领
     */
    // @PreAuthorize("@ss.hasPermi('system:hkrl:remove')")
    @Log(title = "回款认领", businessType = BusinessType.DELETE)
	@DeleteMapping("/{hkIds}")
    public AjaxResult remove(@PathVariable String[] hkIds)
    {
        return toAjax(zjzyHkInfoService.deleteZjzyHkInfoByHkIds(hkIds));
    }

    /**
     * 导入回款模板下载。
     *
     * @param response
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<ZjzyHkInfo> util = new ExcelUtil<ZjzyHkInfo>(ZjzyHkInfo.class);
        util.importTemplateExcel(response, "回款数据");
    }

    /**
     * 导入合同数据.
     *
     * @param file
     * @param updateSupport
     * @return
     * @throws Exception
     */
    @Log(title = "资金占用", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<ZjzyHkInfo> util = new ExcelUtil<ZjzyHkInfo>(ZjzyHkInfo.class);
        List<ZjzyHkInfo> hkList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = zjzyHkInfoService.importHkData(hkList, updateSupport, operName);
        return AjaxResult.success(message);
    }
}
