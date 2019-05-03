package com.example.utils;

import com.example.dto.GenerateInput;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class TemplateUtil {

    private static final Logger log = LoggerFactory.getLogger("adminLogger");

    public static String getTemplete(String fileName){
        return FileUtil.getText(TemplateUtil.class.getClassLoader().getResourceAsStream("generate/"+fileName));
    }
    /**@ClassName saveJava
     *@Description:     bean的model编写
     *@Data 2019/4/9
     *Author censhaojie
     */
    public static void saveJava(GenerateInput input){
        String path = input.getPath();
        String beanPackageName = input.getBeanPackageName();
        String beanName = input.getBeanName();
        List<String> beanFieldName = input.getBeanFieldName();
        List<String> beanFieldType = input.getBeanFieldType();
        List<String> beanFieldValue = input.getBeanFieldValue();

        String text = getTemplete("java.txt");
        text = text.replace("{beanPackageName}",beanPackageName).replace("{beanName}",beanName);

        String imports = "";
        if(beanFieldType.contains(BigDecimal.class.getSimpleName())){
            imports += "import" + BigDecimal.class.getName() + ";\n";
        }
        if(beanFieldType.contains(Date.class.getSimpleName())){
            imports += "import" + Date.class.getName() + ";";//////***********
        }
        text.replace("{import}",imports);

        String fields = getFields(beanFieldName,beanFieldType,beanFieldValue);
        text.replace("{filelds}",fields);

        String getset = getset(beanFieldName,beanFieldType);
        text.replace("{getset}",getset);

        FileUtil.saveTextFile(text,path+ File.separator+getPackagePath(beanPackageName)+beanName+".java");
        log.debug("生成java model ：{}模板",beanName);
    }

    /**@ClassName getFields
     *@Description:       编写bean的属性声明
     *@Data 2019/4/8
     *Author censhaojie
     */
    private static String getFields(List<String> beanFieldName, List<String> beanFieldType, List<String> beanFieldValue) {
        StringBuffer buffer = new StringBuffer();
        int size = beanFieldName.size();
        for (int i = 0; i < size; i++){
            String name = beanFieldName.get(i);
            if("id".equals(name) || "createTime".equals(name) ||"updateTime".equals(name)){
                continue;
            }
            String type = beanFieldType.get(i);
            buffer.append("\t private").append(type).append("  ").append(name);
            buffer.append(";\n");
        }
        return buffer.toString();
    }

    /**@ClassName getset
     *@Description:     编写bean的get 和 set
     *@Data 2019/4/8
     *Author censhaojie
     */
    private static String getset(List<String> beanFieldName, List<String> beanFieldType) {
        StringBuffer buffer = new StringBuffer();//线程安全
        int size = beanFieldName.size();
        for (int i = 0;i < size ;i++){
            String name = beanFieldName.get(i);
            if("id".equals(name)||"createTime".equals(name)||"updateTime".equals(name)){
                continue;
            }
            String type = beanFieldType.get(i);
            buffer.append("\tpublic ").append(type).append(" get")
                    .append(StringUtils.substring(name, 0, 1).toUpperCase() + name.substring(1, name.length()))
                    .append("() {\n");
            buffer.append("\t\treturn ").append(name).append(";\n");
            buffer.append("\t}\n");
            buffer.append("\tpublic void set")
                    .append(StringUtils.substring(name, 0, 1).toUpperCase() + name.substring(1, name.length()))
                    .append("(").append(type).append(" ").append(name).append(") {\n");
            buffer.append("\t\tthis.").append(name).append(" = ").append(name).append(";\n");
            buffer.append("\t}\n");
        }
        return buffer.toString();
    }


    /**@ClassName getPackagePath
     *@Description:       设置好路径
     *@Data 2019/4/9
     *Author censhaojie
     */
    private static String getPackagePath(String packageName) {
        String packagePath = packageName.replace(".", "/");
        if (!packagePath.endsWith("/")) {
            packagePath = packagePath + "/";
        }

        return packagePath;
    }


    /**@ClassName savaJavaDao
     *@Description:       生成dao和mapper模板
     *@Data 2019/4/9
     *Author censhaojie
     */
    public static void savaJavaDao(GenerateInput input){
        String path = input.getPath();
        String tableName = input.getTableName();
        String beanPackageName = input.getBeanPackageName();
        String beanName = input.getBeanName();
        String daoPackageName = input.getDaoPackageName();
        String daoName = input.getDaoName();

        String text = getTemplete("dao.txt");
        text = text.replace("{daoPackageName}",daoPackageName);
        text = text.replace("{beanPackageName}",beanPackageName);
        text = text.replace("{daoName}",daoName);
        text = text.replace("{table_name}",tableName);
        text = text.replace("{beanName}",beanName);
        text = text.replace("{beanParamName}",lowerFristChar(beanName));

        String insertColumns = getInsertColumns(input.getColumnNames());
        text = text.replace("{insert_columns}",insertColumns);
        String insertValues = getInsertValues(input.getColumnNames(),input.getBeanFieldName());
        text = text.replace("{insert_values}",insertValues);
        FileUtil.saveTextFile(text,path + File.separator +getPackagePath(daoPackageName) + daoName +".java");
        log.debug("生成java dao：{}模板", beanName);

        text = getTemplete("mapper.xml");
        text = text.replace("{daoPackageName}",daoPackageName);
        text = text.replace("{daoName}",daoName);
        text = text.replace("{table_name}",tableName);
        text = text.replace("{beanName}",beanName);
        String sets =getUpdateSets(input.getColumnNames(),input.getBeanFieldName());
        text = text.replace("{update_set}",sets);
        String where = getWhere(input.getColumnNames(),input.getBeanFieldName());
        text = text.replace("{where}",where);
        FileUtil.saveTextFile(text,path + File.separator + beanName+"Mapper.xml");
        log.debug("生成java Mapper：{}模板", beanName);

    }
    /**@ClassName lowerFristChar
     *@Description:     变量名
     *@Data 2019/4/9
     *Author censhaojie
     */
    private static String lowerFristChar(String beanName){
        String name = StrUtil.str2hump(beanName);
        String firstChar = name.substring(0,1);
        name = name.replaceFirst(firstChar,firstChar.toLowerCase());
        return name;
    }


    /**@ClassName getInsertColumns
     *@Description:       获取需要insert的字段
     *@Data 2019/4/9
     *Author censhaojie
     */
    private static String getInsertColumns(List<String> columnNames){
        StringBuffer buffer = new StringBuffer();
        int size = columnNames.size();
        for(int i = 0; i < size ;i++){
            String column = columnNames.get(i);
            if(!"id".equals(column)){
                buffer.append(column).append(",  ");
            }
        }
        String insertColumns = StringUtils.substringBeforeLast(buffer.toString(),",");
        return insertColumns;
    }


    /**@ClassName getInsertValues
     *@Description:    获取需要insert的values
     *@Data 2019/4/9
     *Author censhaojie
     */
    private static String getInsertValues(List<String> columnNames,List<String> beanFieldName){
        StringBuffer buffer = new StringBuffer();
        int size = columnNames.size();
        for(int i = 0;i<size;i++){
            String column = columnNames.get(i);
            if(!"id".equals(column)){
                buffer.append("#{").append(beanFieldName.get(i)).append("},   ");
            }
        }
        String insertValue = StringUtils.substringBeforeLast(buffer.toString(),",");
        return insertValue;
    }


    /**@ClassName getUpdateSets
     *@Description:     设置update的 set
     *@Data 2019/4/9
     *Author censhaojie
     */
    private static String getUpdateSets(List<String> columnNames,List<String> beanFieldName){
        StringBuffer buffer = new StringBuffer();
        int size = columnNames.size();
        for (int i = 0; i < size; i++) {
            String column = columnNames.get(i);
            if (!"id".equals(column)) {
                buffer.append("\t\t\t<if test=\"" + column + " != null\">\n");
                buffer.append("\t\t\t\t" + column).append(" = ").append("#{").append(beanFieldName.get(i))
                        .append("}, \n");
                buffer.append("\t\t\t</if>\n");
            }
        }
        return buffer.toString();
    }

    /**@ClassName getWhere
     *@Description:    获取update的where里面的判断
     *@Data 2019/4/9
     *Author censhaojie
     */
    private static String getWhere(List<String> columnNames, List<String> beanFieldName) {
        StringBuffer buffer = new StringBuffer();
        int size = columnNames.size();
        for (int i = 0; i < size; i++) {
            String column = columnNames.get(i);
            buffer.append("\t\t\t<if test=\"params." + column + " != null and params." + column + " != ''\">\n");
            buffer.append("\t\t\t\tand " + column).append(" = ").append("#{params.").append(beanFieldName.get(i))
                    .append("} \n");
            buffer.append("\t\t\t</if>\n");
        }

        return buffer.toString();
    }

    /**@ClassName saveController
     *@Description:       生成controller模板
     *@Data 2019/4/9
     *Author censhaojie
     */
    public static void saveController(GenerateInput input) {
        String path = input.getPath();
        String beanPackageName = input.getBeanPackageName();
        String beanName = input.getBeanName();
        String daoPackageName = input.getDaoPackageName();
        String daoName = input.getDaoName();

        String text = getTemplete("controller.txt");
        text = text.replace("{daoPackageName}", daoPackageName);
        text = text.replace("{beanPackageName}", beanPackageName);
        text = text.replace("{daoName}", daoName);
        text = text.replace("{daoParamName}", lowerFristChar(daoName));
        text = text.replace("{beanName}", beanName);
        text = text.replace("{beanParamName}", lowerFristChar(beanName));
        text = text.replace("{controllerPkgName}", input.getControllerPkgName());
        text = text.replace("{controllerName}", input.getControllerName());

        FileUtil.saveTextFile(text, path + File.separator + getPackagePath(input.getControllerPkgName())
                + input.getControllerName() + ".java");
        log.debug("生成controller：{}模板", beanName);
    }

    /**@ClassName saveHtmlList
     *@Description: 生成html的三个页面
     *@Data 2019/4/9
     *Author censhaojie
     */
    public static void saveHtmlList(GenerateInput input) {
        String path = input.getPath();
        String beanName = input.getBeanName();
        String beanParamName = lowerFristChar(beanName);

        String text = getTemplete("htmlList.txt");
        text = text.replace("{beanParamName}", beanParamName);
        text = text.replace("{beanName}", beanName);
        List<String> beanFieldNames = input.getBeanFieldName();
        text = text.replace("{columnsDatas}", getHtmlColumnsDatas(beanFieldNames));
        text = text.replace("{ths}", getHtmlThs(beanFieldNames));

        FileUtil.saveTextFile(text, path + File.separator + beanParamName + "List.html");
        log.debug("生成查询页面：{}模板", beanName);

        text = getTemplete("htmlAdd.txt");
        text = text.replace("{beanParamName}", beanParamName);
        text = text.replace("{addDivs}", getAddDivs(beanFieldNames));
        FileUtil.saveTextFile(text, path + File.separator + "add" + beanName + ".html");
        log.debug("生成添加页面：{}模板", beanName);

        text = getTemplete("htmlUpdate.txt");
        text = text.replace("{beanParamName}", beanParamName);
        text = text.replace("{addDivs}", getAddDivs(beanFieldNames));
        text = text.replace("{initData}", getInitData(beanFieldNames));
        FileUtil.saveTextFile(text, path + File.separator + "update" + beanName + ".html");
        log.debug("生成修改页面：{}模板", beanName);
    }

    /**@ClassName getInitData
     *@Description:       更新页面赋初始值
     *@Data 2019/4/9
     *Author censhaojie
     */
    private static CharSequence getInitData(List<String> beanFieldNames) {
        StringBuilder builder = new StringBuilder();
        beanFieldNames.forEach(b -> {
            builder.append("\t\t\t\t\t\t$('#" + b + "').val(data." + b + ");\n");
        });

        return builder.toString();
    }

    /**@ClassName getAddDivs
     *@Description:       页面显示选项
     *@Data 2019/4/9
     *Author censhaojie
     */
    private static String getAddDivs(List<String> beanFieldNames) {
        StringBuilder builder = new StringBuilder();
        beanFieldNames.forEach(b -> {
            if (!"id".equals(b) && !"createTime".equals(b) && !"updateTime".equals(b)) {
                builder.append("\t\t\t<div class='form-group'>\n");
                builder.append("\t\t\t\t<label class='col-md-2 control-label'>" + b + "</label>\n");
                builder.append("\t\t\t\t<div class='col-md-10'>\n");
                builder.append("\t\t\t\t\t<input class='form-control' placeholder='" + b + "' type='text' name='" + b
                        + "' id='" + b + "' data-bv-notempty='true' data-bv-notempty-message='" + b + " 不能为空'>\n");
                builder.append("\t\t\t\t</div>\n");
                builder.append("\t\t\t</div>\n");
            }
        });
        return builder.toString();
    }

    /**@ClassName getHtmlThs
     *@Description:       列表项
     *@Data 2019/4/9
     *Author censhaojie
     */
    private static String getHtmlThs(List<String> beanFieldNames) {
        StringBuilder builder = new StringBuilder();
        beanFieldNames.forEach(b -> {
            builder.append("\t\t\t\t\t\t\t\t\t<th>{beanFieldName}</th>\n".replace("{beanFieldName}", b));
        });
        return builder.toString();
    }



    /**@ClassName getHtmlColumnsDatas
     *@Description: list表的columns 数据
     *@Data 2019/4/9
     *Author censhaojie
     */
    private static String getHtmlColumnsDatas(List<String> beanFieldNames) {
        StringBuilder builder = new StringBuilder();
        beanFieldNames.forEach(b -> {
            builder.append("\t\t\t\t{\"data\" : \"{beanFieldName}\", \"defaultContent\" : \"\"},\n"
                    .replace("{beanFieldName}", b));
        });

        builder.append("");
        return builder.toString();
    }

}
