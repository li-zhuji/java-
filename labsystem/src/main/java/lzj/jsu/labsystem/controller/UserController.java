package lzj.jsu.labsystem.controller;

import lzj.jsu.labsystem.config.JwtConfig;
import lzj.jsu.labsystem.pojo.User;
import lzj.jsu.labsystem.service.UserService;
import lzj.jsu.labsystem.utils.ResultOBJ;
import lzj.jsu.labsystem.utils.SYSConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adminapi/users")
public class UserController {
    @Autowired
    private UserService userService;
    //登录成功后生成token
    @Autowired
    private JwtConfig jwtConfig;

    @GetMapping
    public List<User> getUserList(User user){
        return  userService.getUserList(user);
    }

    @PostMapping("/login") // /adminapi/users/login
    public ResultOBJ login(@RequestBody User user){
        try {
            List<User> list = userService.getUserList(user);
            if(list.isEmpty()){
                throw new RuntimeException();
            }
            User currentUser = list.get(0);//取出第一个元素，并生成token
            String token = jwtConfig.createToken(currentUser.getUsername());
            currentUser.setToken(token);
            return new ResultOBJ(SYSConstant.CODE_SUCCESS,SYSConstant.LOGIN_SUCCESS,currentUser);
        } catch (Exception e) {
            return new ResultOBJ(SYSConstant.CODE_ERROR,SYSConstant.LOGIN_ERROR);
        }
    }

    @PostMapping
    public ResultOBJ addUserList(@RequestBody  User user){
        try {
            userService.addUserList(user);
            return ResultOBJ.ADD_SUCCESS;
        } catch (Exception e) {
//            throw new RuntimeException(e);
            return ResultOBJ.ADD_ERROR;
        }
    }

    @PutMapping(value="/{id}")  //动态路由，需要加一个value
    //@RequestBody主要用来接收前端传递给后端的json字符串中的数据的
    //通过 @PathVariable 可以将 URL 中占位符参数绑定到控制器处理方法的入参中:URL 中的 {xxx} 占位符可以通过@PathVariable(“xxx”) 绑定到操作方法的入参中。
    public ResultOBJ updateUserList(@PathVariable Integer id,@RequestBody User user){
        try {
            user.setId(id);
            userService.updateUserList(user);
            return ResultOBJ.UPDATE_SUCCESS;
        } catch (Exception e) {
//            throw new RuntimeException(e);
            return ResultOBJ.UPDATE_ERROR;
        }
    }
    @DeleteMapping(value = "/{id}")
    public ResultOBJ deleteUserList(@PathVariable Integer id){
        try {
            userService.deleteUserList(id);
            return ResultOBJ.DELETE_SUCCESS;
        } catch (Exception e) {
//            throw new RuntimeException(e);
            return ResultOBJ.DELETE_ERROR;
        }

    }
}
