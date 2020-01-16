package com.imooc.service.impl;

import com.imooc.mapper.UserAddressMapper;
import com.imooc.pojo.UserAddress;
import com.imooc.pojo.bo.AddressBO;
import com.imooc.pojo.enums.YesOrNo;
import com.imooc.service.AddressService;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    UserAddressMapper userAddressMapper;

    @Autowired
    Sid sid;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<UserAddress> queryUserAddress(String userId) {
        UserAddress userAddress =new UserAddress();
        userAddress.setUserId(userId);
        List<UserAddress> userAddressList = userAddressMapper.select(userAddress);
        return userAddressList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addUserAddress(AddressBO addressBO) {
        Integer isDefault = YesOrNo.NO.value;
        //查询该userId下有无默认地址
        Example example =new Example(UserAddress.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",addressBO.getUserId());
        criteria.andEqualTo("isDefault",YesOrNo.YES.value);
        List<UserAddress> userAddressList = userAddressMapper.selectByExample(example);
        if (userAddressList.size()==0){
            isDefault =YesOrNo.YES.value;
        }
        //新增收货地址
        UserAddress userAddress =new UserAddress();
        BeanUtils.copyProperties(addressBO,userAddress);
        userAddress.setId(sid.nextShort());
        userAddress.setIsDefault(isDefault);
        userAddress.setCreatedTime(new Date());
        userAddress.setUpdatedTime(new Date());
        userAddressMapper.insertSelective(userAddress);
    }
}
