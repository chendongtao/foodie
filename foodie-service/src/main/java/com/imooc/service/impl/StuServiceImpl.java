package com.imooc.service.impl;

import com.imooc.mapper.StuMapper;
import com.imooc.pojo.Stu;
import com.imooc.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StuServiceImpl implements StuService {

    @Autowired
    private StuMapper stuMapper;

    @Override
    public Stu getStu(int id) {
        return stuMapper.selectByPrimaryKey(id);
    }

    @Override
    public int saveStu(Stu stu) {
        return stuMapper.insert(stu);
    }

    @Override
    public int saveParent() {
        Stu stu =new Stu();
        stu.setName("Parent");
        stu.setAge(40);
        return stuMapper.insert(stu);
    }

    @Override
    public int saveChild1() {
        Stu stu =new Stu();
        stu.setName("child-1");
        stu.setAge(10);
        return stuMapper.insert(stu);
    }

    @Override
    public int saveChild2() {
        Stu stu =new Stu();
        stu.setName("child-2");
        stu.setAge(15);
        return stuMapper.insert(stu);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void saveChild(){
        this.saveChild1();
        int i=1/0;
        this.saveChild2();
    }
}
