package com.mrjiang.ssmdemo.business.student.controller;

import com.mrjiang.ssmdemo.business.student.service.StudentService;
import com.mrjiang.ssmdemo.business.student.service.impl.StudentServiceImpl;
import com.mrjiang.ssmdemo.core.dto.MyPager;
import com.mrjiang.ssmdemo.core.dto.MyResult;
import com.mrjiang.ssmdemo.mybatis.domain.student.StudentDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 模块名称:学生信息管理-控制器
 * 模块类型: C-控制器
 * 编码人:施银江
 * 创建时间:2023/2/22
 * 联系时间:15912181467
 */
@Controller
@RequestMapping("/student")
@Slf4j
public class StudentController {
    //注入业务层实例
    @Resource(name ="studentServiceImpl")
    private StudentServiceImpl StudentService;

    @RequestMapping(value = "/getStudentById",method = RequestMethod.GET)
    @ResponseBody
    public MyResult getStudentById(String id){

        log.debug("学生信息管理:获取学生信息通过学生id[param:id"+id+"]");

        MyResult result = new MyResult();

        //调用业务层
        StudentDO studentDO= StudentService.getStudentByID(id);

        if (studentDO==null){
            result.setCode(500);
            result.setMsg("学生信息查询失败!");
        }else {
            result.setMsg("查询成功！");
            result.setData(studentDO);
        }

        return result;
    }

    @RequestMapping(value = "/getStudentById",method = RequestMethod.GET)
    @ResponseBody
    public MyResult getStudentPage(MyPager pager){//接收分页参数

        log.debug("学生信息管理:查询学生信息分页");

        MyResult result = new MyResult();

        //调用业务层
        MyPager<StudentDO> studentPage = StudentService.getStudentPage(pager);

        result.setData(studentPage);
        result.setMsg("查询成功！");

        return result;
    }

}
