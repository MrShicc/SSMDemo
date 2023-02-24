package com.mrjiang.ssmdemo.core.dto;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;

/**
 * 模块名称:后端返回前端JSON实体
 * 模块类型:DTO
 * 编码人:施银江
 * 创建时间:2023/2/22
 * 联系时间:15912181467
 */

/**
 * 业务层状态码：
 * 200：成功
 * 201:区别上下页码
 * 500：后端系统异常或业务错误
 * -1：登录失效
 * 199：验证码错误
 * 100:添加失败
 *
 */
@Data
public class MyResult implements Serializable {

    private Integer code=200;//默认初始值是操作成功
    private String msg = "操作成功";
    private Object data;//结果对象

    //对象转json字符串工具方法
    public String toJsonString(){
        return  JSON.toJSONString(this);
    }

    //把字符串转对象的工具方法
    public MyResult toMyResultFromJsonString(String jsonStr){
        return (MyResult) JSON.parse(jsonStr);
    }
}
