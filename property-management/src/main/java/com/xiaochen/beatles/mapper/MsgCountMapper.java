package com.xiaochen.beatles.mapper;

import com.xiaochen.beatles.pojo.MsgCount;

public interface MsgCountMapper {

    int insertSelective(MsgCount record);

    MsgCount selectByPrimaryKey(MsgCount record);

    int updateByPrimaryKeySelective(MsgCount record);



}