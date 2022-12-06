package com.ruoyi.zjzy.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.zjzy.mapper.ZjzyFkrlInfoMapper;
import com.ruoyi.zjzy.domain.ZjzyFkrlInfo;
import com.ruoyi.zjzy.service.IZjzyFkrlInfoService;

/**
 * 付款认领Service业务层处理
 * 
 * @author changgong0513
 * @date 2022-12-07
 */
@Service
public class ZjzyFkrlInfoServiceImpl implements IZjzyFkrlInfoService 
{
    @Autowired
    private ZjzyFkrlInfoMapper zjzyFkrlInfoMapper;

    /**
     * 查询付款认领
     * 
     * @param fkrlId 付款认领主键
     * @return 付款认领
     */
    @Override
    public ZjzyFkrlInfo selectZjzyFkrlInfoByFkrlId(String fkrlId)
    {
        return zjzyFkrlInfoMapper.selectZjzyFkrlInfoByFkrlId(fkrlId);
    }

    /**
     * 查询付款认领列表
     * 
     * @param zjzyFkrlInfo 付款认领
     * @return 付款认领
     */
    @Override
    public List<ZjzyFkrlInfo> selectZjzyFkrlInfoList(ZjzyFkrlInfo zjzyFkrlInfo)
    {
        return zjzyFkrlInfoMapper.selectZjzyFkrlInfoList(zjzyFkrlInfo);
    }

    /**
     * 新增付款认领
     * 
     * @param zjzyFkrlInfo 付款认领
     * @return 结果
     */
    @Override
    public int insertZjzyFkrlInfo(ZjzyFkrlInfo zjzyFkrlInfo)
    {
        zjzyFkrlInfo.setCreateTime(DateUtils.getNowDate());
        return zjzyFkrlInfoMapper.insertZjzyFkrlInfo(zjzyFkrlInfo);
    }

    /**
     * 修改付款认领
     * 
     * @param zjzyFkrlInfo 付款认领
     * @return 结果
     */
    @Override
    public int updateZjzyFkrlInfo(ZjzyFkrlInfo zjzyFkrlInfo)
    {
        zjzyFkrlInfo.setUpdateTime(DateUtils.getNowDate());
        return zjzyFkrlInfoMapper.updateZjzyFkrlInfo(zjzyFkrlInfo);
    }

    /**
     * 批量删除付款认领
     * 
     * @param fkrlIds 需要删除的付款认领主键
     * @return 结果
     */
    @Override
    public int deleteZjzyFkrlInfoByFkrlIds(String[] fkrlIds)
    {
        return zjzyFkrlInfoMapper.deleteZjzyFkrlInfoByFkrlIds(fkrlIds);
    }

    /**
     * 删除付款认领信息
     * 
     * @param fkrlId 付款认领主键
     * @return 结果
     */
    @Override
    public int deleteZjzyFkrlInfoByFkrlId(String fkrlId)
    {
        return zjzyFkrlInfoMapper.deleteZjzyFkrlInfoByFkrlId(fkrlId);
    }
}
