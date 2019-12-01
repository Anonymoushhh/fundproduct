package com.sdu.fund.common.dal.daoimpl;

import com.sdu.fund.common.dal.dao.FundDataDao;
import com.sdu.fund.common.dal.entity.FundDataDo;
import com.sdu.fund.common.dal.mapper.FundDataMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @program: fundproduct
 * @description:
 * @author: anonymous
 * @create: 2019-12-01 01:15
 **/
public class FundDataDaoImpl implements FundDataDao {

    @Autowired
    private FundDataMapper fundDataMapper;

    @Override
    public int deleteByPrimaryKey(String fundCode) {
        return fundDataMapper.deleteByPrimaryKey(fundCode);
    }

    @Override
    public int insert(FundDataDo record) {
        return fundDataMapper.insert(record);
    }

    @Override
    public int insertSelective(FundDataDo record) {
        return fundDataMapper.insertSelective(record);
    }

    @Override
    public FundDataDo selectByPrimaryKey(String fundCode) {
        return fundDataMapper.selectByPrimaryKey(fundCode);
    }

    @Override
    public int updateByPrimaryKeySelective(FundDataDo record) {
        return fundDataMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(FundDataDo record) {
        return fundDataMapper.updateByPrimaryKey(record);
    }
}
