package com.sdu.fund.biz.shared.query.service;


import com.sdu.fund.biz.shared.query.vo.NetValuesVO;


/**
 * @program: fundproduct
 * @description:
 * @author: anonymous
 * @create: 2020/2/5 20:57
 **/
public interface FundDataQueryService {

    public NetValuesVO queryNetValues(String fundCode);

}
