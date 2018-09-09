package jsjzx.wlyw.rbactest.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import jsjzx.wlyw.rbactest.entities.*;
import jsjzx.wlyw.rbactest.service.impl.RoleServiceImpl;
import jsjzx.wlyw.rbactest.service.impl.UserRoleServiceImpl;
import jsjzx.wlyw.rbactest.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.jws.soap.SOAPBinding;
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
@RequestMapping("/permission/user")
public class UserController {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    UserRoleServiceImpl userRoleService;
    @Autowired
    RoleServiceImpl roleService;

    //返回所有的角色
    @ResponseBody
    @GetMapping(value = "/getAllUsers")
    public Map<String, Object> getAllUsers(BasicSearchInfo basicSearchInfo){
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
        Page<User> page = new Page<>(pageNumber, pageSize);
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.like("userName", searchInfo);
        wrapper.or();
        wrapper.like("userPassword", searchInfo);

        //设置排序
        if (sortOrder.equalsIgnoreCase("asc")) {
            wrapper.orderAsc(orderColumn);
        } else if (sortOrder.equalsIgnoreCase("desc")) {
            wrapper.orderDesc(orderColumn);
        }

        //获取要查询的数据
        Page<User> page1 = userService.selectPage(page, wrapper);
        //获取查询结果的总量
        int totalUser = userService.selectCount(wrapper);
        //传递到前端的参数
        Map<String, Object> map = new HashMap<>();
        map.put("total", totalUser);
        map.put("rows", page1.getRecords());
        return map;
    }

    //更新用户之前，查询用户的信息
    @ResponseBody
    @RequestMapping("/getOne")
    public Msg getOne(@RequestParam(name = "userId") Integer userId){
        //System.out.println("jsjzx.wlyw.rbactest.controller.UserController.getOne.....");
        User user = userService.selectById(userId);
        return Msg.success().add("user",user);
    }

    //更新用户信息
    @ResponseBody
    @PutMapping("/update")
    public Msg updateUser(User user){
        userService.updateById(user);
        return Msg.success();
    }

    //获取用户的Role信息
    @ResponseBody
    @PostMapping("/getRoles")
    public Msg getRoles(@RequestParam(name = "userId") Integer userId){
        User user = userService.selectById(userId);

        //根据userId获取Roles
        EntityWrapper<UserRole> urWrapper = new EntityWrapper<>();
        urWrapper.eq("userId",userId);
        List<UserRole> userRoles = userRoleService.selectList(urWrapper);
        List<Integer> roleIDS = new ArrayList<>();
        for(int i=0;i<userRoles.size();i++){
            roleIDS.add(userRoles.get(i).getRoleId());
        }

        List<Role> roles = roleService.selectBatchIds(roleIDS);
        List<Role> rolesAll = roleService.selectList(null);

        List<Role> assingedRoles = new ArrayList<>();
        List<Role> unassignRoles = new ArrayList<>();

        //获取已分配role和未分配roles
        for(int i=0; i<rolesAll.size();i++){
            if(roleIDS.contains(rolesAll.get(i).getRoleId())){
               // System.out.println(resourcesAll.get(i));
                assingedRoles.add(rolesAll.get(i));
            }else {
                unassignRoles.add(rolesAll.get(i));
            }
        }
    return Msg.success().add("assingedRoles",assingedRoles).add("unassignRoles",unassignRoles);
    }


    //添加用户role信息
    @ResponseBody
    @PostMapping(value = "doAssign")
    public Msg addRoleAssign(@RequestParam(name = "userId")Integer userId,
                             @RequestParam(name= "unassignroleids") Integer[] unassignroleids){
        userService.addUserRole(userId,unassignroleids);
        return Msg.success();
    }

    //删除用户role信息
    @ResponseBody
    @PostMapping(value = "undoAssign")
    public Msg deleteRoleAssign(@RequestParam(name = "userId")Integer userId,
                                @RequestParam(name= "assignroleids") Integer[] assignroleids){
        userService.deleteUserRole(userId,assignroleids);
        return Msg.success();
    }

}

