package cn.neusoft.xuxiao.dao.entity;

import lombok.Data;

/**
 * 请求日志实体类
 */
@Data
public class RequestLog {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer user_id;

    /**
     * 用户名
     */
    private String user_name;
    /**
     * 请求的url
     */
    private String url;

    /**
     * 请求的方法名
     */
    private String method;
    /**
     * 请求的参数(纯文本)
     */
    private String params;
    /**
     * 客户端IP地址
     */
    private String client_ip;
    /**
     * 类名
     */
    private String class_name;
    /**
     * 请求时间
     */
    private String request_time;
    /**
     * 响应时间
     */
    private String response_time;
    /**
     * 总耗时（毫秒）
     */
    private Integer spend_time;

    /**
     * 响应状态码
     */
    private Integer response_code;

    /**
     * 响应信息
     */
    private String response_remark;


}
