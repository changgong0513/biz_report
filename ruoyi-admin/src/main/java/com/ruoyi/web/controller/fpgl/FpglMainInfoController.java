package com.ruoyi.web.controller.fpgl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.TreeSelect;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.fpgl.domain.FpglListInfo;
import com.ruoyi.purchase.sale.domain.PurchaseSaleOrderInfo;
import com.ruoyi.report.masterdata.domain.MasterDataClientInfo;
import com.ruoyi.report.masterdata.domain.MasterDataMaterialInfo;
import com.ruoyi.report.masterdata.service.IMasterDataClientInfoService;
import com.ruoyi.report.masterdata.service.IMasterDataMaterialInfoService;
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

    @Autowired
    private IMasterDataClientInfoService masterDataClientInfoService;

    @Autowired
    private IMasterDataMaterialInfoService masterDataMaterialInfoService;

    /**
     * 查询发票管理列表
     */
    @GetMapping("/list")
    public TableDataInfo list(FpglListInfo fpglListInfo) {

        startPage();

        List<FpglListInfo> fpDetailList = new ArrayList<>();
        List<FpglListInfo> calculatedFpglList = new ArrayList<>();

        fpglListInfo.setFpglSqr(getUsername());

        Long deptId = getDeptId();
        fpglListInfo.setDeptId(deptId);
        if (Long.compare(deptId, 201) == 0) {
            // 当前登录用户所属部门为财务部
            fpDetailList = fpglMainInfoService.selectFpglListForCw(fpglListInfo);

            Map<String, List<FpglListInfo>> map = fpDetailList.stream()
                    .collect(Collectors.groupingBy(element -> element.getOrderId()));
            map.forEach((key, value) -> {
                FpglListInfo lastItem = value.get(value.size() - 1);
                BigDecimal sum = value.stream()
                        .map(x -> new BigDecimal(String.valueOf(x.getFpglKpje())))
                        .reduce(BigDecimal.ZERO,BigDecimal::add);
                lastItem.setFpglKpje(sum);
                calculatedFpglList.add(lastItem);
            });
        } else {
            // 当前登录用户所属部门为非财务部
            fpDetailList = fpglMainInfoService.selectFpglList(fpglListInfo);
        }

        for (FpglListInfo item : calculatedFpglList) {
            BigDecimal total = item.getContractTotal();
            BigDecimal kpje = item.getFpglKpje();
            if (kpje.compareTo(total) == 0) {
                item.setFpglFpzt("1");
            } else if (kpje.compareTo(new BigDecimal("0")) == 0) {
                item.setFpglFpzt("3");
            } else {
                item.setFpglFpzt("2");
            }

            MasterDataClientInfo clientInfo = masterDataClientInfoService.selectMasterDataClientInfoByBaseId(item.getSupplierName());
            item.setRealSupplierName(clientInfo.getCompanyName());
        }

        List<FpglListInfo> filterList = new ArrayList<>();
        if (StringUtils.isNotBlank(fpglListInfo.getFpglFpzt())) {
            filterList = calculatedFpglList.stream()
                    .filter(item -> item.getFpglFpzt().equals(fpglListInfo.getFpglFpzt()))
                    .collect(Collectors.toList());
        } else {
            filterList = calculatedFpglList;
        }

        return getDataTable(filterList);
    }

    @GetMapping("/fpmxlist/{orderId}")
    public TableDataInfo fpmxList(@PathVariable("orderId") String orderId) {

        FpglMainInfo fpglMainInfo = new FpglMainInfo();
        fpglMainInfo.setFpglDdbh(orderId);

        startPage();
        List<FpglMainInfo> list = fpglMainInfoService.selectFpglMainInfoList(fpglMainInfo);
        List<FpglMainInfo> filterlist = list.stream().filter(element -> element.getFpglKprq() != null).collect(Collectors.toList());
        return getDataTable(filterlist);
    }


    /**
     * 导出发票管理列表
     */
    @Log(title = "发票管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FpglListInfo fpglListInfo) {

        List<FpglListInfo> list = fpglMainInfoService.selectFpglList(fpglListInfo);
        for (FpglListInfo item : list) {
            BigDecimal total = item.getContractTotal();
            BigDecimal kpje = item.getFpglKpje();
            if (kpje.compareTo(total) == 0) {
                // 发票状态：已完成
                item.setFpglFpzt("1");
            } else if (kpje.compareTo(new BigDecimal("0")) == 0) {
                // 发票状态：未申请
                item.setFpglFpzt("3");
            } else {
                // 发票状态：开票中
                item.setFpglFpzt("2");
            }
        }

        ExcelUtil<FpglListInfo> util = new ExcelUtil<FpglListInfo>(FpglListInfo.class);
        util.exportExcel(response, list, "发票管理数据");
    }

    /**
     * 获取发票管理详细信息
     */
    @GetMapping(value = "/{fpglDdbh}")
    public AjaxResult getInfo(@PathVariable("fpglDdbh") String fpglDdbh) {
        FpglMainInfo fpglMainInfo = fpglMainInfoService.selectFpglMainInfoByFpglDdbh(fpglDdbh);
        return AjaxResult.success(1);
    }

    /**
     * 新增发票管理
     */
    @Log(title = "发票管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FpglMainInfo fpglMainInfo) {

        fpglMainInfo.setFpglKprq(DateUtils.parseDate(DateUtils.getDate()));
        fpglMainInfo.setFpglSqr(getUsername());
        fpglMainInfo.setBizVersion(1L);
        fpglMainInfo.setCreateTime(DateUtils.getNowDate());
        fpglMainInfo.setUpdateTime(DateUtils.getNowDate());
        fpglMainInfo.setCreateBy(SecurityUtils.getUsername());
        fpglMainInfo.setUpdateBy(SecurityUtils.getUsername());

        AjaxResult result = AjaxResult.success();
        FpglMainInfo findFpglMainInfo = fpglMainInfoService
                .selectFpglMainInfoByFpglDdbh(fpglMainInfo.getFpglDdbh());
        if (findFpglMainInfo == null) {
            result = toAjax(fpglMainInfoService.insertFpglMainInfo(fpglMainInfo));
        } else {
            fpglMainInfo.setFpglKpje(findFpglMainInfo.getFpglKpje().add(fpglMainInfo.getFpglKpje()));
            result = toAjax(fpglMainInfoService.updateFpglMainInfo(fpglMainInfo));
        }

        return result;
    }

    /**
     * 修改发票管理
     */
    @Log(title = "发票管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FpglMainInfo fpglMainInfo) {

        fpglMainInfo.setFpglId(null);
        fpglMainInfo.setFpglKprq(DateUtils.getNowDate());
        fpglMainInfo.setFpglSqr(fpglMainInfo.getFpglSqr());

        List<MasterDataMaterialInfo> masterDataMaterialInfoList =  masterDataMaterialInfoService.selectMasterDataMaterialInfoList(new MasterDataMaterialInfo());
        Map<String, Integer> maps = masterDataMaterialInfoList.stream()
                .collect(Collectors.toMap(MasterDataMaterialInfo::getMaterialName, MasterDataMaterialInfo::getMaterialId));
        fpglMainInfo.setFpglKpmx(String.valueOf(maps.get(fpglMainInfo.getMaterialName())));

        fpglMainInfo.setBizVersion(1L);
        fpglMainInfo.setCreateTime(DateUtils.getNowDate());
        fpglMainInfo.setUpdateTime(DateUtils.getNowDate());
        fpglMainInfo.setCreateBy(SecurityUtils.getUsername());
        fpglMainInfo.setUpdateBy(SecurityUtils.getUsername());

        return toAjax(fpglMainInfoService.insertFpglMainInfo(fpglMainInfo));
    }

    /**
     * 删除发票管理
     */
    @Log(title = "发票管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{fpglIds}")
    public AjaxResult remove(@PathVariable String[] fpglIds)
    {
        return toAjax(fpglMainInfoService.deleteFpglMainInfoByFpglIds(fpglIds));
    }

    /**
     * 开票申请
     *
     * @param fpglListInfo
     * @return
     */
    @GetMapping("/sqkp/list")
    public TableDataInfo sqkplist(FpglListInfo fpglListInfo) {

        startPage();
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
}
