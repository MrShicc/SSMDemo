package com.mrjiang.ssmdemo.business.student.service;

import com.mrjiang.ssmdemo.core.dto.MyPager;
import com.mrjiang.ssmdemo.mybatis.domain.student.StudentDO;

/**
 * 模块名称:业务-接口层
 * 模块类型:
 * 编码人:施银江
 * 创建时间:2023/2/22
 * 联系时间:15912181467
 */
public interface StudentService {

    StudentDO getStudentByID(String id);

//    MyPager<StudentDO> getStudentPage(MyPager pager);
}
