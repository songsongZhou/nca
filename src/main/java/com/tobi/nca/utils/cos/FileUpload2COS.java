package com.tobi.nca.utils.cos;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.internal.COSDirectSpi;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;

import java.io.File;
import java.net.URL;
import java.util.Date;

public class FileUpload2COS {

    private static String SECRET_ID="AKIDHPJVYOJMVR9ew2id8RVLxMXXIsHSn9gy";
    private static String SECRET_KEY="DDzXpvoSqysX0MvIRL1byMXWHo6D7nER";
    private static String REGION_NAME="ap-shanghai";
    private static String BUCKET_NAME="nca-1253604429";
    private static String BASE_URL="https://nca-1253604429.cos.ap-shanghai.myqcloud.com/";

    public static String uploadImage(File file){
        COSClient cosClient=initCOS();
        // bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式
        String bucketName = BUCKET_NAME;
        String key = "goods-image/"+new Date().getTime() + ".jpg";
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
        cosClient.putObject(putObjectRequest);

        // 关闭客户端(关闭后台线程)
        cosClient.shutdown();
        //删除项目目录下的临时文件
        File del = new File(file.toURI());
        del.delete();


        return BASE_URL+key;
    }


    public static void delImage(String key){
        COSClient cosClient=initCOS();
        cosClient.deleteObject(BUCKET_NAME, key);
        // 关闭客户端(关闭后台线程)
        cosClient.shutdown();
    }

    private static COSClient initCOS() {
        COSCredentials cred = new BasicCOSCredentials(SECRET_ID, SECRET_KEY);
        ClientConfig clientConfig = new ClientConfig(new Region(REGION_NAME));
        COSClient cosClient = new COSClient(cred, clientConfig);

        return cosClient;
    }
}
