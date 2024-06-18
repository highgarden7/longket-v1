package com.longketdan.longket.v1.service;

import com.longketdan.longket.config.exception.CustomException;
import com.longketdan.longket.config.exception.ErrorCode;
import com.longketdan.longket.v1.model.entity.Resource;
import com.longketdan.longket.v1.repository.ResourceRepository;
import com.longketdan.longket.v1.service.storage.AmazonStorageService;
import com.longketdan.longket.v1.service.storage.UploadedInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class ResourceService {

    private final AmazonStorageService amazonStorageService;

    private final ResourceRepository resourceRepository;

    public Resource uploadFile(MultipartFile file, String path, Boolean usingUUID) throws IOException {
        String fileName = file.getOriginalFilename();
        String contentType = file.getContentType();
        Long length = file.getSize();
        InputStream inputStream = file.getInputStream();

        UploadedInfo uploadedInfo = amazonStorageService.uploadFile(inputStream, fileName, path, contentType, length, usingUUID);

        return resourceRepository.save(Resource.builder()
                .name(fileName)
                .rscUrl(uploadedInfo.getAbsoluteUri())
                .length(length)
                .build());
    }

    public byte[] downloadFileAsBytes(String key) throws IOException {
        return amazonStorageService.downloadAsBytes(key);
    }

    public String getFileUrl(Long fileId) {
        Resource file = resourceRepository.findById(fileId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        return amazonStorageService.getObjectUrl(file.getRscUrl());
    }

    public void deleteFile(String key) {
        amazonStorageService.deleteFile(key);
    }
}

