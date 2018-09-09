package jsjzx.wlyw.rbactest.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import jsjzx.wlyw.rbactest.entities.RoleResources;
import jsjzx.wlyw.rbactest.mapper.RoleResourcesMapper;
import jsjzx.wlyw.rbactest.service.IRoleResourcesService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author CiJian
 * @since 2018-09-07
 */
@Service
public class RoleResourcesServiceImpl extends ServiceImpl<RoleResourcesMapper, RoleResources> implements IRoleResourcesService {

    @Autowired
    RoleResourcesMapper roleResourcesMapper;

    @Transactional(readOnly = false)
    public void updateRoleResources(Map<String, Object> paramMap) {

        Integer roleid = (Integer) paramMap.get("roleid");
        Integer[] permissionids = (Integer[]) paramMap.get("permissionids");
        //List<Integer> integers = Arrays.asList(permissionids);

        //删除t_role_resources中roleId对应的记录
        EntityWrapper<RoleResources> wrapper01 = new EntityWrapper<>();
        wrapper01.eq("roleId",roleid);
        roleResourcesMapper.delete(wrapper01);

        RoleResources roleResources = new RoleResources();
        roleResources.setRoleId(roleid);
        //挨个添加记录
        for(int i=0;i<permissionids.length;i++){
            roleResources.setResourcesId(permissionids[i]);
            roleResourcesMapper.insert(roleResources);
        }
    }
}
