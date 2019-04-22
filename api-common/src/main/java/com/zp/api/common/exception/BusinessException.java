package com.zp.api.common.exception;

/**
 * @program: apiparent
 * @description: 业务操作自定义异常
 * @author: zp
 * @create: 2019-04-22 10:00
 **/
public class BusinessException extends RuntimeException{

    public  BusinessException(){
        super();
    }

    public BusinessException(String message,Throwable throwable){
        super(message,throwable);
    }

    public BusinessException(String message){
        super(message);
    }

    public BusinessException (Throwable throwable){
        super(throwable);
    }

}
