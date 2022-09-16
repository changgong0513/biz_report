package com.ruoyi.report.masterdata.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 业务报表对象 master_data_client_info
 *
 * @author ruoyi
 * @date 2022-09-16
 */
public class MasterDataClientInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 客户编号 */
    private Long baseId;

    /** 公司名称 */
    @Excel(name = "公司名称")
    private String companyName;

    /** 成立日期 */
    @Excel(name = "成立日期")
    private String establishDate;

    /** 注册城市 */
    @Excel(name = "注册城市")
    private String registerCity;

    /** 地址 */
    @Excel(name = "地址")
    private String companyAdress;

    /** 邮编 */
    @Excel(name = "邮编")
    private String zipCode;

    /** 企业法人 */
    @Excel(name = "企业法人")
    private String legalPerson;

    /** 注册资金 */
    @Excel(name = "注册资金")
    private Long registeredCapital;

    /** 固定电话 */
    @Excel(name = "固定电话")
    private String fixedPhone;

    /** 传真号码 */
    @Excel(name = "传真号码")
    private String faxNumber;

    /** 营业时间 */
    @Excel(name = "营业时间")
    private String businessHours;

    /** 公司网址 */
    @Excel(name = "公司网址")
    private String companyWebsite;

    /** 标志 */
    @Excel(name = "标志")
    private Long recordFlag;

    /** 姓名 */
    @Excel(name = "姓名")
    private String contactsName;

    /** 手机 */
    @Excel(name = "手机")
    private String contactsMobile;

    /** 电子邮箱 */
    @Excel(name = "电子邮箱")
    private String contactsEmail;

    /** 办公地点 */
    @Excel(name = "办公地点")
    private String contactsOfficeLocation;

    /** 开户行 */
    @Excel(name = "开户行")
    private Long depositBank;

    /** 账号 */
    @Excel(name = "账号")
    private String accountNumber;

    /** 税号 */
    @Excel(name = "税号")
    private String taxNumber;

    /** 发票地址 */
    @Excel(name = "发票地址")
    private String invoiceAddress;

    /** 发票类型 */
    @Excel(name = "发票类型")
    private Long invoiceType;

    /** 版本号 */
    private Long bizVersion;

    public void setBaseId(Long baseId)
    {
        this.baseId = baseId;
    }

    public Long getBaseId()
    {
        return baseId;
    }
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

    public String getCompanyName()
    {
        return companyName;
    }
    public void setEstablishDate(String establishDate)
    {
        this.establishDate = establishDate;
    }

    public String getEstablishDate()
    {
        return establishDate;
    }
    public void setRegisterCity(String registerCity)
    {
        this.registerCity = registerCity;
    }

    public String getRegisterCity()
    {
        return registerCity;
    }
    public void setCompanyAdress(String companyAdress)
    {
        this.companyAdress = companyAdress;
    }

    public String getCompanyAdress()
    {
        return companyAdress;
    }
    public void setZipCode(String zipCode)
    {
        this.zipCode = zipCode;
    }

    public String getZipCode()
    {
        return zipCode;
    }
    public void setLegalPerson(String legalPerson)
    {
        this.legalPerson = legalPerson;
    }

    public String getLegalPerson()
    {
        return legalPerson;
    }
    public void setRegisteredCapital(Long registeredCapital)
    {
        this.registeredCapital = registeredCapital;
    }

    public Long getRegisteredCapital()
    {
        return registeredCapital;
    }
    public void setFixedPhone(String fixedPhone)
    {
        this.fixedPhone = fixedPhone;
    }

    public String getFixedPhone()
    {
        return fixedPhone;
    }
    public void setFaxNumber(String faxNumber)
    {
        this.faxNumber = faxNumber;
    }

    public String getFaxNumber()
    {
        return faxNumber;
    }
    public void setBusinessHours(String businessHours)
    {
        this.businessHours = businessHours;
    }

    public String getBusinessHours()
    {
        return businessHours;
    }
    public void setCompanyWebsite(String companyWebsite)
    {
        this.companyWebsite = companyWebsite;
    }

    public String getCompanyWebsite()
    {
        return companyWebsite;
    }
    public void setRecordFlag(Long recordFlag)
    {
        this.recordFlag = recordFlag;
    }

    public Long getRecordFlag()
    {
        return recordFlag;
    }
    public void setContactsName(String contactsName)
    {
        this.contactsName = contactsName;
    }

    public String getContactsName()
    {
        return contactsName;
    }
    public void setContactsMobile(String contactsMobile)
    {
        this.contactsMobile = contactsMobile;
    }

    public String getContactsMobile()
    {
        return contactsMobile;
    }
    public void setContactsEmail(String contactsEmail)
    {
        this.contactsEmail = contactsEmail;
    }

    public String getContactsEmail()
    {
        return contactsEmail;
    }
    public void setContactsOfficeLocation(String contactsOfficeLocation)
    {
        this.contactsOfficeLocation = contactsOfficeLocation;
    }

    public String getContactsOfficeLocation()
    {
        return contactsOfficeLocation;
    }
    public void setDepositBank(Long depositBank)
    {
        this.depositBank = depositBank;
    }

    public Long getDepositBank()
    {
        return depositBank;
    }
    public void setAccountNumber(String accountNumber)
    {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber()
    {
        return accountNumber;
    }
    public void setTaxNumber(String taxNumber)
    {
        this.taxNumber = taxNumber;
    }

    public String getTaxNumber()
    {
        return taxNumber;
    }
    public void setInvoiceAddress(String invoiceAddress)
    {
        this.invoiceAddress = invoiceAddress;
    }

    public String getInvoiceAddress()
    {
        return invoiceAddress;
    }
    public void setInvoiceType(Long invoiceType)
    {
        this.invoiceType = invoiceType;
    }

    public Long getInvoiceType()
    {
        return invoiceType;
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
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("baseId", getBaseId())
                .append("companyName", getCompanyName())
                .append("establishDate", getEstablishDate())
                .append("registerCity", getRegisterCity())
                .append("companyAdress", getCompanyAdress())
                .append("zipCode", getZipCode())
                .append("legalPerson", getLegalPerson())
                .append("registeredCapital", getRegisteredCapital())
                .append("fixedPhone", getFixedPhone())
                .append("faxNumber", getFaxNumber())
                .append("businessHours", getBusinessHours())
                .append("companyWebsite", getCompanyWebsite())
                .append("recordFlag", getRecordFlag())
                .append("contactsName", getContactsName())
                .append("contactsMobile", getContactsMobile())
                .append("contactsEmail", getContactsEmail())
                .append("contactsOfficeLocation", getContactsOfficeLocation())
                .append("depositBank", getDepositBank())
                .append("accountNumber", getAccountNumber())
                .append("taxNumber", getTaxNumber())
                .append("invoiceAddress", getInvoiceAddress())
                .append("invoiceType", getInvoiceType())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("bizVersion", getBizVersion())
                .toString();
    }
}

