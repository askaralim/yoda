package com.taklip.yoda.mapper;

import java.util.List;

import com.taklip.yoda.model.ImageFile;

public interface ImageFileMapper extends BaseMapper<ImageFile> {
	List<ImageFile> getFiles();

	List<ImageFile> getFilesByContent(int contentType, Long contentId);
}