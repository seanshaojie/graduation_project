package com.example.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;

public class FileUtil {


    /**@ClassName saveFile
     *@Description:    保存文件
     *@Data 2019/4/16
     *Author censhaojie
     */
    public static String saveFile(MultipartFile file,String pathname){
        try {
            File tagetFile = new File(pathname);
            if(tagetFile.exists()){
                return pathname;
            }
            if(!tagetFile.getParentFile().exists()){
                tagetFile.getParentFile().mkdirs();
            }
            file.transferTo(tagetFile);
            return pathname;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**@ClassName deleteFile
     *@Description:       删除文件
     *@Data 2019/4/16
     *Author censhaojie
     */

    public static boolean deleteFile(String pathname){
        File file = new File(pathname);
        if(file.exists()){
            boolean flag = file.delete();
            if(flag){
                File[] files = file.getParentFile().listFiles();
                if(file == null || file.length() == 0){
                    file.getParentFile().delete();
                }
            }
            return flag;
        }
     return  false;
    }

    public static String fileMd5(InputStream inputStream){
        try {
            return DigestUtils.md5Hex(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getPath() {
        return "/" + LocalDate.now().toString().replace("-", "/") + "/";
    }


    /**@ClassName getText
     *@Description:       获取模板的信息
     *@Data 2019/4/8
     *Author censhaojie
     */
    public static String getText(InputStream inputStream){
        InputStreamReader isr = null;//实现从字节流到字符流的转换
        BufferedReader bufferedReader = null;//创建一个使用默认大小缓存区的缓冲字符输入流

        try {
            isr = new InputStreamReader(inputStream,"utf-8");
            bufferedReader = new BufferedReader(isr);
            StringBuilder builder = new StringBuilder();
            String string;
            while ((string = bufferedReader.readLine()) != null){
                string = string +"\n";
                builder.append(string);
            }
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bufferedReader != null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (isr != null){
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static void saveTextFile(String value,String path){
        FileWriter writer = null;
        try {
        File file = new File(path);

        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        writer = new FileWriter(file);
        writer.write(value);
        writer.flush();//意思是把缓冲区的内容强制的写出
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(writer != null){
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
