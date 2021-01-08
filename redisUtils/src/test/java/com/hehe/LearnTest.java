package com.hehe;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.hehe.bean.User;
import com.hehe.mapper.userMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LearnTest {

    @Autowired
   userMapper userMapper;
    @Test
    public void test(){
        InputStream stream= null ;
        List<User> list=new ArrayList<>();
        List<Object> read=null;
        try {
             stream = new FileInputStream("E:\\test.xlsx");
             read = EasyExcelFactory.read(stream, new Sheet(1,1,User.class));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        read.stream().forEach(item->{
            list.add((User) item);
        });
        list.stream().forEach(item->{
            if(userMapper.findById(String.valueOf(item.getId()))==null) {
                userMapper.insertUser(item);
            }
        });
    }

    @Test
    public void test1(){
        List<User> list=new ArrayList<>();
        list.add(userMapper.findById(2+""));
        OutputStream stream=null;
        ExcelWriter writer=null;
        try {
             stream=new FileOutputStream("E:\\test1.xlsx");
             writer = EasyExcelFactory.getWriter(stream);
            Sheet sheet=new Sheet(1,1,User.class);
            sheet.setSheetName("导出");
            writer.write(list,sheet);
            writer.finish();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


}