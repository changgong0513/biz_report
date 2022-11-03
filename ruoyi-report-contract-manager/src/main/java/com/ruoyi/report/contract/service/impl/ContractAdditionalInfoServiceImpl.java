package com.ruoyi.report.contract.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.report.contract.mapper.ContractAdditionalInfoMapper;
import com.ruoyi.report.contract.domain.ContractAdditionalInfo;
import com.ruoyi.report.contract.service.IContractAdditionalInfoService;

/**
 * contractService业务层处理
 * 
 * @author changgong0513
 * @date 2022-11-01
 */
@Service
public class ContractAdditionalInfoServiceImpl implements IContractAdditionalInfoService 
{
    @Autowired
    private ContractAdditionalInfoMapper contractAdditionalInfoMapper;

    /**
     * 查询contract
     * 
     * @param additionalId contract主键
     * @return contract
     */
    @Override
    public List<ContractAdditionalInfo> selectContractAdditionalInfoByContractId(String additionalId) {
        List<ContractAdditionalInfo> list = contractAdditionalInfoMapper.selectContractAdditionalInfoByContractId(additionalId);
        return list;
    }

    /**
     * 查询contract列表
     * 
     * @param contractAdditionalInfo contract
     * @return contract
     */
    @Override
    public List<ContractAdditionalInfo> selectContractAdditionalInfoList(ContractAdditionalInfo contractAdditionalInfo)
    {
        return contractAdditionalInfoMapper.selectContractAdditionalInfoList(contractAdditionalInfo);
    }

    /**
     * 新增contract
     * 
     * @param contractAdditionalInfo contract
     * @return 结果
     */
    @Override
    public int insertContractAdditionalInfo(ContractAdditionalInfo contractAdditionalInfo)
    {
        contractAdditionalInfo.setCreateTime(DateUtils.getNowDate());
        return contractAdditionalInfoMapper.insertContractAdditionalInfo(contractAdditionalInfo);
    }

    /**
     * 修改contract
     * 
     * @param contractAdditionalInfo contract
     * @return 结果
     */
    @Override
    public int updateContractAdditionalInfo(ContractAdditionalInfo contractAdditionalInfo)
    {
        contractAdditionalInfo.setUpdateTime(DateUtils.getNowDate());
        return contractAdditionalInfoMapper.updateContractAdditionalInfo(contractAdditionalInfo);
    }

    /**
     * 批量删除contract
     * 
     * @param additionalIds 需要删除的contract主键
     * @return 结果
     */
    @Override
    public int deleteContractAdditionalInfoByAdditionalIds(String[] additionalIds)
    {
        return contractAdditionalInfoMapper.deleteContractAdditionalInfoByAdditionalIds(additionalIds);
    }

    /**
     * 删除contract信息
     * 
     * @param additionalId contract主键
     * @return 结果
     */
    @Override
    public int deleteContractAdditionalInfoByAdditionalId(String additionalId)
    {
        return contractAdditionalInfoMapper.deleteContractAdditionalInfoByAdditionalId(additionalId);
    }
}
