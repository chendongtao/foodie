package com.imooc.service.impl;

import com.imooc.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestTransactionImpl {

    @Autowired
    private StuService stuService;

    @Transactional
    public void testTransaction(){
        stuService.saveParent();
        stuService.saveChild();
    }

}
