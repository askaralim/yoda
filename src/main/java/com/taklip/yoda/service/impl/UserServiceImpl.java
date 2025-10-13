package com.taklip.yoda.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taklip.yoda.common.contant.Constants;
import com.taklip.yoda.common.tools.ThumbnailUploader;
import com.taklip.yoda.common.util.FileUtil;
import com.taklip.yoda.convertor.ModelConvertor;
import com.taklip.yoda.dto.UserDTO;
import com.taklip.yoda.mapper.UserAuthorityMapper;
import com.taklip.yoda.mapper.UserMapper;
import com.taklip.yoda.model.User;
import com.taklip.yoda.model.UserAuthority;
import com.taklip.yoda.service.UserFollowRelationService;
import com.taklip.yoda.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Transactional
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ThumbnailUploader thumbnailUploader;

    @Autowired
    private UserAuthorityMapper authorityMapper;

    @Autowired
    private ModelConvertor modelConvertor;
    
    @Autowired
    private UserFollowRelationService userFollowRelationService;

    @Override
    public User getUserByUsername(String username) {
        User user = baseMapper.getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Set<UserAuthority> roles =
        // baseMapper.selectAuthoritiesByUserId(user.getId());
        // user.setAuthorities(roles);

        return user;
    }

    @Override
    public UserDTO create(User user) {
        // Validate user data
        if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
            throw new IllegalArgumentException("Username and password are required");
        }

        // Check if username exists
        if (lambdaQuery().eq(User::getUsername, user.getUsername()).exists()) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (lambdaQuery().eq(User::getEmail, user.getEmail()).exists()) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save user
        save(user);

        saveUserRole(user, Constants.USER_ROLE_USER);

        return modelConvertor.convertToUserDTO(user);
    }

    @Override
    public UserDTO getUserDetail(long id) {
        User user = baseMapper.getUserById(id);

        Long followerCount = userFollowRelationService.getUserFollowerCount(user.getId());
        Long followeeCount = userFollowRelationService.getUserFolloweeCount(user.getId());

        UserDTO userDTO = modelConvertor.convertTo(user, UserDTO.class);
        userDTO.setFollowerCount(followerCount);
        userDTO.setFolloweeCount(followeeCount);

        return userDTO;
    }

    @Override
    public User getUser(long id) {
        List<UserAuthority> authorities = authorityMapper
                .selectList(new LambdaQueryWrapper<UserAuthority>().eq(UserAuthority::getUserId, id));
        User user = baseMapper.getUserById(id);
        user.setAuthorities(authorities.stream().collect(Collectors.toSet()));
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        User user = baseMapper.getUserByEmail(email);
        List<UserAuthority> authorities = authorityMapper
                .selectList(new LambdaQueryWrapper<UserAuthority>().eq(UserAuthority::getUserId, user.getId()));
        user.setAuthorities(authorities.stream().collect(Collectors.toSet()));
        return user;
    }

    @Override
    public List<User> getUsers() {
        return baseMapper.getUsers();
    }

    @Override
    public List<User> search(Long userId, String username, String role, Boolean enabled) {
        return baseMapper.search(userId, username, role, enabled);
    }

    @Override
    public void delete(long userId) {
        User user = getUser(userId);

        this.removeById(userId);

        for (UserAuthority ua : user.getAuthorities()) {
            authorityMapper.deleteById(ua.getId());
        }
    }

    @Override
    public UserDTO update(User user) {
        User userDb = baseMapper.getUserById(user.getId());

        if (!StringUtils.isBlank(user.getPassword())) {
            userDb.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        userDb.setUsername(user.getUsername());
        userDb.setEmail(user.getEmail());
        userDb.setEnabled(user.isEnabled());
        userDb.setPhone(user.getPhone());
        userDb.setAddressLine1(user.getAddressLine1());
        userDb.setAddressLine2(user.getAddressLine2());

        baseMapper.updateById(userDb);

        return modelConvertor.convertToUserDTO(userDb);
    }

    @Override
    public UserDTO updateImage(long id, MultipartFile photo) {
        User userDb = baseMapper.getUserById(id);
        if (!photo.isEmpty()) {
            thumbnailUploader.deleteFile(userDb.getProfilePhoto());
            thumbnailUploader.deleteFile(userDb.getProfilePhotoSmall());

            if (FileUtil.isImage(photo.getOriginalFilename())) {
                try {
                    userDb.setProfilePhoto(thumbnailUploader.createThumbnailLarge(photo.getInputStream()));
                    userDb.setProfilePhotoSmall(thumbnailUploader.createThumbnailMedium(photo.getInputStream()));
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }

        baseMapper.updateById(userDb);

        return modelConvertor.convertToUserDTO(userDb);
    }

    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {
        User userDb = baseMapper.getUserByUsername(username);

        userDb.setPassword(passwordEncoder.encode(newPassword));

        baseMapper.updateById(userDb);
    }

    private void saveUserRole(User user, String role) {
        if (role.equals(Constants.USER_ROLE_ADMINISTRATOR)) {
            authorityMapper.insert(new UserAuthority(user.getId(), "ROLE_ADMIN"));
            authorityMapper.insert(new UserAuthority(user.getId(), "ROLE_USER"));

            user.getAuthorities().clear();

            user.getAuthorities().add(new UserAuthority(user.getId(), "ROLE_ADMIN"));
            user.getAuthorities().add(new UserAuthority(user.getId(), "ROLE_USER"));
        } else if (role.equals(Constants.USER_ROLE_SUPERUSER)) {
            authorityMapper.insert(new UserAuthority(user.getId(), "ROLE_SUPERUSER"));

            user.getAuthorities().clear();

            user.getAuthorities().add(new UserAuthority(user.getId(), "ROLE_SUPERUSER"));
        } else {
            authorityMapper.insert(new UserAuthority(user.getId(), "ROLE_USER"));

            user.getAuthorities().clear();

            user.getAuthorities().add(new UserAuthority(user.getId(), "ROLE_USER"));
        }
    }

    @Override
    public Page<UserDTO> getByPage(Page<User> page) {
        Page<User> userPage = this.page(page, new LambdaQueryWrapper<User>().orderByDesc(User::getCreateTime));
        Page<UserDTO> userDTOPage = new Page<>();
        userDTOPage.setTotal(userPage.getTotal());
        userDTOPage.setCurrent(userPage.getCurrent());
        userDTOPage.setSize(userPage.getSize());

        return userDTOPage;
    }
}