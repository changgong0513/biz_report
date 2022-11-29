package com.ruoyi.report.contract.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 合同管理-审批记录对象 contract_approval_records_info
 * 
 * @author changgong
 * @date 2022-11-29
 */
public class ContractApprovalRecordsInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 审批记录编号 */
    private String approvalRecordId;

    /** 审批状态 */
    private String approvalStatus;

    /** 审批结果 */
    private String approvalResult;

    /** 完成时间 */
    private Date completeTime;

    /** 审批人姓名 */
    private String approvalName;

    /** 审批记录 */
    private String approvalRecord;

    /** 审批编号 */
    private String approvalId;

    /** 版本号 */
    private Long bizVersion;

    public void setApprovalRecordId(String approvalRecordId) 
    {
        this.approvalRecordId = approvalRecordId;
    }

    public String getApprovalRecordId() 
    {
        return approvalRecordId;
    }
    public void setApprovalStatus(String approvalStatus) 
    {
        this.approvalStatus = approvalStatus;
    }

    public String getApprovalStatus() 
    {
        return approvalStatus;
    }
    public void setApprovalResult(String approvalResult) 
    {
        this.approvalResult = approvalResult;
    }

    public String getApprovalResult() 
    {
        return approvalResult;
    }
    public void setCompleteTime(Date completeTime) 
    {
        this.completeTime = completeTime;
    }

    public Date getCompleteTime() 
    {
        return completeTime;
    }
    public void setApprovalName(String approvalName) 
    {
        this.approvalName = approvalName;
    }

    public String getApprovalName() 
    {
        return approvalName;
    }
    public void setApprovalRecord(String approvalRecord) 
    {
        this.approvalRecord = approvalRecord;
    }

    public String getApprovalRecord() 
    {
        return approvalRecord;
    }
    public void setApprovalId(String approvalId) 
    {
        this.approvalId = approvalId;
    }

    public String getApprovalId() 
    {
        return approvalId;
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
            .append("approvalRecordId", getApprovalRecordId())
            .append("approvalStatus", getApprovalStatus())
            .append("approvalResult", getApprovalResult())
            .append("completeTime", getCompleteTime())
            .append("approvalName", getApprovalName())
            .append("approvalRecord", getApprovalRecord())
            .append("approvalId", getApprovalId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("bizVersion", getBizVersion())
            .toString();
    }
}
