package com.xiaochen.beatles.service.Impl;

import com.xiaochen.beatles.mapper.AdminMapper;
import com.xiaochen.beatles.mapper.RepairMapper;
import com.xiaochen.beatles.mapper.ReplyMapper;
import com.xiaochen.beatles.pojo.Admin;
import com.xiaochen.beatles.pojo.Repair;
import com.xiaochen.beatles.pojo.Reply;
import com.xiaochen.beatles.service.CCRepairService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("repairService")
public class CCRepairServiceImpl implements CCRepairService {
    @Resource
    RepairMapper mapper;
    @Resource
    AdminMapper adminMapper;

    @Resource
    ReplyMapper replyMapper;
    @Override
    public Repair getRepair(int id){
      return  mapper.ccSelectById(id);
    }

    @Override
    public List<Repair> ccGetRepairAll(Map map) {
        return mapper.ccSelectAll(map);
    }

    @Override
    public List<Admin> ccGetAdminAll() {
        return adminMapper.ccSelectAllWX();
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
    public int ccUpdateState(Repair repair) {
        return mapper.ccUpdateState(repair);
    }

}
