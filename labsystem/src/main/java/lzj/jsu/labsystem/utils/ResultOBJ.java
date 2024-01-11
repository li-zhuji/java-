package lzj.jsu.labsystem.utils;

import lombok.Data;

//进行封装
@Data
public class ResultOBJ {
    private Integer code; //是否成功
    private  String msg; //提示信息
    private Object data; //数据

    //任意参数的创建
    public ResultOBJ(Integer code){
        this.code =code;
    }

    public ResultOBJ(Integer code,String msg){
        this.code = code;
        this.msg= msg;
    }
    public ResultOBJ(Integer code,String msg,Object data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    //设置几个状态码
    public  static  final  ResultOBJ ADD_SUCCESS = new ResultOBJ(SYSConstant.CODE_SUCCESS,SYSConstant.ADD_SUCCESS);

    public  static  final  ResultOBJ ADD_ERROR = new ResultOBJ(SYSConstant.CODE_ERROR,SYSConstant.ADD_ERROR);

    public  static  final  ResultOBJ UPDATE_SUCCESS = new ResultOBJ(SYSConstant.CODE_SUCCESS,SYSConstant.UPDATE_SUCCESS);

    public  static  final  ResultOBJ UPDATE_ERROR = new ResultOBJ(SYSConstant.CODE_ERROR,SYSConstant.UPDATE_ERROR);

    public  static  final  ResultOBJ DELETE_SUCCESS = new ResultOBJ(SYSConstant.CODE_SUCCESS,SYSConstant.DELETE_SUCCESS);

    public  static  final  ResultOBJ DELETE_ERROR = new ResultOBJ(SYSConstant.CODE_ERROR,SYSConstant.DELETE_ERROR);





}
