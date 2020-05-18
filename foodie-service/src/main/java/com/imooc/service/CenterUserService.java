package com.imooc.service;


import com.imooc.pojo.Users;
import com.imooc.pojo.bo.CenterUserInfoBo;

public interface CenterUserService {

    Users userInfo(String userId);

    Users update(CenterUserInfoBo centerUserInfoBo);
}
