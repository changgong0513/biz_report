package com.ruoyi.purchase.sale.service;

import java.util.List;
import com.ruoyi.purchase.sale.domain.SaleDeliverInfo;

/**
 * 发货管理Service接口
 * 
 * @author chanagong0513
 * @date 2022-11-05
 */
public interface ISaleDeliverInfoService 
{
    /**
     * 查询发货管理
     * 
     * @param deliverId 发货管理主键
     * @return 发货管理
     */
    public SaleDeliverInfo selectSaleDeliverInfoByDeliverId(String deliverId);

    /**
     * 查询发货管理列表
     * 
     * @param saleDeliverInfo 发货管理
     * @return 发货管理集合
     */
    public List<SaleDeliverInfo> selectSaleDeliverInfoList(SaleDeliverInfo saleDeliverInfo);

    /**
     * 取得最大的发货编号
     *
     * @return 发货管理
     */
    public SaleDeliverInfo selectMaxDeliverId();

    /**
     * 新增发货管理
     * 
     * @param saleDeliverInfo 发货管理
     * @return 结果
     */
    public int insertSaleDeliverInfo(SaleDeliverInfo saleDeliverInfo);

    /**
     * 修改发货管理
     * 
     * @param saleDeliverInfo 发货管理
     * @return 结果
     */
    public int updateSaleDeliverInfo(SaleDeliverInfo saleDeliverInfo);

    /**
     * 批量删除发货管理
     * 
     * @param deliverIds 需要删除的发货管理主键集合
     * @return 结果
     */
    public int deleteSaleDeliverInfoByDeliverIds(String[] deliverIds);

    /**
     * 删除发货管理信息
     * 
     * @param deliverId 发货管理主键
     * @return 结果
     */
    public int deleteSaleDeliverInfoByDeliverId(String deliverId);
}
