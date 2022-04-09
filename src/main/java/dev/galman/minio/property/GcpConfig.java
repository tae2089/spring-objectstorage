package dev.galman.minio.property;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Component
@Getter
public class GcpConfig {
    @Value("${gcp.bucket}")
    private String bucketName;

    @Value("${gcp.key}")
    private String projectKey;

    public GcpConfig() {
    }

    public GcpConfig(String bucketName, String projectKey) {
        this.bucketName = bucketName;
        this.projectKey = projectKey;
    }
}
