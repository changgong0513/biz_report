package com.ruoyi.report.contract.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.report.contract.mapper.ContractApprovalInfoMapper;
import com.ruoyi.report.contract.domain.ContractApprovalInfo;
import com.ruoyi.report.contract.service.IContractApprovalInfoService;

/**
 * 合同管理-审批管理Service业务层处理
 * 
 * @author changgong
 * @date 2022-11-29
 */
@Service
public class ContractApprovalInfoServiceImpl implements IContractApprovalInfoService 
{
    @Autowired
    private ContractApprovalInfoMapper contractApprovalInfoMapper;

    /**
     * 查询合同管理-审批管理
     * 
     * @param approvalId 合同管理-审批管理主键
     * @return 合同管理-审批管理
     */
    @Override
    public ContractApprovalInfo selectContractApprovalInfoByApprovalId(String approvalId)
    {
        return contractApprovalInfoMapper.selectContractApprovalInfoByApprovalId(approvalId);
    }

    /**
     * 查询合同管理-审批管理列表
     * 
     * @param contractApprovalInfo 合同管理-审批管理
     * @return 合同管理-审批管理
     */
    @Override
    public List<ContractApprovalInfo> selectContractApprovalInfoList(ContractApprovalInfo contractApprovalInfo)
    {
        return contractApprovalInfoMapper.selectContractApprovalInfoList(contractApprovalInfo);
    }

    /**
     * 新增合同管理-审批管理
     * 
     * @param contractApprovalInfo 合同管理-审批管理
     * @return 结果
     */
    @Override
    public int insertContractApprovalInfo(ContractApprovalInfo contractApprovalInfo)
    {
        contractApprovalInfo.setCreateTime(DateUtils.getNowDate());
        return contractApprovalInfoMapper.insertContractApprovalInfo(contractApprovalInfo);
    }

    /**
     * 修改合同管理-审批管理
     * 
     * @param contractApprovalInfo 合同管理-审批管理
     * @return 结果
     */
    @Override
    public int updateContractApprovalInfo(ContractApprovalInfo contractApprovalInfo)
    {
        contractApprovalInfo.setUpdateTime(DateUtils.getNowDate());
        return contractApprovalInfoMapper.updateContractApprovalInfo(contractApprovalInfo);
    }

    /**
     * 批量删除合同管理-审批管理
     * 
     * @param approvalIds 需要删除的合同管理-审批管理主键
     * @return 结果
     */
    @Override
    public int deleteContractApprovalInfoByApprovalIds(String[] approvalIds)
    {
        return contractApprovalInfoMapper.deleteContractApprovalInfoByApprovalIds(approvalIds);
    }

    /**
     * 删除合同管理-审批管理信息
     * 
     * @param approvalId 合同管理-审批管理主键
     * @return 结果
     */
    @Override
    public int deleteContractApprovalInfoByApprovalId(String approvalId)
    {
        return contractApprovalInfoMapper.deleteContractApprovalInfoByApprovalId(approvalId);
    }
}
