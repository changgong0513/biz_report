package com.ruoyi.report.masterdata.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 主数据管理对象 master_data_material_info
 * 
 * @author changgong0513
 * @date 2022-10-28
 */
public class MasterDataMaterialInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 物料编码 */
    @Excel(name = "物料编码")
    private String materialId;

    /** 物料名称 */
    @Excel(name = "物料名称")
    private String materialName;

    /** 计量单位 */
    @Excel(name = "计量单位")
    private BigDecimal materialUnit;

    /** 开票别名 */
    @Excel(name = "开票别名")
    private String billingAlias;

    /** 版本号 */
    private Long bizVersion;

    public void setMaterialId(String materialId) 
    {
        this.materialId = materialId;
    }

    public String getMaterialId() 
    {
        return materialId;
    }
    public void setMaterialName(String materialName) 
    {
        this.materialName = materialName;
    }

    public String getMaterialName() 
    {
        return materialName;
    }
    public void setMaterialUnit(BigDecimal materialUnit) 
    {
        this.materialUnit = materialUnit;
    }

    public BigDecimal getMaterialUnit() 
    {
        return materialUnit;
    }
    public void setBillingAlias(String billingAlias) 
    {
        this.billingAlias = billingAlias;
    }

    public String getBillingAlias() 
    {
        return billingAlias;
    }
    public void setBizVersion(Long bizVersion) 
    {
        this.bizVersion = bizVersion;
    }

    public Long getBizVersion() 
    {
        return bizVersion;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("materialId", getMaterialId())
            .append("materialName", getMaterialName())
            .append("materialUnit", getMaterialUnit())
            .append("billingAlias", getBillingAlias())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("bizVersion", getBizVersion())
            .toString();
    }
}
