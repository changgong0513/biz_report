package com.ruoyi.report.contract.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.exception.file.FileNameLengthLimitExceededException;
import com.ruoyi.common.exception.file.InvalidExtensionException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.MimeTypeUtils;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.report.contract.domain.*;
import com.ruoyi.report.contract.service.IContractAdditionalInfoService;
import com.ruoyi.report.contract.service.IContractApprovalInfoService;
import com.ruoyi.report.contract.service.IContractApprovalRecordsInfoService;
import com.ruoyi.report.masterdata.domain.MasterDataMaterialInfo;
import com.ruoyi.report.masterdata.mapper.MasterDataMaterialInfoMapper;
import com.ruoyi.report.masterdata.service.IMasterDataMaterialInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
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

    @Autowired
    private IContractApprovalInfoService contractApprovalInfoService;

    @Autowired
    private IContractApprovalRecordsInfoService contractApprovalRecordsInfoService;

    @Autowired
    private IMasterDataMaterialInfoService masterDataMaterialInfoService;

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

        int retCode = 1;

        contractContentInfo.setGoodsId(UUID.randomUUID().toString().replace("-", ""));

        String actionType = contractContentInfo.getContractActionType();
        if (StringUtils.equals(actionType, "1")) {
            // 保存合同合同数据
            contractContentInfo.setBizVersion(1L);
            contractContentInfo.setCreateTime(DateUtils.getNowDate());
            contractContentInfo.setUpdateTime(DateUtils.getNowDate());
            contractContentInfo.setCreateBy(SecurityUtils.getUsername());
            contractContentInfo.setUpdateBy(SecurityUtils.getUsername());
            retCode = contractContentInfoService.insertContractContentInfo(contractContentInfo);
        } else {
            // 根据合同数据，导入到采购表或者销售表
            retCode = contractContentInfoService
                    .importContractDataIntoPurchaseSaleTable(contractContentInfo);
        }

        return toAjax(retCode);
    }

    /**
     * 修改合同管理
     */
    @Log(title = "合同管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ContractContentInfo contractContentInfo) {

        int retCode = 1;

        contractContentInfo.setUpdateTime(DateUtils.getNowDate());
        contractContentInfo.setUpdateBy(SecurityUtils.getUsername());

        String actionType = contractContentInfo.getContractActionType();
        if (StringUtils.equals(actionType, "1")) {
            retCode = contractContentInfoService
                    .updateContractContentInfo(contractContentInfo);
        } else {
            retCode = contractContentInfoService
                    .importContractDataIntoPurchaseSaleTable(contractContentInfo);
        }

        return toAjax(retCode);
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
     * 从钉钉同步合同数据
     */
    @PostMapping("/fk/sync")
    public AjaxResult fkSync() throws Exception {
        return toAjax(contractContentInfoService.syncFkContractInfo());
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
     * 根据订单编号，取得附件
     */
    @GetMapping(value = "/order/additional/{orderId}")
    public TableDataInfo getOrderAdditional(@PathVariable("orderId") String orderId) {
        List<ContractAdditionalInfo> list = contractAdditionalInfoService.selectOrderAdditional(orderId);
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

    /**
     * 导入合同模板下载。
     *
     * @param response
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<ContractContentInfo> util = new ExcelUtil<ContractContentInfo>(ContractContentInfo.class);
        util.importTemplateExcel(response, "合同数据");
    }

    /**
     * 导入合同数据.
     *
     * @param file
     * @param updateSupport
     * @return
     * @throws Exception
     */
    @Log(title = "合同管理", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<ContractContentInfo> util = new ExcelUtil<ContractContentInfo>(ContractContentInfo.class);
        List<ContractContentInfo> contractList = util.importExcel(file.getInputStream());
        List<String> materialNameList = contractList
                .parallelStream()
                .filter(element -> StringUtils.isNotBlank(element.getGoodsName()))
                .map(ContractContentInfo::getGoodsName).collect(Collectors.toList());

        List<MasterDataMaterialInfo> materialIdList =  masterDataMaterialInfoService
                .getMaterialIds(materialNameList.toArray(new String[materialNameList.size()]));

        Map<String, Integer> materialMap = materialIdList
                .stream()
                .collect(Collectors.toMap(MasterDataMaterialInfo::getMaterialName, MasterDataMaterialInfo::getMaterialId));

        contractList.stream().forEach(element -> {
            if (materialMap.containsKey(element.getGoodsName())) {
                element.setGoodsId(materialMap.get(element.getGoodsName()).toString());
            }

            element.setContractStatus("IMPORT");
        });

        String operName = getUsername();
        String message = contractContentInfoService.importContract(contractList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    /**
     * 根据合同编号，取得合同审批审批数据
     */
    @GetMapping(value = "/approval/{contractId}")
    public AjaxResult getContractApprovalInfoByContractId(@PathVariable("contractId") String contractId) {

        ContractApprovalInfo contractApprovalInfo = contractApprovalInfoService
                .getContractApprovalInfoByContractId(contractId);

        List<ContractApprovalRecordsInfo> list = null;
        if (contractApprovalInfo != null && StringUtils.isNotBlank(contractApprovalInfo.getApprovalId())) {
            list = contractApprovalRecordsInfoService
                    .getContractApprovalRecordsByApprovalId(contractApprovalInfo.getApprovalId());

            contractApprovalInfo.setApprovalRecordList(list);
        }

        return AjaxResult.success(contractApprovalInfo);
    }

    /**
     * 根据审批编号，取得审批记录数据
     */
    @GetMapping(value = "/approval/record/{approvalId}")
    public TableDataInfo getContractApprovalRecordsByApprovalId(@PathVariable("approvalId") String approvalId) {
        List<ContractApprovalRecordsInfo> list = contractApprovalRecordsInfoService
                .getContractApprovalRecordsByApprovalId(approvalId);
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
