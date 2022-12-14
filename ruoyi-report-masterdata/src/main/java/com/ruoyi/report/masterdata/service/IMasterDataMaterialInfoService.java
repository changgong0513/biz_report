package com.ruoyi.report.masterdata.service;

import java.util.List;
import com.ruoyi.report.masterdata.domain.MasterDataMaterialInfo;

/**
 * 主数据管理Service接口
 * 
 * @author changgong0513
 * @date 2022-10-28
 */
public interface IMasterDataMaterialInfoService 
{
    /**
     * 查询主数据管理
     * 
     * @param materialId 主数据管理主键
     * @return 主数据管理
     */
    public MasterDataMaterialInfo selectMasterDataMaterialInfoByMaterialId(String materialId);

    /**
     * 查询主数据管理列表
     * 
     * @param masterDataMaterialInfo 主数据管理
     * @return 主数据管理集合
     */
    public List<MasterDataMaterialInfo> selectMasterDataMaterialInfoList(MasterDataMaterialInfo masterDataMaterialInfo);

    /**
     * 取得最大的物料编号
     *
     * @return 主数据管理
     */
    public MasterDataMaterialInfo selectMaxMaterialId();

    /**
     * 根据导入物料名称，取得物料编码
     *
     * @param materialNames 需要取得的物料名称集合
     * @return 主数据管理集合
     */
    public List<MasterDataMaterialInfo> getMaterialIds(String[] materialNames);

    /**
     * 新增主数据管理
     * 
     * @param masterDataMaterialInfo 主数据管理
     * @return 结果
     */
    public int insertMasterDataMaterialInfo(MasterDataMaterialInfo masterDataMaterialInfo);

    /**
     * 修改主数据管理
     * 
     * @param masterDataMaterialInfo 主数据管理
     * @return 结果
     */
    public int updateMasterDataMaterialInfo(MasterDataMaterialInfo masterDataMaterialInfo);

    /**
     * 批量删除主数据管理
     * 
     * @param materialIds 需要删除的主数据管理主键集合
     * @return 结果
     */
    public int deleteMasterDataMaterialInfoByMaterialIds(String[] materialIds);

    /**
     * 删除主数据管理信息
     * 
     * @param materialId 主数据管理主键
     * @return 结果
     */
    public int deleteMasterDataMaterialInfoByMaterialId(String materialId);
}
