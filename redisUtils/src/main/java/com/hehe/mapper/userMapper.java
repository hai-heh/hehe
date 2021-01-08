package com.hehe.mapper;


import com.hehe.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface userMapper {
    @Select("select id,passWord,userName from user where id=#{id}")
    User findById(String id);

    @Update("update user set userName=#{userName} where id=#{id}")
    void updateById(User user);

    @Insert("insert user(id,userName,passWord) values(#{id},#{userName},#{passWord})")
    void insertUser(User user);
}
