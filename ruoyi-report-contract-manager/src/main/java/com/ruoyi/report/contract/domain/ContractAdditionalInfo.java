package com.ruoyi.report.contract.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * contract对象 contract_additional_info
 * 
 * @author changgong0513
 * @date 2022-11-01
 */
public class ContractAdditionalInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 合同附加内容编号 */
    private String additionalId;

    /** 上传图片路径 */
    private String uploadImagePath;

    /** 上传文件路径 */
    private String uplloadFilePath;

    /** 合同编号 */
    private String contractId;

    /** 版本号 */
    private Long bizVersion;

    public void setAdditionalId(String additionalId) 
    {
        this.additionalId = additionalId;
    }

    public String getAdditionalId() 
    {
        return additionalId;
    }
    public void setUploadImagePath(String uploadImagePath) 
    {
        this.uploadImagePath = uploadImagePath;
    }

    public String getUploadImagePath() 
    {
        return uploadImagePath;
    }
    public void setUplloadFilePath(String uplloadFilePath) 
    {
        this.uplloadFilePath = uplloadFilePath;
    }

    public String getUplloadFilePath() 
    {
        return uplloadFilePath;
    }
    public void setContractId(String contractId) 
    {
        this.contractId = contractId;
    }

    public String getContractId() 
    {
        return contractId;
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
            .append("additionalId", getAdditionalId())
            .append("uploadImagePath", getUploadImagePath())
            .append("uplloadFilePath", getUplloadFilePath())
            .append("contractId", getContractId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("bizVersion", getBizVersion())
            .toString();
    }
}
