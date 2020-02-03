package com.imooc.service;

import com.imooc.pojo.UserAddress;
import com.imooc.pojo.bo.AddressBO;

import java.util.List;

public interface AddressService {

    List<UserAddress> queryUserAddress(String userId);

    void addUserAddress(AddressBO addressBO);

    void updateUserAddress(AddressBO addressBO);

    void deleteAddress(String userId, String addressId);

    void setDefalut(String userId, String addressId);

    UserAddress selectUserAddressByPrimaryKey(String id);
}
