package jsjzx.wlyw.rbactest.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import jsjzx.wlyw.rbactest.entities.User;
import jsjzx.wlyw.rbactest.entities.UserRole;
import jsjzx.wlyw.rbactest.mapper.UserMapper;
import jsjzx.wlyw.rbactest.service.IUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author CiJian
 * @since 2018-09-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    UserRoleServiceImpl userRoleService;

    @Transactional(readOnly = false)
    public void addUserRole(Integer userId, Integer[] unassignroleids) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        for(int i=0;i<unassignroleids.length;i++){
            userRole.setRoleId(unassignroleids[i]);
            userRoleService.insert(userRole);
        }
    }

    @Transactional(readOnly = false)
    public void deleteUserRole(Integer userId, Integer[] assignroleids) {
        EntityWrapper<UserRole> urWrapper = new EntityWrapper<>();
        urWrapper.eq("userId",userId);
        for(int i=0;i<assignroleids.length;i++){
            urWrapper.eq("roleId",assignroleids[i]);
            userRoleService.delete(urWrapper);
        }
    }
}
