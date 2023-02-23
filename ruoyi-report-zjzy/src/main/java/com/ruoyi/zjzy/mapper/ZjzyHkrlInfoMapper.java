package com.ruoyi.zjzy.mapper;

import com.ruoyi.zjzy.domain.ZjzyHkrlInfo;

import java.util.List;

/**
 * 回款认领Mapper接口
 * 
 * @author changgong0513
 * @date 2022-12-04
 */
public interface ZjzyHkrlInfoMapper 
{
    /**
     * 查询回款认领
     * 
     * @param hkrlId 回款认领主键
     * @return 回款认领
     */
    public ZjzyHkrlInfo selectZjzyHkrlInfoByHkrlId(Long hkrlId);

    /**
     * 查询回款认领列表
     * 
     * @param zjzyHkrlInfo 回款认领
     * @return 回款认领集合
     */
    public List<ZjzyHkrlInfo> selectZjzyHkrlInfoList(ZjzyHkrlInfo zjzyHkrlInfo);

    /**
     * 新增回款认领
     * 
     * @param zjzyHkrlInfo 回款认领
     * @return 结果
     */
    public int insertZjzyHkrlInfo(ZjzyHkrlInfo zjzyHkrlInfo);

    /**
     * 修改回款认领
     * 
     * @param zjzyHkrlInfo 回款认领
     * @return 结果
     */
    public int updateZjzyHkrlInfo(ZjzyHkrlInfo zjzyHkrlInfo);

    /**
     * 删除回款认领
     * 
     * @param hkrlId 回款认领主键
     * @return 结果
     */
    public int deleteZjzyHkrlInfoByHkrlId(Long hkrlId);

    /**
     * 批量删除回款认领
     * 
     * @param hkrlIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteZjzyHkrlInfoByHkrlIds(Long[] hkrlIds);

    /**
     * 根据部门编号，取得该部门的回款总额
     *
     * @param bmbh
     * @return
     */
    public double getHkrlTotalByBmbh(final double bmbh);
}
