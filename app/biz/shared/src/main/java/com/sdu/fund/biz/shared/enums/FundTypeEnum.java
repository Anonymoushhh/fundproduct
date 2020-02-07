package com.sdu.fund.biz.shared.enums;

/**
 * @program: fundproduct
 * @description:
 * @author: anonymous
 * @create: 2020/2/6 17:46
 **/
public enum FundTypeEnum {

    // 股票型
    SHARES(0,"SHARES"),
    // 债券型
    BOND(1,"BOND"),
    // 货币型
    CURRENCY(2,"CURRENCY"),
    // 混合型
    MIX(3,"MIX"),
    // 保本型
    CAPITAL(4,"CAPITAL"),
    // 交易型开放式指数基金
    ETF(5,"ETF"),
    //
    QDII(6,"QDII"),
    // 分级基金
    STRUCTURED(7,"STRUCTURED"),
    // fund of fund
    FOF(8,"FOF"),;
    /**
     * 响应状态码
     */
    private final Integer code;

    /**
     * 响应提示
     */
    private final String msg;

    FundTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static FundTypeEnum getEnumByCode(Integer code) {
        for(FundTypeEnum e:FundTypeEnum.values()){
            if(code == e.getCode()){
                return e;
            }
        }
        return null;
    }

    public static FundTypeEnum getEnumByMsg(String msg) {
        for(FundTypeEnum e:FundTypeEnum.values()){
            if(msg == e.getMsg()){
                return e;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
