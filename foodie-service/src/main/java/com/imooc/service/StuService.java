package com.imooc.service;

import com.imooc.pojo.Stu;

public interface StuService {
    public Stu getStu(int id);

    public int saveStu(Stu stu);

    public int saveParent();
    public int saveChild1();
    public int saveChild2();
    public void saveChild();
}
