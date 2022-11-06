package com.ruoyi.fpgl.service.impl;

import java.util.HashMap;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.fpgl.domain.FpglListInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fpgl.mapper.FpglMainInfoMapper;
import com.ruoyi.fpgl.domain.FpglMainInfo;
import com.ruoyi.fpgl.service.IFpglMainInfoService;

/**
 * 发票管理Service业务层处理
 * 
 * @author changgong0513
 * @date 2022-11-06
 */
@Service
public class FpglMainInfoServiceImpl implements IFpglMainInfoService 
{
    @Autowired
    private FpglMainInfoMapper fpglMainInfoMapper;

    /**
     * 查询发票管理
     * 
     * @param fpglDdbh 发票管理主键
     * @return 发票管理
     */
    @Override
    public FpglMainInfo selectFpglMainInfoByFpglDdbh(String fpglDdbh)
    {
        return fpglMainInfoMapper.selectFpglMainInfoByFpglDdbh(fpglDdbh);
    }

    /**
     * 查询发票管理列表
     * 
     * @param fpglMainInfo 发票管理
     * @return 发票管理
     */
    @Override
    public List<FpglMainInfo> selectFpglMainInfoList(FpglMainInfo fpglMainInfo)
    {
        return fpglMainInfoMapper.selectFpglMainInfoList(fpglMainInfo);
    }

    /**
     *
     *
     * @return
     */
    public List<FpglListInfo> selectFpglList(FpglListInfo fpglListInfo)
    {
        return fpglMainInfoMapper.selectFpglList(fpglListInfo);
    }


    /**
     * 新增发票管理
     * 
     * @param fpglMainInfo 发票管理
     * @return 结果
     */
    @Override
    public int insertFpglMainInfo(FpglMainInfo fpglMainInfo)
    {
        fpglMainInfo.setCreateTime(DateUtils.getNowDate());
        return fpglMainInfoMapper.insertFpglMainInfo(fpglMainInfo);
    }

    /**
     * 修改发票管理
     * 
     * @param fpglMainInfo 发票管理
     * @return 结果
     */
    @Override
    public int updateFpglMainInfo(FpglMainInfo fpglMainInfo)
    {
        fpglMainInfo.setUpdateTime(DateUtils.getNowDate());
        return fpglMainInfoMapper.updateFpglMainInfo(fpglMainInfo);
    }

    /**
     * 批量删除发票管理
     * 
     * @param fpglIds 需要删除的发票管理主键
     * @return 结果
     */
    @Override
    public int deleteFpglMainInfoByFpglIds(String[] fpglIds)
    {
        return fpglMainInfoMapper.deleteFpglMainInfoByFpglIds(fpglIds);
    }

    /**
     * 删除发票管理信息
     * 
     * @param fpglId 发票管理主键
     * @return 结果
     */
    @Override
    public int deleteFpglMainInfoByFpglId(String fpglId)
    {
        return fpglMainInfoMapper.deleteFpglMainInfoByFpglId(fpglId);
    }
}
