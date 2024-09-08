package com.bootx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Work04 {
    static int count = 0;	// 初始化统计变量
    
    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\blackboy1987\\StudioProjects\\ai_flutter1\\lib");	// 需要查找的文件目录
        List<File> files =  new ArrayList<>();
        getTxtFilesCount(file,files);
        countLines(files);
    }

    private static void countLines(List<File> files) throws IOException {
        Long lineCount = 0L;
        for (File file : files) {
            lineCount += countLines1(file.getAbsolutePath());
        }
        System.out.println(lineCount);
    }

    public static int countLines1(String filePath) throws IOException {
        int lineCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while (reader.readLine() != null) {
                lineCount++;
            }
        }
        return lineCount;
    }

    /*
    * 方法名：getTxtFilesCount
    * 作用：统计.txt文件个数
    */
    public static List<File> getTxtFilesCount(File srcFile,List<File> list){
        // 判断传入的文件是不是为空
        if (srcFile == null) {
            throw new NullPointerException();
        }
        // 把所有目录、文件放入数组
        File[] files = srcFile.listFiles();
        // 遍历数组每一个元素
        for (File f : files) {
            // 判断元素是不是文件夹，是文件夹就重复调用此方法（递归）
            if (f.isDirectory()) {
                getTxtFilesCount(f,list);
            }else {
                // 判断文件是不是以.txt结尾的文件，并且count++（注意：文件要显示扩展名）
                if (f.getName().endsWith(".dart")) {
                    list.add(f);
                    count++;
                }
            }
        }
        // 返回.txt文件个数
        return list;
    }
}
