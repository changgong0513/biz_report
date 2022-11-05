package com.ruoyi.kcdb.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.kcdb.mapper.KcdbMainInfoMapper;
import com.ruoyi.kcdb.domain.KcdbMainInfo;
import com.ruoyi.kcdb.service.IKcdbMainInfoService;

/**
 * 存库调拨Service业务层处理
 * 
 * @author changgong0513
 * @date 2022-11-05
 */
@Service
public class KcdbMainInfoServiceImpl implements IKcdbMainInfoService 
{
    @Autowired
    private KcdbMainInfoMapper kcdbMainInfoMapper;

    /**
     * 查询存库调拨
     * 
     * @param dh 存库调拨主键
     * @return 存库调拨
     */
    @Override
    public KcdbMainInfo selectKcdbMainInfoByDh(String dh)
    {
        return kcdbMainInfoMapper.selectKcdbMainInfoByDh(dh);
    }

    /**
     * 查询存库调拨列表
     * 
     * @param kcdbMainInfo 存库调拨
     * @return 存库调拨
     */
    @Override
    public List<KcdbMainInfo> selectKcdbMainInfoList(KcdbMainInfo kcdbMainInfo)
    {
        return kcdbMainInfoMapper.selectKcdbMainInfoList(kcdbMainInfo);
    }

    /**
     * 新增存库调拨
     * 
     * @param kcdbMainInfo 存库调拨
     * @return 结果
     */
    @Override
    public int insertKcdbMainInfo(KcdbMainInfo kcdbMainInfo)
    {
        kcdbMainInfo.setCreateTime(DateUtils.getNowDate());
        return kcdbMainInfoMapper.insertKcdbMainInfo(kcdbMainInfo);
    }

    /**
     * 修改存库调拨
     * 
     * @param kcdbMainInfo 存库调拨
     * @return 结果
     */
    @Override
    public int updateKcdbMainInfo(KcdbMainInfo kcdbMainInfo)
    {
        kcdbMainInfo.setUpdateTime(DateUtils.getNowDate());
        return kcdbMainInfoMapper.updateKcdbMainInfo(kcdbMainInfo);
    }

    /**
     * 批量删除存库调拨
     * 
     * @param dhs 需要删除的存库调拨主键
     * @return 结果
     */
    @Override
    public int deleteKcdbMainInfoByDhs(String[] dhs)
    {
        return kcdbMainInfoMapper.deleteKcdbMainInfoByDhs(dhs);
    }

    /**
     * 删除存库调拨信息
     * 
     * @param dh 存库调拨主键
     * @return 结果
     */
    @Override
    public int deleteKcdbMainInfoByDh(String dh)
    {
        return kcdbMainInfoMapper.deleteKcdbMainInfoByDh(dh);
    }
}
