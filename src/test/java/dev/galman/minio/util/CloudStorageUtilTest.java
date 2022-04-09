package dev.galman.minio.util;

import dev.galman.minio.property.GcpConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;


class CloudStorageUtilTest {
    private ObjectStorageUtil objectStorageUtil;

    private GcpConfig gcpConfig;

    @BeforeEach
    void setup(){
        String fileName = "src/main/resources/application.yml";
        Yaml yaml = new Yaml();
        try {
            HashMap<String, HashMap<String,String>> cloudAwsMap = yaml.load(new FileReader(fileName));
            String bucket = cloudAwsMap.get("gcp").get("bucket");
            String key = cloudAwsMap.get("gcp").get("key");
            gcpConfig = new GcpConfig(bucket, key);
            objectStorageUtil = new CloudStorageUtilImpl(gcpConfig);
            objectStorageUtil.setup();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Disabled("테스트를 하실 경우 주석해주세요")
    void checkBucket(){
        MockMultipartFile file = new MockMultipartFile("file", "hello2.png", MediaType.IMAGE_PNG_VALUE, "Hello, World!".getBytes());
        String result =  objectStorageUtil.uploadFile(file,"");
        System.out.println("result = " + result);
    }
}
