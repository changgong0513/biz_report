package com.ruoyi.report.contract.service;

import java.util.List;
import com.ruoyi.report.contract.domain.ContractApprovalRecordsInfo;

/**
 * 合同管理-审批记录Service接口
 * 
 * @author changgong
 * @date 2022-11-29
 */
public interface IContractApprovalRecordsInfoService 
{
    /**
     * 查询合同管理-审批记录
     * 
     * @param approvalRecordId 合同管理-审批记录主键
     * @return 合同管理-审批记录
     */
    public ContractApprovalRecordsInfo selectContractApprovalRecordsInfoByApprovalRecordId(String approvalRecordId);

    /**
     * 查询合同管理-审批记录列表
     * 
     * @param contractApprovalRecordsInfo 合同管理-审批记录
     * @return 合同管理-审批记录集合
     */
    public List<ContractApprovalRecordsInfo> selectContractApprovalRecordsInfoList(ContractApprovalRecordsInfo contractApprovalRecordsInfo);

    /**
     * 根据审批编号和用户编号，取得审批记录
     *
     * @param contractApprovalRecordsInfo
     * @return
     */
    public ContractApprovalRecordsInfo selectContractApprovalRecordsInfoByApprovalIdAndUserId(ContractApprovalRecordsInfo contractApprovalRecordsInfo);

    /**
     * 查询合同管理-根据审批编号，取得审批记录数据
     *
     * @param approvalId 审批编号
     * @return 合同管理-审批记录集合
     */
    public List<ContractApprovalRecordsInfo> getContractApprovalRecordsByApprovalId(String approvalId);
    /**
     * 新增合同管理-审批记录
     * 
     * @param contractApprovalRecordsInfo 合同管理-审批记录
     * @return 结果
     */
    public int insertContractApprovalRecordsInfo(ContractApprovalRecordsInfo contractApprovalRecordsInfo);

    /**
     * 修改合同管理-审批记录
     * 
     * @param contractApprovalRecordsInfo 合同管理-审批记录
     * @return 结果
     */
    public int updateContractApprovalRecordsInfo(ContractApprovalRecordsInfo contractApprovalRecordsInfo);

    /**
     * 批量删除合同管理-审批记录
     * 
     * @param approvalRecordIds 需要删除的合同管理-审批记录主键集合
     * @return 结果
     */
    public int deleteContractApprovalRecordsInfoByApprovalRecordIds(String[] approvalRecordIds);

    /**
     * 删除合同管理-审批记录信息
     * 
     * @param approvalRecordId 合同管理-审批记录主键
     * @return 结果
     */
    public int deleteContractApprovalRecordsInfoByApprovalRecordId(String approvalRecordId);
}
