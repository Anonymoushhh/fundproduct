package com.sdu.fund.biz.shared.query.serviceImpl.wxpages;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.sdu.fund.biz.shared.query.service.wxpages.FundDetailPageService;
import com.sdu.fund.biz.shared.query.vo.FooterMessageVO;
import com.sdu.fund.biz.shared.query.vo.NetValuesVO;
import com.sdu.fund.biz.shared.query.vo.TopMessageVO;
import com.sdu.fund.core.model.bo.FundArchive;
import com.sdu.fund.core.model.bo.FundData;
import com.sdu.fund.core.repository.FundArchiveRepository;
import com.sdu.fund.core.repository.FundDataRepository;

/**
 * @program: fundproduct
 * @description:
 * @author: anonymous
 * @create: 2020/2/7 12:28
 **/
public class FundDetailPageServiceImpl implements FundDetailPageService {

    @SofaReference
    private FundArchiveRepository fundArchiveRepository;

    @Override
    public TopMessageVO queryTopMessage(String fundCode) {
        FundArchive fundArchive = fundArchiveRepository.get(fundCode);
        return new TopMessageVO().convert(fundArchive);
    }

    @Override
    public FooterMessageVO queryFooterMessage(String fundCode) {
        FundArchive fundArchive = fundArchiveRepository.get(fundCode);
        return new FooterMessageVO().convert(fundArchive);
    }
}
