package com.taklip.yoda.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taklip.yoda.enums.ContentTypeEnum;
import com.taklip.yoda.mapper.ImageFileMapper;
import com.taklip.yoda.model.ImageFile;
import com.taklip.yoda.service.FileService;
import com.taklip.yoda.common.tools.ImageUploader;
import com.taklip.yoda.common.contant.StringPool;

@Service
public class FileServiceImpl extends ServiceImpl<ImageFileMapper, ImageFile> implements FileService {
    @Autowired
    ImageUploader imageUploader;

    @Override
    public String save(String contentType, Long contentId, MultipartFile image) {
        ImageFile imageFile = imageUploader.uploadImage(image, contentType + StringPool.FORWARD_SLASH + contentId);

        if (null != imageFile) {
            imageFile.setFileType(image.getContentType());
            imageFile.setContentId(contentId);
            imageFile.setContentType(ContentTypeEnum.getCode(contentType));
        }

        return this.save(imageFile) ? imageFile.getFilePath() : null;
    }

    // @Override
    // public boolean save(ImageFile file) {
    //     return this.save(file);
    // }

    @Override
    public List<ImageFile> getFiles() {
        return this.list();
    }

    @Override
    public List<ImageFile> getFilesByContent(String contentType, Long contentId) {
        return this.list(
                new LambdaQueryWrapper<ImageFile>().eq(ImageFile::getContentType, ContentTypeEnum.getCode(contentType))
                        .eq(ImageFile::getContentId, contentId));
    }
}