package com.ruoyi.zjzy.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 回款认领对象 zjzy_hkrl_info
 * 
 * @author changgong0513
 * @date 2022-12-04
 */
public class ZjzyHkrlInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long hkrlId;

    /** 部门编号 */
    @Excel(name = "部门编号")
    private String hkrlBmbh;

    /** 批次号 */
    @Excel(name = "批次号")
    private String hkrlPch;

    /** 合同编号 */
    @Excel(name = "合同编号")
    private String hkrlHtbh;

    /** 金额 */
    @Excel(name = "金额")
    private BigDecimal hkrlJe;

    /** 回款编号 */
    private String hkId;

    /** 版本号 */
    private Long bizVersion;

    /** 部门名称 */
    private String deptName;

    public void setHkrlId(Long hkrlId) 
    {
        this.hkrlId = hkrlId;
    }

    public Long getHkrlId() 
    {
        return hkrlId;
    }
    public void setHkrlBmbh(String hkrlBmbh) 
    {
        this.hkrlBmbh = hkrlBmbh;
    }

    public String getHkrlBmbh() 
    {
        return hkrlBmbh;
    }
    public void setHkrlPch(String hkrlPch) 
    {
        this.hkrlPch = hkrlPch;
    }

    public String getHkrlPch() 
    {
        return hkrlPch;
    }
    public void setHkrlHtbh(String hkrlHtbh) 
    {
        this.hkrlHtbh = hkrlHtbh;
    }

    public String getHkrlHtbh() 
    {
        return hkrlHtbh;
    }
    public void setHkrlJe(BigDecimal hkrlJe) 
    {
        this.hkrlJe = hkrlJe;
    }

    public BigDecimal getHkrlJe() 
    {
        return hkrlJe;
    }

    public String getHkId() {
        return hkId;
    }

    public void setHkId(String hkId) {
        this.hkId = hkId;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("hkrlId", getHkrlId())
            .append("hkrlBmbh", getHkrlBmbh())
            .append("hkrlPch", getHkrlPch())
            .append("hkrlHtbh", getHkrlHtbh())
            .append("hkrlJe", getHkrlJe())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("bizVersion", getBizVersion())
            .toString();
    }
}
