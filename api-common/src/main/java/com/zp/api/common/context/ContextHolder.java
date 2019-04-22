package com.zp.api.common.context;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @program: apiparent
 * @description: 系统全局上下文，拥有存储httpsession
 * @author: zp
 * @create: 2019-04-19 22:26
 **/
public class ContextHolder {

    /**用于保存HttpRequest 对象   */
    private static final ThreadLocal<HttpServletRequest> requestThradLocal=new ThreadLocal<>();
    /** 用于保存HttpResponse*/
    private static  final  ThreadLocal<HttpServletResponse>responseThreadLocal=new ThreadLocal<>();
    /** 用于保存httpsession*/
    private  static final ThreadLocal<HttpSession>session=new ThreadLocal<>();

    public static  void setRequestThradLocal(HttpServletRequest request){
        requestThradLocal.set(request);
    }

    public  static  HttpServletRequest getRequest(){
        return  requestThradLocal.get();
    }

    public static  void setResponseThreadLocal(HttpServletResponse response){
        responseThreadLocal.set(response);
    }

    public static HttpServletResponse getResponse(){
        return responseThreadLocal.get();
    }

    public static void  setSession(HttpSession httpsession){
        session.set(httpsession);
    }

    public static HttpSession getSession(){
        return session.get();
    }
}
