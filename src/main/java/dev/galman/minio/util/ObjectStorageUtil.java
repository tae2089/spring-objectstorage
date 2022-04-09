package dev.galman.minio.util;

import org.springframework.web.multipart.MultipartFile;

public interface ObjectStorageUtil {
    void setup();
    String uploadFile(MultipartFile file, String filePath);

    String spyTest();
}
