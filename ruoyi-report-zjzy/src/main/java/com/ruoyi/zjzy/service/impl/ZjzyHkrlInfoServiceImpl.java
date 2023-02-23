package com.ruoyi.zjzy.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.zjzy.domain.ZjzyHkrlInfo;
import com.ruoyi.zjzy.mapper.ZjzyHkrlInfoMapper;
import com.ruoyi.zjzy.service.IZjzyHkrlInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 回款认领Service业务层处理
 * 
 * @author changgong0513
 * @date 2022-12-04
 */
@Service
public class ZjzyHkrlInfoServiceImpl implements IZjzyHkrlInfoService
{
    @Autowired
    private ZjzyHkrlInfoMapper zjzyHkrlInfoMapper;

    /**
     * 查询回款认领
     * 
     * @param hkrlId 回款认领主键
     * @return 回款认领
     */
    @Override
    public ZjzyHkrlInfo selectZjzyHkrlInfoByHkrlId(Long hkrlId)
    {
        return zjzyHkrlInfoMapper.selectZjzyHkrlInfoByHkrlId(hkrlId);
    }

    /**
     * 查询回款认领列表
     * 
     * @param zjzyHkrlInfo 回款认领
     * @return 回款认领
     */
    @Override
    public List<ZjzyHkrlInfo> selectZjzyHkrlInfoList(ZjzyHkrlInfo zjzyHkrlInfo)
    {
        return zjzyHkrlInfoMapper.selectZjzyHkrlInfoList(zjzyHkrlInfo);
    }

    /**
     * 新增回款认领
     * 
     * @param zjzyHkrlInfo 回款认领
     * @return 结果
     */
    @Override
    public int insertZjzyHkrlInfo(ZjzyHkrlInfo zjzyHkrlInfo)
    {
        zjzyHkrlInfo.setCreateTime(DateUtils.getNowDate());
        return zjzyHkrlInfoMapper.insertZjzyHkrlInfo(zjzyHkrlInfo);
    }

    /**
     * 修改回款认领
     * 
     * @param zjzyHkrlInfo 回款认领
     * @return 结果
     */
    @Override
    public int updateZjzyHkrlInfo(ZjzyHkrlInfo zjzyHkrlInfo)
    {
        zjzyHkrlInfo.setUpdateTime(DateUtils.getNowDate());
        return zjzyHkrlInfoMapper.updateZjzyHkrlInfo(zjzyHkrlInfo);
    }

    /**
     * 批量删除回款认领
     * 
     * @param hkrlIds 需要删除的回款认领主键
     * @return 结果
     */
    @Override
    public int deleteZjzyHkrlInfoByHkrlIds(Long[] hkrlIds)
    {
        return zjzyHkrlInfoMapper.deleteZjzyHkrlInfoByHkrlIds(hkrlIds);
    }

    /**
     * 删除回款认领信息
     * 
     * @param hkrlId 回款认领主键
     * @return 结果
     */
    @Override
    public int deleteZjzyHkrlInfoByHkrlId(Long hkrlId)
    {
        return zjzyHkrlInfoMapper.deleteZjzyHkrlInfoByHkrlId(hkrlId);
    }

    /**
     * 根据部门编号，取得该部门的回款总额
     *
     * @param bmbh
     * @return
     */
    public double getHkrlTotalByBmbh(final double bmbh) {
        return zjzyHkrlInfoMapper.getHkrlTotalByBmbh(bmbh);
    }
}
