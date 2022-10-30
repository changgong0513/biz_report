package com.ruoyi.report.contract.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.report.contract.domain.ContractContentInfo;
import com.ruoyi.report.contract.service.IContractContentInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 合同管理Controller
 * 
 * @author changgong
 * @date 2022-10-30
 */
@RestController
@RequestMapping("/contract/mgr")
public class ContractContentInfoController extends BaseController
{
    @Autowired
    private IContractContentInfoService contractContentInfoService;

    /**
     * 查询合同管理列表
     */
    @PreAuthorize("@ss.hasPermi('contract:contract:list')")
    @GetMapping("/list")
    public TableDataInfo list(ContractContentInfo contractContentInfo)
    {
        startPage();
        List<ContractContentInfo> list = contractContentInfoService.selectContractContentInfoList(contractContentInfo);
        return getDataTable(list);
    }

    /**
     * 导出合同管理列表
     */
    @PreAuthorize("@ss.hasPermi('contract:contract:export')")
    @Log(title = "合同管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ContractContentInfo contractContentInfo)
    {
        List<ContractContentInfo> list = contractContentInfoService.selectContractContentInfoList(contractContentInfo);
        ExcelUtil<ContractContentInfo> util = new ExcelUtil<ContractContentInfo>(ContractContentInfo.class);
        util.exportExcel(response, list, "合同管理数据");
    }

    /**
     * 获取合同管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('contract:contract:query')")
    @GetMapping(value = "/{goodsId}")
    public AjaxResult getInfo(@PathVariable("goodsId") String goodsId)
    {
        return AjaxResult.success(contractContentInfoService.selectContractContentInfoByGoodsId(goodsId));
    }

    /**
     * 新增合同管理
     */
    @PreAuthorize("@ss.hasPermi('contract:contract:add')")
    @Log(title = "合同管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ContractContentInfo contractContentInfo)
    {
        return toAjax(contractContentInfoService.insertContractContentInfo(contractContentInfo));
    }

    /**
     * 修改合同管理
     */
    @PreAuthorize("@ss.hasPermi('contract:contract:edit')")
    @Log(title = "合同管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ContractContentInfo contractContentInfo)
    {
        return toAjax(contractContentInfoService.updateContractContentInfo(contractContentInfo));
    }

    /**
     * 删除合同管理
     */
    @PreAuthorize("@ss.hasPermi('contract:contract:remove')")
    @Log(title = "合同管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{goodsIds}")
    public AjaxResult remove(@PathVariable String[] goodsIds)
    {
        return toAjax(contractContentInfoService.deleteContractContentInfoByGoodsIds(goodsIds));
    }

    /**
     * 从钉钉同步合同数据
     */
    @PostMapping("/sync")
    public AjaxResult sync(ContractContentInfo contractContentInfo) throws Exception {
        return toAjax(contractContentInfoService.syncContractContentInfo());
    }
}
