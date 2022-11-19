package com.ruoyi.report.masterdata.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.report.masterdata.mapper.MasterDataMaterialInfoMapper;
import com.ruoyi.report.masterdata.domain.MasterDataMaterialInfo;
import com.ruoyi.report.masterdata.service.IMasterDataMaterialInfoService;

/**
 * 主数据管理Service业务层处理
 * 
 * @author changgong0513
 * @date 2022-10-28
 */
@Service
public class MasterDataMaterialInfoServiceImpl implements IMasterDataMaterialInfoService 
{
    @Autowired
    private MasterDataMaterialInfoMapper masterDataMaterialInfoMapper;

    /**
     * 查询主数据管理
     * 
     * @param materialId 主数据管理主键
     * @return 主数据管理
     */
    @Override
    public MasterDataMaterialInfo selectMasterDataMaterialInfoByMaterialId(String materialId)
    {
        return masterDataMaterialInfoMapper.selectMasterDataMaterialInfoByMaterialId(materialId);
    }

    /**
     * 查询主数据管理列表
     * 
     * @param masterDataMaterialInfo 主数据管理
     * @return 主数据管理
     */
    @Override
    public List<MasterDataMaterialInfo> selectMasterDataMaterialInfoList(MasterDataMaterialInfo masterDataMaterialInfo)
    {
        return masterDataMaterialInfoMapper.selectMasterDataMaterialInfoList(masterDataMaterialInfo);
    }

    /**
     * 取得最大的物料编号
     *
     * @return 主数据管理
     */
    @Override
    public MasterDataMaterialInfo selectMaxMaterialId() {
        return masterDataMaterialInfoMapper.selectMaxMaterialId();
    }

    /**
     * 新增主数据管理
     * 
     * @param masterDataMaterialInfo 主数据管理
     * @return 结果
     */
    @Override
    public int insertMasterDataMaterialInfo(MasterDataMaterialInfo masterDataMaterialInfo)
    {
        masterDataMaterialInfo.setCreateTime(DateUtils.getNowDate());
        return masterDataMaterialInfoMapper.insertMasterDataMaterialInfo(masterDataMaterialInfo);
    }

    /**
     * 修改主数据管理
     * 
     * @param masterDataMaterialInfo 主数据管理
     * @return 结果
     */
    @Override
    public int updateMasterDataMaterialInfo(MasterDataMaterialInfo masterDataMaterialInfo)
    {
        masterDataMaterialInfo.setUpdateTime(DateUtils.getNowDate());
        return masterDataMaterialInfoMapper.updateMasterDataMaterialInfo(masterDataMaterialInfo);
    }

    /**
     * 批量删除主数据管理
     * 
     * @param materialIds 需要删除的主数据管理主键
     * @return 结果
     */
    @Override
    public int deleteMasterDataMaterialInfoByMaterialIds(String[] materialIds)
    {
        return masterDataMaterialInfoMapper.deleteMasterDataMaterialInfoByMaterialIds(materialIds);
    }

    /**
     * 删除主数据管理信息
     * 
     * @param materialId 主数据管理主键
     * @return 结果
     */
    @Override
    public int deleteMasterDataMaterialInfoByMaterialId(String materialId)
    {
        return masterDataMaterialInfoMapper.deleteMasterDataMaterialInfoByMaterialId(materialId);
    }
}
