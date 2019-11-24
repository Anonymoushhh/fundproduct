package com.sdu.fund.common.dal.entity;

import java.util.Date;

public class fundCompanyDo {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fundCompany.fund_company_code
     *
     * @mbg.generated Thu Nov 21 20:12:48 CST 2019
     */
    private String fundCompanyCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fundCompany.fund_company_name
     *
     * @mbg.generated Thu Nov 21 20:12:48 CST 2019
     */
    private String fundCompanyName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fundCompany.establish_date
     *
     * @mbg.generated Thu Nov 21 20:12:48 CST 2019
     */
    private Date establishDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fundCompany.fund_account
     *
     * @mbg.generated Thu Nov 21 20:12:48 CST 2019
     */
    private Integer fundAccount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fundCompany.manager
     *
     * @mbg.generated Thu Nov 21 20:12:48 CST 2019
     */
    private String manager;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fundCompany.fund_company_name_abbr
     *
     * @mbg.generated Thu Nov 21 20:12:48 CST 2019
     */
    private String fundCompanyNameAbbr;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fundCompany.fund_company_name_acronym
     *
     * @mbg.generated Thu Nov 21 20:12:48 CST 2019
     */
    private String fundCompanyNameAcronym;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fundCompany.management_scale
     *
     * @mbg.generated Thu Nov 21 20:12:48 CST 2019
     */
    private String managementScale;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fundCompany.grade
     *
     * @mbg.generated Thu Nov 21 20:12:48 CST 2019
     */
    private String grade;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fundCompany.update_time
     *
     * @mbg.generated Thu Nov 21 20:12:48 CST 2019
     */
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fundCompany.ext_info
     *
     * @mbg.generated Thu Nov 21 20:12:48 CST 2019
     */
    private String extInfo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fundCompany.gmt_create
     *
     * @mbg.generated Thu Nov 21 20:12:48 CST 2019
     */
    private Date gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fundCompany.gmt_update
     *
     * @mbg.generated Thu Nov 21 20:12:48 CST 2019
     */
    private Date gmtUpdate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fundCompany.fund_company_code
     *
     * @return the value of fundCompany.fund_company_code
     *
     * @mbg.generated Thu Nov 21 20:12:48 CST 2019
     */
    public String getFundCompanyCode() {
        return fundCompanyCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fundCompany.fund_company_code
     *
     * @param fundCompanyCode the value for fundCompany.fund_company_code
     *
     * @mbg.generated Thu Nov 21 20:12:48 CST 2019
     */
    public void setFundCompanyCode(String fundCompanyCode) {
        this.fundCompanyCode = fundCompanyCode == null ? null : fundCompanyCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fundCompany.fund_company_name
     *
     * @return the value of fundCompany.fund_company_name
     *
     * @mbg.generated Thu Nov 21 20:12:48 CST 2019
     */
    public String getFundCompanyName() {
        return fundCompanyName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fundCompany.fund_company_name
     *
     * @param fundCompanyName the value for fundCompany.fund_company_name
     *
     * @mbg.generated Thu Nov 21 20:12:48 CST 2019
     */
    public void setFundCompanyName(String fundCompanyName) {
        this.fundCompanyName = fundCompanyName == null ? null : fundCompanyName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fundCompany.establish_date
     *
     * @return the value of fundCompany.establish_date
     *
     * @mbg.generated Thu Nov 21 20:12:48 CST 2019
     */
    public Date getEstablishDate() {
        return establishDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fundCompany.establish_date
     *
     * @param establishDate the value for fundCompany.establish_date
     *
     * @mbg.generated Thu Nov 21 20:12:48 CST 2019
     */
    public void setEstablishDate(Date establishDate) {
        this.establishDate = establishDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fundCompany.fund_account
     *
     * @return the value of fundCompany.fund_account
     *
     * @mbg.generated Thu Nov 21 20:12:48 CST 2019
     */
    public Integer getFundAccount() {
        return fundAccount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fundCompany.fund_account
     *
     * @param fundAccount the value for fundCompany.fund_account
     *
     * @mbg.generated Thu Nov 21 20:12:48 CST 2019
     */
    public void setFundAccount(Integer fundAccount) {
        this.fundAccount = fundAccount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fundCompany.manager
     *
     * @return the value of fundCompany.manager
     *
     * @mbg.generated Thu Nov 21 20:12:48 CST 2019
     */
    public String getManager() {
        return manager;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fundCompany.manager
     *
     * @param manager the value for fundCompany.manager
     *
     * @mbg.generated Thu Nov 21 20:12:48 CST 2019
     */
    public void setManager(String manager) {
        this.manager = manager == null ? null : manager.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fundCompany.fund_company_name_abbr
     *
     * @return the value of fundCompany.fund_company_name_abbr
     *
     * @mbg.generated Thu Nov 21 20:12:48 CST 2019
     */
    public String getFundCompanyNameAbbr() {
        return fundCompanyNameAbbr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fundCompany.fund_company_name_abbr
     *
     * @param fundCompanyNameAbbr the value for fundCompany.fund_company_name_abbr
     *
     * @mbg.generated Thu Nov 21 20:12:48 CST 2019
     */
    public void setFundCompanyNameAbbr(String fundCompanyNameAbbr) {
        this.fundCompanyNameAbbr = fundCompanyNameAbbr == null ? null : fundCompanyNameAbbr.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fundCompany.fund_company_name_acronym
     *
     * @return the value of fundCompany.fund_company_name_acronym
     *
     * @mbg.generated Thu Nov 21 20:12:48 CST 2019
     */
    public String getFundCompanyNameAcronym() {
        return fundCompanyNameAcronym;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fundCompany.fund_company_name_acronym
     *
     * @param fundCompanyNameAcronym the value for fundCompany.fund_company_name_acronym
     *
     * @mbg.generated Thu Nov 21 20:12:48 CST 2019
     */
    public void setFundCompanyNameAcronym(String fundCompanyNameAcronym) {
        this.fundCompanyNameAcronym = fundCompanyNameAcronym == null ? null : fundCompanyNameAcronym.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fundCompany.management_scale
     *
     * @return the value of fundCompany.management_scale
     *
     * @mbg.generated Thu Nov 21 20:12:48 CST 2019
     */
    public String getManagementScale() {
        return managementScale;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fundCompany.management_scale
     *
     * @param managementScale the value for fundCompany.management_scale
     *
     * @mbg.generated Thu Nov 21 20:12:48 CST 2019
     */
    public void setManagementScale(String managementScale) {
        this.managementScale = managementScale == null ? null : managementScale.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fundCompany.grade
     *
     * @return the value of fundCompany.grade
     *
     * @mbg.generated Thu Nov 21 20:12:48 CST 2019
     */
    public String getGrade() {
        return grade;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fundCompany.grade
     *
     * @param grade the value for fundCompany.grade
     *
     * @mbg.generated Thu Nov 21 20:12:48 CST 2019
     */
    public void setGrade(String grade) {
        this.grade = grade == null ? null : grade.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fundCompany.update_time
     *
     * @return the value of fundCompany.update_time
     *
     * @mbg.generated Thu Nov 21 20:12:48 CST 2019
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fundCompany.update_time
     *
     * @param updateTime the value for fundCompany.update_time
     *
     * @mbg.generated Thu Nov 21 20:12:48 CST 2019
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fundCompany.ext_info
     *
     * @return the value of fundCompany.ext_info
     *
     * @mbg.generated Thu Nov 21 20:12:48 CST 2019
     */
    public String getExtInfo() {
        return extInfo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fundCompany.ext_info
     *
     * @param extInfo the value for fundCompany.ext_info
     *
     * @mbg.generated Thu Nov 21 20:12:48 CST 2019
     */
    public void setExtInfo(String extInfo) {
        this.extInfo = extInfo == null ? null : extInfo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fundCompany.gmt_create
     *
     * @return the value of fundCompany.gmt_create
     *
     * @mbg.generated Thu Nov 21 20:12:48 CST 2019
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fundCompany.gmt_create
     *
     * @param gmtCreate the value for fundCompany.gmt_create
     *
     * @mbg.generated Thu Nov 21 20:12:48 CST 2019
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fundCompany.gmt_update
     *
     * @return the value of fundCompany.gmt_update
     *
     * @mbg.generated Thu Nov 21 20:12:48 CST 2019
     */
    public Date getGmtUpdate() {
        return gmtUpdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fundCompany.gmt_update
     *
     * @param gmtUpdate the value for fundCompany.gmt_update
     *
     * @mbg.generated Thu Nov 21 20:12:48 CST 2019
     */
    public void setGmtUpdate(Date gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }
}