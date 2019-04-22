package com.zp.api.common.exception;

/**
 * @program: apiparent
 * @description: Dao自定义异常
 * @author: zp
 * @create: 2019-04-22 10:07
 **/
public class DaoException extends RuntimeException {

    public DaoException(){
        super();
    }

    public DaoException(String mesaage,Throwable throwable){
        super(mesaage,throwable);
    }

    public DaoException(String message){
        super(message);
    }

    public DaoException(Throwable throwable){
        super(throwable);
    }



}
