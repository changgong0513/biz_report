package com.ruoyi.report.masterdata.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.report.masterdata.mapper.MasterDataWarehouseBaseInfoMapper;
import com.ruoyi.report.masterdata.domain.MasterDataWarehouseBaseInfo;
import com.ruoyi.report.masterdata.service.IMasterDataWarehouseBaseInfoService;

/**
 * 仓库管理Service业务层处理
 *
 * @author changgong0513
 * @date 2022-09-17
 */
@Service
public class MasterDataWarehouseBaseInfoServiceImpl implements IMasterDataWarehouseBaseInfoService
{
    @Autowired
    private MasterDataWarehouseBaseInfoMapper masterDataWarehouseBaseInfoMapper;

    /**
     * 查询仓库管理
     *
     * @param warehouseId 仓库管理主键
     * @return 仓库管理
     */
    @Override
    public MasterDataWarehouseBaseInfo selectMasterDataWarehouseBaseInfoByWarehouseId(Long warehouseId)
    {
        return masterDataWarehouseBaseInfoMapper.selectMasterDataWarehouseBaseInfoByWarehouseId(warehouseId);
    }

    /**
     * 查询仓库管理列表
     *
     * @param masterDataWarehouseBaseInfo 仓库管理
     * @return 仓库管理
     */
    @Override
    public List<MasterDataWarehouseBaseInfo> selectMasterDataWarehouseBaseInfoList(MasterDataWarehouseBaseInfo masterDataWarehouseBaseInfo)
    {
        return masterDataWarehouseBaseInfoMapper.selectMasterDataWarehouseBaseInfoList(masterDataWarehouseBaseInfo);
    }

    /**
     * 新增仓库管理
     *
     * @param masterDataWarehouseBaseInfo 仓库管理
     * @return 结果
     */
    @Override
    public int insertMasterDataWarehouseBaseInfo(MasterDataWarehouseBaseInfo masterDataWarehouseBaseInfo)
    {
        masterDataWarehouseBaseInfo.setCreateTime(DateUtils.getNowDate());
        return masterDataWarehouseBaseInfoMapper.insertMasterDataWarehouseBaseInfo(masterDataWarehouseBaseInfo);
    }

    /**
     * 修改仓库管理
     *
     * @param masterDataWarehouseBaseInfo 仓库管理
     * @return 结果
     */
    @Override
    public int updateMasterDataWarehouseBaseInfo(MasterDataWarehouseBaseInfo masterDataWarehouseBaseInfo)
    {
        masterDataWarehouseBaseInfo.setUpdateTime(DateUtils.getNowDate());
        return masterDataWarehouseBaseInfoMapper.updateMasterDataWarehouseBaseInfo(masterDataWarehouseBaseInfo);
    }

    /**
     * 批量删除仓库管理
     *
     * @param warehouseIds 需要删除的仓库管理主键
     * @return 结果
     */
    @Override
    public int deleteMasterDataWarehouseBaseInfoByWarehouseIds(Long[] warehouseIds)
    {
        return masterDataWarehouseBaseInfoMapper.deleteMasterDataWarehouseBaseInfoByWarehouseIds(warehouseIds);
    }

    /**
     * 删除仓库管理信息
     *
     * @param warehouseId 仓库管理主键
     * @return 结果
     */
    @Override
    public int deleteMasterDataWarehouseBaseInfoByWarehouseId(Long warehouseId)
    {
        return masterDataWarehouseBaseInfoMapper.deleteMasterDataWarehouseBaseInfoByWarehouseId(warehouseId);
    }
}
