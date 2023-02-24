package com.mrjiang.ssmdemo.mybatis.domain.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 模块名称:施先生
 * 模块类型:
 * 编码人:施银江
 * 创建时间:2023/2/23
 * 联系时间:15912181467
 */
@Data
public class UserDO implements Serializable {
    private String userId ;
    private String userName;
    private String userPwd;
    private Integer numb;
    private Integer state =0;//默认登录状态
    private String reqCodeText;
}
