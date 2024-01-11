package lzj.jsu.labsystem.controller;

import lzj.jsu.labsystem.service.UploadService;
import lzj.jsu.labsystem.utils.ResultOBJ;
import lzj.jsu.labsystem.utils.SYSConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

@RequestMapping("/adminapi/upload")
@RestController
public class UploadController {

    @Autowired
    private UploadService uploadService;


    @PostMapping
    public ResultOBJ upload(@RequestParam("file") MultipartFile file){
        try {
            uploadService.upload(file);
            return new ResultOBJ(SYSConstant.CODE_SUCCESS,"上传成功");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
