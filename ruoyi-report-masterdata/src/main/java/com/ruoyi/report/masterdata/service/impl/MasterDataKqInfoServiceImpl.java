package com.ruoyi.report.masterdata.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.report.masterdata.mapper.MasterDataKqInfoMapper;
import com.ruoyi.report.masterdata.domain.MasterDataKqInfo;
import com.ruoyi.report.masterdata.service.IMasterDataKqInfoService;

/**
 * WarehouseService业务层处理
 * 
 * @author changgong0513
 * @date 2022-10-30
 */
@Service
public class MasterDataKqInfoServiceImpl implements IMasterDataKqInfoService 
{
    @Autowired
    private MasterDataKqInfoMapper masterDataKqInfoMapper;

    /**
     * 查询Warehouse
     * 
     * @param kqCode Warehouse主键
     * @return Warehouse
     */
    @Override
    public MasterDataKqInfo selectMasterDataKqInfoByKqCode(String kqCode)
    {
        return masterDataKqInfoMapper.selectMasterDataKqInfoByKqCode(kqCode);
    }

    /**
     * 查询Warehouse列表
     * 
     * @param masterDataKqInfo Warehouse
     * @return Warehouse
     */
    @Override
    public List<MasterDataKqInfo> selectMasterDataKqInfoList(MasterDataKqInfo masterDataKqInfo)
    {
        return masterDataKqInfoMapper.selectMasterDataKqInfoList(masterDataKqInfo);
    }

    /**
     * 新增Warehouse
     * 
     * @param masterDataKqInfo Warehouse
     * @return 结果
     */
    @Override
    public int insertMasterDataKqInfo(MasterDataKqInfo masterDataKqInfo)
    {
        masterDataKqInfo.setCreateTime(DateUtils.getNowDate());
        return masterDataKqInfoMapper.insertMasterDataKqInfo(masterDataKqInfo);
    }

    /**
     * 修改Warehouse
     * 
     * @param masterDataKqInfo Warehouse
     * @return 结果
     */
    @Override
    public int updateMasterDataKqInfo(MasterDataKqInfo masterDataKqInfo)
    {
        masterDataKqInfo.setUpdateTime(DateUtils.getNowDate());
        return masterDataKqInfoMapper.updateMasterDataKqInfo(masterDataKqInfo);
    }

    /**
     * 批量删除Warehouse
     * 
     * @param kqCodes 需要删除的Warehouse主键
     * @return 结果
     */
    @Override
    public int deleteMasterDataKqInfoByKqCodes(String[] kqCodes)
    {
        return masterDataKqInfoMapper.deleteMasterDataKqInfoByKqCodes(kqCodes);
    }

    /**
     * 删除Warehouse信息
     * 
     * @param kqCode Warehouse主键
     * @return 结果
     */
    @Override
    public int deleteMasterDataKqInfoByKqCode(String kqCode)
    {
        return masterDataKqInfoMapper.deleteMasterDataKqInfoByKqCode(kqCode);
    }
}
