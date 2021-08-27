package com.zxf.common.utils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 顺序读写操作工具类
 *
 * @author zhuxiaofeng
 * @date 2021/8/27
 */
public class SequentialAccessUtil {

    /**
     * 顺序写入文件
     *
     * @param filePath 文件路径
     * @param content 内容
     * @param index 定位
     * @return 位置
     */
    public static MappedByteBuffer fileWrite(String filePath, String content, int index) {
        File file = new File(filePath);
        RandomAccessFile randomAccessTargetFile;
        MappedByteBuffer map;
        try {
            randomAccessTargetFile = new RandomAccessFile(file, "rw");
            FileChannel targetFileChannel = randomAccessTargetFile.getChannel();
            map = targetFileChannel.map(FileChannel.MapMode.READ_WRITE, 0, (long) 1024 * 1024 * 1024);
            map.position(index);
            map.put(content.getBytes());
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return null;
    }

    /**
     * 顺序读文件
     *
     * @param filePath 文件位置
     * @param index 定位
     * @return 内容
     */
    public static String fileRead(String filePath, long index) {
        File file = new File(filePath);
        RandomAccessFile randomAccessTargetFile;
        MappedByteBuffer map;
        try {
            randomAccessTargetFile = new RandomAccessFile(file, "rw");
            FileChannel targetFileChannel = randomAccessTargetFile.getChannel();
            map = targetFileChannel.map(FileChannel.MapMode.READ_WRITE, 0, index);
            byte[] byteArr = new byte[10 * 1024];
            map.get(byteArr, 0, (int) index);
            return new String(byteArr);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return "";
    }

}
