package com.mrjiang.ssmdemo.mybatis.dao.user;

import com.mrjiang.ssmdemo.mybatis.domain.user.UserDO;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 模块名称:施先生
 * 模块类型:
 * 编码人:施银江
 * 创建时间:2023/2/23
 * 联系时间:15912181467
 */
@Mapper
public interface UserMapper {
    UserDO doLogin(@Param("user")UserDO user);

    List<UserDO> getList();
    List<UserDO> queryList(@Param("user")UserDO user);

    int doAdd(@Param("user")UserDO user);

    int doUp(@Param("user")UserDO user);
    int doState(@Param("user")UserDO user);
    int doDelete(String userId);
}
