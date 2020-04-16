package com.xiaochen.beatles.service;

import com.xiaochen.beatles.pojo.MsgCount;


public interface MsgCountService {

    int insertSelective(MsgCount record);

    MsgCount selectByPrimaryKey(MsgCount record);

    int updateByPrimaryKeySelective(MsgCount record);
}
