package com.ruoyi.report.contract.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.report.contract.mapper.ContractApprovalRecordsInfoMapper;
import com.ruoyi.report.contract.domain.ContractApprovalRecordsInfo;
import com.ruoyi.report.contract.service.IContractApprovalRecordsInfoService;

/**
 * 合同管理-审批记录Service业务层处理
 * 
 * @author changgong
 * @date 2022-11-29
 */
@Service
public class ContractApprovalRecordsInfoServiceImpl implements IContractApprovalRecordsInfoService 
{
    @Autowired
    private ContractApprovalRecordsInfoMapper contractApprovalRecordsInfoMapper;

    /**
     * 查询合同管理-审批记录
     * 
     * @param approvalRecordId 合同管理-审批记录主键
     * @return 合同管理-审批记录
     */
    @Override
    public ContractApprovalRecordsInfo selectContractApprovalRecordsInfoByApprovalRecordId(String approvalRecordId)
    {
        return contractApprovalRecordsInfoMapper.selectContractApprovalRecordsInfoByApprovalRecordId(approvalRecordId);
    }

    /**
     * 查询合同管理-审批记录列表
     * 
     * @param contractApprovalRecordsInfo 合同管理-审批记录
     * @return 合同管理-审批记录
     */
    @Override
    public List<ContractApprovalRecordsInfo> selectContractApprovalRecordsInfoList(ContractApprovalRecordsInfo contractApprovalRecordsInfo)
    {
        return contractApprovalRecordsInfoMapper.selectContractApprovalRecordsInfoList(contractApprovalRecordsInfo);
    }

    /**
     * 根据审批编号和用户编号，取得审批记录
     *
     * @param contractApprovalRecordsInfo
     * @return
     */
    @Override
    public ContractApprovalRecordsInfo selectContractApprovalRecordsInfoByApprovalIdAndUserId(ContractApprovalRecordsInfo contractApprovalRecordsInfo) {
        return contractApprovalRecordsInfoMapper.selectContractApprovalRecordsInfoByApprovalIdAndUserId(contractApprovalRecordsInfo);
    }

    /**
     * 查询合同管理-根据审批编号，取得审批记录数据
     *
     * @param approvalId 审批编号
     * @return 合同管理-审批记录集合
     */
    public List<ContractApprovalRecordsInfo> getContractApprovalRecordsByApprovalId(String approvalId) {
        return contractApprovalRecordsInfoMapper.getContractApprovalRecordsByApprovalId(approvalId);
    }

    /**
     * 新增合同管理-审批记录
     * 
     * @param contractApprovalRecordsInfo 合同管理-审批记录
     * @return 结果
     */
    @Override
    public int insertContractApprovalRecordsInfo(ContractApprovalRecordsInfo contractApprovalRecordsInfo)
    {
        contractApprovalRecordsInfo.setCreateTime(DateUtils.getNowDate());
        return contractApprovalRecordsInfoMapper.insertContractApprovalRecordsInfo(contractApprovalRecordsInfo);
    }

    /**
     * 修改合同管理-审批记录
     * 
     * @param contractApprovalRecordsInfo 合同管理-审批记录
     * @return 结果
     */
    @Override
    public int updateContractApprovalRecordsInfo(ContractApprovalRecordsInfo contractApprovalRecordsInfo)
    {
        contractApprovalRecordsInfo.setUpdateTime(DateUtils.getNowDate());
        return contractApprovalRecordsInfoMapper.updateContractApprovalRecordsInfo(contractApprovalRecordsInfo);
    }

    /**
     * 批量删除合同管理-审批记录
     * 
     * @param approvalRecordIds 需要删除的合同管理-审批记录主键
     * @return 结果
     */
    @Override
    public int deleteContractApprovalRecordsInfoByApprovalRecordIds(String[] approvalRecordIds)
    {
        return contractApprovalRecordsInfoMapper.deleteContractApprovalRecordsInfoByApprovalRecordIds(approvalRecordIds);
    }

    /**
     * 删除合同管理-审批记录信息
     * 
     * @param approvalRecordId 合同管理-审批记录主键
     * @return 结果
     */
    @Override
    public int deleteContractApprovalRecordsInfoByApprovalRecordId(String approvalRecordId)
    {
        return contractApprovalRecordsInfoMapper.deleteContractApprovalRecordsInfoByApprovalRecordId(approvalRecordId);
    }
}
