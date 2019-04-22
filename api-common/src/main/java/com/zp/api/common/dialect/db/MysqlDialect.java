package com.zp.api.common.dialect.db;

import com.zp.api.common.dialect.Dialect;

/**
 * @program: apiparent
 * @description: mysql方言解析器
 * @author: zp
 * @create: 2019-04-19 23:20
 **/
public class MysqlDialect implements Dialect {
    @Override
    public String getLimitString(String sql, int firstResult, int maxResult, int initResult) {
        StringBuilder sb = new StringBuilder();
        sb.append(" LIMIT  ");
        if (firstResult >0){
            sb.append(firstResult).append(" ,").append(maxResult);
        }else{
            sb.append(maxResult);
        }
        return sb.toString();
    }
}
