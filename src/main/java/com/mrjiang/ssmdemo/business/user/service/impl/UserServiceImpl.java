package com.mrjiang.ssmdemo.business.user.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mrjiang.ssmdemo.business.user.service.UserService;
import com.mrjiang.ssmdemo.core.dto.MyPager;
import com.mrjiang.ssmdemo.core.dto.MyResult;
import com.mrjiang.ssmdemo.mybatis.dao.user.UserMapper;
import com.mrjiang.ssmdemo.mybatis.domain.user.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 模块名称:用户信息管理实现层
 * 模块类型:
 * 编码人:施银江
 * 创建时间:2023/2/23
 * 联系时间:15912181467
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 登录判断
     * @param user
     * @return
     */
    public UserDO doLogin(UserDO user) {

        UserDO userDO = userMapper.doLogin(user);
        return userDO;
    }

    /**
     * 初始化主页表格
     * @return
     */
    public MyPager<UserDO> getList(MyPager page) {
        //创建分页结果集对象
        MyPager<UserDO> resultPageData = new MyPager<UserDO>();

        //调用分页工具设置分页参数（页码，每页显示多少行数据）
        Page<UserDO> userPage = PageHelper.startPage(page.getPage(),page.getSize());

        //正常调用mapper接口方法！
        userMapper.getList();
        //此时，pageHepler已经拦截到sql，且把sql分页执行的结果映射到stuPage对象中
        //取出分页的结果集
        List<UserDO> result = userPage.getResult();//当前页码下的分页数据
        long total = userPage.getTotal();//数据查询的所有行数值


        resultPageData.setTotal(total);
        resultPageData.setRows(result);
        resultPageData.setPage(page.getPage());
        resultPageData.setSize(page.getSize());


        return resultPageData;
    }

    /**
     * 条件查询主页表格
     * @param page
     * @param user
     * @return
     */
    public MyPager<UserDO> queryList(MyPager page, UserDO user) {
        //创建分页结果集对象
        MyPager<UserDO> resultPageData = new MyPager<UserDO>();

        //调用分页工具设置分页参数（页码，每页显示多少行数据）
        Page<UserDO> userPage = PageHelper.startPage(page.getPage(),page.getSize());

        //正常调用mapper接口方法！
        userMapper.queryList(user);
        //此时，pageHepler已经拦截到sql，且把sql分页执行的结果映射到stuPage对象中
        //取出分页的结果集
        List<UserDO> result = userPage.getResult();//当前页码下的分页数据
        long total = userPage.getTotal();//数据查询的所有行数值

        resultPageData.setTotal(total);
        resultPageData.setRows(result);
        resultPageData.setPage(page.getPage());
        resultPageData.setSize(page.getSize());


        return resultPageData;
    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    public int doAdd(UserDO user) {
        int i = userMapper.doAdd(user);
        return i;
    }

    /**
     * 更新用户
     * @param user
     * @return
     */
    public int doUpdate(UserDO user) {
        int i = userMapper.doUp(user);
        return i;
    }

    /**
     * 更新用户登录状态
     * @param user
     * @return
     */
    public int doState(UserDO user) {
        int i = userMapper.doState(user);
        return i;
    }

    /**
     * 删除用户操作
     * @param userId
     * @return
     */
    public int doDelete(String userId) {
        int i = userMapper.doDelete(userId);
        return i;
    }
}
