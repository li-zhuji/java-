package lzj.jsu.labsystem.dao;

import lzj.jsu.labsystem.pojo.Lab;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LabMapper {

    public List<Lab> getLabList(Lab lab);
    public void addLabList(Lab lab);

    void updateLabList(Lab lab);

    void deleteLabList(Integer id);
}
