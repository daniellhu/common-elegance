package com.yonyou.cloud.common.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpFileUtil {
	
private static Logger logger = LoggerFactory.getLogger(HttpFileUtil.class);

    public static String postMedia(String url,String filePath,String filename) throws Exception {
        CloseableHttpClient client = null;
        CloseableHttpResponse resp = null;
        try {
            client = HttpClients.createDefault();
            HttpPost post = new HttpPost(url);
//            FileBody fb = new FileBody(new File(filePath));
            
            ByteArrayBody byteArrayBody = new ByteArrayBody(getFileByteArray(filePath),filename);
            //文件上传 -基于表单的形式 写法
            HttpEntity reqEntity = MultipartEntityBuilder
                        .create().addPart("media", byteArrayBody).build();
            post.setEntity(reqEntity);
            resp = client.execute(post);
            int sc = resp.getStatusLine().getStatusCode();
            if(sc>=200&&sc<300) {
                String result = EntityUtils.toString(resp.getEntity());
                return result;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        } finally {

            try {
                if(client!=null) {
                		client.close();
                }
            } catch (IOException e) {
            }
            try {
                if(resp!=null) { 
                		resp.close();
                }
            } catch (IOException e) {
            }

            
            
        }
        return null;
    }
    public static String postMedia(String url,String fileUrl) throws Exception {
        CloseableHttpClient client = null;
        CloseableHttpResponse resp = null;
        try {
            client = HttpClients.createDefault();
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(60*1000).setConnectTimeout(120*1000).build();
            HttpPost post = new HttpPost(url);
            post.setConfig(requestConfig);
//            FileBody fb = new FileBody(new File(filePath));
            
            ByteArrayBody byteArrayBody = new ByteArrayBody(getFileByteArray(fileUrl),"tempJpg.jpg");
            //文件上传 -基于表单的形式 写法
            HttpEntity reqEntity = MultipartEntityBuilder
                        .create().addPart("image", byteArrayBody).build();
            post.setEntity(reqEntity);
            resp = client.execute(post);
            int sc = resp.getStatusLine().getStatusCode();
            if(sc>=200&&sc<300) {
                String result = EntityUtils.toString(resp.getEntity());
                return result;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        } finally {

            try {
                if(client!=null) {
                		client.close();
                }
            } catch (IOException e) {
            }
            try {
                if(resp!=null) {
                		resp.close();
                }
            } catch (IOException e) {
            }

            
            
        }
        return null;
    }
    
    public static byte[] getFileByteArray(String filePath) throws Exception {
        InputStream inputStream = null;
        ByteArrayOutputStream output = null;
        try {
            URL urlTemp = new URL(filePath);
            HttpURLConnection httpConn = (HttpURLConnection) urlTemp.openConnection();

            httpConn.connect();
            inputStream = httpConn.getInputStream();

            output = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int n = 0;
            while (-1 != (n = inputStream.read(buffer))) {
                output.write(buffer, 0, n);
            }
            return output.toByteArray();
        } catch (Exception e) {
        	logger.info("---url="+filePath+e.getMessage());
            logger.error(e.getMessage());
            throw e;

        } finally {
            try {
                if (inputStream != null) {
                		inputStream.close();
                }
            } catch (Exception e) {
            }
            try {
                if (output != null) {
                		output.close();
                }
            } catch (Exception e) {

            }

        }

    }

}
