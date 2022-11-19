package com.ruoyi.report.masterdata.mapper;

import java.util.List;
import com.ruoyi.report.masterdata.domain.MasterDataMaterialInfo;

/**
 * 主数据管理Mapper接口
 * 
 * @author changgong0513
 * @date 2022-10-28
 */
public interface MasterDataMaterialInfoMapper 
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
     * 删除主数据管理
     * 
     * @param materialId 主数据管理主键
     * @return 结果
     */
    public int deleteMasterDataMaterialInfoByMaterialId(String materialId);

    /**
     * 批量删除主数据管理
     * 
     * @param materialIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMasterDataMaterialInfoByMaterialIds(String[] materialIds);
}
