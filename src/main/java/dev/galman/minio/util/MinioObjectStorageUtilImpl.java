package dev.galman.minio.util;

import dev.galman.minio.property.MinioConfig;
import io.minio.BucketExistsArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MinioObjectStorageUtilImpl implements ObjectStorageUtil {

    private MinioClient minioClient;
    private final MinioConfig minioConfig;

    @Override
    @PostConstruct
    public void setup() {

        minioClient = MinioClient.builder()
                .endpoint(minioConfig.getEndpoint())
                .credentials(minioConfig.getAccessKey(),minioConfig.getSecretKey())
                .build();
    }

    @Override
    public String uploadFile(MultipartFile file, String filePath) {
        try{

            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioConfig.getBucket()).build());
            if(found){
                String path = !Objects.equals(filePath, "") ? filePath+"/"+file.getOriginalFilename() : file.getOriginalFilename();
                byte[] bytes = IOUtils.toByteArray(file.getInputStream());
                ByteArrayInputStream byteArrayIs = new ByteArrayInputStream(bytes);
                minioClient.putObject(PutObjectArgs.builder()
                                .object(path)
                                .bucket(minioConfig.getBucket())
                                .stream(byteArrayIs,byteArrayIs.available(),-1)
                        .build());
                byteArrayIs.close();
                return minioConfig.getEndpoint()+"/"+minioConfig.getBucket()+"/"+path;
            }
            return "";
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String spyTest() {
        return "hello";
    }
}
