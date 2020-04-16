package com.xiaochen.beatles.service.Impl;

import com.xiaochen.beatles.mapper.MsgMapper;
import com.xiaochen.beatles.pojo.Msg;
import com.xiaochen.beatles.service.MsgService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("MsgService")
public class MsgServiceImp implements MsgService {

    @Resource
    private MsgMapper msgMapper = null;
    @Override
    public List<Msg> select(Map map) {
        return msgMapper.select(map);
    }

    @Override
    public int insertSelective(Msg record) {
        return msgMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(Msg record) {
        return msgMapper.updateByPrimaryKeySelective(record);
    }
}
