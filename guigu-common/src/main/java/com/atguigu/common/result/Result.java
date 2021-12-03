package com.atguigu.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author ozq
 * @Date 2021/11/9 22:25
 * @Description
 */
@Data
public class Result {

    private Integer code;

    private String message;

    private Map<String,Object> data = new HashMap<>();

    private Result(){};

    /**
     * 成功
     * @return
     */
    public static Result ok(){
        Result result = new Result();
        result.setCode(ResponseEnum.SUCCESS.getCode());
        result.setMessage(ResponseEnum.SUCCESS.getMessage());
        return result;
    }

    /**
     * 失败
     * @return
     */
    public static Result error(){
        Result result = new Result();
        result.setCode(ResponseEnum.ERROR.getCode());
        result.setMessage(ResponseEnum.ERROR.getMessage());
        return result;
    }

    /**
     * 设置特定的值
     * @return
     */
    public static Result setResult(ResponseEnum responseEnum){
        Result result = new Result();
        result.setCode(responseEnum.getCode());
        result.setMessage(responseEnum.getMessage());
        return result;
    }

    /**
     * 设置返回数据
     * @param key
     * @param value
     * @return
     */
    public Result data(String key, Object value){
        this.data.put(key,value);
        return this;
    }

    /**
     * 设置返回消息
     * @param message
     * @return
     */
    public Result message(String message){
        this.setMessage(message);
        return this;
    }

    /**
     * 设置响应码
     * @param code
     * @return
     */
    public Result code(Integer code){
        this.setCode(code);
        return this;
    }

    /**
     * 设置响应结果
     * @param map
     * @return
     */
    public Result data(Map<String,Object> map){
        this.setData(map);
        return this;
    }


}
