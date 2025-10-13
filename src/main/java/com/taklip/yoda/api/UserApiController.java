package com.taklip.yoda.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContext;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taklip.yoda.common.util.Validator;
import com.taklip.yoda.controller.PortalBaseController;
import com.taklip.yoda.dto.ContentDTO;
import com.taklip.yoda.dto.PostDTO;
import com.taklip.yoda.dto.UserDTO;
import com.taklip.yoda.model.User;
import com.taklip.yoda.service.ContentService;
import com.taklip.yoda.service.PostService;
import com.taklip.yoda.service.UserFollowRelationService;
import com.taklip.yoda.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
public class UserApiController extends PortalBaseController {
    @Autowired
    protected ContentService contentService;

    @Autowired
    protected UserService userService;

    @Autowired
    protected PostService postService;

    @Autowired
    protected UserFollowRelationService userFollowRelationService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserDetail(@PathVariable long id) {
        return ResponseEntity.ok(userService.getUserDetail(id));
    }

    @GetMapping("/{id}/profile")
    public ResponseEntity<UserDTO> getProfile(@PathVariable long id) {
        return ResponseEntity.ok(userService.getUserDetail(id));
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<Page<PostDTO>> getPostsByUser(@PathVariable Long id,
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit) {
        return ResponseEntity.ok(postService.getByUser(id, offset, limit));
    }

    @GetMapping("/{id}/contents")
    public ResponseEntity<Page<ContentDTO>> getContentsByUser(@PathVariable Long id,
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit) {
        return ResponseEntity.ok(contentService.getContentByUserId(id, offset, limit));
    }

    @GetMapping("/page")
    public ResponseEntity<Page<UserDTO>> page(@RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit) {
        return ResponseEntity.ok(userService.getByPage(new Page<>(offset, limit)));
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable long id, @RequestBody User user) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(user));
    }

    // @PostMapping("/{id}/prof")
    // public ResponseEntity<UserDTO> updateSettings(@PathVariable long id, @RequestBody User user) {
    //     return ResponseEntity.status(HttpStatus.OK).body(userService.update(user));
    // }

    @PostMapping("/{id}/uploadImage")
    public ResponseEntity<UserDTO> uploadImage(@PathVariable long id, @RequestParam MultipartFile image) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateImage(id, image));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> submit(@RequestBody User user) {
        if (!Validator.isEmailAddress(user.getEmail())) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(user));
    }

    @PostMapping("/follow")
    public ResponseEntity<Void> follow(@RequestParam Long userId,
            @RequestParam Long loginUserId) {

        userFollowRelationService.follow(loginUserId, userId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/unfollow")
    public ResponseEntity<Void> unfollow(@RequestParam Long userId,
            @RequestParam Long loginUserId) {

        userFollowRelationService.unFollow(loginUserId, userId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/register/ajax")
    public ResponseEntity<String> ajaxRegister(@RequestParam String username, @RequestParam String email,
            @RequestParam String password, HttpServletRequest request) {
        User userDb = userService.getUserByUsername(username);

        RequestContext requestContext = new RequestContext(request);

        JSONObject jsonResult = new JSONObject();

        try {
            if (null != userDb) {
                jsonResult.put("error", requestContext.getMessage("duplicate-username"));
            }

            if (!Validator.isEmailAddress(email)) {
                jsonResult.put("error", requestContext.getMessage("invalid-email"));
            }

            userDb = userService.getUserByEmail(email);

            if (null != userDb) {
                jsonResult.put("error", requestContext.getMessage("duplicate-email"));
            }
        } catch (JSONException e) {

        }

        if (jsonResult.isEmpty()) {
            try {
                User user = new User();
                user.setPassword(password);
                user.setUsername(username);
                user.setEmail(email);
                userService.save(user);
            } catch (Exception e) {
                log.error("Saving User with username:" + username + " - password:" + password
                        + " - email:" + email + e.getMessage());
            }

            // try {
            // UsernamePasswordAuthenticationToken authResult = new
            // UsernamePasswordAuthenticationToken(email, password);

            // Authentication result = authenticationManager.authenticate(authResult);

            // // redirect into secured main page if authentication successful
            // if (result.isAuthenticated()) {
            // SecurityContextHolder.getContext().setAuthentication(result);
            // }
            // }
            // catch (Exception e) {
            // logger.debug("Problem authenticating user" + username, e);
            // }
        }

        String jsonString = jsonResult.toString();

        return ResponseEntity.ok(jsonString);
    }
}
