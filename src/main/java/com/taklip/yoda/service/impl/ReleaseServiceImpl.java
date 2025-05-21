package com.taklip.yoda.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taklip.yoda.mapper.ReleaseMapper;
import com.taklip.yoda.model.Release;
import com.taklip.yoda.service.ReleaseService;

@Service
class ReleaseServiceImpl extends ServiceImpl<ReleaseMapper, Release> implements ReleaseService {
    public Release getRelease(int buildNumber) {
        return this.getOne(new QueryWrapper<Release>().eq("build_number", buildNumber));
    }

    @Transactional
    public Release addRelease(int buildNumber) {
        Release release = new Release();

        release.setBuildNumber(buildNumber);
        release.setModifiedDate(LocalDateTime.now());
        release.setVerified(true);

        this.save(release);
        return release;
    }
}
