package lzj.jsu.labsystem.service;

import lzj.jsu.labsystem.pojo.Lab;

import java.util.List;

public interface LabService {
    public List<Lab> getLabList(Lab lab);
    public void addLabList(Lab lab);

    void updateLabList(Lab lab);

    void deleteLabList(Integer id);
}
