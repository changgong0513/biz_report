package com.ruoyi.report.masterdata.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * Warehouse对象 master_data_kq_info
 * 
 * @author changgong0513
 * @date 2022-10-30
 */
public class MasterDataKqInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 仓库ID */
    @Excel(name = "仓库ID")
    private String warehouseId;

    /** 库区编码 */
    private String kqCode;

    /** 库区名称 */
    @Excel(name = "库区名称")
    private String kqName;

    /** 区划 */
    @Excel(name = "区划", dictType = "masterdata_warehouse_region")
    private Long warehouseRegion;

    /** 库区地址 */
    @Excel(name = "库区地址")
    private String warehouseAddress;

    /** 管理部门 */
    @Excel(name = "管理部门", dictType = "masterdata_management_department")
    private String managementDepartment;

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
    @Excel(name = "仓库类别", dictType = "masterdata_warehouse_category")
    private String warehouseCategory;

    /** 备注 */
    @Excel(name = "备注")
    private String kqRemarks;

    /** 版本号 */
    @Excel(name = "版本号")
    private Long bizVersion;

    public void setWarehouseId(String warehouseId) 
    {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseId() 
    {
        return warehouseId;
    }
    public void setKqCode(String kqCode) 
    {
        this.kqCode = kqCode;
    }

    public String getKqCode() 
    {
        return kqCode;
    }
    public void setKqName(String kqName) 
    {
        this.kqName = kqName;
    }

    public String getKqName() 
    {
        return kqName;
    }
    public void setWarehouseRegion(Long warehouseRegion) 
    {
        this.warehouseRegion = warehouseRegion;
    }

    public Long getWarehouseRegion() 
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
    public void setManagementDepartment(String managementDepartment) 
    {
        this.managementDepartment = managementDepartment;
    }

    public String getManagementDepartment() 
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
    public void setWarehouseCategory(String warehouseCategory) 
    {
        this.warehouseCategory = warehouseCategory;
    }

    public String getWarehouseCategory() 
    {
        return warehouseCategory;
    }
    public void setKqRemarks(String kqRemarks) 
    {
        this.kqRemarks = kqRemarks;
    }

    public String getKqRemarks() 
    {
        return kqRemarks;
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
            .append("kqCode", getKqCode())
            .append("kqName", getKqName())
            .append("warehouseRegion", getWarehouseRegion())
            .append("warehouseAddress", getWarehouseAddress())
            .append("managementDepartment", getManagementDepartment())
            .append("warehouseManager", getWarehouseManager())
            .append("contactMobile1", getContactMobile1())
            .append("contactMobile2", getContactMobile2())
            .append("warehouseCategory", getWarehouseCategory())
            .append("kqRemarks", getKqRemarks())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("bizVersion", getBizVersion())
            .toString();
    }
}
