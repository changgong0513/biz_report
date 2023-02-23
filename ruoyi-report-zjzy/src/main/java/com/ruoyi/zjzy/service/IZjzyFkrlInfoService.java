package com.ruoyi.zjzy.service;

import java.util.List;
import com.ruoyi.zjzy.domain.ZjzyFkrlInfo;

/**
 * 付款认领Service接口
 * 
 * @author changgong0513
 * @date 2022-12-07
 */
public interface IZjzyFkrlInfoService 
{
    /**
     * 查询付款认领
     * 
     * @param fkrlId 付款认领主键
     * @return 付款认领
     */
    public ZjzyFkrlInfo selectZjzyFkrlInfoByFkrlId(String fkrlId);

    /**
     * 查询付款认领列表
     * 
     * @param zjzyFkrlInfo 付款认领
     * @return 付款认领集合
     */
    public List<ZjzyFkrlInfo> selectZjzyFkrlInfoList(ZjzyFkrlInfo zjzyFkrlInfo);

    /**
     * 新增付款认领
     * 
     * @param zjzyFkrlInfo 付款认领
     * @return 结果
     */
    public int insertZjzyFkrlInfo(ZjzyFkrlInfo zjzyFkrlInfo);

    /**
     * 修改付款认领
     * 
     * @param zjzyFkrlInfo 付款认领
     * @return 结果
     */
    public int updateZjzyFkrlInfo(ZjzyFkrlInfo zjzyFkrlInfo);

    /**
     * 批量删除付款认领
     * 
     * @param fkrlIds 需要删除的付款认领主键集合
     * @return 结果
     */
    public int deleteZjzyFkrlInfoByFkrlIds(String[] fkrlIds);

    /**
     * 删除付款认领信息
     * 
     * @param fkrlId 付款认领主键
     * @return 结果
     */
    public int deleteZjzyFkrlInfoByFkrlId(String fkrlId);

    /**
     * 根据部门编号，取得该部门的付款总额
     *
     * @param bmbh 部门编号
     * @return
     */
    public double getFkrlTotalByBmbh(final double bmbh);
}
