package com.ruoyi.fpgl.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 发票管理对象 fpgl_main_info
 * 
 * @author changgong0513
 * @date 2022-11-06
 */
public class FpglListInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单编号（purchase_sale_order_info） */
    @Excel(name = "订单编号")
    private String orderId;

    /** 客户名称（purchase_sale_order_info） */
    @Excel(name = "客户名称")
    private String supplierName;

    /** 物料名称（purchase_sale_order_info） */
    @Excel(name = "物料名称")
    private String materialName;

    /** 合同类型（contract_content_info） */
    @Excel(name = "合同类型", dictType = "contractmgr_contract_type")
    private String contractType;

    /** 合同金额（contract_content_info） */
    @Excel(name = "合同金额")
    private BigDecimal contractTotal;

    /** 开票数量 */
    @Excel(name = "开票数量")
    private Long fpglKpsl;

    /** 开票单价 */
    @Excel(name = "开票单价")
    private BigDecimal fpglKpdj;

    /** 开票金额（fpgl_main_info） */
    @Excel(name = "开票金额")
    private BigDecimal fpglKpje;

    /** 客户税号（master_data_client_info） */
    @Excel(name = "客户税号")
    private String taxNumber;

    /** 发票状态 */
    @Excel(name = "发票状态", dictType = "fpgl_fp_status")
    private String fpglFpzt;

    /** 我方单位名称 */
    @Excel(name = "我方单位名称")
    private String ourCompanyName;

    /** 发票号 */
    @Excel(name = "发票号")
    private String fpglFpno;

    /** 当前登录用户的部门编号 */
    private Long deptId;

    /** 发票管理编号 */
    private String fpglId;

    /** 当前登录用户名称 */
    private String currentLoginUserName;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public BigDecimal getContractTotal() {
        return contractTotal;
    }

    public Long getFpglKpsl() {
        return fpglKpsl;
    }

    public void setFpglKpsl(Long fpglKpsl) {
        this.fpglKpsl = fpglKpsl;
    }

    public BigDecimal getFpglKpdj() {
        return fpglKpdj;
    }

    public void setFpglKpdj(BigDecimal fpglKpdj) {
        this.fpglKpdj = fpglKpdj;
    }

    public void setContractTotal(BigDecimal contractTotal) {
        this.contractTotal = contractTotal;
    }

    public BigDecimal getFpglKpje() {
        return fpglKpje;
    }

    public void setFpglKpje(BigDecimal fpglKpje) {
        this.fpglKpje = fpglKpje;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getFpglFpzt() {
        return fpglFpzt;
    }

    public void setFpglFpzt(String fpglFpzt) {
        this.fpglFpzt = fpglFpzt;
    }

    public String getOurCompanyName() {
        return ourCompanyName;
    }

    public void setOurCompanyName(String ourCompanyName) {
        this.ourCompanyName = ourCompanyName;
    }

    public String getFpglFpno() {
        return fpglFpno;
    }

    public void setFpglFpno(String fpglFpno) {
        this.fpglFpno = fpglFpno;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getFpglId() {
        return fpglId;
    }

    public void setFpglId(String fpglId) {
        this.fpglId = fpglId;
    }

    public String getCurrentLoginUserName() {
        return currentLoginUserName;
    }

    public void setCurrentLoginUserName(String currentLoginUserName) {
        this.currentLoginUserName = currentLoginUserName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("orderId", getOrderId())
            .append("supplierName", getSupplierName())
            .append("materialName", getMaterialName())
            .append("contractType", getContractType())
            .append("contractTotal", getContractTotal())
            .append("fpglKpje", getFpglKpje())
            .append("taxNumber", getTaxNumber())
                .append("fpglFpzt", getFpglFpzt())
            .toString();
    }
}
