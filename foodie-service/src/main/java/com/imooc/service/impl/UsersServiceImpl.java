package com.imooc.service.impl;

import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBo;
import com.imooc.pojo.enums.Sex;
import com.imooc.service.UsersService;
import com.imooc.utils.DateUtil;
import com.imooc.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@Service
public class UsersServiceImpl  implements UsersService {

    private final String USER_FACE ="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1578457251504&di=6c21b87f62d39d5ef950fff22e6ed4f6&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01786557e4a6fa0000018c1bf080ca.png%401280w_1l_2o_100sh.png";

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private Sid sid;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean isUserNameExist(String username) {
        Example example =new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",username);
        Users user = usersMapper.selectOneByExample(example);
        return user==null?false:true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Users createUsers(UserBo userBo) {
        Users users =new Users();
        users.setId(sid.nextShort());
        users.setUsername(userBo.getUsername());
        users.setSex(Sex.secret.type);
        try {
            users.setPassword(MD5Utils.getMD5Str(userBo.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        users.setNickname(userBo.getUsername());
        users.setFace(USER_FACE);
        users.setBirthday(DateUtil.stringToDate("1990-01-01"));
        users.setCreatedTime(new Date());
        users.setUpdatedTime(new Date());
        usersMapper.insert(users);
        return users;
    }

    @Override
    public Users login(UserBo userBo) {
        Example example =new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",userBo.getUsername());
        criteria.andEqualTo("password",userBo.getPassword());
        Users user = usersMapper.selectOneByExample(example);
        return user;
    }
}
