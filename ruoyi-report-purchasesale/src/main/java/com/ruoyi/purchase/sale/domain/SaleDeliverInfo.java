package com.ruoyi.purchase.sale.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.poi.hpsf.Decimal;

/**
 * 发货管理对象 sale_deliver_info
 * 
 * @author chanagong0513
 * @date 2022-11-05
 */
public class SaleDeliverInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 发货编号 */
    @Excel(name = "发货编号")
    private String deliverId;

    /** 销售订单编号 */
    @Excel(name = "销售订单编号")
    private String saleOrderId;

    /** 销售合同编号 */
    private String saleContractId;

    /** 经办人 */
    @Excel(name = "经办人")
    private String handledBy;

    /** 发货日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发货日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date deliverDate;

    /** 客户编号 */
    private String clientId;

    /** 客户名称 */
    private String clientName;

    /** 物料编号 */
    private Long materialId;

    /** 物料名称 */
    @Excel(name = "物料名称")
    private String materialName;

    /** 合同单价 */
    private BigDecimal contractPrice;

    /** 计量单位 */
    private String measurementUnit;

    /** 发货方式 */
    private String deliverMode;

    /** 仓库编码 */
    private String warehouseCode;

    /** 仓库名称 */
    @Excel(name = "仓库名称")
    private String warehouseName;

    /** 发货数量 */
    private BigDecimal deliverQuantity;

    /** 核算数量 */
    private BigDecimal checkQuantity;

    /** 核算单价 */
    private BigDecimal checkPrice;

    /** 核算金额 */
    private BigDecimal checkMoney;

    /** 货损数量 */
    private BigDecimal cargoDamageQuantity;

    /** 货损金额 */
    private BigDecimal cargoDamageMoney;

    /** 运输方式 */
    private String transportMode;

    /** 运输单号 */
    private String transportNumber;

    /** 运费金额 */
    private BigDecimal transportMoney;

    /** 其他金额 */
    private BigDecimal otherMoney;

    /** 预期到货日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expectArrivalDate;

    /** 要求到货日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date requireArrivalDate;

    /** 账期 */
    private int accountPeriod;

    /** 备注 */
    private String deliverRemark;

    /** 版本号 */
    private Long bizVersion;

    public String getDeliverId() {
        return deliverId;
    }

    public void setDeliverId(String deliverId) {
        this.deliverId = deliverId;
    }

    public String getSaleOrderId() {
        return saleOrderId;
    }

    public void setSaleOrderId(String saleOrderId) {
        this.saleOrderId = saleOrderId;
    }

    public String getSaleContractId() {
        return saleContractId;
    }

    public void setSaleContractId(String saleContractId) {
        this.saleContractId = saleContractId;
    }

    public String getHandledBy() {
        return handledBy;
    }

    public void setHandledBy(String handledBy) {
        this.handledBy = handledBy;
    }

    public Date getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(Date deliverDate) {
        this.deliverDate = deliverDate;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public BigDecimal getContractPrice() {
        return contractPrice;
    }

    public void setContractPrice(BigDecimal contractPrice) {
        this.contractPrice = contractPrice;
    }

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    public String getDeliverMode() {
        return deliverMode;
    }

    public void setDeliverMode(String deliverMode) {
        this.deliverMode = deliverMode;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public BigDecimal getDeliverQuantity() {
        return deliverQuantity;
    }

    public void setDeliverQuantity(BigDecimal deliverQuantity) {
        this.deliverQuantity = deliverQuantity;
    }

    public BigDecimal getCheckQuantity() {
        return checkQuantity;
    }

    public void setCheckQuantity(BigDecimal checkQuantity) {
        this.checkQuantity = checkQuantity;
    }

    public BigDecimal getCheckPrice() {
        return checkPrice;
    }

    public void setCheckPrice(BigDecimal checkPrice) {
        this.checkPrice = checkPrice;
    }

    public BigDecimal getCheckMoney() {
        return checkMoney;
    }

    public void setCheckMoney(BigDecimal checkMoney) {
        this.checkMoney = checkMoney;
    }

    public BigDecimal getCargoDamageQuantity() {
        return cargoDamageQuantity;
    }

    public void setCargoDamageQuantity(BigDecimal cargoDamageQuantity) {
        this.cargoDamageQuantity = cargoDamageQuantity;
    }

    public BigDecimal getCargoDamageMoney() {
        return cargoDamageMoney;
    }

    public void setCargoDamageMoney(BigDecimal cargoDamageMoney) {
        this.cargoDamageMoney = cargoDamageMoney;
    }

    public String getTransportMode() {
        return transportMode;
    }

    public void setTransportMode(String transportMode) {
        this.transportMode = transportMode;
    }

    public String getTransportNumber() {
        return transportNumber;
    }

    public void setTransportNumber(String transportNumber) {
        this.transportNumber = transportNumber;
    }

    public BigDecimal getTransportMoney() {
        return transportMoney;
    }

    public void setTransportMoney(BigDecimal transportMoney) {
        this.transportMoney = transportMoney;
    }

    public BigDecimal getOtherMoney() {
        return otherMoney;
    }

    public void setOtherMoney(BigDecimal otherMoney) {
        this.otherMoney = otherMoney;
    }

    public Date getExpectArrivalDate() {
        return expectArrivalDate;
    }

    public void setExpectArrivalDate(Date expectArrivalDate) {
        this.expectArrivalDate = expectArrivalDate;
    }

    public Date getRequireArrivalDate() {
        return requireArrivalDate;
    }

    public void setRequireArrivalDate(Date requireArrivalDate) {
        this.requireArrivalDate = requireArrivalDate;
    }

    public int getAccountPeriod() {
        return accountPeriod;
    }

    public void setAccountPeriod(int accountPeriod) {
        this.accountPeriod = accountPeriod;
    }

    public String getDeliverRemark() {
        return deliverRemark;
    }

    public void setDeliverRemark(String deliverRemark) {
        this.deliverRemark = deliverRemark;
    }

    public Long getBizVersion() {
        return bizVersion;
    }

    public void setBizVersion(Long bizVersion) {
        this.bizVersion = bizVersion;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("deliverId", getDeliverId())
            .append("saleOrderId", getSaleOrderId())
            .append("saleContractId", getSaleContractId())
            .append("handledBy", getHandledBy())
            .append("deliverDate", getDeliverDate())
            .append("clientId", getClientId())
            .append("clientName", getClientName())
            .append("materialId", getMaterialId())
            .append("materialName", getMaterialName())
            .append("contractPrice", getContractPrice())
            .append("measurementUnit", getMeasurementUnit())
            .append("deliverMode", getDeliverMode())
            .append("warehouseCode", getWarehouseCode())
            .append("warehouseName", getWarehouseName())
            .append("deliverQuantity", getDeliverQuantity())
            .append("checkQuantity", getCheckQuantity())
            .append("checkPrice", getCheckPrice())
            .append("checkMoney", getCheckMoney())
            .append("cargoDamageQuantity", getCargoDamageQuantity())
            .append("cargoDamageMoney", getCargoDamageMoney())
            .append("transportMode", getTransportMode())
            .append("transportNumber", getTransportNumber())
            .append("transportMoney", getTransportMoney())
            .append("otherMoney", getOtherMoney())
            .append("expectArrivalDate", getExpectArrivalDate())
            .append("requireArrivalDate", getRequireArrivalDate())
                .append("accountPeriod", getAccountPeriod())
            .append("receiptRemark", getDeliverRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("bizVersion", getBizVersion())
            .toString();
    }
}
