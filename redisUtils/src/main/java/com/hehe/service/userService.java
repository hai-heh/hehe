package com.hehe.service;

import com.hehe.bean.User;
import com.hehe.mapper.userMapper;
import com.hehe.utils.ExcelUtil;
import com.hehe.utils.redisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class userService {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private redisUtils redisUtils;

    @Autowired
    private userMapper userMapper;

    /**
     * 根据id查询
     * @param user
     * @return
     */
   public User findById(User user){
       String id=String.valueOf(user.getId());
       Object o;
       if ((o=redisUtils.get(id))!=null){
           logger.info("缓存中");
       }else {
           o = userMapper.findById(id);
           redisUtils.set(id, o, 60, TimeUnit.MINUTES);
       }
       return (User) o;
   }

    /**
     * 文件导入
     * @param file
     */
    public void upload(MultipartFile file) {
        try {
            List<User> list = ExcelUtil.upload(User.class, file.getInputStream());
            list.stream().forEach(item->{
                if(userMapper.findById(String.valueOf(item.getId()))==null) {
                    userMapper.insertUser(item);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件导出
     */
    public void download(String path) {
        List<User> list=new ArrayList<>();
        list.add(userMapper.findById(2+""));
        File file=new File(path);
        System.out.println(file.getAbsolutePath());
        try {
            ExcelUtil.download(User.class,new FileOutputStream(path),list);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
