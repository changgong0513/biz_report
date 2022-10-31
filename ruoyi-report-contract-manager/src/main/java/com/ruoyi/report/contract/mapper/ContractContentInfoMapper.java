package com.ruoyi.report.contract.mapper;

import java.util.List;
import com.ruoyi.report.contract.domain.ContractContentInfo;

/**
 * 合同管理Mapper接口
 * 
 * @author changgong
 * @date 2022-10-31
 */
public interface ContractContentInfoMapper 
{
    /**
     * 查询合同管理
     * 
     * @param contractId 合同管理主键
     * @return 合同管理
     */
    public ContractContentInfo selectContractContentInfoByContractId(String contractId);

    /**
     * 查询合同管理列表
     * 
     * @param contractContentInfo 合同管理
     * @return 合同管理集合
     */
    public List<ContractContentInfo> selectContractContentInfoList(ContractContentInfo contractContentInfo);

    /**
     * 新增合同管理
     * 
     * @param contractContentInfo 合同管理
     * @return 结果
     */
    public int insertContractContentInfo(ContractContentInfo contractContentInfo);

    /**
     * 修改合同管理
     * 
     * @param contractContentInfo 合同管理
     * @return 结果
     */
    public int updateContractContentInfo(ContractContentInfo contractContentInfo);

    /**
     * 删除合同管理
     * 
     * @param contractId 合同管理主键
     * @return 结果
     */
    public int deleteContractContentInfoByContractId(String contractId);

    /**
     * 批量删除合同管理
     * 
     * @param contractIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteContractContentInfoByContractIds(String[] contractIds);
}
