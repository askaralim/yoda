package com.taklip.yoda.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taklip.yoda.mapper.ContentContributorMapper;
import com.taklip.yoda.model.ContentContributor;
import com.taklip.yoda.service.ContentContributorService;
import com.taklip.yoda.service.RedisService;
import com.taklip.yoda.common.contant.Constants;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ContentContributorServiceImpl extends ServiceImpl<ContentContributorMapper, ContentContributor>
        implements ContentContributorService {
    @Autowired
    private RedisService redisService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void create(ContentContributor contentContributor) {
        this.save(contentContributor);
        this.setContentContributorIntoCache(contentContributor);
    }

    @Override
    public void update(ContentContributor contentContributor) {
        this.updateById(contentContributor);
        this.deleteContentContributorFromCache(contentContributor.getId());
    }

    @Override
    public void delete(Long id) {
        this.removeById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ContentContributor getById(long id) {
        ContentContributor contentContributor = this.getContentContributorFromCache(id);

        if (null != contentContributor) {
            return contentContributor;
        }

        contentContributor = this.getById(id);

        this.setContentContributorIntoCache(contentContributor);

        return contentContributor;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContentContributor> getByContentIdAndUserId(long contentId, long userId) {
        return this.list(new LambdaQueryWrapper<ContentContributor>().eq(ContentContributor::getContentId, contentId)
                .eq(ContentContributor::getUserId, userId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContentContributor> getContentContributorsByContentId(long contentId) {
        return this.list(new LambdaQueryWrapper<ContentContributor>().eq(ContentContributor::getContentId, contentId)
                .orderByDesc(ContentContributor::getCreateTime));
    }

    private ContentContributor getContentContributorFromCache(long contentContributorId) {
        String key = Constants.REDIS_CONTENT_CONTRIBUROR + ":" + contentContributorId;
        Map<String, String> map = redisService.getMap(key);

        if (CollectionUtils.isEmpty(map)) {
            return null;
        }

        try {
            return objectMapper.convertValue(map, ContentContributor.class);
        } catch (IllegalArgumentException e) {
            log.error("Failed to convert cached data to ContentContributor: {}", e.getMessage());
            return null;
        }
    }

    private void deleteContentContributorFromCache(Long id) {
        redisService.delete(Constants.REDIS_CONTENT_CONTRIBUROR + ":" + id);
    }

    private void setContentContributorIntoCache(ContentContributor contentContributor) {
        if (Objects.isNull(contentContributor)) {
            return;
        }

        try {
            Map<String, String> value = objectMapper.convertValue(contentContributor,
                    new TypeReference<Map<String, String>>() {
                    });

            redisService.setMap(Constants.REDIS_CONTENT_CONTRIBUROR + ":" + contentContributor.getId(), value);
        } catch (IllegalArgumentException e) {
            log.error("Failed to cache content contributor {}: {}", contentContributor.getId(), e.getMessage());
        }
    }
}