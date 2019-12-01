package com.sdu.fund.common.dal.daoimpl;

import com.sdu.fund.common.dal.dao.FundManagerDao;
import com.sdu.fund.common.dal.entity.FundManagerDo;
import com.sdu.fund.common.dal.mapper.FundManagerMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @program: fundproduct
 * @description:
 * @author: anonymous
 * @create: 2019-12-01 01:16
 **/
public class FundManagerDaoImpl implements FundManagerDao {

    @Autowired
    private FundManagerMapper fundManagerMapper;

    @Override
    public int deleteByPrimaryKey(String managerId) {
        return fundManagerMapper.deleteByPrimaryKey(managerId);
    }

    @Override
    public int insert(FundManagerDo record) {
        return fundManagerMapper.insert(record);
    }

    @Override
    public int insertSelective(FundManagerDo record) {
        return fundManagerMapper.insertSelective(record);
    }

    @Override
    public FundManagerDo selectByPrimaryKey(String managerId) {
        return fundManagerMapper.selectByPrimaryKey(managerId);
    }

    @Override
    public int updateByPrimaryKeySelective(FundManagerDo record) {
        return fundManagerMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(FundManagerDo record) {
        return fundManagerMapper.updateByPrimaryKey(record);
    }
}
