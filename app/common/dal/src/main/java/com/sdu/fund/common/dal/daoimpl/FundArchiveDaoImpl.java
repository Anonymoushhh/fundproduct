package com.sdu.fund.common.dal.daoimpl;

import com.sdu.fund.common.dal.dao.FundArchiveDao;
import com.sdu.fund.common.dal.entity.FundArchiveDo;
import com.sdu.fund.common.dal.mapper.FundArchiveMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @program: fundproduct
 * @description:
 * @author: anonymous
 * @create: 2019-12-01 01:15
 **/
public class FundArchiveDaoImpl implements FundArchiveDao {

    @Autowired
    private FundArchiveMapper fundArchiveMapper;

    @Override
    public int deleteByPrimaryKey(String fundCode) {
        return fundArchiveMapper.deleteByPrimaryKey(fundCode);
    }

    @Override
    public int insert(FundArchiveDo record) {
        return fundArchiveMapper.insert(record);
    }

    @Override
    public int insertSelective(FundArchiveDo record) {
        return fundArchiveMapper.insertSelective(record);
    }

    @Override
    public FundArchiveDo selectByPrimaryKey(String fundCode) {
        return fundArchiveMapper.selectByPrimaryKey(fundCode);
    }

    @Override
    public int updateByPrimaryKeySelective(FundArchiveDo record) {
        return fundArchiveMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(FundArchiveDo record) {
        return fundArchiveMapper.updateByPrimaryKey(record);
    }
}
