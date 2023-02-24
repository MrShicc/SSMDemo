package com.mrjiang.ssmdemo.business.user.service;

import com.mrjiang.ssmdemo.core.dto.MyPager;
import com.mrjiang.ssmdemo.core.dto.MyResult;
import com.mrjiang.ssmdemo.mybatis.domain.user.UserDO;

import java.util.List;

/**
 * 模块名称:业务-接口层
 * 模块类型:
 * 编码人:施银江
 * 创建时间:2023/2/23
 * 联系时间:15912181467
 */
public interface UserService {
        UserDO doLogin (UserDO user);

        MyPager<UserDO> getList(MyPager page);

        MyPager<UserDO> queryList(MyPager page,UserDO user);

        int doAdd (UserDO user);

        int doUpdate(UserDO user);

        int doState(UserDO user);
        int doDelete(String userId);

}
