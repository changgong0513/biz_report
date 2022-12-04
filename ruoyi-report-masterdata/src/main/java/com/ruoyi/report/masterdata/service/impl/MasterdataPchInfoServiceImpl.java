package com.ruoyi.report.masterdata.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.report.masterdata.domain.MasterdataPchInfo;
import com.ruoyi.report.masterdata.mapper.MasterdataPchInfoMapper;
import com.ruoyi.report.masterdata.service.IMasterdataPchInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 批次号管理Service业务层处理
 * 
 * @author changgong
 * @date 2022-12-04
 */
@Service
public class MasterdataPchInfoServiceImpl implements IMasterdataPchInfoService
{
    @Autowired
    private MasterdataPchInfoMapper masterdataPchInfoMapper;

    /**
     * 查询批次号管理
     * 
     * @param id 批次号管理主键
     * @return 批次号管理
     */
    @Override
    public MasterdataPchInfo selectMasterdataPchInfoById(String id)
    {
        return masterdataPchInfoMapper.selectMasterdataPchInfoById(id);
    }

    /**
     * 查询批次号管理列表
     * 
     * @param masterdataPchInfo 批次号管理
     * @return 批次号管理
     */
    @Override
    public List<MasterdataPchInfo> selectMasterdataPchInfoList(MasterdataPchInfo masterdataPchInfo)
    {
        return masterdataPchInfoMapper.selectMasterdataPchInfoList(masterdataPchInfo);
    }

    /**
     * 新增批次号管理
     * 
     * @param masterdataPchInfo 批次号管理
     * @return 结果
     */
    @Override
    public int insertMasterdataPchInfo(MasterdataPchInfo masterdataPchInfo)
    {
        masterdataPchInfo.setCreateTime(DateUtils.getNowDate());
        return masterdataPchInfoMapper.insertMasterdataPchInfo(masterdataPchInfo);
    }

    /**
     * 修改批次号管理
     * 
     * @param masterdataPchInfo 批次号管理
     * @return 结果
     */
    @Override
    public int updateMasterdataPchInfo(MasterdataPchInfo masterdataPchInfo)
    {
        masterdataPchInfo.setUpdateTime(DateUtils.getNowDate());
        return masterdataPchInfoMapper.updateMasterdataPchInfo(masterdataPchInfo);
    }

    /**
     * 批量删除批次号管理
     * 
     * @param ids 需要删除的批次号管理主键
     * @return 结果
     */
    @Override
    public int deleteMasterdataPchInfoByIds(String[] ids)
    {
        return masterdataPchInfoMapper.deleteMasterdataPchInfoByIds(ids);
    }

    /**
     * 删除批次号管理信息
     * 
     * @param id 批次号管理主键
     * @return 结果
     */
    @Override
    public int deleteMasterdataPchInfoById(String id)
    {
        return masterdataPchInfoMapper.deleteMasterdataPchInfoById(id);
    }
}
