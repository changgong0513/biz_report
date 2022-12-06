package com.ruoyi.zjzy.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.zjzy.mapper.ZjzyFkInfoMapper;
import com.ruoyi.zjzy.domain.ZjzyFkInfo;
import com.ruoyi.zjzy.service.IZjzyFkInfoService;

/**
 * 付款Service业务层处理
 * 
 * @author changgong0513
 * @date 2022-12-06
 */
@Service
public class ZjzyFkInfoServiceImpl implements IZjzyFkInfoService 
{
    @Autowired
    private ZjzyFkInfoMapper zjzyFkInfoMapper;

    /**
     * 查询付款
     * 
     * @param fkId 付款主键
     * @return 付款
     */
    @Override
    public ZjzyFkInfo selectZjzyFkInfoByFkId(String fkId)
    {
        return zjzyFkInfoMapper.selectZjzyFkInfoByFkId(fkId);
    }

    /**
     * 根据业务编号，查询付款数据
     *
     * @param fkBusinessId 付款主键
     * @return 付款
     */
    public ZjzyFkInfo selectZjzyFkInfoByFkBusinessId(String fkBusinessId) {
        return zjzyFkInfoMapper.selectZjzyFkInfoByFkBusinessId(fkBusinessId);
    }

    /**
     * 查询付款列表
     * 
     * @param zjzyFkInfo 付款
     * @return 付款
     */
    @Override
    public List<ZjzyFkInfo> selectZjzyFkInfoList(ZjzyFkInfo zjzyFkInfo)
    {
        return zjzyFkInfoMapper.selectZjzyFkInfoList(zjzyFkInfo);
    }

    /**
     * 新增付款
     * 
     * @param zjzyFkInfo 付款
     * @return 结果
     */
    @Override
    public int insertZjzyFkInfo(ZjzyFkInfo zjzyFkInfo)
    {
        zjzyFkInfo.setCreateTime(DateUtils.getNowDate());
        return zjzyFkInfoMapper.insertZjzyFkInfo(zjzyFkInfo);
    }

    /**
     * 修改付款
     * 
     * @param zjzyFkInfo 付款
     * @return 结果
     */
    @Override
    public int updateZjzyFkInfo(ZjzyFkInfo zjzyFkInfo)
    {
        zjzyFkInfo.setUpdateTime(DateUtils.getNowDate());
        return zjzyFkInfoMapper.updateZjzyFkInfo(zjzyFkInfo);
    }

    /**
     * 批量删除付款
     * 
     * @param fkIds 需要删除的付款主键
     * @return 结果
     */
    @Override
    public int deleteZjzyFkInfoByFkIds(String[] fkIds)
    {
        return zjzyFkInfoMapper.deleteZjzyFkInfoByFkIds(fkIds);
    }

    /**
     * 删除付款信息
     * 
     * @param fkId 付款主键
     * @return 结果
     */
    @Override
    public int deleteZjzyFkInfoByFkId(String fkId)
    {
        return zjzyFkInfoMapper.deleteZjzyFkInfoByFkId(fkId);
    }
}
