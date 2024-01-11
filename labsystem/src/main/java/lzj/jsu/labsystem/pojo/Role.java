package lzj.jsu.labsystem.pojo;

import lombok.Data;

@Data
public class Role {
    private Integer id;
    private String roleName;
    private Integer roleType;
    private String rights;
}
