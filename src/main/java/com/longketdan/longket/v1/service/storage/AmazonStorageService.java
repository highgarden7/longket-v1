package com.longketdan.longket.v1.service.storage;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.util.IOUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

@Component
public class AmazonStorageService {
    AmazonS3 s3Client;

    String bucketName;

    public AmazonStorageService(AmazonS3 s3Client, String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    public UploadedInfo uploadFile(InputStream inputStream, String fileName, String path, String contentType, Long length, Boolean usingUUID) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        metadata.setContentLength(length);

        if (usingUUID) {
            path = getKeyNamePathWithUUID(path, fileName);
        } else {
            path = getKeyNamePath(path, fileName);
        }

        s3Client.putObject(bucketName, path, inputStream, metadata);

        return new UploadedInfo(path, length);
    }

    public InputStream download(String key) {
        return s3Client.getObject(bucketName, key).getObjectContent();
    }

    public byte[] downloadAsBytes(String key) throws IOException {
        InputStream inputStream = s3Client.getObject(bucketName, key).getObjectContent();
        return IOUtils.toByteArray(inputStream);
    }

    // 다운로드 가능한 preSignedUrl
    public String getObjectUrl(String key) {
        Date expiration = new Date(System.currentTimeMillis() + 10000);    // 만료시간 60분
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, key)
            .withMethod(HttpMethod.GET)
            .withExpiration(expiration);
        return s3Client.generatePresignedUrl(generatePresignedUrlRequest).toString();
    }

    private String getKeyNamePathWithUUID(String destPath, String fileName) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        Path path = Paths.get(destPath, uuid, fileName);
        path = path.normalize();

        return path.toString();
    }

    private String getKeyNamePath(String destPath, String fileName) {
        Path path = Paths.get(destPath, fileName);
        path = path.normalize();

        return path.toString();
    }

    public void deleteFile(String objectKey) {
        String object = getObjectUrl(objectKey);
        s3Client.deleteObject(bucketName, object);
    }
}
