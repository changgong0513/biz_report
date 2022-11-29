package com.ruoyi.report.contract.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 合同管理-审批管理对象 contract_approval_info
 * 
 * @author changgong
 * @date 2022-11-29
 */
public class ContractApprovalInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 合同编号 */
    private String contractId;

    /** 审批编号 */
    private String approvalId;

    /** 标题 */
    private String approvalTitle;

    /** 审批状态 */
    private String approvalStatus;

    /** 审批结果 */
    private String approvalResult;

    /** 发起时间 */
    private Date launchTime;

    /** 完成时间 */
    private Date completeTime;

    /** 耗时 */
    private String takeupTime;

    /** 发起人工号 */
    private String launchJobId;

    /** 发起人ID */
    private String launchId;

    /** 发起人姓名 */
    private String launchName;

    /** 发起人部门 */
    private String launchDepartment;

    /** 审批人姓名 */
    private String approvalName;

    /** 当前处理人姓名 */
    private String processorName;

    /** 审批记录 */
    private String approvalRecords;

    /** 版本号 */
    private Long bizVersion;

    /** 审批记录 */
    List<ContractApprovalRecordsInfo> approvalRecordList;

    public void setContractId(String contractId) 
    {
        this.contractId = contractId;
    }

    public String getContractId() 
    {
        return contractId;
    }
    public void setApprovalId(String approvalId) 
    {
        this.approvalId = approvalId;
    }

    public String getApprovalId() 
    {
        return approvalId;
    }
    public void setApprovalTitle(String approvalTitle) 
    {
        this.approvalTitle = approvalTitle;
    }

    public String getApprovalTitle() 
    {
        return approvalTitle;
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
    public void setLaunchTime(Date launchTime) 
    {
        this.launchTime = launchTime;
    }

    public Date getLaunchTime() 
    {
        return launchTime;
    }
    public void setCompleteTime(Date completeTime) 
    {
        this.completeTime = completeTime;
    }

    public Date getCompleteTime() 
    {
        return completeTime;
    }
    public void setTakeupTime(String takeupTime)
    {
        this.takeupTime = takeupTime;
    }

    public String getTakeupTime()
    {
        return takeupTime;
    }
    public void setLaunchJobId(String launchJobId) 
    {
        this.launchJobId = launchJobId;
    }

    public String getLaunchJobId() 
    {
        return launchJobId;
    }
    public void setLaunchId(String launchId) 
    {
        this.launchId = launchId;
    }

    public String getLaunchId() 
    {
        return launchId;
    }
    public void setLaunchName(String launchName) 
    {
        this.launchName = launchName;
    }

    public String getLaunchName() 
    {
        return launchName;
    }
    public void setLaunchDepartment(String launchDepartment) 
    {
        this.launchDepartment = launchDepartment;
    }

    public String getLaunchDepartment() 
    {
        return launchDepartment;
    }
    public void setApprovalName(String approvalName) 
    {
        this.approvalName = approvalName;
    }

    public String getApprovalName() 
    {
        return approvalName;
    }
    public void setProcessorName(String processorName) 
    {
        this.processorName = processorName;
    }

    public String getProcessorName() 
    {
        return processorName;
    }
    public void setApprovalRecords(String approvalRecords) 
    {
        this.approvalRecords = approvalRecords;
    }

    public String getApprovalRecords() 
    {
        return approvalRecords;
    }
    public void setBizVersion(Long bizVersion) 
    {
        this.bizVersion = bizVersion;
    }

    public Long getBizVersion() 
    {
        return bizVersion;
    }

    public List<ContractApprovalRecordsInfo> getApprovalRecordList() {
        return approvalRecordList;
    }

    public void setApprovalRecordList(List<ContractApprovalRecordsInfo> approvalRecordList) {
        this.approvalRecordList = approvalRecordList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("contractId", getContractId())
            .append("approvalId", getApprovalId())
            .append("approvalTitle", getApprovalTitle())
            .append("approvalStatus", getApprovalStatus())
            .append("approvalResult", getApprovalResult())
            .append("launchTime", getLaunchTime())
            .append("completeTime", getCompleteTime())
            .append("takeupTime", getTakeupTime())
            .append("launchJobId", getLaunchJobId())
            .append("launchId", getLaunchId())
            .append("launchName", getLaunchName())
            .append("launchDepartment", getLaunchDepartment())
            .append("approvalName", getApprovalName())
            .append("processorName", getProcessorName())
            .append("approvalRecords", getApprovalRecords())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("bizVersion", getBizVersion())
            .toString();
    }
}
