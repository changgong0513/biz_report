package com.ruoyi.report.contract.service;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.report.contract.domain.ContractContentInfo;
import com.taobao.api.ApiException;

/**
 * 合同管理Service接口
 * 
 * @author changgong
 * @date 2022-10-30
 */
public interface IContractContentInfoService {
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
     * 批量删除合同管理
     *
     * @param contractIds 需要删除的合同管理主键集合
     * @return 结果
     */
    public int deleteContractContentInfoByContractIds(String[] contractIds);

    /**
     * 删除合同管理信息
     *
     * @param contractId 合同管理主键
     * @return 结果
     */
    public int deleteContractContentInfoByContractId(String contractId);

    /**
     * 从钉钉同步合同数据
     *
     * @return 结果
     */
    public int syncContractContentInfo() throws Exception;

    /**
     * 从钉钉同步付款数据
     *
     * @return 结果
     */
    public int syncFkContractInfo() throws Exception;

    /**
     * 导入合同数据到采购表或者销售表
     *
     * @return 结果
     */
    public int importContractDataIntoPurchaseSaleTable(ContractContentInfo contract);

    /**
     * 导入合同数据
     *
     * @param contractList 合同数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importContract(List<ContractContentInfo> contractList, Boolean isUpdateSupport, String operName);
}
