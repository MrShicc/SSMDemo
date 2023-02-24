package com.mrjiang.ssmdemo.core.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 模块名称:分页实体类
 * 模块类型:DTO
 * 编码人:施银江
 * 创建时间:2023/2/22
 * 联系时间:15912181467
 */
@Data
public class MyPager<T> implements Serializable {
    private Integer page = 1;//当前的页码
    private Integer size = 4;//煤业显示的行数
    private List<T> rows;//当页码的数据
    private Long total;//数据总行数

}
