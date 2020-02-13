package com.sdu.fund.biz.shared.query.serviceImpl;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.google.common.collect.Lists;
import com.sdu.fund.common.exception.CommonException;
import com.sdu.fund.core.model.enums.FundTypeEnum;
import com.sdu.fund.biz.shared.enums.GainTypeEnum;
import com.sdu.fund.biz.shared.query.vo.RankVO;
import com.sdu.fund.biz.shared.query.service.FundCommonQueryService;
import com.sdu.fund.common.result.Result;
import com.sdu.fund.common.validator.Validator;
import com.sdu.fund.core.model.bo.FundData;
import com.sdu.fund.core.repository.FundDataRepository;

import java.util.List;

/**
 * @program: fundproduct
 * @description:
 * @author: anonymous
 * @create: 2020/2/5 20:57
 **/
public class FundCommonQueryServiceImpl implements FundCommonQueryService {

    @SofaReference
    private FundDataRepository fundDataRepository;

    @Override
    public List<RankVO> queryFundList(Integer sortType, Integer fundType, Integer gainType, Integer curPage,
                                      Integer pageSize) {
        List<RankVO> rankVOS = Lists.newArrayList();

        FundTypeEnum fundTypeEnum = FundTypeEnum.getEnumByCode(fundType);
        GainTypeEnum gainTypeEnum = GainTypeEnum.getEnumByCode(gainType);
        Validator.notNull(fundTypeEnum);
        Validator.notNull(gainTypeEnum);

        Result<List<FundData>> result = fundDataRepository.getFundList(fundTypeEnum.getCode(),
                gainTypeEnum.getMsg(), (curPage - 1) * pageSize, pageSize);
        if (result != null && result.isSuccess()) {
            List<FundData> fundDatas = result.getData();
            for (FundData fundData : fundDatas) {
                rankVOS.add(new RankVO().convert(fundData));
            }
        }else{
            throw new CommonException("FundCommonQueryServiceImpl.queryFundList出错！");
        }
        return rankVOS;
    }
}
