package lzj.jsu.labsystem.pojo;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private Integer roleId;

    private Integer is_default;
    private Role role;         //可以查看前端传过来的json数据

    private String  token;
}
