package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Class1;
import com.qf.service.IClassService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/class")
public class ClassController {

    @Reference
    private IClassService classService;

    @RequestMapping("/list")
    public String list(ModelMap map){
        List<Class1> class1List = classService.queryAll();
        map.put("class1List",class1List);
        return "classlist";
    }
}
