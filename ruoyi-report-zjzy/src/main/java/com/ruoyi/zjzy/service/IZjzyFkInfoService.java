package com.ruoyi.zjzy.service;

import java.util.List;
import com.ruoyi.zjzy.domain.ZjzyFkInfo;
import com.ruoyi.zjzy.domain.ZjzyStatisticsInfo;
import com.ruoyi.zjzy.domain.ZytjHistoryInfo;

/**
 * 付款Service接口
 * 
 * @author changgong0513
 * @date 2022-12-06
 */
public interface IZjzyFkInfoService 
{
    /**
     * 查询付款
     * 
     * @param fkId 付款主键
     * @return 付款
     */
    public ZjzyFkInfo selectZjzyFkInfoByFkId(String fkId);

    /**
     * 根据业务编号，查询付款数据
     *
     * @param fkBusinessId 付款主键
     * @return 付款
     */
    public ZjzyFkInfo selectZjzyFkInfoByFkBusinessId(String fkBusinessId);

    /**
     * 查询付款列表
     * 
     * @param zjzyFkInfo 付款
     * @return 付款集合
     */
    public List<ZjzyFkInfo> selectZjzyFkInfoList(ZjzyFkInfo zjzyFkInfo);

    /**
     * 取得付款总金额
     *
     * @return 结果
     */
    public double getFkrlTotal();

    /**
     * 新增付款
     * 
     * @param zjzyFkInfo 付款
     * @return 结果
     */
    public int insertZjzyFkInfo(ZjzyFkInfo zjzyFkInfo);

    /**
     * 修改付款
     * 
     * @param zjzyFkInfo 付款
     * @return 结果
     */
    public int updateZjzyFkInfo(ZjzyFkInfo zjzyFkInfo);

    /**
     * 批量删除付款
     * 
     * @param fkIds 需要删除的付款主键集合
     * @return 结果
     */
    public int deleteZjzyFkInfoByFkIds(String[] fkIds);

    /**
     * 删除付款信息
     * 
     * @param fkId 付款主键
     * @return 结果
     */
    public int deleteZjzyFkInfoByFkId(String fkId);

    /**
     * 查询占用统计列表
     *
     * @return
     */
    public List<ZjzyStatisticsInfo> selectZjzyStatisticsList(ZjzyStatisticsInfo zjzyStatisticsInfo);

    /**
     * 查询占用统计记录
     *
     * @return
     */
    public List<ZytjHistoryInfo> selectZytjRecords(ZjzyStatisticsInfo zjzyStatisticsInfo);

    /**
     * 添加占用统计记录
     *
     * @param zjzyStatisticsInfo
     * @return
     */
    public int insertZjzyStatisticsInfo(ZjzyStatisticsInfo zjzyStatisticsInfo);

    /**
     * 更新占用统计记录
     *
     * @param zjzyStatisticsInfo
     * @return
     */
    public int updateZjzyStatisticsInfo(ZjzyStatisticsInfo zjzyStatisticsInfo);

    /**
     * 占用统计利息总额
     *
     * @return 结果
     */
    public double getZytjLxTotal();

    /**
     * 根据部门编号，占用统计利息总额
     *
     * @param bmbh 部门编号
     * @return
     */
    public double getZytjLxTotalByBmbh(final double bmbh);
}
