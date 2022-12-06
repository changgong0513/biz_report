package com.ruoyi.zjzy.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 付款认领对象 zjzy_fkrl_info
 * 
 * @author changgong0513
 * @date 2022-12-07
 */
public class ZjzyFkrlInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private String fkrlId;

    /** 部门编号 */
    @Excel(name = "部门编号")
    private String fkrlBmbh;

    /** 批次号 */
    @Excel(name = "批次号")
    private String fkrlPch;

    /** 合同编号 */
    @Excel(name = "合同编号")
    private String fkrlHtbh;

    /** 金额 */
    @Excel(name = "金额")
    private BigDecimal fkrlJe;

    /** 回款编号 */
    @Excel(name = "付款编号")
    private Long fkId;

    /** 版本号 */
    private String bizVersion;

    /** 部门名称 */
    private String deptName;

    public void setFkrlId(String fkrlId) 
    {
        this.fkrlId = fkrlId;
    }

    public String getFkrlId() 
    {
        return fkrlId;
    }
    public void setFkrlBmbh(String fkrlBmbh) 
    {
        this.fkrlBmbh = fkrlBmbh;
    }

    public String getFkrlBmbh() 
    {
        return fkrlBmbh;
    }
    public void setFkrlPch(String fkrlPch) 
    {
        this.fkrlPch = fkrlPch;
    }

    public String getFkrlPch() 
    {
        return fkrlPch;
    }
    public void setFkrlHtbh(String fkrlHtbh) 
    {
        this.fkrlHtbh = fkrlHtbh;
    }

    public String getFkrlHtbh() 
    {
        return fkrlHtbh;
    }
    public void setFkrlJe(BigDecimal fkrlJe) 
    {
        this.fkrlJe = fkrlJe;
    }

    public BigDecimal getFkrlJe() 
    {
        return fkrlJe;
    }
    public void setFkId(Long fkId) 
    {
        this.fkId = fkId;
    }

    public Long getFkId() 
    {
        return fkId;
    }
    public void setBizVersion(String bizVersion) 
    {
        this.bizVersion = bizVersion;
    }

    public String getBizVersion() 
    {
        return bizVersion;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("fkrlId", getFkrlId())
            .append("fkrlBmbh", getFkrlBmbh())
            .append("fkrlPch", getFkrlPch())
            .append("fkrlHtbh", getFkrlHtbh())
            .append("fkrlJe", getFkrlJe())
            .append("fkId", getFkId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("bizVersion", getBizVersion())
            .toString();
    }
}
