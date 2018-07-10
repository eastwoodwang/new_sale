package com.ns.warlock.filter;

import com.alibaba.fastjson.JSON;
import com.ns.warlock.common.ErrorCode;
import com.ns.warlock.common.Result;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

/**
 * 定义过滤器规定每月初1号00:00:00 - 02:00:00时间段任何请求无法处理
 */
public class QuatzFilter implements Filter {

    public FilterConfig config;

    /**
     * 初始化获取WEB.xml里面的配置
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String loginPath = config.getInitParameter("loginPath");
        if (isContains(httpServletRequest.getServletPath(), loginPath.split(","))) { //不过滤
            chain.doFilter(request, response);
            return;
        } else {

            LocalDateTime nowDateTime = LocalDateTime.now();

            LocalTime localSTTime = LocalTime.of(0, 0, 0); // 00:00:00
            LocalTime localEDTime = LocalTime.of(2, 0, 0); // 02:00:00

            //获取当前时间月的第一天
            LocalDate nowLocalDate = LocalDate.now();
            LocalDate firstDayOfThisMonth = nowLocalDate.with(TemporalAdjusters.firstDayOfMonth());


            LocalDateTime localDateTimeST = LocalDateTime.of(firstDayOfThisMonth, localSTTime); //每月第一天 00:00:00
            LocalDateTime localDateTimeED = LocalDateTime.of(firstDayOfThisMonth, localEDTime); //每月第一天 04:00:00

            //如果请求的时间在这两耳时间段间隔
            if (nowDateTime.isAfter(localDateTimeST) && nowDateTime.isBefore(localDateTimeED)) {
                //设置输出流
                PrintWriter out = httpServletResponse.getWriter();
                httpServletResponse.setContentType("application/json; charset=utf-8");

                Result result = new Result("1", ErrorCode.SYSTEM_MAINTAIN.getErrorValue());
                out.print(JSON.toJSONString(result));
                out.flush();
                out.close();
            } else {
                chain.doFilter(request, response);
                return;
            }
        }
    }

    /**
     * 私有检查格式是否匹配的
     * @param container
     * @param regx
     * @return
     */
    private boolean isContains(String container, String[] regx) {
        boolean result = false;
        for (int i = 0; i < regx.length; i++) {
            if (container.indexOf(regx[i]) != -1) {
                return true;
            }
        }
        return result;
    }

    @Override
    public void destroy() {
        this.config = null;
    }
}
