package com.sdu.fund.biz.shared.query.serviceImpl;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.sdu.fund.biz.shared.query.service.FundArchiveQueryService;
import com.sdu.fund.biz.shared.query.vo.FundArchiveVO;
import com.sdu.fund.core.model.bo.FundArchive;
import com.sdu.fund.core.repository.FundArchiveRepository;

/**
 * @program: fundproduct
 * @description:
 * @author: anonymous
 * @create: 2020/2/7 19:23
 **/
public class FundArchiveQueryServiceImpl implements FundArchiveQueryService {

    @SofaReference
    private FundArchiveRepository fundArchiveRepository;

    @Override
    public FundArchiveVO queryFundArchive(String fundCode) {
        FundArchive fundArchive = fundArchiveRepository.get(fundCode);
        return new FundArchiveVO().convert(fundArchive);
    }
}
