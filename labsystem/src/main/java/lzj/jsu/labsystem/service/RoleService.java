package lzj.jsu.labsystem.service;

import lzj.jsu.labsystem.pojo.Role;

import java.util.List;

public interface RoleService {
    public List<Role> getRoleList();

    void updateRolelist(Role role);

    void deleteRoleList(Integer id);
}
