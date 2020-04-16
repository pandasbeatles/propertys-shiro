package com.xiaochen.beatles.service.Impl;

import com.xiaochen.beatles.mapper.AdminMapper;
import com.xiaochen.beatles.mapper.AppealMapper;
import com.xiaochen.beatles.mapper.ReplyMapper;
import com.xiaochen.beatles.pojo.Admin;
import com.xiaochen.beatles.pojo.Appeal;
import com.xiaochen.beatles.pojo.Reply;
import com.xiaochen.beatles.service.CCAppealService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("appealService")
public class CCAppealServiceImpl implements CCAppealService {

    @Resource
    AppealMapper mapper;
    @Resource
    AdminMapper adminMapper;

    @Resource
    ReplyMapper replyMapper;

    @Override
    public Appeal getAppeal(int id){
        return  mapper.ccSelectById(id);
    }

    @Override
    public List<Appeal> ccGetAppealAll(Map map) {
        return mapper.ccSelectAll(map);
    }

    @Override
    public List<Admin> ccGetAdminAll() {
        return adminMapper.ccSelectAllKF();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int ccUpdateAdmin(Map map) {
        return mapper.ccUpdateAdmin(map);
    }

    public int ccInsert(Reply reply){
        return replyMapper.ccInsert(reply);
    }

    @Override
    public int ccUpdateState(Appeal appeal) {
        return mapper.ccUpdateState(appeal);
    }
}
