package lzj.jsu.labsystem.service;

import lzj.jsu.labsystem.dao.RightMapper;
import lzj.jsu.labsystem.pojo.Right;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

//声明该类为服务层类
public class RightServiceImpl implements RightService{
    @Autowired
    //自动装载RightMapper的依赖进对象
    private RightMapper rightMapper;

    //下面的方法在对应的xml文件中都有映射
    @Override
    public List<Right> getRightList() {
        return rightMapper.getRightList();
    }

    @Override
    public void updateRightlist(Right right) {
        rightMapper.updateRightList(right);
    }

    @Override
    public void deleteRightList(Integer id) {
        rightMapper.deleteRightList(id);
    }
}
