package com.imooc.service;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBo;

public interface UsersService {
    boolean isUserNameExist(String username);

    Users createUsers(UserBo userBo);

    Users login(UserBo userBo);
}
