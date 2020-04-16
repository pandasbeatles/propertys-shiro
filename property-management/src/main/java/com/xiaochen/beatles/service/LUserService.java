package com.xiaochen.beatles.service;

import com.xiaochen.beatles.pojo.User;

import java.util.List;
import java.util.Map;

public interface LUserService {
    List<User> selectuser(Map map);

    User ccSelectById(int id);

    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> selectUser();

    User getUser(String username);

    void regUser(String username, String password);
}
