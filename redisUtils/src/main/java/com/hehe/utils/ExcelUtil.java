package com.hehe.utils;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.hehe.bean.User;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {

    /**将excel文件变成集合
     * @param tClass 类型
     * @param inputStream 文件流
     * @param <T> 泛型参数
     * @return 返回集合
     */
   static public <T extends BaseRowModel> List<T> upload(Class<T> tClass, InputStream inputStream){
       List<T> list=new ArrayList<>();
       List<Object> read=null;
       read = EasyExcelFactory.read(inputStream, new Sheet(1,1, tClass));
       read.stream().forEach(item->{
           list.add((T) item);
       });
       return list;
   }

    /**
     * 将集合变为execl文件
     * @param tClass
     * @param outputStream
     * @param list
     * @param <T>
     */
   static public <T extends BaseRowModel> void download(Class<T> tClass, OutputStream outputStream,List<T> list){
       ExcelWriter writer = EasyExcelFactory.getWriter(outputStream);
       Sheet sheet=new Sheet(1,1,tClass);
       sheet.setSheetName("导出");
       writer.write(list,sheet);
       writer.finish();
   }

}
