package com.ruoyi.purchase.sale.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.purchase.sale.mapper.PurchaseSaleOrderInfoMapper;
import com.ruoyi.purchase.sale.domain.PurchaseSaleOrderInfo;
import com.ruoyi.purchase.sale.service.IPurchaseSaleOrderInfoService;

/**
 * 采购收货销售发货管理Service业务层处理
 * 
 * @author changgong0513
 * @date 2022-11-03
 */
@Service
public class PurchaseSaleOrderInfoServiceImpl implements IPurchaseSaleOrderInfoService 
{
    @Autowired
    private PurchaseSaleOrderInfoMapper purchaseSaleOrderInfoMapper;

    /**
     * 查询采购收货销售发货管理
     * 
     * @param contractId 采购收货销售发货管理主键
     * @return 采购收货销售发货管理
     */
    @Override
    public PurchaseSaleOrderInfo selectPurchaseSaleOrderInfoByContractId(String contractId) {
        return purchaseSaleOrderInfoMapper.selectPurchaseSaleOrderInfoByContractId(contractId);
    }

    /**
     * 查询采购收货销售发货管理列表
     * 
     * @param purchaseSaleOrderInfo 采购收货销售发货管理
     * @return 采购收货销售发货管理
     */
    @Override
    public List<PurchaseSaleOrderInfo> selectPurchaseSaleOrderInfoList(PurchaseSaleOrderInfo purchaseSaleOrderInfo) {
        return purchaseSaleOrderInfoMapper.selectPurchaseSaleOrderInfoList(purchaseSaleOrderInfo);
    }

    /**
     * 查询采购收货管理列表
     *
     * @param purchaseSaleOrderInfo 采购收货销售发货管理
     * @return 采购收货集合
     */
    @Override
    public List<PurchaseSaleOrderInfo> selectPurchaseOrderInfoUnionList(PurchaseSaleOrderInfo purchaseSaleOrderInfo)
    {
        return purchaseSaleOrderInfoMapper.selectPurchaseOrderInfoUnionList(purchaseSaleOrderInfo);
    }

    /**
     * 查询销售发货管理列表
     *
     * @param purchaseSaleOrderInfo 采购收货销售发货管理
     * @return 销售发货集合
     */
    @Override
    public List<PurchaseSaleOrderInfo> selectSaleOrderInfoUnionList(PurchaseSaleOrderInfo purchaseSaleOrderInfo)
    {
        return purchaseSaleOrderInfoMapper.selectSaleOrderInfoUnionList(purchaseSaleOrderInfo);
    }

    /**
     * 新增采购收货销售发货管理
     * 
     * @param purchaseSaleOrderInfo 采购收货销售发货管理
     * @return 结果
     */
    @Override
    public int insertPurchaseSaleOrderInfo(PurchaseSaleOrderInfo purchaseSaleOrderInfo)
    {
        purchaseSaleOrderInfo.setCreateTime(DateUtils.getNowDate());
        return purchaseSaleOrderInfoMapper.insertPurchaseSaleOrderInfo(purchaseSaleOrderInfo);
    }

    /**
     * 修改采购收货销售发货管理
     * 
     * @param purchaseSaleOrderInfo 采购收货销售发货管理
     * @return 结果
     */
    @Override
    public int updatePurchaseSaleOrderInfo(PurchaseSaleOrderInfo purchaseSaleOrderInfo)
    {
        purchaseSaleOrderInfo.setUpdateTime(DateUtils.getNowDate());
        return purchaseSaleOrderInfoMapper.updatePurchaseSaleOrderInfo(purchaseSaleOrderInfo);
    }

    /**
     * 批量删除采购收货销售发货管理
     * 
     * @param orderIds 需要删除的采购收货销售发货管理主键
     * @return 结果
     */
    @Override
    public int deletePurchaseSaleOrderInfoByOrderIds(String[] orderIds)
    {
        return purchaseSaleOrderInfoMapper.deletePurchaseSaleOrderInfoByOrderIds(orderIds);
    }

    /**
     * 删除采购收货销售发货管理信息
     * 
     * @param orderId 采购收货销售发货管理主键
     * @return 结果
     */
    @Override
    public int deletePurchaseSaleOrderInfoByOrderId(String orderId)
    {
        return purchaseSaleOrderInfoMapper.deletePurchaseSaleOrderInfoByOrderId(orderId);
    }
}
