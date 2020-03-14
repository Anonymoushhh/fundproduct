package com.sdu.fund.common.dal.extMapper;

import com.sdu.fund.common.dal.entity.FundDataDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExtFundDataMapper {

    List<FundDataDo> selectFundList(@Param("fundType") Integer fundType, @Param("gainType") String gainType, @Param(
            "curIndex") int curIndex, @Param("pageSize") int pageSize);
}