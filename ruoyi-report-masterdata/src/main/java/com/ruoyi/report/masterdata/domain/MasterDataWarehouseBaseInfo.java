package com.ruoyi.report.masterdata.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 仓库管理对象 master_data_warehouse_base_info
 *
 * @author changgong0513
 * @date 2022-09-17
 */
public class MasterDataWarehouseBaseInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 仓库ID */
    @Excel(name = "仓库ID")
    private Long warehouseId;

    /** 仓库编码 */
    @Excel(name = "仓库编码")
    private String warehouseCode;

    /** 仓库名称 */
    @Excel(name = "仓库名称")
    private String warehouseName;

    /** 区划 */
    @Excel(name = "区划")
    private Integer warehouseRegion;

    /** 仓库地址 */
    @Excel(name = "仓库地址")
    private String warehouseAddress;

    /** 管理部门 */
    @Excel(name = "管理部门")
    private Integer managementDepartment;

    /** 管理人员 */
    @Excel(name = "管理人员")
    private String warehouseManager;

    /** 联系方式1 */
    @Excel(name = "联系方式1")
    private String contactMobile1;

    /** 联系方式2 */
    @Excel(name = "联系方式2")
    private String contactMobile2;

    /** 仓库类别 */
    @Excel(name = "仓库类别")
    private Integer warehouseCategory;

    /** 占地面积 */
    @Excel(name = "占地面积")
    private Long useArea;

    /** 最大容量 */
    @Excel(name = "最大容量")
    private Long maximumCapacity;

    /** 计量单位 */
    @Excel(name = "计量单位")
    private Integer measurementUnit;

    /** 建库日期 */
    @Excel(name = "建库日期")
    private String buildDate;

    /** 备注 */
    @Excel(name = "备注")
    private String warehouseRemarks;

    /** 版本号 */
    @Excel(name = "版本号")
    private Long bizVersion;

    public void setWarehouseId(Long warehouseId)
    {
        this.warehouseId = warehouseId;
    }

    public Long getWarehouseId()
    {
        return warehouseId;
    }
    public void setWarehouseCode(String warehouseCode)
    {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseCode()
    {
        return warehouseCode;
    }
    public void setWarehouseName(String warehouseName)
    {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseName()
    {
        return warehouseName;
    }
    public void setWarehouseRegion(Integer warehouseRegion)
    {
        this.warehouseRegion = warehouseRegion;
    }

    public Integer getWarehouseRegion()
    {
        return warehouseRegion;
    }
    public void setWarehouseAddress(String warehouseAddress)
    {
        this.warehouseAddress = warehouseAddress;
    }

    public String getWarehouseAddress()
    {
        return warehouseAddress;
    }
    public void setManagementDepartment(Integer managementDepartment)
    {
        this.managementDepartment = managementDepartment;
    }

    public Integer getManagementDepartment()
    {
        return managementDepartment;
    }
    public void setWarehouseManager(String warehouseManager)
    {
        this.warehouseManager = warehouseManager;
    }

    public String getWarehouseManager()
    {
        return warehouseManager;
    }
    public void setContactMobile1(String contactMobile1)
    {
        this.contactMobile1 = contactMobile1;
    }

    public String getContactMobile1()
    {
        return contactMobile1;
    }
    public void setContactMobile2(String contactMobile2)
    {
        this.contactMobile2 = contactMobile2;
    }

    public String getContactMobile2()
    {
        return contactMobile2;
    }
    public void setWarehouseCategory(Integer warehouseCategory)
    {
        this.warehouseCategory = warehouseCategory;
    }

    public Integer getWarehouseCategory()
    {
        return warehouseCategory;
    }
    public void setUseArea(Long useArea)
    {
        this.useArea = useArea;
    }

    public Long getUseArea()
    {
        return useArea;
    }
    public void setMaximumCapacity(Long maximumCapacity)
    {
        this.maximumCapacity = maximumCapacity;
    }

    public Long getMaximumCapacity()
    {
        return maximumCapacity;
    }
    public void setMeasurementUnit(Integer measurementUnit)
    {
        this.measurementUnit = measurementUnit;
    }

    public Integer getMeasurementUnit()
    {
        return measurementUnit;
    }
    public void setBuildDate(String buildDate)
    {
        this.buildDate = buildDate;
    }

    public String getBuildDate()
    {
        return buildDate;
    }
    public void setWarehouseRemarks(String warehouseRemarks)
    {
        this.warehouseRemarks = warehouseRemarks;
    }

    public String getWarehouseRemarks()
    {
        return warehouseRemarks;
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
                .append("warehouseId", getWarehouseId())
                .append("warehouseCode", getWarehouseCode())
                .append("warehouseName", getWarehouseName())
                .append("warehouseRegion", getWarehouseRegion())
                .append("warehouseAddress", getWarehouseAddress())
                .append("managementDepartment", getManagementDepartment())
                .append("warehouseManager", getWarehouseManager())
                .append("contactMobile1", getContactMobile1())
                .append("contactMobile2", getContactMobile2())
                .append("warehouseCategory", getWarehouseCategory())
                .append("useArea", getUseArea())
                .append("maximumCapacity", getMaximumCapacity())
                .append("measurementUnit", getMeasurementUnit())
                .append("buildDate", getBuildDate())
                .append("warehouseRemarks", getWarehouseRemarks())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("bizVersion", getBizVersion())
                .toString();
    }
}
