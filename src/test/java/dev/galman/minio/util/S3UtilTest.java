package dev.galman.minio.util;

import dev.galman.minio.property.AwsConfig;
import dev.galman.minio.property.MinioConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class S3UtilTest {


    private ObjectStorageUtil objectStorageUtil;


    private AwsConfig awsConfig;

    @BeforeEach
    void setup(){
        String fileName = "src/main/resources/application.yml";
        Yaml yaml = new Yaml();
        try {
            HashMap<String, HashMap<String,HashMap<String,HashMap<String,String>>>> cloudAwsMap = yaml.load(new FileReader(fileName));
            HashMap<String, String> s3 = cloudAwsMap.get("cloud").get("aws").get("s3");
            HashMap<String, String> region =  cloudAwsMap.get("cloud").get("aws").get("region");
            HashMap<String, String> credentials = cloudAwsMap.get("cloud").get("aws").get("credentials");
            awsConfig = new AwsConfig(credentials.get("accessKey"),credentials.get("secretKey"),s3.get("bucket"),region.get("static"));
            objectStorageUtil = new S3StorageUtilImpl(awsConfig);
            objectStorageUtil.setup();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void checkBucket(){
        MockMultipartFile file = new MockMultipartFile("file", "hello3.png", MediaType.IMAGE_PNG_VALUE, "Hello, World!".getBytes());
        String result =  objectStorageUtil.uploadFile(file,"");
        System.out.println("result = " + result);
    }
}
