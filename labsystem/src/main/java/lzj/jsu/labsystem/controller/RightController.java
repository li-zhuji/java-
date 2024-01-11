package lzj.jsu.labsystem.controller;

import lzj.jsu.labsystem.pojo.Right;
import lzj.jsu.labsystem.service.RightService;
import lzj.jsu.labsystem.utils.ResultOBJ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adminapi/rights")
public class RightController {
    @Autowired
    private RightService rightService;
    @GetMapping
    //如果前端返回的是一个get请求的话就调用getMapping
    public List<Right> getRightList(){
        return rightService.getRightList();
    }
    // /adminapi/rights/3
    @PutMapping(value="/{id}")
    //用于接受put请求，来更新数据，因为能进来这个方法的，前端都是发送了一个/adminapi/rights请求
    //id就只是一个占位符，看前端传什么我们这边就接受什么
    public ResultOBJ updateRightList(@PathVariable Integer id, @RequestBody  Right right){
        //通过PathVariable可以获取浏览器传过来的id，RequestBody就是传过来的对象
        //ResultOBJ是一个状态码，用于标识成功失败还有一些信息的
        try {
            right.setId(id);
            //把id拼起来
            rightService.updateRightlist(right);
            return ResultOBJ.UPDATE_SUCCESS;
        } catch (Exception e) {
            return ResultOBJ.UPDATE_ERROR;
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResultOBJ deleteRightList(@PathVariable Integer id){
        try {
            rightService.deleteRightList(id);
            return ResultOBJ.DELETE_SUCCESS;
        } catch (Exception e) {
            return ResultOBJ.DELETE_ERROR;
        }
    }
}
