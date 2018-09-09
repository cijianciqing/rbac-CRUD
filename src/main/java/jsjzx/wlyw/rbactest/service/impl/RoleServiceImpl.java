package jsjzx.wlyw.rbactest.service.impl;

import jsjzx.wlyw.rbactest.entities.Role;
import jsjzx.wlyw.rbactest.entities.RoleResources;
import jsjzx.wlyw.rbactest.mapper.RoleMapper;
import jsjzx.wlyw.rbactest.mapper.RoleResourcesMapper;
import jsjzx.wlyw.rbactest.service.IRoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author CiJian
 * @since 2018-09-07
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    RoleResourcesMapper roleResourcesMapper;

    
    @Transactional(readOnly = false)
    public void addRole(Role role, List<Integer> resourceIDS) {
        //插入t_role，生成role id
        roleMapper.insert(role);
        Integer roleId = role.getRoleId();

        RoleResources roleResources = new RoleResources();
        roleResources.setRoleId(roleId);

        //将对应权限插入到t_role_resources
        for(int i=0;i<resourceIDS.size();i++){
            roleResources.setResourcesId(resourceIDS.get(i));
            roleResourcesMapper.insert(roleResources);
        }
    }
}
