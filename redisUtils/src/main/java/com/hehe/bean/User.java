package com.hehe.bean;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

public class User extends BaseRowModel {
    @ExcelProperty(value = "id",index = 0)
    private int id;
    @ExcelProperty(value = "userName",index = 1)
    private String userName;

    @ExcelProperty(value = "passWord",index = 2)
    private String passWord;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
