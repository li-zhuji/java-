package lzj.jsu.labsystem.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

public interface UploadService {
    public void upload(MultipartFile file) throws FileNotFoundException;
}
