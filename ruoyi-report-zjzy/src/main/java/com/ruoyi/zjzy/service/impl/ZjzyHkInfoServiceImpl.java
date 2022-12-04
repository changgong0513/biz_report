package com.ruoyi.zjzy.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.zjzy.domain.ZjzyHkInfo;
import com.ruoyi.zjzy.mapper.ZjzyHkInfoMapper;
import com.ruoyi.zjzy.service.IZjzyHkInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 回款认领Service业务层处理
 * 
 * @author changgong0513
 * @date 2022-12-04
 */
@Service
public class ZjzyHkInfoServiceImpl implements IZjzyHkInfoService
{
    @Autowired
    private ZjzyHkInfoMapper zjzyHkInfoMapper;

    /**
     * 查询回款认领
     * 
     * @param hkId 回款认领主键
     * @return 回款认领
     */
    @Override
    public ZjzyHkInfo selectZjzyHkInfoByHkId(String hkId)
    {
        return zjzyHkInfoMapper.selectZjzyHkInfoByHkId(hkId);
    }

    /**
     * 查询回款认领列表
     * 
     * @param zjzyHkInfo 回款认领
     * @return 回款认领
     */
    @Override
    public List<ZjzyHkInfo> selectZjzyHkInfoList(ZjzyHkInfo zjzyHkInfo)
    {
        return zjzyHkInfoMapper.selectZjzyHkInfoList(zjzyHkInfo);
    }

    /**
     * 新增回款认领
     * 
     * @param zjzyHkInfo 回款认领
     * @return 结果
     */
    @Override
    public int insertZjzyHkInfo(ZjzyHkInfo zjzyHkInfo)
    {
        zjzyHkInfo.setCreateTime(DateUtils.getNowDate());
        return zjzyHkInfoMapper.insertZjzyHkInfo(zjzyHkInfo);
    }

    /**
     * 修改回款认领
     * 
     * @param zjzyHkInfo 回款认领
     * @return 结果
     */
    @Override
    public int updateZjzyHkInfo(ZjzyHkInfo zjzyHkInfo)
    {
        zjzyHkInfo.setUpdateTime(DateUtils.getNowDate());
        return zjzyHkInfoMapper.updateZjzyHkInfo(zjzyHkInfo);
    }

    /**
     * 批量删除回款认领
     * 
     * @param hkIds 需要删除的回款认领主键
     * @return 结果
     */
    @Override
    public int deleteZjzyHkInfoByHkIds(String[] hkIds)
    {
        return zjzyHkInfoMapper.deleteZjzyHkInfoByHkIds(hkIds);
    }

    /**
     * 删除回款认领信息
     * 
     * @param hkId 回款认领主键
     * @return 结果
     */
    @Override
    public int deleteZjzyHkInfoByHkId(String hkId)
    {
        return zjzyHkInfoMapper.deleteZjzyHkInfoByHkId(hkId);
    }
}
