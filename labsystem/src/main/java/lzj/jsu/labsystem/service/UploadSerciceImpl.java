package lzj.jsu.labsystem.service;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class UploadSerciceImpl implements UploadService{

    @Override
    public void upload(MultipartFile file) throws FileNotFoundException {
        String fileName = "bg.jpg";
        String filepath = ResourceUtils.getURL("classpath:").getPath()+"static/upload/";

        File dist = new File(filepath+fileName);
        if(!dist.getParentFile().exists()){
            dist.getParentFile().mkdirs();
        }

        try {
            file.transferTo(dist);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
