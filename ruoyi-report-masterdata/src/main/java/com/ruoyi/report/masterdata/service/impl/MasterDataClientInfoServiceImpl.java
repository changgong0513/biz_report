package com.ruoyi.report.masterdata.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.report.masterdata.mapper.MasterDataClientInfoMapper;
import com.ruoyi.report.masterdata.domain.MasterDataClientInfo;
import com.ruoyi.report.masterdata.service.IMasterDataClientInfoService;

/**
 * 主数据管理Service业务层处理
 * 
 * @author changgong0513
 * @date 2022-10-18
 */
@Service
public class MasterDataClientInfoServiceImpl implements IMasterDataClientInfoService 
{
    @Autowired
    private MasterDataClientInfoMapper masterDataClientInfoMapper;

    /**
     * 查询主数据管理
     * 
     * @param baseId 主数据管理主键
     * @return 主数据管理
     */
    @Override
    public MasterDataClientInfo selectMasterDataClientInfoByBaseId(String baseId)
    {
        return masterDataClientInfoMapper.selectMasterDataClientInfoByBaseId(baseId);
    }

    /**
     * 查询主数据管理列表
     * 
     * @param masterDataClientInfo 主数据管理
     * @return 主数据管理
     */
    @Override
    public List<MasterDataClientInfo> selectMasterDataClientInfoList(MasterDataClientInfo masterDataClientInfo)
    {
        return masterDataClientInfoMapper.selectMasterDataClientInfoList(masterDataClientInfo);
    }

    /**
     * 根据导入物料名称，取得物料编码
     *
     * @param companyNames 需要取得的物料名称集合
     * @return 主数据管理集合
     */
    public List<MasterDataClientInfo> getClientIds(String[] companyNames) {
        return masterDataClientInfoMapper.getClientIds(companyNames);
    }

    /**
     * 新增主数据管理
     * 
     * @param masterDataClientInfo 主数据管理
     * @return 结果
     */
    @Override
    public int insertMasterDataClientInfo(MasterDataClientInfo masterDataClientInfo)
    {
        masterDataClientInfo.setCreateTime(DateUtils.getNowDate());
        return masterDataClientInfoMapper.insertMasterDataClientInfo(masterDataClientInfo);
    }

    /**
     * 修改主数据管理
     * 
     * @param masterDataClientInfo 主数据管理
     * @return 结果
     */
    @Override
    public int updateMasterDataClientInfo(MasterDataClientInfo masterDataClientInfo)
    {
        masterDataClientInfo.setUpdateTime(DateUtils.getNowDate());
        return masterDataClientInfoMapper.updateMasterDataClientInfo(masterDataClientInfo);
    }

    /**
     * 批量删除主数据管理
     * 
     * @param baseIds 需要删除的主数据管理主键
     * @return 结果
     */
    @Override
    public int deleteMasterDataClientInfoByBaseIds(String[] baseIds)
    {
        return masterDataClientInfoMapper.deleteMasterDataClientInfoByBaseIds(baseIds);
    }

    /**
     * 删除主数据管理信息
     * 
     * @param baseId 主数据管理主键
     * @return 结果
     */
    @Override
    public int deleteMasterDataClientInfoByBaseId(String baseId)
    {
        return masterDataClientInfoMapper.deleteMasterDataClientInfoByBaseId(baseId);
    }
}
