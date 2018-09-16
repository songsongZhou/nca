package com.tobi.nca.utils.weixin;

import net.sf.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.UUID;

/**
 * 获取access_token
 * resources 下面的 WxTokenUtil.properties中存储了 相关配置信息
 * 7200秒内从缓存文件中读取，否则重新调取接口获取
 *
 */
public class AccessTokenUtil {


    public synchronized static String getSignature(String noncestr,String timestamp,String url){
        String str="jsapi_ticket=" +getTicket()+
                "&noncestr=" +noncestr+
                "&timestamp=1414587457" +timestamp+
                "&url="+url;
        //sha1加密
        System.out.println("生成签名的字符串："+str);
        String signature = SHA1(str);
        System.out.println("signature:"+signature);
        return signature;

    }

    public synchronized  static String getTicket(){

        String url="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" +getAccessToken()
                +"&type=jsapi";
        JSONObject jsonObject=httpRequest(url);
        String ticket=jsonObject.getString("ticket");
        System.out.println("ticket>>"+ticket);
        return ticket;

    }

    //synchronized static可以防止同时被多实例化
    public synchronized static String getAccessToken() {
        //保存access_token文件名字
        String FileName = "WxTokenUtil.properties";
        try {
            // 从文件中获取token值及时间
            Properties prop = new Properties();// 属性集合对象
            //获取文件流
            InputStream fis = AccessTokenUtil.class.getClassLoader().getResourceAsStream(FileName);
            prop.load(fis);// 将属性文件流装载到Properties对象中
            fis.close();// 关闭流
            //获取Appid，APPsecret
            String APPID = prop.getProperty("APPID");
            String APPSECRET = prop.getProperty("APPSECRET");
            // 获取accesstoken，初始值为空，第一次调用之后会把值写入文件
            String access_token = prop.getProperty("access_token");
            String expires_in = prop.getProperty("expires_in");
            String last_time = prop.getProperty("last_time");

            int int_expires_in = 0;
            long long_last_time = 0;


            int_expires_in = Integer.parseInt(expires_in);
            long_last_time = Long.parseLong(last_time);

            //得到当前时间
            long current_time = System.currentTimeMillis();

            // 每次调用，判断expires_in是否过期，如果token时间超时，重新获取，expires_in有效期为7200
            if ((current_time - long_last_time) / 1000 >= int_expires_in) {
                //获取token url
                String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                        + APPID + "&secret=" + APPSECRET;
                //发送http请求得到json流
                JSONObject jobject = httpRequest(url);
                //从json流中获取access_token
                String j_access_token = (String) jobject.get("access_token");
                String j_expires_in = jobject.get("expires_in")+"";

                //保存access_token
                if (j_access_token != null && j_expires_in != null) {
                    prop.setProperty("access_token", j_access_token);
                    prop.setProperty("expires_in", j_expires_in);
                    prop.setProperty("last_time", System.currentTimeMillis() + "");

                    URL url_ = AccessTokenUtil.class.getClassLoader().getResource(FileName);
                    FileOutputStream fos = new FileOutputStream(new File(url_.toURI()));
                    prop.store(fos, null);
                    fos.close();// 关闭流
                }
                //如果已经过期返回获取到的access_token
                System.out.println("access_token已过期，重新获取");
                return j_access_token;
            } else {
                //如果没有过期，返回从文件中读取的access_token
                System.out.println("access_token未过期");
                return access_token;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    // 获取accesstoken
    public synchronized static JSONObject httpRequest(String requestUrl) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
                    .openConnection();

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod("GET");

            httpUrlConn.connect();

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(
                    inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(
                    inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public static String SHA1(String decript) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
