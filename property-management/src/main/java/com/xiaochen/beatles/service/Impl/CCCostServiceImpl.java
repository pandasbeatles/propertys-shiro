package com.xiaochen.beatles.service.Impl;

import com.xiaochen.beatles.mapper.CostMapper;
import com.xiaochen.beatles.mapper.PayMapper;
import com.xiaochen.beatles.mapper.UserMapper;
import com.xiaochen.beatles.pojo.Cost;
import com.xiaochen.beatles.pojo.Pay;
import com.xiaochen.beatles.pojo.User;
import com.xiaochen.beatles.service.CCCostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class CCCostServiceImpl implements CCCostService {
    @Resource
    CostMapper mapper;

    @Resource
    UserMapper userMapper;

    @Resource
    PayMapper payMapper;

    @Override
    public List<Cost> ccGetAll(Map map) {
        return mapper.ccSelectAll(map);
    }

    @Override
    public User ccGetUser(User user) {
        return userMapper.ccSelectByUser(user);
    }

    @Override
    public int ccAdd(Cost cost) {
        return mapper.ccInsert(cost);
    }

    @Override
    public Cost ccGetCostByUserDate(Map map) {
        return mapper.ccSelectByUserDate(map);
    }

    @Override
    public int ccUpdate(Cost cost) {
        return mapper.ccUpdate(cost);
    }

    @Override
    public List<Cost> ccGetPay(Map map) {
        return mapper.ccSelectPay(map);
    }

    @Override
    public Cost ccGetCost(int id) {
        return mapper.ccSelectById(id);
    }

    @Override
    public int ccUpdateState(int id) {
        return mapper.ccUpdateState(id);
    }

    @Override
    public int ccAddPay(Pay pay) {
        return payMapper.ccInsertPay(pay);
    }

    @Override
    public List<Pay> ccGetPayAll(Map map) {
        return payMapper.ccSelectAll(map);
    }
}
