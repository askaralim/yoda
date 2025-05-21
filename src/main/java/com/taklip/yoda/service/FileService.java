package com.taklip.yoda.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taklip.yoda.model.ImageFile;

public interface FileService extends IService<ImageFile> {
    String save(String contentType, Long contentId, MultipartFile file) throws IOException ;

    List<ImageFile> getFiles();

    List<ImageFile> getFilesByContent(String contentType, Long contentId);

    // boolean save(ImageFile file);
}