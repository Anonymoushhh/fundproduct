package com.sdu.fund.biz.shared.query.serviceImpl;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.sdu.fund.biz.shared.query.service.FundCompanyQueryService;
import com.sdu.fund.biz.shared.query.vo.FundCompanyVO;
import com.sdu.fund.core.model.bo.FundCompany;
import com.sdu.fund.core.repository.FundCompanyRepository;

/**
 * @program: fundproduct
 * @description:
 * @author: anonymous
 * @create: 2020/2/7 19:23
 **/
public class FundCompanyQueryServiceImpl implements FundCompanyQueryService {

    @SofaReference
    private FundCompanyRepository fundCompanyRepository;

    @Override
    public FundCompanyVO queryFundCompanyInfoList(String fundCompanyId) {
        FundCompany fundCompany = fundCompanyRepository.get(fundCompanyId);
        return new FundCompanyVO().convert(fundCompany);
    }
}
