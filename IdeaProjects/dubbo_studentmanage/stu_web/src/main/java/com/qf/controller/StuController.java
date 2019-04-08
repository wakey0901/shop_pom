package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Student;
import com.qf.service.IStuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/stu")
public class StuController {

    @Reference
    private IStuService stuService;

    @RequestMapping("/list")
    public String list(ModelMap map){
        //远程调用服务的提供者
        List<Student> studentList = stuService.queryAll();
        map.put("studentList",studentList);
        return "stulist";
    }

}
