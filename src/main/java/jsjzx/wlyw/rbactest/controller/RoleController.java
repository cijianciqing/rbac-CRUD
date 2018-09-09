package jsjzx.wlyw.rbactest.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import jsjzx.wlyw.rbactest.entities.*;
import jsjzx.wlyw.rbactest.service.impl.ResourcesServiceImpl;
import jsjzx.wlyw.rbactest.service.impl.RoleResourcesServiceImpl;
import jsjzx.wlyw.rbactest.service.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author CiJian
 * @since 2018-09-07
 */
@Controller
@RequestMapping("/permission/role")
public class RoleController {
    @Autowired
    RoleServiceImpl roleService;
    @Autowired
    RoleResourcesServiceImpl roleResourcesService;
    @Autowired
    ResourcesServiceImpl resourcesService;

    //返回所有的角色
    @ResponseBody
    @GetMapping(value = "/getAllRoles")
    public Map<String, Object> getAllRoles(BasicSearchInfo basicSearchInfo){
        //输出当前的查询信息
        System.out.println("当前的查询信息： " + basicSearchInfo);
        //获取前台传递的参数
        Integer pageSize = basicSearchInfo.getPageSize();
        Integer pageNumber = basicSearchInfo.getPageNumber();
        String searchText = basicSearchInfo.getSearchText();
        String sortName = basicSearchInfo.getSortName();
        String sortOrder = basicSearchInfo.getSortOrder();
        String searchInfo = basicSearchInfo.getSearchInfo();

        //排序使用的列
        List<String> orderColumn = new ArrayList<>();
        orderColumn.add(sortName);
        //后台分页查询
        Page<Role> page = new Page<>(pageNumber, pageSize);
        EntityWrapper<Role> wrapper = new EntityWrapper<>();
        wrapper.like("roleName", searchInfo);
        wrapper.or();
        wrapper.like("description", searchInfo);

        //设置排序
        if (sortOrder.equalsIgnoreCase("asc")) {
            wrapper.orderAsc(orderColumn);
        } else if (sortOrder.equalsIgnoreCase("desc")) {
            wrapper.orderDesc(orderColumn);
        }

        //获取要查询的数据
        Page<Role> page1 = roleService.selectPage(page, wrapper);
        //获取查询结果的总量
        int totalUser = roleService.selectCount(wrapper);
        //传递到前端的参数
        Map<String, Object> map = new HashMap<>();
        map.put("total", totalUser);
        map.put("rows", page1.getRecords());
        return map;
    }

    //返回role的resources，用于role的resource更新
    @ResponseBody
    @RequestMapping(value = "/loadCustomPermissionData")
    public List<Resources> loadCustomPermissionData(@RequestParam(name = "roleId") Integer roleId){
        System.out.println("__________________________"+roleId);
        //根据role-id获得所有的resource-id
        EntityWrapper<RoleResources> rrWrapper = new EntityWrapper<>();
        rrWrapper.eq("roleId",roleId);
        List<RoleResources> roleResources = roleResourcesService.selectList(rrWrapper);

        List<Integer> resourceIds = new ArrayList<>();
        for(int i=0; i<roleResources.size();i++){
            resourceIds.add(roleResources.get(i).getResourcesId());
        }

        //根据所有的resource-id获得 此角色的resource list
        List<Resources> resources = resourcesService.selectBatchIds(resourceIds);
        //获得所有的resources
        List<Resources>  resourcesAll= resourcesService.selectList(null);

        //设置已选择权限
        for(int i=0; i<resourcesAll.size();i++){
            if(resourceIds.contains(resourcesAll.get(i).getId())){
                System.out.println(resourcesAll.get(i));
                resourcesAll.get(i).setChecked(true);
            }
        }

        return resourcesAll;
    }

    //正式更新role的resources
    @ResponseBody
    @RequestMapping("/updatePermission")
    public Msg updatePermission( Integer roleid, Integer[] permissionids ) {
        try {

            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("roleid", roleid);
            paramMap.put("permissionids", permissionids);
            roleResourcesService.updateRoleResources(paramMap);
           return Msg.success();
        } catch ( Exception e ) {
            e.printStackTrace();
            return Msg.fail();
        }
    }

    //添加角色及其权限
    @ResponseBody
    @RequestMapping("/addRoleWithResources")
    public Msg addRoleWithResources(@RequestParam(name="rolename") String roleName,
                                    @RequestParam(name="roleDescription",required = false) String roleDescription,
                                    @RequestParam(name="rIds") String resourceIds
                                    ) {
        //生成role对象
        Role role = new Role();
        if(roleName != null && roleName !=""){
            String rname = roleName;
            role.setRoleName(roleName);
        }
        if(roleDescription != null && roleDescription !=""){
            String rdes = roleDescription;
            role.setDescription(roleDescription);
        }

        //生成role对应的resource的ids
        List<Integer> resourceIDS = new ArrayList<>();
        if(resourceIds.contains("-")){
            String[] str_ids = resourceIds.trim().split("-");
            //组装id的集合
            for (String string : str_ids) {
                resourceIDS.add(Integer.parseInt(string));
            }
        }else{
            resourceIDS.add(Integer.parseInt(resourceIds));
        }

        roleService.addRole(role,resourceIDS);
        return Msg.success();
    }
}

