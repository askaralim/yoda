package com.taklip.yoda.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taklip.yoda.model.User;
import com.taklip.yoda.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService extends IService<User> {
    User getUser(long userId);

    UserDTO getUserDetail(long id);

    User getUserByUsername(String userName);

    User getUserByEmail(String email);

    List<User> getUsers();

    List<User> search(Long userId, String username, String role, Boolean enabled);

    void deleteUser(long userId);

    User update(User user, MultipartFile profilePhoto);

    User createUser(User user);

    void changePassword(String username, String oldPassword, String newPassword);
}