package com.sdu.fund.core.constants;

/**
 * @program: fundproduct
 * @description:
 * @author: anonymous
 * @create: 2019-12-06 21:26
 **/
public class Url {

    // fundcode替换
    public static final String fundCodeList = "http://fund.eastmoney.com/js/fundcode_search.js";

    // fundcode替换
    public static final String fundDetail = "http://fund.eastmoney.com/pingzhongdata/fundcode.js";

    // fundcode替换
    public static final String fundDetailHtml = "http://fundf10.eastmoney.com/jbgk_fundcode.html";

    // fundcode替换
    public static final String fundCompanyDetail = "http://fund.eastmoney.com/company/fundcode.html";

    // 基金经理信息（分页，page替换）
    public static final String fundManagerList = "http://fund.eastmoney.com/Data/FundDataPortfolio_Interface.aspx?dt=14&mc=returnjson&ft=all&pn=50&pi=page&sc=abbname&st=asc";

    // managerid替换
    public static final String fundManagerHtml = "http://fund.eastmoney.com/manager/managerid.html";

    // startDate,endDate替换
    public static final String fundDataList = "http://fund.eastmoney.com/data/rankhandler" +
            ".aspx?op=ph&dt=kf&ft=all&rs=&gs=0&sc=zzf&st=desc&sd=startDate&ed=endDate&qdii=&tabSubtype=,,,,," +
            "&pi=1&pn=10000&dx=1";

    // fundcode替换
    public static final String fundDataHtml = "http://fundf10.eastmoney.com/jjfl_fundcode.html";

}
