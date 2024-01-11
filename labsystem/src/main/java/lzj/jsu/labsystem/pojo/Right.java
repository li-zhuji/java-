package lzj.jsu.labsystem.pojo;

import lombok.Data;

import java.util.List;

@Data  //避免代码冗余，直接用lombok注解
public class Right {
    private Integer id;
    private String title;
    private String icon;
    private String path;
    private List<Right> children;
}

//映射数据库信息
