package com.bootx;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class FfmpegExample {

    public static void main(String[] args) {

        // FFmpeg 命令
        String[] command = {
            "E:\\ffmpeg-7.0.1-essentials_build\\bin\\ffmpeg.exe",
            "-i", "E:/ffmpeg-7.0.1-essentials_build/bin/1.jpg",
            "-filter_complex",
            "\"[0:v]scale=w=1920:h=1920:force_original_aspect_ratio=increase,boxblur=40:5,crop=1920:1080[bg];[0:v]scale=-1:1080[fg];[bg][fg]overlay=(W-w)/2:(H-h)/2\"",
            "D:\\output.jpg"
        };

        System.out.println(StringUtils.join(command," "));
        try {
            // 创建 ProcessBuilder
            ProcessBuilder pb = new ProcessBuilder(command);
            
            // 启动进程
            Process process = pb.start();

            // 读取进程的输入流（标准输出和标准错误）
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            while ((line = errorReader.readLine()) != null) {
                System.err.println(line);
            }

            // 等待进程结束并获取退出码
            int exitCode = process.waitFor();
            System.out.println("FFmpeg exited with code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
