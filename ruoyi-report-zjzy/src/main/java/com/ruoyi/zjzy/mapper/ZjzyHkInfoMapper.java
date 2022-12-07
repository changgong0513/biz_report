package com.ruoyi.zjzy.mapper;

import com.ruoyi.zjzy.domain.ZjzyHkInfo;

import java.util.List;

/**
 * 回款认领Mapper接口
 * 
 * @author changgong0513
 * @date 2022-12-04
 */
public interface ZjzyHkInfoMapper 
{
    /**
     * 查询回款认领
     * 
     * @param hkId 回款认领主键
     * @return 回款认领
     */
    public ZjzyHkInfo selectZjzyHkInfoByHkId(String hkId);

    /**
     * 查询回款认领列表
     * 
     * @param zjzyHkInfo 回款认领
     * @return 回款认领集合
     */
    public List<ZjzyHkInfo> selectZjzyHkInfoList(ZjzyHkInfo zjzyHkInfo);

    /**
     * 取得回款总金额
     *
     * @return 结果
     */
    public double getHkrlTotal();

    /**
     * 新增回款认领
     * 
     * @param zjzyHkInfo 回款认领
     * @return 结果
     */
    public int insertZjzyHkInfo(ZjzyHkInfo zjzyHkInfo);

    /**
     * 修改回款认领
     * 
     * @param zjzyHkInfo 回款认领
     * @return 结果
     */
    public int updateZjzyHkInfo(ZjzyHkInfo zjzyHkInfo);

    /**
     * 删除回款认领
     * 
     * @param hkId 回款认领主键
     * @return 结果
     */
    public int deleteZjzyHkInfoByHkId(String hkId);

    /**
     * 批量删除回款认领
     * 
     * @param hkIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteZjzyHkInfoByHkIds(String[] hkIds);
}
