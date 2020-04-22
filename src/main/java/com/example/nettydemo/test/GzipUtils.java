package com.example.nettydemo.test;

import ch.qos.logback.core.pattern.SpacePadder;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GzipUtils {
    public static byte[] gzip(byte[] data) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(bos);
        gzip.write(data);
        gzip.finish();
        gzip.close();
        byte[] ret = bos.toByteArray();
        bos.close();
        return ret;
    }
    public static byte[] unzip(byte[] data) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        GZIPInputStream gzip = new GZIPInputStream(bis);
        byte[] buf = new byte[1024 * 1024];
        int num = -1;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((num = gzip.read(buf, 0, buf.length)) != -1) {
            bos.write(buf, 0, num);
        }
        gzip.close();
        bis.close();
        byte[] ret = bos.toByteArray();
        bos.flush();
        bos.close();
        return ret;
    }
    public static void main(String[] args) throws IOException {
        String path = System.getProperty("user.dir") + File.separatorChar + "source" + File.separatorChar + "tree.jpg";
        File file = new File(path);
        FileInputStream is = new FileInputStream(file);
        byte[] bytes = new byte[is.available()];
        is.read(bytes);
        is.close();
        System.out.println("原始照片大小：" + bytes.length);
        byte[] ret1 = GzipUtils.gzip(bytes);
        System.out.println("压缩之后大小：" + ret1.length);
        byte[] ret2 = GzipUtils.unzip(ret1);
        System.out.println("解压之后大小：" + ret2.length);
        String path1 = System.getProperty("user.dir") + File.separatorChar + "source" + File.separatorChar + "tree11.jpg";
        File file1 = new File(path1);
        FileOutputStream os = new FileOutputStream(file1);
        os.write(bytes);
        os.close();
        System.out.println(path);
    }
}
