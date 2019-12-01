package com.sdu.fund.core.repository;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.sdu.fund.common.code.ResultCode;
import com.sdu.fund.common.dal.entity.FundCompanyDo;
import com.sdu.fund.common.result.Result;
import com.sdu.fund.common.util.ResultUtil;
import com.sdu.fund.core.converter.FundCompanyConverter;
import com.sdu.fund.core.model.bo.FundCompany;

import java.util.List;

import com.sdu.fund.common.dal.dao.FundCompanyDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;


/**
 * @program: fundproduct
 * @description: 基金公司仓储层
 * @author: anonymous
 * @create: 2019-11-28 23:21
 **/
public class FundCompanyRepository implements Repository<FundCompany> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FundCompanyRepository.class);

    @SofaReference
    private FundCompanyDao fundCompanyDao;

    public FundCompany get(String fundCompanyCode) {
        return FundCompanyConverter.FundCompanyDoconvert2FundCompany(fundCompanyDao.selectByPrimaryKey(fundCompanyCode));
    }

    public Result add(FundCompany fundCompany) {
        // 预校验
        boolean check = preCheck(fundCompany);
        if (!check) {
            LOGGER.error("基金公司信息插入失败，fundCompanyCode={},errCode={}", fundCompany.getFundCompanyCode(),
                ResultCode.PARAMETER_ILLEGAL);
            return ResultUtil.buildFailedResult(ResultCode.PARAMETER_ILLEGAL);
        }

        try {
            int id = fundCompanyDao.insert(FundCompanyConverter.FundCompanyconvert2FundCompanyDo(fundCompany));
            if (id > 0) {
                return ResultUtil.buildSuccessResult();
            } else {
                LOGGER.error("基金公司信息插入失败，fundCompanyCode={},errCode={}", fundCompany.getFundCompanyCode(),
                    ResultCode.DATABASE_EXCEPTION);
                return ResultUtil.buildFailedResult(ResultCode.DATABASE_EXCEPTION);
            }
        } catch (DataAccessException e1) {
            LOGGER.error("基金公司信息插入失败，fundCompanyCode={},errCode={}", fundCompany.getFundCompanyCode(),
                ResultCode.DATABASE_EXCEPTION);
            return ResultUtil.buildFailedResult(ResultCode.DATABASE_EXCEPTION);
        } catch (Exception e2) {
            LOGGER.error("基金公司信息插入失败，fundCompanyCode={},errCode={}", fundCompany.getFundCompanyCode(),
                ResultCode.SERVER_EXCEPTION);
            return ResultUtil.buildFailedResult(ResultCode.SERVER_EXCEPTION);
        }
    }

    public Result update(FundCompany fundCompany) {
        // 预校验
        boolean check = preCheck(fundCompany);
        if (!check) {
            LOGGER.error("基金公司信息插入失败，fundCompanyCode={},errCode={}", fundCompany.getFundCompanyCode(),
                ResultCode.PARAMETER_ILLEGAL);
            return ResultUtil.buildFailedResult(ResultCode.PARAMETER_ILLEGAL);
        }

        try {
            int count =
                fundCompanyDao.updateByPrimaryKey(FundCompanyConverter.FundCompanyconvert2FundCompanyDo(fundCompany));
            if (count > 0) {
                return ResultUtil.buildSuccessResult();
            } else {
                LOGGER.error("基金公司信息更新失败，fundCompanyCode={},errCode={}", fundCompany.getFundCompanyCode(),
                    ResultCode.DATABASE_EXCEPTION);
                return ResultUtil.buildFailedResult(ResultCode.DATABASE_EXCEPTION);
            }
        } catch (DataAccessException e1) {
            LOGGER.error("基金公司信息更新失败，fundCompanyCode={},errCode={}", fundCompany.getFundCompanyCode(),
                ResultCode.DATABASE_EXCEPTION);
            return ResultUtil.buildFailedResult(ResultCode.DATABASE_EXCEPTION);
        } catch (Exception e2) {
            LOGGER.error("基金公司信息更新失败，fundCompanyCode={},errCode={}", fundCompany.getFundCompanyCode(),
                ResultCode.SERVER_EXCEPTION);
            return ResultUtil.buildFailedResult(ResultCode.SERVER_EXCEPTION);
        }
    }

    public Result delete(String fundCompanyCode) {
        try {
            int count = fundCompanyDao.deleteByPrimaryKey(fundCompanyCode);
            if (count > 0) {
                return ResultUtil.buildSuccessResult();
            } else {
                LOGGER.error("基金公司信息删除失败，fundCompanyCode={},errCode={}", fundCompanyCode,
                    ResultCode.DATABASE_EXCEPTION);
                return ResultUtil.buildFailedResult(ResultCode.DATABASE_EXCEPTION);
            }
        } catch (DataAccessException e1) {
            LOGGER.error("基金公司信息删除失败，fundCompanyCode={},errCode={}", fundCompanyCode,
                ResultCode.DATABASE_EXCEPTION);
            return ResultUtil.buildFailedResult(ResultCode.DATABASE_EXCEPTION);
        } catch (Exception e2) {
            LOGGER.error("基金公司信息删除失败，fundCompanyCode={},errCode={}", fundCompanyCode,
                ResultCode.SERVER_EXCEPTION);
            return ResultUtil.buildFailedResult(ResultCode.SERVER_EXCEPTION);
        }
    }


    /*
     * @description 预校验
     * @param [fundCompany]
     * @return boolean
     * @author anonymous
     * @date 2019/11/29
     */
    private boolean preCheck(FundCompany fundCompany) {
        boolean check = false;
        if (fundCompany != null && fundCompany.getFundCompanyCode() != null) {
            check = true;
        }
        return check;
    }
}
