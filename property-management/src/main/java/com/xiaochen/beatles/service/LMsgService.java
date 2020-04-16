package com.xiaochen.beatles.service;

import com.xiaochen.beatles.pojo.Msg;
import net.sf.json.JSONObject;

import java.util.Map;

public interface LMsgService {
    int deleteByPrimaryKey(Integer msgId);

    int insert(Msg record);

    int insertSelective(Msg record);

    Msg selectByPrimaryKey(Integer msgId);

    int updateByPrimaryKeySelective(Msg record);

    int updateByPrimaryKey(Msg record);

    JSONObject showmsg(Map map);
}