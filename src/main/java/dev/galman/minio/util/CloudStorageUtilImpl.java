package dev.galman.minio.util;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.common.collect.Lists;
import dev.galman.minio.property.GcpConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.util.UUID;

//@Service
@RequiredArgsConstructor
public class CloudStorageUtilImpl implements ObjectStorageUtil{

    private final GcpConfig gcpConfig;
    private Storage storageClient;

    @Override
    @PostConstruct
    public void setup() {
        try{
            GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(gcpConfig.getProjectKey()))
                    .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
            storageClient =  StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        }catch(Exception e){
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public String uploadFile(MultipartFile file, String filePath) {
        try{
            String objectName = filePath + UUID.randomUUID();
            BlobId blobId = BlobId.of(gcpConfig.getBucketName(), objectName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
            storageClient.create(blobInfo, file.getBytes());
        }catch(Exception e){
            throw new IllegalStateException(e.getMessage());
        }
        return null;
    }

    @Override
    public String spyTest() {
        return null;
    }
}
