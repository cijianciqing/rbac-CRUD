package jsjzx.wlyw.rbactest.controller;


import jsjzx.wlyw.rbactest.entities.Resources;
import jsjzx.wlyw.rbactest.service.impl.ResourcesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author CiJian
 * @since 2018-09-07
 */
@Controller
@RequestMapping("/permission/resources")
public class ResourcesController {

    @Autowired
    ResourcesServiceImpl resourcesService;

    //返回所有的resources
    @ResponseBody
    @RequestMapping(value = "/loadData")
    public List<Resources> getAllResources(){
        List<Resources> resources = resourcesService.selectList(null);
        return resources;
    }

}

