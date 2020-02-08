package com.sdu.fund.biz.shared.query.service;

import com.sdu.fund.biz.shared.query.vo.FundCompanyVO;


/**
 * @program: fundproduct
 * @description:
 * @author: anonymous
 * @create: 2020/2/5 20:57
 **/
public interface FundCompanyQueryService {

    public FundCompanyVO queryFundCompanyInfoList(String fundCompanyId);

}
