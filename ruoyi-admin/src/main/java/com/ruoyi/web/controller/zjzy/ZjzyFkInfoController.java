package com.ruoyi.web.controller.zjzy;

import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.report.contract.domain.ContractContentInfo;
import com.ruoyi.zjzy.domain.ZjzyStatisticsInfo;
import com.ruoyi.zjzy.domain.ZytjHistoryInfo;
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
import com.ruoyi.zjzy.domain.ZjzyFkInfo;
import com.ruoyi.zjzy.service.IZjzyFkInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 付款Controller
 * 
 * @author changgong0513
 * @date 2022-12-06
 */
@RestController
@RequestMapping("/zjzy/fk")
public class ZjzyFkInfoController extends BaseController
{
    @Autowired
    private IZjzyFkInfoService zjzyFkInfoService;

    /**
     * 查询付款列表
     */
    //@PreAuthorize("@ss.hasPermi('zjzy:fk:list')")
    @GetMapping("/list")
    public TableDataInfo list(ZjzyFkInfo zjzyFkInfo)
    {
        startPage();
        List<ZjzyFkInfo> list = zjzyFkInfoService.selectZjzyFkInfoList(zjzyFkInfo);
        list.stream().forEach(fkData -> {
            if (fkData.getFkJe() != null && fkData.getFkrlJe() != null) {
                BigDecimal fkje = fkData.getFkJe();
                BigDecimal fkrlje = fkData.getFkrlJe();
                if (fkje.compareTo(fkrlje) == 0) {
                    fkData.setFkZt("1");
                } else if (fkje.compareTo(fkrlje) == 1) {
                    fkData.setFkZt("2");
                } else {
                    fkData.setFkZt("3");
                }
            } else {
                fkData.setFkZt("3");
            }
        });
        return getDataTable(list);
    }

    /**
     * 导出付款列表
     */
    // @PreAuthorize("@ss.hasPermi('zjzy:fk:export')")
    @Log(title = "付款", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ZjzyFkInfo zjzyFkInfo)
    {
        List<ZjzyFkInfo> list = zjzyFkInfoService.selectZjzyFkInfoList(zjzyFkInfo);
        ExcelUtil<ZjzyFkInfo> util = new ExcelUtil<ZjzyFkInfo>(ZjzyFkInfo.class);
        util.exportExcel(response, list, "付款数据");
    }

    @GetMapping(value = "/total")
    public AjaxResult getFkrlTotal() {
        return AjaxResult.success(zjzyFkInfoService.getFkrlTotal());
    }

    /**
     * 查询占用统计列表
     *
     * @return
     */
    @GetMapping("/zytj/list")
    public TableDataInfo zjzyStatisticslist(ZjzyStatisticsInfo zjzyStatisticsInfo) {

        startPage();
        List<ZjzyStatisticsInfo> list = zjzyFkInfoService.selectZjzyStatisticsList(zjzyStatisticsInfo);

        zjzyStatisticsInfo.setTjJssj(DateUtils.dateTimeNow());
        zjzyStatisticsInfo.setCreateTime(DateUtils.getNowDate());
        zjzyStatisticsInfo.setUpdateTime(DateUtils.getNowDate());
        zjzyStatisticsInfo.setCreateBy(SecurityUtils.getUsername());
        zjzyStatisticsInfo.setUpdateBy(SecurityUtils.getUsername());
        for (ZjzyStatisticsInfo record : list) {
            record.setTjJssj(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, DateUtils.getNowDate()));
            record.setCreateTime(DateUtils.getNowDate());
            record.setUpdateTime(DateUtils.getNowDate());
            record.setCreateBy(SecurityUtils.getUsername());
            record.setUpdateBy(SecurityUtils.getUsername());
            List<ZytjHistoryInfo> existZytjRecrods = zjzyFkInfoService.selectZytjRecords(record);
            if (existZytjRecrods.size() == 0) {
                zjzyFkInfoService.insertZjzyStatisticsInfo(record);
            }
        }

        return getDataTable(list);
    }

    @GetMapping("/zytj/history/list")
    public TableDataInfo listZytjHistoryData(ZjzyStatisticsInfo zjzyStatisticsInfo) {
        startPage();
        List<ZytjHistoryInfo> list = zjzyFkInfoService.selectZytjRecords(zjzyStatisticsInfo);

        return getDataTable(list);
    }
}
