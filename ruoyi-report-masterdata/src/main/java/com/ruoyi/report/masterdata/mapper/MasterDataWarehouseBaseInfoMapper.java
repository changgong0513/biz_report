package com.ruoyi.report.masterdata.mapper;

import java.util.List;
import com.ruoyi.report.masterdata.domain.MasterDataWarehouseBaseInfo;

/**
 * 仓库管理Mapper接口
 *
 * @author changgong0513
 * @date 2022-09-17
 */
public interface MasterDataWarehouseBaseInfoMapper
{
    /**
     * 查询仓库管理
     *
     * @param warehouseId 仓库管理主键
     * @return 仓库管理
     */
    public MasterDataWarehouseBaseInfo selectMasterDataWarehouseBaseInfoByWarehouseId(Long warehouseId);

    /**
     * 查询仓库管理列表
     *
     * @param masterDataWarehouseBaseInfo 仓库管理
     * @return 仓库管理集合
     */
    public List<MasterDataWarehouseBaseInfo> selectMasterDataWarehouseBaseInfoList(MasterDataWarehouseBaseInfo masterDataWarehouseBaseInfo);

    /**
     * 新增仓库管理
     *
     * @param masterDataWarehouseBaseInfo 仓库管理
     * @return 结果
     */
    public int insertMasterDataWarehouseBaseInfo(MasterDataWarehouseBaseInfo masterDataWarehouseBaseInfo);

    /**
     * 修改仓库管理
     *
     * @param masterDataWarehouseBaseInfo 仓库管理
     * @return 结果
     */
    public int updateMasterDataWarehouseBaseInfo(MasterDataWarehouseBaseInfo masterDataWarehouseBaseInfo);

    /**
     * 删除仓库管理
     *
     * @param warehouseId 仓库管理主键
     * @return 结果
     */
    public int deleteMasterDataWarehouseBaseInfoByWarehouseId(Long warehouseId);

    /**
     * 批量删除仓库管理
     *
     * @param warehouseIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMasterDataWarehouseBaseInfoByWarehouseIds(Long[] warehouseIds);
}
