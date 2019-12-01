package com.sdu.fund.common.dal.mapper;

import com.sdu.fund.common.dal.entity.FundCompanyDo;

public interface FundCompanyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fundCompany
     *
     * @mbg.generated Sun Dec 01 14:20:19 CST 2019
     */
    int deleteByPrimaryKey(String fundCompanyCode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fundCompany
     *
     * @mbg.generated Sun Dec 01 14:20:19 CST 2019
     */
    int insert(FundCompanyDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fundCompany
     *
     * @mbg.generated Sun Dec 01 14:20:19 CST 2019
     */
    int insertSelective(FundCompanyDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fundCompany
     *
     * @mbg.generated Sun Dec 01 14:20:19 CST 2019
     */
    FundCompanyDo selectByPrimaryKey(String fundCompanyCode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fundCompany
     *
     * @mbg.generated Sun Dec 01 14:20:19 CST 2019
     */
    int updateByPrimaryKeySelective(FundCompanyDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fundCompany
     *
     * @mbg.generated Sun Dec 01 14:20:19 CST 2019
     */
    int updateByPrimaryKey(FundCompanyDo record);
}