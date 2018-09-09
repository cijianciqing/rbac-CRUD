package jsjzx.wlyw.rbactest;

import com.baomidou.mybatisplus.mapper.EntityWrapper;

import jsjzx.wlyw.rbactest.entities.Resources;
import jsjzx.wlyw.rbactest.entities.Role;
import jsjzx.wlyw.rbactest.entities.RoleResources;
import jsjzx.wlyw.rbactest.mapper.RoleMapper;
import jsjzx.wlyw.rbactest.service.impl.ResourcesServiceImpl;
import jsjzx.wlyw.rbactest.service.impl.RoleResourcesServiceImpl;
import jsjzx.wlyw.rbactest.service.impl.RoleServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RbactestApplicationTests {

    @Autowired
    RoleMapper roleMapper;

    @Test
    public void testAutoI(){
        Role role = new Role();
        role.setRoleName("skjfkajsdkfjas");
        roleMapper.insert(role);
        System.out.println(role.getRoleId());
    }

}
