package lzj.jsu.labsystem.dao;

import lzj.jsu.labsystem.pojo.Right;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RightMapper {
//    @Select("select * from rights")
    public List<Right> getRightList();

    void updateRightList(Right right);

    void deleteRightList(Integer id);
}
