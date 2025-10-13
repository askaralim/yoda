package com.taklip.yoda.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    Page<UserDTO> getByPage(Page<User> page);

    List<User> search(Long userId, String username, String role, Boolean enabled);

    void delete(long userId);

    UserDTO update(User user);

    UserDTO create(User user);

    void changePassword(String username, String oldPassword, String newPassword);

    UserDTO updateImage(long id, MultipartFile photo);
}
