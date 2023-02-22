package com.mrjiang.ssmdemo.mybatis.dao.student;

import com.mrjiang.ssmdemo.core.dto.MyPager;
import com.mrjiang.ssmdemo.mybatis.domain.student.StudentDO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 模块名称:施先生
 * 模块类型:
 * 编码人:施银江
 * 创建时间:2023/2/22
 * 联系时间:15912181467
 */
@Mapper
public interface StudentMapper {
    StudentDO getStudentById(String id);

    List<StudentDO> getStudentPage();
}
