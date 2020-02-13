package com.sdu.fund.core.converter;

import com.sdu.fund.core.model.enums.FundTypeEnum;

/**
 * @program: fundproduct
 * @description: 爬取数据的基金类型和模型中基金类型的转换
 * @author: anonymous
 * @create: 2020/2/9 17:21
 **/
public class FundTypeConverter {

    public static FundTypeEnum convert2Model(String data){
        switch (data){
            case "混合型":
                return FundTypeEnum.MIX;
            case "QDII":
            case "QDII-ETF":
            case "QDII-指数":
                return FundTypeEnum.QDII;
            case "保本型":
            case "固定收益":
            case "理财型":
                return FundTypeEnum.CAPITAL;
            case "债券型":
            case "定开债券":
                return FundTypeEnum.BOND;
            case "债券指数":
            case "股票指数":
                return FundTypeEnum.INDEX;
            case "混合-FOF":
                return FundTypeEnum.FOF;
            case "联接基金":
            case "ETF-场内":
                return FundTypeEnum.ETF;
            case "分级杠杆":
                return FundTypeEnum.STRUCTURED;
            case "货币型":
            case "其他创新":
                return FundTypeEnum.CURRENCY;
            case "股票型":
            default:
                return FundTypeEnum.SHARES;
        }
    }

    public static String convert2Data(FundTypeEnum model){
return null;
    }
}
