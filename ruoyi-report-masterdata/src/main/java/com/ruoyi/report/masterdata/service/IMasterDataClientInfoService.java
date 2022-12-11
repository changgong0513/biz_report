package com.ruoyi.report.masterdata.service;

import java.util.List;
import com.ruoyi.report.masterdata.domain.MasterDataClientInfo;

/**
 * 主数据管理Service接口
 * 
 * @author changgong0513
 * @date 2022-10-18
 */
public interface IMasterDataClientInfoService 
{
    /**
     * 查询主数据管理
     * 
     * @param baseId 主数据管理主键
     * @return 主数据管理
     */
    public MasterDataClientInfo selectMasterDataClientInfoByBaseId(String baseId);

    /**
     * 查询主数据管理列表
     * 
     * @param masterDataClientInfo 主数据管理
     * @return 主数据管理集合
     */
    public List<MasterDataClientInfo> selectMasterDataClientInfoList(MasterDataClientInfo masterDataClientInfo);

    /**
     * 根据导入物料名称，取得物料编码
     *
     * @param companyNames 需要取得的物料名称集合
     * @return 主数据管理集合
     */
    public List<MasterDataClientInfo> getClientIds(String[] companyNames);

    /**
     * 新增主数据管理
     * 
     * @param masterDataClientInfo 主数据管理
     * @return 结果
     */
    public int insertMasterDataClientInfo(MasterDataClientInfo masterDataClientInfo);

    /**
     * 修改主数据管理
     * 
     * @param masterDataClientInfo 主数据管理
     * @return 结果
     */
    public int updateMasterDataClientInfo(MasterDataClientInfo masterDataClientInfo);

    /**
     * 批量删除主数据管理
     * 
     * @param baseIds 需要删除的主数据管理主键集合
     * @return 结果
     */
    public int deleteMasterDataClientInfoByBaseIds(String[] baseIds);

    /**
     * 删除主数据管理信息
     * 
     * @param baseId 主数据管理主键
     * @return 结果
     */
    public int deleteMasterDataClientInfoByBaseId(String baseId);
}
