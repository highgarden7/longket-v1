package com.longketdan.longket.v1.controller;

import com.longketdan.longket.config.exception.CustomException;
import com.longketdan.longket.config.exception.ErrorCode;
import com.longketdan.longket.config.exception.CommonResponse;
import com.longketdan.longket.v1.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
public class ResourceController {

    private final ResourceService resourceService;

    @PostMapping("/upload")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("path") String path) {
        try {
            return CommonResponse.dataResponseEntity(resourceService.uploadFile(file, path, true));
        } catch (IOException e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam("key") String key) {
        try {
            byte[] data = resourceService.downloadFileAsBytes(key);
            return ResponseEntity.ok().body(data);
        } catch (IOException e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{fileId}/url")
    public ResponseEntity<Object> getFileUrl(@PathVariable(value = "fileId") Long fileId) {
        return CommonResponse.dataResponseEntity(resourceService.getFileUrl(fileId));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteFile(@RequestParam("key") String key) {
        resourceService.deleteFile(key);
        return CommonResponse.successResponseEntity();
    }
}
