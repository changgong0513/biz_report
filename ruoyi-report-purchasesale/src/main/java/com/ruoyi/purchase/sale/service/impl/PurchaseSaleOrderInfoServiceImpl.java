package com.ruoyi.purchase.sale.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ruoyi.common.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
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
    public List<PurchaseSaleOrderInfo> selectPurchaseOrderInfoUnionList(PurchaseSaleOrderInfo purchaseSaleOrderInfo) {

        List<PurchaseSaleOrderInfo> list = purchaseSaleOrderInfoMapper
                .selectPurchaseOrderInfoUnionList(purchaseSaleOrderInfo);
        Map<String, List<PurchaseSaleOrderInfo>> map = list.stream()
                .collect(Collectors.groupingBy(element -> element.getSupplierName()));

        List<PurchaseSaleOrderInfo> findPurchaseOrderList = new ArrayList<>();

        // 查询条件：合同数量
        int htslCounts = purchaseSaleOrderInfo.getHtsl();
        if (htslCounts > 0) {
            for (Map.Entry<String, List<PurchaseSaleOrderInfo>> entry:map.entrySet()){
                if (entry.getValue() != null && entry.getValue().size() == htslCounts) {
                    findPurchaseOrderList = entry.getValue();
                }
            }
        } else {
            findPurchaseOrderList = list;
        }

        return findPurchaseOrderList;
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
     * 取得采购合同总数
     *
     * @return 结果
     */
    public int getPurchaseContractCounts() {
        return purchaseSaleOrderInfoMapper.getPurchaseContractCounts();
    }

    /**
     * 取得销售合同总数
     *
     * @return 结果
     */
    public int getSaleContractCounts() {
        return purchaseSaleOrderInfoMapper.getSaleContractCounts();
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
