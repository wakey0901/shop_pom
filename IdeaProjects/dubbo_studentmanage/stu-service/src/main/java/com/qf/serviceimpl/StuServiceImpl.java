package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.StuMapper;
import com.qf.entity.Student;
import com.qf.service.IStuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
//学生服务的实现类
@Service
public class StuServiceImpl implements IStuService {

    @Autowired
    private StuMapper stuMapper;
    @Override
    public List<Student> queryAll() {
        return stuMapper.selectList(null);
    }
}
