package com.ruoyi.kcdb.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 存库调拨对象 kcdb_main_info
 * 
 * @author changgong0513
 * @date 2022-11-05
 */
public class KcdbMainInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 调拨编号（无业务含义） */
    private String dbId;

    /** 调拨单号 */
    @Excel(name = "调拨单号")
    private String dh;

    /** 调拨类型 */
    @Excel(name = "调拨类型", dictType = "kcdb_db_type")
    private String lx;

    /** 发货仓库 */
    @Excel(name = "发货仓库")
    private String fhck;

    /** 发货仓库名称 */
    @Excel(name = "发货仓库名称")
    private String fhckmc;

    /** 收货仓库 */
    @Excel(name = "收货仓库")
    private String shck;

    /** 发货部门 */
    @Excel(name = "发货部门", dictType = "purchasesale_belong_dept")
    private String fhbm;

    /** 收货部门 */
    @Excel(name = "收货部门", dictType = "purchasesale_belong_dept")
    private String shbm;

    /** 物料编号 */
    private String wlmc;

    /** 物料名称 */
    private String materialName;

    /** 调拨数量 */
    private BigDecimal dbsl;

    /** 运输方式 */
    private String ysfs;

    /** 结算方式 */
    private String jsfs;

    /** 结算单价 */
    private BigDecimal jsdj;

    /** 卸货数量 */
    private Long xhsl;

    /** 调拨日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "调拨日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dbrq;

    /** 备注 */
    private String bz;

    /** 批次号 */
    private int pch;

    /** 调拨金额 */
    private BigDecimal dbje;

    /** 调拨方向（dr: 调入 dc: 调出） */
    private String recordFlag;

    /** 内勤人员 */
    private String nqry;

    /** 所属部门名称 */
    private String deptName;

    /** 版本号 */
    private Long bizVersion;

    public void setDh(String dh) 
    {
        this.dh = dh;
    }

    public String getDh() 
    {
        return dh;
    }
    public void setLx(String lx) 
    {
        this.lx = lx;
    }

    public String getLx() 
    {
        return lx;
    }
    public void setFhck(String fhck) 
    {
        this.fhck = fhck;
    }

    public String getFhck() 
    {
        return fhck;
    }
    public void setShck(String shck) 
    {
        this.shck = shck;
    }

    public String getShck() 
    {
        return shck;
    }
    public void setFhbm(String fhbm) 
    {
        this.fhbm = fhbm;
    }

    public String getFhbm() 
    {
        return fhbm;
    }
    public void setShbm(String shbm) 
    {
        this.shbm = shbm;
    }

    public String getShbm() 
    {
        return shbm;
    }
    public void setWlmc(String wlmc) 
    {
        this.wlmc = wlmc;
    }

    public String getWlmc() 
    {
        return wlmc;
    }
    public void setDbsl(BigDecimal dbsl) 
    {
        this.dbsl = dbsl;
    }

    public BigDecimal getDbsl() 
    {
        return dbsl;
    }
    public void setYsfs(String ysfs) 
    {
        this.ysfs = ysfs;
    }

    public String getYsfs() 
    {
        return ysfs;
    }
    public void setJsfs(String jsfs) 
    {
        this.jsfs = jsfs;
    }

    public String getJsfs() 
    {
        return jsfs;
    }
    public void setJsdj(BigDecimal jsdj) 
    {
        this.jsdj = jsdj;
    }

    public BigDecimal getJsdj() 
    {
        return jsdj;
    }
    public void setXhsl(Long xhsl) 
    {
        this.xhsl = xhsl;
    }

    public Long getXhsl() 
    {
        return xhsl;
    }
    public void setDbrq(Date dbrq) 
    {
        this.dbrq = dbrq;
    }

    public Date getDbrq() 
    {
        return dbrq;
    }
    public void setBz(String bz) 
    {
        this.bz = bz;
    }

    public String getBz() 
    {
        return bz;
    }
    public void setBizVersion(Long bizVersion) 
    {
        this.bizVersion = bizVersion;
    }

    public Long getBizVersion() 
    {
        return bizVersion;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public int getPch() {
        return pch;
    }

    public void setPch(int pch) {
        this.pch = pch;
    }

    public BigDecimal getDbje() {
        return dbje;
    }

    public void setDbje(BigDecimal dbje) {
        this.dbje = dbje;
    }

    public String getRecordFlag() {
        return recordFlag;
    }

    public void setRecordFlag(String recordFlag) {
        this.recordFlag = recordFlag;
    }

    public String getNqry() {
        return nqry;
    }

    public void setNqry(String nqry) {
        this.nqry = nqry;
    }

    public String getFhckmc() {
        return fhckmc;
    }

    public void setFhckmc(String fhckmc) {
        this.fhckmc = fhckmc;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("dh", getDh())
            .append("lx", getLx())
            .append("fhck", getFhck())
            .append("shck", getShck())
            .append("fhbm", getFhbm())
            .append("shbm", getShbm())
            .append("wlmc", getWlmc())
            .append("dbsl", getDbsl())
            .append("ysfs", getYsfs())
            .append("jsfs", getJsfs())
            .append("jsdj", getJsdj())
            .append("xhsl", getXhsl())
            .append("dbrq", getDbrq())
            .append("bz", getBz())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("bizVersion", getBizVersion())
            .toString();
    }
}
