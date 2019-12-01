package com.sdu.fund.common.dal.daoimpl;

import com.sdu.fund.common.dal.dao.FundCompanyDao;
import com.sdu.fund.common.dal.entity.FundCompanyDo;
import com.sdu.fund.common.dal.mapper.FundCompanyMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @program: fundproduct
 * @description:
 * @author: anonymous
 * @create: 2019-12-01 01:15
 **/
public class FundCompanyDaoImpl implements FundCompanyDao {

    @Autowired
    private FundCompanyMapper fundCompanyMapper;

    @Override
    public int deleteByPrimaryKey(String fundCompanyCode) {
        return fundCompanyMapper.deleteByPrimaryKey(fundCompanyCode);
    }

    @Override
    public int insert(FundCompanyDo record) {
        return fundCompanyMapper.insert(record);
    }

    @Override
    public int insertSelective(FundCompanyDo record) {
        return fundCompanyMapper.insertSelective(record);
    }

    @Override
    public FundCompanyDo selectByPrimaryKey(String fundCompanyCode) {
        return fundCompanyMapper.selectByPrimaryKey(fundCompanyCode);
    }

    @Override
    public int updateByPrimaryKeySelective(FundCompanyDo record) {
        return fundCompanyMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(FundCompanyDo record) {
        return fundCompanyMapper.updateByPrimaryKey(record);
    }
}
