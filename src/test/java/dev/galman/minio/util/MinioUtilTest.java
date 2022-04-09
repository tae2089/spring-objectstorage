package dev.galman.minio.util;


import dev.galman.minio.property.MinioConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class MinioUtilTest {

    private ObjectStorageUtil objectStorageUtil;
    private MinioConfig minioConfig;
    String endPoint;
    String bucket;
    @BeforeEach
    void setup(){
        String fileName = "src/main/resources/application.yml";
        Yaml yaml = new Yaml();
        try {
            HashMap<String, HashMap<String,String>> cloudAwsMap = yaml.load(new FileReader(fileName));
            String accessKey = cloudAwsMap.get("minio").get("accessKey");
            String secretKey = cloudAwsMap.get("minio").get("secretKey");
             endPoint = cloudAwsMap.get("minio").get("endPoint");
             bucket = cloudAwsMap.get("minio").get("bucket");
            minioConfig = new MinioConfig(accessKey, secretKey, endPoint, bucket);
            objectStorageUtil = new MinioObjectStorageUtilImpl(minioConfig);
            objectStorageUtil.setup();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void checkBucket(){
        MockMultipartFile file = new MockMultipartFile("file", "hello3.png", MediaType.IMAGE_PNG_VALUE, "Hello, World!".getBytes());
        String result =  objectStorageUtil.uploadFile(file,"");
        assertThat(result).isEqualTo(endPoint+"/"+bucket+"/"+file.getOriginalFilename());
    }
}
