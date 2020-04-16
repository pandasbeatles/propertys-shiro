package com.xiaochen.beatles.service.Impl;

import com.xiaochen.beatles.mapper.AdminMapper;
import com.xiaochen.beatles.pojo.Admin;
import com.xiaochen.beatles.service.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("adminService")
public class AdminServiceImp implements AdminService {
    @Resource
    private AdminMapper adminMapper = null;



    @Override
    public Admin loginAdmin(Admin admin) {
        return this.adminMapper.loginAdmin(admin);
    }

    @Override
    public int insert(Admin record) {
        return adminMapper.insert(record);
    }




    @Override
    public List<Admin> selectByStatus(Map map) {
        return adminMapper.selectByStatus(map);
    }

    @Override
    public int updateByPrimaryKeySelective(Admin record) {
        return adminMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public Admin getAdmin(String username) {
        return this.adminMapper.getAdmin(username);
    }

}
