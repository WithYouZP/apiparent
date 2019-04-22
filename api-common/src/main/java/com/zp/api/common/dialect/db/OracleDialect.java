package com.zp.api.common.dialect.db;

import com.zp.api.common.dialect.Dialect;

/**
 * @program: apiparent
 * @description: oracle方言解析器
 * @author: zp
 * @create: 2019-04-22 09:49
 **/
public class OracleDialect implements Dialect {
    @Override
    public String getLimitString(String sql, int firstResult, int maxResult, int initResult) {
        sql = sql.trim();
        boolean isForUpdate = false;
        if (sql.toLowerCase().endsWith(" for update")) {
            sql = sql.substring(0, sql.length() - 11);
            isForUpdate = true;
        }
        StringBuilder stringBuilder = new StringBuilder(sql.length() + 100);

        if (firstResult > 0) {
            stringBuilder.append("select * from ( select row_.*, rownum rownum_ from ( ");
        } else {
            stringBuilder.append("select row_.* from ( ");
        }

        stringBuilder.append(sql);
        if (firstResult > 0) {
            stringBuilder.append(" ) row_ where rownum <= ").append(maxResult).append(")").append(" where rownum_ > ")
                    .append(firstResult);
        } else {
            stringBuilder.append(" ) row_ where rownum <= ").append(initResult);
        }
        if (isForUpdate) {
            stringBuilder.append(" for update");
        }
        return stringBuilder.toString();
    }
}
