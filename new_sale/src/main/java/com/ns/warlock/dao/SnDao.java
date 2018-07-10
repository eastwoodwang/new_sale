package com.ns.warlock.dao;


import org.apache.ibatis.annotations.Param;

/**
 *
 */
public interface SnDao {

    /**
     * 获取最近一位数
     * @return
     */
    long getLastValue(int type);

    /**
     * 更新数据
     * @param lastValue
     */
    void updateLastValue(long lastValue, int type);

}
