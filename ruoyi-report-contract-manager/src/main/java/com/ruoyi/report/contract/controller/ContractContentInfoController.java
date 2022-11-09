package com.ruoyi.report.contract.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.exception.file.FileNameLengthLimitExceededException;
import com.ruoyi.common.exception.file.InvalidExtensionException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.MimeTypeUtils;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.framework.config.ServerConfig;
import com.ruoyi.report.contract.domain.ContractAdditionalInfo;
import com.ruoyi.report.contract.domain.UploadData;
import com.ruoyi.report.contract.service.IContractAdditionalInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.report.contract.domain.ContractContentInfo;
import com.ruoyi.report.contract.service.IContractContentInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private IContractAdditionalInfoService contractAdditionalInfoService;

    /**
     * 查询合同管理列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ContractContentInfo contractContentInfo) {
        startPage();
        List<ContractContentInfo> list = contractContentInfoService.selectContractContentInfoList(contractContentInfo);
        return getDataTable(list);
    }

    /**
     * 导出合同管理列表
     */
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
    @GetMapping(value = "/{contractId}")
    public AjaxResult getInfo(@PathVariable("contractId") String contractId) {
        return AjaxResult.success(contractContentInfoService.selectContractContentInfoByContractId(contractId));
    }

    /**
     * 新增合同管理
     */
    @Log(title = "合同管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ContractContentInfo contractContentInfo) {
        contractContentInfo.setGoodsId(UUID.randomUUID().toString().replace("-", ""));
        contractContentInfo.setBizVersion(1L);
        contractContentInfo.setCreateTime(DateUtils.getNowDate());
        contractContentInfo.setUpdateTime(DateUtils.getNowDate());
        contractContentInfo.setCreateBy(SecurityUtils.getUsername());
        contractContentInfo.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(contractContentInfoService.insertContractContentInfo(contractContentInfo));
    }

    /**
     * 修改合同管理
     */
    @Log(title = "合同管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ContractContentInfo contractContentInfo) {
        return toAjax(contractContentInfoService.updateContractContentInfo(contractContentInfo));
    }

    /**
     * 删除合同管理
     */
    @Log(title = "合同管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{contractId}")
    public AjaxResult remove(@PathVariable String[] contractId) {
        return toAjax(contractContentInfoService.deleteContractContentInfoByContractIds(contractId));
    }

    /**
     * 从钉钉同步合同数据
     */
    @PostMapping("/sync")
    public AjaxResult sync(ContractContentInfo contractContentInfo) throws Exception {
        return toAjax(contractContentInfoService.syncContractContentInfo());
    }

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public AjaxResult uploadFile(@RequestParam("file") MultipartFile file, UploadData uploadData)
            throws InvalidExtensionException, IOException {

        String filePath = RuoYiConfig.getUploadPath();
        String fileName =  file.getOriginalFilename();
        File file_dir= new File(filePath);
        if(!file_dir.exists()) {
            file_dir.mkdirs();
        }

        int fileNamelength = file.getOriginalFilename().length();
        if (fileNamelength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
            throw new FileNameLengthLimitExceededException(FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
        }

        FileUploadUtils.assertAllowed(file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
        File desc = createAbsoluteFile(filePath, fileName);
        file.transferTo(desc);

        ContractAdditionalInfo contractAdditionalInfo = new ContractAdditionalInfo();
        contractAdditionalInfo.setBizVersion(1L);
        contractAdditionalInfo.setCreateTime(DateUtils.getNowDate());
        contractAdditionalInfo.setUpdateTime(DateUtils.getNowDate());
        contractAdditionalInfo.setCreateBy(SecurityUtils.getUsername());
        contractAdditionalInfo.setUpdateBy(SecurityUtils.getUsername());
        contractAdditionalInfo.setContractId(uploadData.getUploadContractId());

        if (fileName.lastIndexOf(".jpeg") > 0 || fileName.lastIndexOf(".png") > 0) {
            contractAdditionalInfo.setUploadImagePath(filePath + File.separator + fileName);
        } else {
            contractAdditionalInfo.setUplloadFilePath(filePath + File.separator + fileName);
        }

        contractAdditionalInfoService.insertContractAdditionalInfo(contractAdditionalInfo);

        return AjaxResult.success();
    }

    /**
     * 获取合同附件列表
     */
    @GetMapping(value = "/additional/{contractId}")
    public TableDataInfo getContractAdditional(@PathVariable("contractId") String contractId) {
        List<ContractAdditionalInfo> list = contractAdditionalInfoService.selectContractAdditionalInfoByContractId(contractId);
        return getDataTable(list);
    }

    /**
     * 删除合同附件
     */
    @GetMapping(value = "/del/{contractId}/additional/{additionalId}")
    public TableDataInfo delContractAdditional(@PathVariable("contractId") String contractId,
                                               @PathVariable("additionalId") String additionalId) {

        int delResult = contractAdditionalInfoService.deleteContractAdditionalInfoByAdditionalId(additionalId);
        toAjax(delResult);

        List<ContractAdditionalInfo> list = contractAdditionalInfoService.selectContractAdditionalInfoByContractId(contractId);
        return getDataTable(list);
    }



    private static final File createAbsoluteFile(String uploadDir, String fileName)
            throws IOException {

        File desc = new File(uploadDir + File.separator + fileName);
        if (!desc.getParentFile().exists()) {
            desc.getParentFile().mkdirs();
        }

        if (!desc.exists()) {
            desc.createNewFile();
        }

        return desc;
    }
}
