package com.xmy.web.controller.masterdata;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 主数据管理
 *
 * @author changgong0513
 */
@RestController
@RequestMapping("/md/client")
public class MasterDataClientController extends BaseController {

    /**
     * 新增供应商
     */
    @Log(title = "主数据管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add() {
        System.out.println("------新增供应商");
        return null;
    }
}

