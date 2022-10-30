package com.ruoyi.report.contract.service;

import java.util.List;
import com.ruoyi.report.contract.domain.ContractContentInfo;
import com.taobao.api.ApiException;

/**
 * 合同管理Service接口
 * 
 * @author changgong
 * @date 2022-10-30
 */
public interface IContractContentInfoService 
{
    /**
     * 查询合同管理
     * 
     * @param goodsId 合同管理主键
     * @return 合同管理
     */
    public ContractContentInfo selectContractContentInfoByGoodsId(String goodsId);

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
     * @param goodsIds 需要删除的合同管理主键集合
     * @return 结果
     */
    public int deleteContractContentInfoByGoodsIds(String[] goodsIds);

    /**
     * 删除合同管理信息
     * 
     * @param goodsId 合同管理主键
     * @return 结果
     */
    public int deleteContractContentInfoByGoodsId(String goodsId);

    /**
     * 从钉钉同步合同数据
     *
     * @return 结果
     */
    public int syncContractContentInfo() throws Exception;
}
