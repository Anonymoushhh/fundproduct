package com.sdu.fund.biz.shared.query.serviceImpl;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.sdu.fund.biz.shared.query.service.FundDataQueryService;
import com.sdu.fund.biz.shared.query.vo.FundManagerVO;
import com.sdu.fund.biz.shared.query.vo.NetValuesVO;
import com.sdu.fund.biz.shared.query.vo.RankVO;
import com.sdu.fund.core.model.bo.FundData;
import com.sdu.fund.core.repository.FundDataRepository;

import java.util.List;

/**
 * @program: fundproduct
 * @description:
 * @author: anonymous
 * @create: 2020/2/7 19:05
 **/
public class FundDataQueryServiceImpl implements FundDataQueryService {

    @SofaReference
    private FundDataRepository fundDataRepository;

    @Override
    public NetValuesVO queryNetValues(String fundCode) {
        FundData fundData = fundDataRepository.get(fundCode);
        return new NetValuesVO().convert(fundData);
    }
}
