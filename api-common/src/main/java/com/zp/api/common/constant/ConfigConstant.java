package com.zp.api.common.constant;

/**
 * @program: apiparent
 * @description: 系统静态常量类
 * @author: zp
 * @create: 2019-04-19 22:17
 **/
public abstract class ConfigConstant {
    /** 扫描基础包*/
    public static final String SCAN_BASE_PACKAGES = "cn.zp";
    /** http 请求的5中形式    */
    public final static String[] HTTP_METOHD = { "GET", "POST", "PUT", "DELETE", "OPTIONS" };
    /** swagger 访问的页面    */
    public final static String SWAGGER_UI_HTML = "/swagger-ui.html";

    public final static String SWAGGER_UI_URL = "classpath:/META-INF/resources/";

    public final static String SWAGGER_WEBJARS = "/webjars/**";

    public final static String SWAGGER_WEBJARS_URL = "classpath:/META-INF/resources/webjars/";

    public final static String SWAGGER_FLAG_ENABLE = "Y";

    public final static String SWAGGER_FLAG_DISABLE = "N";
    public final static String SYSTEM_MSG_ERROR = "System.error";
    public static final int DEL_FLAG_ZERO = 0;

    public static final int DEL_FLAG_ONE = 1;

    public final static String CONTENT_TYPE = "application/json;UTF-8";

    public static final String SESSION_TOKEN = "xaccesstokensession";

    public static final String MODELREF_TYPE = "string";

    public static final String SESSION_TOKEN_DESC = "token";

    public static final String PARAMETER_TYPE = "header";

    public static final String TOKEN_ERROR="token error！";


}
