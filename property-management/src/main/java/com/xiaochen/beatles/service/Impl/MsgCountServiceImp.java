package com.xiaochen.beatles.service.Impl;

import com.xiaochen.beatles.mapper.MsgCountMapper;
import com.xiaochen.beatles.pojo.MsgCount;
import com.xiaochen.beatles.service.MsgCountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("MsgCountService")
public class MsgCountServiceImp implements MsgCountService {


    @Resource
    MsgCountMapper msgCountMapper;

    @Override
    public int insertSelective(MsgCount record) {
        return msgCountMapper.insertSelective(record);
    }

    @Override
    public MsgCount selectByPrimaryKey(MsgCount record) {
        return msgCountMapper.selectByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(MsgCount record) {
        return msgCountMapper.updateByPrimaryKeySelective(record);
    }
}
