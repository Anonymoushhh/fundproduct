package com.sdu.fund.biz.shared.query.serviceImpl;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.sdu.fund.biz.shared.query.service.FundManagerQueryService;
import com.sdu.fund.biz.shared.query.vo.FundManagerVO;
import com.sdu.fund.core.model.bo.FundManager;
import com.sdu.fund.core.repository.FundManagerRepository;

/**
 * @program: fundproduct
 * @description:
 * @author: anonymous
 * @create: 2020/2/7 19:23
 **/
public class FundManagerQueryServiceImpl implements FundManagerQueryService {

    @SofaReference
    private FundManagerRepository fundManagerRepository;

    @Override
    public FundManagerVO queryFundManagerInfoList(String managerId) {
        FundManager fundManager = fundManagerRepository.get(managerId);
        return new FundManagerVO().convert(fundManager);
    }
}
