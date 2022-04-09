package dev.galman.minio.property;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class MinioConfig {

    @Value("${minio.accessKey}")
    private String accessKey;

    @Value("${minio.secretKey}")
    private String secretKey;

    @Value("${minio.endPoint}")
    private String endpoint;

    @Value("${minio.bucket}")
    private String bucket;

    public MinioConfig() {
    }

    public MinioConfig(String accessKey, String secretKey, String endpoint, String bucket) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.endpoint = endpoint;
        this.bucket = bucket;
    }
}
