package com.taklip.yoda.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taklip.yoda.mapper.ContentUserRateMapper;
import com.taklip.yoda.model.ContentUserRate;
import com.taklip.yoda.model.User;
import com.taklip.yoda.service.ContentUserRateService;
import com.taklip.yoda.service.RedisService;
import com.taklip.yoda.common.contant.Constants;
import com.taklip.yoda.common.util.AuthenticatedUtil;

@Service
public class ContentUserRateServiceImpl extends ServiceImpl<ContentUserRateMapper, ContentUserRate>
        implements ContentUserRateService {
    @Autowired
    private RedisService redisService;

    @Override
    public void create(Long contentId, String thumb) {
        User loginUser = AuthenticatedUtil.getAuthenticatedUser();

        if (loginUser == null) {
            return;
        }

        ContentUserRate rate = new ContentUserRate();

        rate.setContentId(contentId);

        ContentUserRate rateDb = getByContentIdAndUserId(contentId, loginUser.getId());

        if ((rateDb != null) && thumb.equals(Constants.USER_RATE_THUMB_NEUTRAL)) {
            this.removeById(rateDb);

            return;
        } else if ((rateDb != null) && (rateDb.getScore() == 1) && thumb.equals(Constants.USER_RATE_THUMB_UP)) {
            return;
        } else if ((rateDb != null) && (rateDb.getScore() == -1) && thumb.equals(Constants.USER_RATE_THUMB_DOWN)) {
            return;
        } else if ((rateDb != null) && (rateDb.getScore() == -1) && thumb.equals(Constants.USER_RATE_THUMB_UP)) {
            this.removeById(rateDb);

            return;
        } else if ((rateDb != null) && (rateDb.getScore() == 1) && thumb.equals(Constants.USER_RATE_THUMB_DOWN)) {
            this.removeById(rateDb);

            return;
        } else if ((rateDb == null) && thumb.equals(Constants.USER_RATE_THUMB_UP)) {
            rate.setScore(1);
        } else if ((rateDb == null) && thumb.equals(Constants.USER_RATE_THUMB_DOWN)) {
            rate.setScore(-1);
        } else {
            return;
        }

        this.save(rate);

    }

    @Override
    public void update(ContentUserRate contentUserRate) {
        this.updateById(contentUserRate);
    }

    @Override
    public void delete(Long id) {
        this.removeById(id);
    }

    @Override
    public ContentUserRate getById(Long id) {
        return this.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getTotalRateByContentId(Long contentId) {
        String rateCached = redisService.get(Constants.REDIS_CONTENT_RATE + ":" + contentId);

        if (StringUtils.isNoneBlank(rateCached) && !"nil".equalsIgnoreCase(rateCached)) {
            return Integer.valueOf(rateCached);
        }

        Integer rate = baseMapper.getContentRateByContentId(contentId);

        if (rate == null) {
            rate = 0;
        }

        redisService.set(Constants.REDIS_CONTENT_RATE + ":" + contentId, String.valueOf(rate), 3600);

        return rate;
    }

    @Override
    @Transactional(readOnly = true)
    public ContentUserRate getByContentIdAndUserId(Long contentId, Long userId) {
        return this.getOne(new LambdaQueryWrapper<ContentUserRate>().eq(ContentUserRate::getContentId, contentId)
                .eq(ContentUserRate::getCreateBy, userId));
    }

    @Override
    public List<ContentUserRate> getByContentId(Long contentId) {
        return this.list(new LambdaQueryWrapper<ContentUserRate>().eq(ContentUserRate::getContentId, contentId));
    }
}