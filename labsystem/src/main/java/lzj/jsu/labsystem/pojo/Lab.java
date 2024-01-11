package lzj.jsu.labsystem.pojo;

import lombok.Data;

@Data
public class Lab {
    private Integer id;
    private String title;
    private Integer capacity;

    private Integer lab_type;
    private Integer college_type;
    private Integer x;
    private Integer y;
}
