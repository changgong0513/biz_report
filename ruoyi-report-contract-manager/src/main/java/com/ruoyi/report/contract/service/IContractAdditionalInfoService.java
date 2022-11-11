package com.ruoyi.report.contract.service;

import java.util.List;
import com.ruoyi.report.contract.domain.ContractAdditionalInfo;

/**
 * contractService接口
 * 
 * @author changgong0513
 * @date 2022-11-01
 */
public interface IContractAdditionalInfoService 
{
    /**
     * 查询contract
     * 
     * @param additionalId contract主键
     * @return contract
     */
    public List<ContractAdditionalInfo> selectContractAdditionalInfoByContractId(String additionalId);

    /**
     * 根据订单编号，取得附件
     *
     * @param orderId
     * @return
     */
    public List<ContractAdditionalInfo> selectOrderAdditional(String orderId);

    /**
     * 查询contract列表
     * 
     * @param contractAdditionalInfo contract
     * @return contract集合
     */
    public List<ContractAdditionalInfo> selectContractAdditionalInfoList(ContractAdditionalInfo contractAdditionalInfo);

    /**
     * 新增contract
     * 
     * @param contractAdditionalInfo contract
     * @return 结果
     */
    public int insertContractAdditionalInfo(ContractAdditionalInfo contractAdditionalInfo);

    /**
     * 修改contract
     * 
     * @param contractAdditionalInfo contract
     * @return 结果
     */
    public int updateContractAdditionalInfo(ContractAdditionalInfo contractAdditionalInfo);

    /**
     * 批量删除contract
     * 
     * @param additionalIds 需要删除的contract主键集合
     * @return 结果
     */
    public int deleteContractAdditionalInfoByAdditionalIds(String[] additionalIds);

    /**
     * 删除contract信息
     * 
     * @param additionalId contract主键
     * @return 结果
     */
    public int deleteContractAdditionalInfoByAdditionalId(String additionalId);

    /**
     * 删除上传文件
     *
     * @param filePath
     * @return
     */
    public int deleteUploadFile(String filePath);
}
