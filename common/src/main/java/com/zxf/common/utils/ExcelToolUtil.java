package com.zxf.common.utils;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;

/**
 * excel 表格生成工具类-----支持导出功能这类需求，更复杂的excel自己去写😀
 *
 * @author zhuxiaofeng
 * @date 2021/9/29
 */
public class ExcelToolUtil {

    /**
     * 生成Excel file
     * 单个sheet的最大行数为 65536
     *
     * @param data 数据
     * @param fieldMapping 别名字段映射
     * @param fileName 生成文件名称
     * @param fileLocation 生成文件位置
     * @param outputAliasField 是否只输出别名字段 true 是， false 全输出
     * @return file
     */
    public static File produceExcelFile(Collection<?> data, Map<String, String> fieldMapping, String fileName,
                                        String fileLocation, boolean outputAliasField) throws java.io.FileNotFoundException {
        File file = new File(fileLocation + File.separator + fileName);
        ExcelWriter writer = ExcelUtil.getWriter();
        fieldMapping.forEach(writer::addHeaderAlias);
        writer.setOnlyAlias(outputAliasField);
        writer.write(data);
        OutputStream outputStream = new FileOutputStream(file, true);
        BufferedOutputStream bos = new BufferedOutputStream(outputStream);
        //try(IO)会自动关闭流
        writer.flush(bos, true);
        writer.close();
        return file;
    }

    /**
     * 生成Excel file
     * 默认只保留别名(fieldMapping)字段
     *
     * @param data 数据
     * @param fieldMapping 别名字段映射
     * @param fileName 生成文件名称
     * @param fileLocation 生成文件位置
     * @return file
     */
    public static File produceExcelFile(Collection<?> data, Map<String, String> fieldMapping, String fileName, String fileLocation)
            throws FileNotFoundException {
        return produceExcelFile(data, fieldMapping, fileName, fileLocation, true);
    }

    /**
     * 生成excel file
     * 此excel 单个sheet行数为1048576
     *
     * @param data 数据
     * @param fieldMapping 别名字段映射
     * @param fileName 生成文件名称
     * @param fileLocation 生成文件位置
     * @param outputAliasField 是否只输出别名字段 true 是， false 全输出
     * @return file
     */
    public static File produceExcelFileOverMillion(Collection<?> data, Map<String, String> fieldMapping, String fileName,
                                                   String fileLocation, boolean outputAliasField) throws FileNotFoundException {
        File file = new File(fileLocation + File.separator + fileName);
        ExcelWriter writer = ExcelUtil.getBigWriter();
        fieldMapping.forEach(writer::addHeaderAlias);
        writer.setOnlyAlias(outputAliasField);
        writer.write(data);
        OutputStream outputStream = new FileOutputStream(file, true);
        BufferedOutputStream bos = new BufferedOutputStream(outputStream);
        //try(IO)会自动关闭流
        writer.flush(bos, true);
        writer.close();
        return file;
    }

    /**
     * 生成Excel file
     * 默认只保留别名(fieldMapping)字段
     *
     * @param data 数据
     * @param fieldMapping 别名字段映射
     * @param fileName 生成文件名称
     * @param fileLocation 生成文件位置
     * @return file
     */
    public static File produceExcelFileOverMillion(Collection<?> data, Map<String, String> fieldMapping, String fileName, String fileLocation)
            throws FileNotFoundException {
        return produceExcelFileOverMillion(data, fieldMapping, fileName, fileLocation, true);
    }

}
