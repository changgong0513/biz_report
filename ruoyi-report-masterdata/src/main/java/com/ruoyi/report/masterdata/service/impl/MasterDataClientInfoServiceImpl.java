package com.ruoyi.report.masterdata.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.report.masterdata.domain.MasterDataClientInfo;
import com.ruoyi.report.masterdata.mapper.MasterDataClientInfoMapper;
import com.ruoyi.report.masterdata.service.IMasterDataClientInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务报表Service业务层处理
 *
 * @author ruoyi
 * @date 2022-09-16
 */
@Service
public class MasterDataClientInfoServiceImpl implements IMasterDataClientInfoService
{
    @Autowired
    private MasterDataClientInfoMapper masterDataClientInfoMapper;

    /**
     * 查询业务报表
     *
     * @param baseId 业务报表主键
     * @return 业务报表
     */
    @Override
    public MasterDataClientInfo selectMasterDataClientInfoByBaseId(String baseId)
    {
        return masterDataClientInfoMapper.selectMasterDataClientInfoByBaseId(baseId);
    }

    /**
     * 查询业务报表列表
     *
     * @param masterDataClientInfo 业务报表
     * @return 业务报表
     */
    @Override
    public List<MasterDataClientInfo> selectMasterDataClientInfoList(MasterDataClientInfo masterDataClientInfo)
    {
        return masterDataClientInfoMapper.selectMasterDataClientInfoList(masterDataClientInfo);
    }

    /**
     * 新增业务报表
     *
     * @param masterDataClientInfo 业务报表
     * @return 结果
     */
    @Override
    public int insertMasterDataClientInfo(MasterDataClientInfo masterDataClientInfo)
    {
        masterDataClientInfo.setCreateTime(DateUtils.getNowDate());
        return masterDataClientInfoMapper.insertMasterDataClientInfo(masterDataClientInfo);
    }

    /**
     * 修改业务报表
     *
     * @param masterDataClientInfo 业务报表
     * @return 结果
     */
    @Override
    public int updateMasterDataClientInfo(MasterDataClientInfo masterDataClientInfo)
    {
        masterDataClientInfo.setUpdateTime(DateUtils.getNowDate());
        return masterDataClientInfoMapper.updateMasterDataClientInfo(masterDataClientInfo);
    }

    /**
     * 批量删除业务报表
     *
     * @param baseIds 需要删除的业务报表主键
     * @return 结果
     */
    @Override
    public int deleteMasterDataClientInfoByBaseIds(String[] baseIds)
    {
        return masterDataClientInfoMapper.deleteMasterDataClientInfoByBaseIds(baseIds);
    }

    /**
     * 删除业务报表信息
     *
     * @param baseId 业务报表主键
     * @return 结果
     */
    @Override
    public int deleteMasterDataClientInfoByBaseId(String baseId)
    {
        return masterDataClientInfoMapper.deleteMasterDataClientInfoByBaseId(baseId);
    }
}

