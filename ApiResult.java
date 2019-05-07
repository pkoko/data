package com.nfrc.modules.core.bean;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Maps;
import com.nfrc.modules.core.utils.JacksonUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 服务接口响应类
 *
 * @author admin
 * @date 2018年5月11日
 */
public class ApiResult<T> implements Serializable {

    /**
     * 成功返回码
     */
    private static final String RESULT_CODE_SUCCESS = "0";

    /**
     * 未知错误返回码
     */
    private static final String RESULT_CODE_FAIL = "-1";


    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * <pre>
     * 返回码
     * 0：成功（默认值）
     * 其他：失败
     * </pre>
     */
    private String code = RESULT_CODE_SUCCESS;

    /**
     * 响应消息内容，可存放错误提示消息或成功消息
     */
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private String message;

    /**
     * 资源地址
     */
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private String resUrl;

    /**
     * token信息
     */
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private String token;

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private List<String> permissionInfo;

    /**
     * 版本号
     */
    private String version = "1";

    /**
     * 兼容优服通老版本接口的响应结果
     */
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private String common_return ;

    /**
     * 响应对象
     */
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private T entry;

    private ApiResult() {

    }


    public String getCommon_return() {
        return common_return;
    }

    public void setCommon_return(String common_return) {
        this.common_return = common_return;
    }

    public List<String> getPermissionInfo() {
        return permissionInfo;
    }

    public void setPermissionInfo(List<String> permissionInfo) {
        this.permissionInfo = permissionInfo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getResUrl() {
        return resUrl;
    }

    public void setResUrl(String resUrl) {
        this.resUrl = resUrl;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getEntry() {
        return entry;
    }

    public void setEntry(T entry) {
        this.entry = entry;
    }

    /**
     * 构建一个新的实例
     *
     * @date 2017年5月9日
     * @author ljq
     */
    public static <T> ApiResult<T> build() {
        return new ApiResult<>();
    }

    public static <T> ApiResult<T> build(Class<T> clazz) {
        return new ApiResult<T>();
    }


    /**
     * 构建一个具有返回对象的实例
     *
     * @param obj 待返回內容
     * @return
     * @date 2017年5月9日
     * @author ljq
     */
    public static <T> ApiResult<T> build(T obj) {
        ApiResult<T> result = new ApiResult<>();
        result.setEntry(obj);
        return result;
    }


    /**
     * 构建一个具有返回对象的实例
     *
     * @param code 错误代码
     * @param msg  错误信息
     * @return
     * @date 2017年5月9日
     * @author ljq
     */
    public static <T> ApiResult<T> isError(String code, String msg) {
        ApiResult<T> result = new ApiResult<>();
        result.error(code, msg);
        return result;
    }


    /**
     * 判断返回结果中响应码是否成功
     *
     * @return
     * @date 2017年5月9日
     * @author ljq
     */
    public boolean isSuccess() {
        return RESULT_CODE_SUCCESS.equals(code);
    }

    /**
     * 返回错误信息，标记响应码为-1
     *
     * @param message 錯誤信息
     * @return
     * @date 2017年5月9日
     * @author ljq
     */
    public ApiResult<T> error(String message) {
        return error(RESULT_CODE_FAIL, message);
    }


    /**
     * 返回错误信息，标记响应码为具体的错误码
     *
     * @param code    錯誤編碼
     * @param message 錯誤信息
     * @return
     * @date 2017年5月9日
     * @author ljq
     */
    public ApiResult<T> error(String code, String message) {
        this.code = code;
        this.message = message;
        return this;
    }

    /**
     * 判断返回结果是否失败
     *
     * @param result 返回结果对象
     * @return 如果失败, 则返回true；否则返回false
     * @date 2017年5月18日
     * @author ljq
     */
    public static boolean isFail(ApiResult<?> result) {
        return result == null || !result.isSuccess();
    }

    /**
     * 判断返回结果是否成功
     *
     * @param result 返回结果对象
     * @return 如果成功, 则返回true；否则返回false
     * @date 2017年5月24日
     * @author ljq
     */
    public static boolean isSuccess(ApiResult<?> result) {
        return result != null && result.isSuccess();
    }

    @Override
    public String toString() {
        return JacksonUtils.bean2Json(this);
    }


    private static Map<String, String> messageMap = Maps.newHashMap();

    //初始化状态码与文字说明
    static {
        messageMap.put("0", "");

        messageMap.put("400", "请求错误,服务器无法理解此请求");
        messageMap.put("401", "请求未授权");
        messageMap.put("405", "请求方法不允许访问");
        messageMap.put("406", "无法获取请求的数据");
        messageMap.put("500", "系统开小差了");

        messageMap.put("1000", "[服务器]运行时异常");
        messageMap.put("1001", "[服务器]空值异常");
        messageMap.put("1002", "[服务器]数据类型转换异常");
        messageMap.put("1003", "[服务器]IO异常");
        messageMap.put("1004", "[服务器]未知方法异常");
        messageMap.put("1005", "[服务器]数组越界异常");
        messageMap.put("1006", "[服务器]网络异常");
    }

    /**
     * 构建一个具有返回对象的实例
     *
     * @param code 错误代码
     * @date 2017年5月9日
     * @author ljq
     */
    public static <T> ApiResult<T> isError(Integer code) {
        ApiResult<T> result = new ApiResult<>();
        result.error(code + "", messageMap.get(String.valueOf(code)));
        return result;
    }


    public static <T> ApiResult<T> setOldError(Integer code) {
        ApiResult<T> result = new ApiResult<>();
        result.setCommon_return(messageMap.get(String.valueOf(code)));
        result.error(code + "", messageMap.get(String.valueOf(code)));
        return result;
    }

}
