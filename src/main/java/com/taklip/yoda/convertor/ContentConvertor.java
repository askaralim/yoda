package com.taklip.yoda.convertor;

import org.springframework.stereotype.Service;

import com.taklip.yoda.dto.ContentDTO;
import com.taklip.yoda.model.Content;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContentConvertor {
    // public ContentDTO.Brief toBrief(Content content) {
    //     if (content == null) return null;
        
    //     ContentDTO.Brief brief = new ContentDTO.Brief();
    //     brief.setId(content.getId());
    //     brief.setTitle(content.getTitle());
    //     brief.setShortDescription(content.getShortDescription());
    //     brief.setFeaturedImage(content.getFeaturedImage());
    //     brief.setPublished(content.isPublished());
    //     brief.setPublishDate(content.getPublishDate());
    //     brief.setHitCounter(content.getHitCounter());
    //     brief.setScore(content.getScore());
    //     if (content.getCategory() != null) {
    //         brief.setCategoryName(content.getCategory().getName());
    //     }
    //     return brief;
    // }
    
    // public ContentDTO.Detail toDetail(Content content) {
    //     if (content == null) return null;
        
    //     ContentDTO.Detail detail = new ContentDTO.Detail();
    //     detail.setId(content.getId());
    //     detail.setTitle(content.getTitle());
    //     detail.setShortDescription(content.getShortDescription());
    //     detail.setContent(content.getContent());
    //     detail.setFeaturedImage(content.getFeaturedImage());
    //     detail.setPublished(content.isPublished());
    //     detail.setPublishDate(content.getPublishDate());
    //     detail.setHitCounter(content.getHitCounter());
    //     detail.setScore(content.getScore());
    //     if (content.getCategory() != null) {
    //         detail.setCategoryName(content.getCategory().getName());
    //         detail.setCategoryId(content.getCategory().getId());
    //     }
    //     return detail;
    // }
    
    // public Content toEntity(ContentDTO.Create dto) {
    //     if (dto == null) return null;
        
    //     Content content = new Content();
    //     content.setTitle(dto.getTitle());
    //     content.setShortDescription(dto.getShortDescription());
    //     content.setContent(dto.getContent());
    //     content.setFeaturedImage(dto.getFeaturedImage());
    //     content.setPublished(dto.isPublished());
    //     content.setPublishDate(dto.getPublishDate());
        
    //     return content;
    //     // Mapping from DTO to entity for creation
    // }
}
