package com.zp.api.common.dialect;

/**
 *  sql 方言解析器接口
 * @author  zp
 * @date 2019/4/19
 */
public interface Dialect {
    /**
     * 分页方言解析器接口
     * @param sql sql语句
     * @param firstResult 当前页
     * @param maxResult 每页查询条数
     * @param initResult 第一页查询条数,针对oracle数据库
     * @return
     */
    public String getLimitString(String sql,int firstResult,int maxResult,int initResult);

}
