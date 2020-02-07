package com.sdu.fund.biz.shared.query.service;


import com.sdu.fund.biz.shared.query.vo.FundManagerVO;
import com.sdu.fund.biz.shared.query.vo.RankVO;

import java.util.List;

/**
 * @program: fundproduct
 * @description:
 * @author: anonymous
 * @create: 2020/2/5 20:57
 **/
public interface FundCommonQueryService {

    public List<RankVO> queryFundList(Integer sortType, Integer fundType, Integer gainType, Integer curPage,
                                      Integer pageSize);

    public FundManagerVO queryFundManager(String managerId);
}
