package com.sdu.fund.biz.shared.query.service.wxpages;

import com.sdu.fund.biz.shared.query.vo.FooterMessageVO;
import com.sdu.fund.biz.shared.query.vo.NetValuesVO;
import com.sdu.fund.biz.shared.query.vo.TopMessageVO;


/**
 * @program: fundproduct
 * @description:
 * @author: anonymous
 * @create: 2020/2/7 12:23
 **/
public interface FundDetailPageService {

    public TopMessageVO queryTopMessage(String fundCode);


    public FooterMessageVO queryFooterMessage(String fundCode);
}
