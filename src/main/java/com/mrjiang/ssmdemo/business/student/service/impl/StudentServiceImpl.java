package com.mrjiang.ssmdemo.business.student.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mrjiang.ssmdemo.business.student.service.StudentService;
import com.mrjiang.ssmdemo.core.dto.MyPager;
import com.mrjiang.ssmdemo.mybatis.dao.student.StudentMapper;
import com.mrjiang.ssmdemo.mybatis.domain.student.StudentDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 模块名称:学生管理信息业务实现层
 * 模块类型:业务层-实现类
 * 编码人:施银江
 * 创建时间:2023/2/22
 * 联系时间:15912181467
 */
@Service
public class StudentServiceImpl implements StudentService {


    // mybatis使用自动注入的方法实现实例注入
    @Autowired
    private StudentMapper studentMapper;
    public StudentDO getStudentByID(String id) {

        StudentDO studentById = studentMapper.getStudentById(id);

        return studentById;
    }

//    public MyPager<StudentDO> getStudentPage(MyPager pager) {
//
//        //创建分页结果集对象
//        MyPager<StudentDO> resultPageData = new MyPager<StudentDO>();
//
//        //调用分页工具设置分页参数（页码，每页显示多少行数据）
//        Page<StudentDO> stuPage = PageHelper.startPage(pager.getPage(),pager.getSize());
//
//        //正常调用mapper接口方法！
//        //此时，pageHepler已经拦截到sql，且把sql分页执行的结果映射到stuPage对象中
//        //取出分页的结果集
//        List<StudentDO> result = stuPage.getResult();//当前页码下的分页数据
//        long total = stuPage.getTotal();//数据查询的所有行数值
//
//        resultPageData.setTotal(total);
//        resultPageData.setRows(result);
//        resultPageData.setPage(pager.getPage());
//        resultPageData.setSize(pager.getSize());
//
//
//        return resultPageData;
//    }
}
