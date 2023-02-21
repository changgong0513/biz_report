package com.ruoyi.purchase.sale.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.fpgl.domain.FpglMainInfo;
import com.ruoyi.fpgl.mapper.FpglMainInfoMapper;
import com.ruoyi.report.masterdata.domain.MasterDataClientInfo;
import com.ruoyi.report.masterdata.mapper.MasterDataClientInfoMapper;
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

    @Autowired
    private FpglMainInfoMapper fpglMainInfoMapper;

    @Autowired
    private MasterDataClientInfoMapper masterDataClientInfoMapper;

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

        // 根据客户编号，取得客户名称
        findPurchaseOrderList.stream().forEach(elment -> {
            String supplierName = elment.getSupplierName();
            MasterDataClientInfo supplierData = masterDataClientInfoMapper
                    .selectMasterDataClientInfoByBaseId(supplierName);
            String supplierRealName = supplierData.getCompanyName();
            elment.setSupplierRealName(supplierRealName);
        });

        return findPurchaseOrderList;
    }

    /**
     * 查询销售发货管理列表
     *
     * @param purchaseSaleOrderInfo 采购收货销售发货管理
     * @return 销售发货集合
     */
    @Override
    public List<PurchaseSaleOrderInfo> selectSaleOrderInfoUnionList(PurchaseSaleOrderInfo purchaseSaleOrderInfo) {

        List<PurchaseSaleOrderInfo> list = purchaseSaleOrderInfoMapper.selectSaleOrderInfoUnionList(purchaseSaleOrderInfo);

        Map<String, List<PurchaseSaleOrderInfo>> map = list.stream()
                .collect(Collectors.groupingBy(element -> element.getSupplierName()));

        // 根据客户编号，取得客户名称
        list.stream().forEach(elment -> {
            String supplierName = elment.getSupplierName();
            MasterDataClientInfo supplierData = masterDataClientInfoMapper
                    .selectMasterDataClientInfoByBaseId(supplierName);
            String supplierRealName = supplierData.getCompanyName();
            elment.setSupplierRealName(supplierRealName);
        });

        return list;
    }

    /**
     * 取得采购合同总数
     *
     * @return 结果
     */
    public int getPurchaseContractCounts(String belongDept) {
        return purchaseSaleOrderInfoMapper.getPurchaseContractCounts(belongDept);
    }

    /**
     * 取得销售合同总数
     *
     * @return 结果
     */
    public int getSaleContractCounts(String belongDept) {
        return purchaseSaleOrderInfoMapper.getSaleContractCounts(belongDept);
    }

    /**
     * 新增采购收货销售发货管理
     * 
     * @param purchaseSaleOrderInfo 采购收货销售发货管理
     * @return 结果
     */
    @Override
    public int insertPurchaseSaleOrderInfo(PurchaseSaleOrderInfo purchaseSaleOrderInfo) {
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
    public int updatePurchaseSaleOrderInfo(PurchaseSaleOrderInfo purchaseSaleOrderInfo) {

        purchaseSaleOrderInfo.setUpdateTime(DateUtils.getNowDate());
        int result = purchaseSaleOrderInfoMapper.updatePurchaseSaleOrderInfo(purchaseSaleOrderInfo);
        if (result > 0) {
            FpglMainInfo data = fpglMainInfoMapper.selectFpglMainInfoByFpglDdbh(purchaseSaleOrderInfo.getOrderId());
            if (purchaseSaleOrderInfo.getIsInvoicing() == 1) {
                // 开票
                if (data == null) {
                    // 未查询到该订单编号的发票数据
                    FpglMainInfo fpglMainInfo = new FpglMainInfo();
                    fpglMainInfo.setFpglKpmx(purchaseSaleOrderInfo.getMaterialName());
                    fpglMainInfo.setFpglKpsl(0L);
                    fpglMainInfo.setFpglKpdj(BigDecimal.ZERO);
                    fpglMainInfo.setFpglKpje(BigDecimal.ZERO);
                    fpglMainInfo.setFpglFpzt("3");
                    fpglMainInfo.setFpglDdbh(purchaseSaleOrderInfo.getOrderId());
                    fpglMainInfo.setFpglSqr(SecurityUtils.getUsername());
                    fpglMainInfo.setCreateTime(DateUtils.getNowDate());
                    fpglMainInfo.setUpdateTime(DateUtils.getNowDate());
                    fpglMainInfo.setCreateBy(SecurityUtils.getUsername());
                    fpglMainInfo.setUpdateBy(SecurityUtils.getUsername());
                    result = fpglMainInfoMapper.insertFpglMainInfo(fpglMainInfo);
                }
            } else {
                // 不开票
                if (data != null) {
                    if (!StringUtils.equals(data.getFpglFpzt(), "3")) {
                        result = 100;
                    } else {
                        result = fpglMainInfoMapper.deleteFpglMainInfoByFpglId(data.getFpglId());
                    }
                }
            }
        }

        return result;
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

    /**
     * 设置采购订单状态和完成率
     *
     * @param list
     * @param contractType
     */
    @Override
    public void setPurchaseOrderStatusAndCompletionRate(List<PurchaseSaleOrderInfo> list, final String contractType) {

        if (list == null || list.size() == 0) {
            return;
        }

        // 设置合同状态和完成率
        list.stream().forEach(element -> {
            // 采购数量（来自于采购管理）
            long purchaseQuantity = element.getPurchaseQuantity();
            // 核算数量（来自于收货管理）
            long checkQuantity = element.getCheckQuantity();
            if (0 == Long.compare(purchaseQuantity, checkQuantity)) {
                // 采购数量（来自于采购管理） == 核算数量（来自于收货管理）
                // 订单状态：已关闭
                element.setOrderStatus("1");
                // 完成率：100%
                element.setCompletionRate(100 + "%");
            } else {
                // 采购数量（来自于采购管理） <> 核算数量（来自于收货管理）
                // 订单状态：待确认
                element.setOrderStatus("2");
                if (0 != Long.compare(purchaseQuantity, 0)) {
                    // 完成率：核算数量（来自于收货管理）/ 采购数量（来自于采购管理）* 100
                    double completionRate = division(checkQuantity, purchaseQuantity, 2) * 100;
                    element.setCompletionRate(completionRate + "%");
                }
            }
        });
    }

    /**
     * double除法
     *
     * @param a
     * @param b
     * @param accurate 结果保留位数
     * @return
     */
    private double division(double a, double b, int accurate) {
        if (accurate < 0) {
            throw new RuntimeException("精确度必须是正整数或零");
        }
        BigDecimal b1 = new BigDecimal(a);
        BigDecimal b2 = new BigDecimal(b);
        return b1.divide(b2, accurate, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
