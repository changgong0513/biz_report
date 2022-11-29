package com.ruoyi.report.contract.service;

import java.util.List;
import com.ruoyi.report.contract.domain.ContractApprovalInfo;

/**
 * 合同管理-审批管理Service接口
 * 
 * @author changgong
 * @date 2022-11-29
 */
public interface IContractApprovalInfoService 
{
    /**
     * 查询合同管理-审批管理
     * 
     * @param approvalId 合同管理-审批管理主键
     * @return 合同管理-审批管理
     */
    public ContractApprovalInfo selectContractApprovalInfoByApprovalId(String approvalId);

    /**
     * 查询合同管理-审批管理列表
     * 
     * @param contractApprovalInfo 合同管理-审批管理
     * @return 合同管理-审批管理集合
     */
    public List<ContractApprovalInfo> selectContractApprovalInfoList(ContractApprovalInfo contractApprovalInfo);

    /**
     * 合同管理-根据合同编号，取得合同审批数据
     *
     * @param contractId 合同管理-合同编号
     * @return 合同管理-审批管理
     */
    public ContractApprovalInfo getContractApprovalInfoByContractId(String contractId);

    /**
     * 新增合同管理-审批管理
     * 
     * @param contractApprovalInfo 合同管理-审批管理
     * @return 结果
     */
    public int insertContractApprovalInfo(ContractApprovalInfo contractApprovalInfo);

    /**
     * 修改合同管理-审批管理
     * 
     * @param contractApprovalInfo 合同管理-审批管理
     * @return 结果
     */
    public int updateContractApprovalInfo(ContractApprovalInfo contractApprovalInfo);

    /**
     * 批量删除合同管理-审批管理
     * 
     * @param approvalIds 需要删除的合同管理-审批管理主键集合
     * @return 结果
     */
    public int deleteContractApprovalInfoByApprovalIds(String[] approvalIds);

    /**
     * 删除合同管理-审批管理信息
     * 
     * @param approvalId 合同管理-审批管理主键
     * @return 结果
     */
    public int deleteContractApprovalInfoByApprovalId(String approvalId);
}
