package com.imooc.service.impl;

import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.CenterUserInfoBo;
import com.imooc.service.CenterUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CenterUserServiceImpl implements CenterUserService {
    @Autowired
    UsersMapper usersMapper;

    @Override
    public Users userInfo(String userId) {
        Users users = usersMapper.selectByPrimaryKey(userId);
        users.setPassword(null);
        return users;
    }

    @Override
    public Users update(CenterUserInfoBo centerUserInfoBo) {
        Users usersUpate =new Users();
        BeanUtils.copyProperties(centerUserInfoBo,usersUpate);
        usersMapper.updateByPrimaryKeySelective(usersUpate);
        //重新获取用户信息
        return this.userInfo(centerUserInfoBo.getId());

    }
}
