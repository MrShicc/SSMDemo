package com.mrjiang.ssmdemo.mybatis.domain.student;

import lombok.Data;

import java.io.Serializable;

/**
 * 模块名称:施先生
 * 模块类型:
 * 编码人:施银江
 * 创建时间:2023/2/22
 * 联系时间:15912181467
 */
@Data
public class StudentDO implements Serializable {
    private String id;
    private String name;
    private String birth;
    private String sex;
}
