package com.hehe.controller;


import com.hehe.bean.User;
import com.hehe.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class userController {

    @Autowired
    userService userService;

    @GetMapping("/{id}")
    public User findById(@PathVariable("id") int id){
        User user=new User();
        user.setId(id);
        return userService.findById(user);
    }

    @PostMapping("upload")
    public String upload(MultipartFile file){
        userService.upload(file);
        return "上传成功";
    }


    @PostMapping("download")
    public String download(String path){
        userService.download(path);
        return "下载成功";
    }
}
