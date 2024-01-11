package lzj.jsu.labsystem.service;

import lzj.jsu.labsystem.dao.RoleMapper;
import lzj.jsu.labsystem.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public List<Role> getRoleList() {
        return roleMapper.getRoleList();
    }

    @Override
    public void updateRolelist(Role role) {
        roleMapper.updateRolelist(role);
    }

    @Override
    public void deleteRoleList(Integer id) {
        roleMapper.deleteRoleList(id);
    }
}
