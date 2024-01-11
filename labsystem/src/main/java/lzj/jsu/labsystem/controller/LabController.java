package lzj.jsu.labsystem.controller;

import lzj.jsu.labsystem.pojo.Lab;
import lzj.jsu.labsystem.service.LabService;
import lzj.jsu.labsystem.utils.ResultOBJ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adminapi/labs")
public class LabController {
    @Autowired
    private LabService labService;

    @GetMapping
    public List<Lab> getLabList(Lab lab){
        return labService.getLabList(lab);
    }

    @PostMapping
    public ResultOBJ addLabList(@RequestBody  Lab lab){
        try {
            labService.addLabList(lab);
            return ResultOBJ.ADD_SUCCESS;
        } catch (Exception e) {
//            throw new RuntimeException(e);
            return ResultOBJ.ADD_ERROR;
        }
    }

    @PutMapping(value="/{id}")
    public  ResultOBJ updateLabList(@PathVariable Integer id,@RequestBody Lab lab){
        try {
            lab.setId(id);
            labService.updateLabList(lab);
            return  ResultOBJ.UPDATE_SUCCESS;
        } catch (Exception e) {
//            throw new RuntimeException(e);
            return ResultOBJ.UPDATE_ERROR;
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResultOBJ deleteLabList(@PathVariable  Integer id){
        try {
            labService.deleteLabList(id);
            return ResultOBJ.DELETE_SUCCESS;
        } catch (Exception e) {
//            throw new RuntimeException(e);
            return  ResultOBJ.DELETE_ERROR;
        }

    }
}
