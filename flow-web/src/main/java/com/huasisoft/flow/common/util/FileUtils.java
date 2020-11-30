package com.huasisoft.flow.common.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Objects;

public class FileUtils {

    /*
     * Java文件操作 获取文件扩展名
     *
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            } 
        }
        return filename;
    }

    /*
     * Java文件操作 获取不带扩展名的文件名
     *
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    public static void channelFileReader(String filePath , OutputStream os) throws IOException {
        FileInputStream fileIn = null;
        FileChannel fileInChannel = null;
        WritableByteChannel fileOutChannel = null;
        try {
            fileIn = new FileInputStream(filePath);
            fileInChannel = fileIn.getChannel();
            fileOutChannel = Channels.newChannel(os);
            fileInChannel.transferTo(0,fileIn.available(),fileOutChannel);
        } finally {
            Objects.requireNonNull(fileIn).close();
            if (fileInChannel != null) {
                    fileInChannel.close();
            }
            if (fileOutChannel != null) {
                fileInChannel.close();
            }
        }
    }
}
