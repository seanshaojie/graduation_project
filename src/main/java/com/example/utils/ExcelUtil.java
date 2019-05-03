package com.example.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
/**@ClassName
 *@Description:       excel 的工具类
 *@Data 2019/4/6
 *Author censhaojie
 */

public class ExcelUtil {
    public static void excelLocal(String local, String filename, String[] header, List<Object[]> datas){

    }
/**@ClassName excelExport
 *@Description:       导出excel
 *@Data 2019/4/6
 *Author censhaojie
 */
    public static void excelExport(String filename, String[] headers, List<Object[]> datas,
                                   HttpServletResponse response){
        Workbook workbook = getWorkbook(headers,datas);
        if (workbook != null){
            ByteArrayOutputStream byteArrayOutputStream = null;

            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
                workbook.write(byteArrayOutputStream);
                String suffix = ".xls";
                response.setContentType("application/vnd.ms-excel;charset=utf-8");
                response.setHeader("Content-Disposition","attachment;filename="+new String((filename + suffix).getBytes(),"iso-8859-1"));
                OutputStream outputStream = response.getOutputStream();
                outputStream.write(byteArrayOutputStream.toByteArray());
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if(byteArrayOutputStream != null){
                        byteArrayOutputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**@ClassName getWorkbook
     *@Description:    创建xlxs 赋值和设置样式
     *@Data 2019/4/6
     *Author censhaojie
     */
    public static Workbook getWorkbook(String[] headers,List<Object[]> datas){
        Workbook workbook = new HSSFWorkbook();//创建一个excel文件
        Sheet sheet = workbook.createSheet();//创建一个excel 的sheet
        CellStyle style = workbook.createCellStyle();//设置样式
        Font font = workbook.createFont();//创建文字
        style.setAlignment(HorizontalAlignment.CENTER_SELECTION);//设置样式水平居中
        /*style.setVerticalAlignment(VerticalAlignment.CENTER);//水平居中*/
        Row row = null; //行
        Cell cell = null;//单元格
        int line = 0,maxColumn = 0;
        if(headers != null && headers.length > 0){
            row = sheet.createRow(line++);//在sheet中创建第一行
            row.setHeightInPoints(23);//设置行高
            font.setBold(true);//设置粗体
            font.setFontHeightInPoints((short)13);//设置字体大小
            style.setFont(font);
            maxColumn = headers.length;
            for (int i =0;i < maxColumn;i++){
                cell = row.createCell(i);//创建单元格
                cell.setCellValue(headers[i]);//设置单元格的值
                cell.setCellStyle(style);//设置单元格的样式
            }
        }
        if(datas != null && datas.size() >0){
            for (int index = 0,size = datas.size();index < size; index++){
                Object[] data = datas.get(index);
                if(data != null && data.length >0){
                    row = sheet.createRow(line++);
                    row.setHeightInPoints(20);
                    int length = data.length;
                    if(length > maxColumn){
                        maxColumn = length;
                    }
                    for (int i = 0;i<length;i++){
                        cell = row.createCell(i);
                        cell.setCellValue(data[i] == null? null:data[i].toString());
                    }
                }
            }
        }
        for (int i = 0;i < maxColumn;i++){
            sheet.autoSizeColumn(i);
        }
        return workbook;
    }
}
