package com.example.urldemo.utils;

/**
 * @author yangyinhua
 */

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class ConnectChatGPTUtil {

    public static void connect(String content) {

        String adress = "1714564639@qq.com"; //邮箱地址
        String title = "标题"; //邮件标题
        String response = mail(adress, title, content);
        response = "{" + response.substring(response.indexOf("{") + 1, response.lastIndexOf("}")) + "}";
        HashMap<String, String> uniJson_ = uniJson(response, 5);
        System.out.println(uniJson_.get("Code"));
        System.out.println(uniJson_.get("ip"));
        System.out.println(uniJson_.get("adress"));
        System.out.println(uniJson_.get("title"));
        System.out.println(uniJson_.get("msg"));
        //输出
    }

    /*
     接口信息:
     接口名称    是否必填    接口类型    接口说明
     adress        是      string    发送地址
     title         是      string    发送标题
     content       是      string    发送内容


     返回参数:
     接口名称    接口类型    接口说明
     Code       string     1：发送成功，2：发送失败
     ip         string     发送服务器节点ip
     adress     string     发送邮件
     title      string     发送标题
     msg        string     发送内容


     {
     "Code":"1",
     "ip":"36.27.212.9",
     "adress":"15001904@qq.com",
     "title":"标题test",
     "msg":"内容test"
     }
     */
    public static String mail(String adress, String title, String content) {
        URL url = null;
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        StringBuffer sb = new StringBuffer();
        try {
            url = new URL("https://v.api.aa1.cn/api/mail/t/api.php?adress=" + adress + "&title=" + title + "&content=" + content);
            is = url.openStream();
            isr = new InputStreamReader(is, "utf-8");
            br = new BufferedReader(isr);
            char[] c = new char[1024];
            while (br.read(c) != -1) {
                final String ss = new String(c);
                sb.append(ss);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /*
     json1 {}括号内数据
     strSeq 遍历字符串序列
     json_time 解析次数
     */
    public static HashMap<String, String> uniJson(String json, int time) {
        int i = 0;//写入数据的次数
        int i1 = 0;//字符串长度
        boolean pattern = true; //当前模式,true为名称,false为数据
        String[] json_title = new String[time];
        String[] json_data = new String[time];
        String json1 = json.substring(json.indexOf("{") + 1, json.lastIndexOf("}"));
        String data = ""; //缓存数据
        for (char x : json1.toCharArray()) {
            i1++;
            switch (x) {
                case ':':
                    //将缓存数据写入数组，并切换模式
                    if (pattern || data.charAt(-1) == '"') {
                        json_title[i] = data;
                        data = "";
                        pattern = false;
                    }
                    if (!pattern) {
                        data = data + x;
                    }
                    break;
                case ',':
                    if (!pattern || data.charAt(-1) == '"') {
                        json_data[i] = data.substring(1);
                        data = "";
                        pattern = true;
                        i++;
                    }
                    break;
                default:
                    //模式3，结束
                    if (i1 == json1.length()) {
                        json_data[i] = data.substring(1) + '"';
                        data = "";
                        pattern = true;
                        i++;
                    } else { //模式2，写入缓存数据
                        data = data + x;
                    }
            }
        }
        HashMap<String, String> re = new HashMap<String, String>();
        // 添加键值对
        for (int i2 = 0; i2 < json_title.length; i2++) {//标准化输出字符串
            json_title[i2] = json_title[i2].substring(1, json_title[i2].length() - 1);
            json_data[i2] = json_data[i2].substring(1, json_data[i2].length() - 1);
            re.put(json_title[i2], json_data[i2]);
            if (i2 == json_title.length - 1) {
                return re;
            }
        }
        return null;
    }

}