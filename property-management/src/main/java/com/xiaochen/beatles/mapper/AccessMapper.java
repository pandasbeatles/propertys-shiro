package com.xiaochen.beatles.mapper;

import com.xiaochen.beatles.pojo.Access;

import java.util.List;
import java.util.Map;

public interface AccessMapper {

    int insertAccess(Access access);

    int updateAccess(Access access);

    List<Access> selectAccessList(Map map);

    List<Access> selectAccess(Access access);


}