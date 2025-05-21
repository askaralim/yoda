// package com.taklip.yoda.controller;

// import java.util.HashMap;
// import java.util.Map;

// import org.springframework.http.ResponseEntity;
// import org.springframework.security.core.Authentication;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.servlet.ModelAndView;

// import com.taklip.yoda.model.User;
// import com.taklip.yoda.service.UserService;

// import jakarta.servlet.http.HttpServletRequest;
// import lombok.extern.slf4j.Slf4j;

// @Controller
// @RequestMapping("/")
// @Slf4j
// public class AuthController {

//     private final UserService userService;

//     public AuthController(UserService userService) {
//         this.userService = userService;
//     }

//     @GetMapping("/login")
//     public String login(@RequestParam(required = false) String error, 
//                        @RequestParam(required = false) String logout,
//                        Model model) {
//         if (error != null) model.addAttribute("error", "用户名或密码错误");
//         if (logout != null) model.addAttribute("message", "您已成功登出");
//         return "login";
//     }

//     @RequestMapping(value = "/home", method = RequestMethod.GET)
//     public ModelAndView home() {
//         ModelAndView model = new ModelAndView();
//         model.setViewName("home");
//         model.addObject("message", "Welcome to Spring Boot with Thymeleaf!");
//         model.addObject("users", userService.getUsers());
//         model.addObject("newUser", new User());

//         return model;
//     }

//     @GetMapping("/register")
//     public String registerForm(Model model) {
//         model.addAttribute("user", new User());
//         return "register";
//     }

//     @PostMapping("/register")
//     public ResponseEntity<?> register(@RequestParam String username, @RequestParam String password) {
//         log.info("Registering user: {}", username);
//         User user = new User();
//         user.setUsername(username);
//         user.setPassword(password);
//         userService.createUser(user);
//         return ResponseEntity.ok(Map.of("message", "User registered successfully", "username", user.getUsername()));
//     }
// }