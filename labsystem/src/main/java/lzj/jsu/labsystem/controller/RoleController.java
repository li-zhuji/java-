package lzj.jsu.labsystem.controller;

import lzj.jsu.labsystem.pojo.Role;
import lzj.jsu.labsystem.service.RoleService;
import lzj.jsu.labsystem.utils.ResultOBJ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adminapi/roles")
//两个不能少
public class RoleController {
    @Autowired
    private RoleService roleService;
    @GetMapping
    public List<Role> getRoleList(){
        return roleService.getRoleList();
    }

    @PutMapping(value="/{id}")
    public ResultOBJ updateRoleList(@PathVariable Integer id, @RequestBody Role role){
        try {
            role.setId(id);
            roleService.updateRolelist(role);
            return ResultOBJ.UPDATE_SUCCESS;
        } catch (Exception e) {
            return ResultOBJ.UPDATE_ERROR;
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResultOBJ deleteRoleList(@PathVariable Integer id){
        try {
            roleService.deleteRoleList(id);
            return ResultOBJ.DELETE_SUCCESS;
        } catch (Exception e) {
            return ResultOBJ.DELETE_ERROR;
        }
    }
}
