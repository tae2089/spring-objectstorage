package dev.galman.minio.api;

import dev.galman.minio.util.ObjectStorageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ObjectStorageController {

    private final ObjectStorageUtil objectStorageUtil;

    @PostMapping("upload")
    public String postFile(MultipartFile file) {
        return objectStorageUtil.uploadFile(file, "");
    }

}
