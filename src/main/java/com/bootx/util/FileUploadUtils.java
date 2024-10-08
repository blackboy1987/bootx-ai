package com.bootx.util;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class FileUploadUtils {

    public static void upload(String content,String path){
        String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
        String bucketName = "bootx-video";

        OSS ossClient = new OSSClientBuilder().build(endpoint, "LTAI5tAy4RgkiwXmdj8jeCSK", "xK3mKmO6QurmjPtyO6DEkIu1KdqbMs");
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, path, new ByteArrayInputStream(content.getBytes()));
            PutObjectResult result = ossClient.putObject(putObjectRequest);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }


    public static String uploadNet(String url,String path){
        String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
        String bucketName = "bootx-video";
        OSS ossClient = new OSSClientBuilder().build(endpoint, "LTAI5tAy4RgkiwXmdj8jeCSK", "xK3mKmO6QurmjPtyO6DEkIu1KdqbMs");
        try {
            InputStream inputStream = new URL(url).openStream();
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, path, inputStream);
            // 创建PutObject请求。
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            return "https://bootx-video.oss-cn-hangzhou.aliyuncs.com/"+path;
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException | IOException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return null;
    }
}   