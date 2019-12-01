package com.sdu.fund.common.util;

import com.sdu.fund.common.enums.RequestMethodEnum;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.sdu.fund.common.enums.RequestMethodEnum.GET;

/**
 * @program: fundproduct
 * @description: http工具类
 * @author: anonymous
 * @create: 2019-11-23 22:10
 **/
public class HttpUtil {

    private static final CloseableHttpClient httpclient = HttpClients.createDefault();

    public static String send(String url, RequestMethodEnum requestMethodEnum) {
        try {
            switch (requestMethodEnum) {
                case GET:
                    return sendGet(url);
                case POST:
                    return sendPost(url);
                default:
                    throw new IllegalStateException("Unexpected value: " + requestMethodEnum);
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String send(String url, RequestMethodEnum requestMethodEnum, Map<String, String> map) {
        try {
            switch (requestMethodEnum) {
                case GET:
                    return sendGet(url);
                case POST:
                    return sendPost(url, map);
                default:
                    throw new IllegalStateException("Unexpected value: " + requestMethodEnum);
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发送HttpGet请求
     *
     * @param url
     * @return
     */
    private static String sendGet(String url) {

        HttpGet httpget = new HttpGet(url);
        httpget.addHeader("Content-Type", "application/json; charset=utf-8");
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpget);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        String result = null;
        try {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 发送HttpPost请求，参数为map
     *
     * @param url
     * @param map
     * @return
     */
    private static String sendPost(String url, Map<String, String> map) {
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
        HttpPost httppost = new HttpPost(url);
        httppost.addHeader("Content-Type", "application/json; charset=utf-8");
        httppost.setEntity(entity);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httppost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity entity1 = response.getEntity();
        String result = null;
        try {
            result = EntityUtils.toString(entity1, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 发送不带参数的HttpPost请求
     *
     * @param url
     * @return
     */
    private static String sendPost(String url) {
        HttpPost httppost = new HttpPost(url);
        httppost.addHeader("Content-Type", "application/json; charset=utf-8");
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httppost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity entity = response.getEntity();
        String result = null;
        try {
            result = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }
}
