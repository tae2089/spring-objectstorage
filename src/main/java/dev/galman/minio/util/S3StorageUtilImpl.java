package dev.galman.minio.util;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import dev.galman.minio.property.AwsConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;

//@Service
@RequiredArgsConstructor
public class S3StorageUtilImpl implements ObjectStorageUtil{

    private AmazonS3 s3Client;
    private final AwsConfig awsConfig;



    @Override
    @PostConstruct
    public void setup() {
        AWSCredentials credentials = new BasicAWSCredentials(awsConfig.getAccessKey(), awsConfig.getSecretKey());
        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(awsConfig.getRegion())
                .build();
    }

    @Override
    public String uploadFile(MultipartFile file, String filePath) {
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            throw new IllegalStateException("file name null");
        }
        if(fileName.contains(".jpg")) {
            fileName = fileName.replace(".jpg", ".jpeg");
        }
        try{
            s3Client.putObject(new PutObjectRequest(awsConfig.getBucket(), filePath+fileName, file.getInputStream(), null)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            return s3Client.getUrl(awsConfig.getBucket(), filePath+fileName).toString();
        }catch(Exception e){
            throw new IllegalStateException("image send error");
        }
    }

    @Override
    public String spyTest() {
        return "hello";
    }
}
