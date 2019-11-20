package com.util;

import ch.qos.logback.core.db.dialect.DBUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.util.*;
import java.util.Map.Entry;

/**
 * 利用HttpClient进行post请求的工具类
 */
public class HttpClientUtil {

    private static Log log = LogFactory.getLog(HttpClientUtil.class);

    private static CloseableHttpClient httpClient = null;

    public static final String WXPAYSDK_VERSION = "WXPaySDK/3.0.9";
    public static final String USER_AGENT = WXPAYSDK_VERSION +
            " (" + System.getProperty("os.arch") + " " + System.getProperty("os.name") + " " + System.getProperty("os.version") +
            ") Java/" + System.getProperty("java.version") + " HttpClient/" + HttpClient.class.getPackage().getImplementationVersion();

    /**
     * 最大连接数
     */
    public final static int MAX_TOTAL_CONNECTIONS = 600;
    /**
     * 每个路由最大连接数
     */
    public final static int MAX_ROUTE_CONNECTIONS = 300;

    /**
     * 获取连接的最大等待时间
     */
    public final static int WAIT_TIMEOUT = 30000;
    /**
     * 连接超时时间
     */
    public final static int CONNECT_TIMEOUT = 30000;
    /**
     * 读取超时时间
     */
    public final static int READ_TIMEOUT = 60000;

    private static RequestConfig getConfig() {
        RequestConfig requestConfig = RequestConfig.custom()
            .setConnectionRequestTimeout(WAIT_TIMEOUT).setConnectTimeout(CONNECT_TIMEOUT)
            .setSocketTimeout(READ_TIMEOUT).build();
        return requestConfig;
    }


    private final static PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();

    static {
        poolingHttpClientConnectionManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);
    }

    public static CloseableHttpClient getHttpClient() {
        if (httpClient == null) {
            return HttpClientBuilder.create().setConnectionManager(poolingHttpClientConnectionManager).build();
        }
        return httpClient;
    }

    public static String doPost(String url, Map<String, String> map,
                                String charset) {
        HttpPost httpPost = null;
        String result = null;
        try {
            httpClient = getHttpClient();
            httpPost = new HttpPost(url);
            httpPost.setConfig(getConfig());

            // 设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator<Entry<String, String>> iterator = map.entrySet()
                .iterator();
            while (iterator.hasNext()) {
                Entry<String, String> elem = (Entry<String, String>) iterator
                    .next();
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }
            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,
                    charset);
                httpPost.setEntity(entity);
                log.info("发送报文：" + EntityUtils.toString(entity, charset));
            }
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                    log.info("返回报文：" + URLDecoder.decode(result, charset));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * 发送报文
     *
     * @param url
     * @param text
     * @param charset
     * @return
     */
    public static String doPost(String url, String text, String charset) {
        return doPost(url, text, charset, null);
    }

    public static String doPost(String url, String text, String charset, String payChannel) {
        HttpPost httpPost = null;
        String result = null;
        try {
            httpClient = getHttpClient();
            httpPost = new HttpPost(url);
            httpPost.setConfig(getConfig());

            // 设置报文内容
            StringEntity entity = new StringEntity(text);
            entity.setContentEncoding(charset);
            httpPost.setEntity(entity);
            if(null != payChannel){
                httpPost.setHeader("Connection","close");
            }

            log.info("---------发送报文：" + text);
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception ex) {
            log.info(ex);
            ex.printStackTrace();
        }
        log.info("------------返回报文：" + result);
        return result;
    }

    public static String doPostLocal(String url, String text, String charset) {
        HttpPost httpPost = null;
        String result = null;
        try {
            httpClient = getHttpClient();
            httpPost = new HttpPost(url);
            httpPost.setConfig(getConfig());
            httpPost.setHeader("source", "local");

            // 设置报文内容
            StringEntity entity = new StringEntity(text, charset);
            entity.setContentEncoding(charset);
            entity.setContentType("application/xml;charset=utf-8");
            httpPost.setEntity(entity);

            log.info("---------发送报文：" + text);
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        log.info("------------返回报文：" + result);
        return result;
    }

    /**
     * 发送报文
     *
     * @param url
     * @param charset
     * @return
     */
    public static String doPost(String url, String charset) {
        HttpPost httpPost = null;
        String result = null;
        try {
            httpClient = getHttpClient();
            httpPost = new HttpPost(url);
            httpPost.setConfig(getConfig());
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        log.info("------------返回报文：" + result);
        return result;
    }

    public static String requestOnce(String url, String data,String mchid) throws Exception {
        BasicHttpClientConnectionManager connManager;

        connManager = new BasicHttpClientConnectionManager(
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.getSocketFactory())
                        .register("https", SSLConnectionSocketFactory.getSocketFactory())
                        .build(),
                null,
                null,
                null
        );


        HttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(connManager)
                .build();

        HttpPost httpPost = new HttpPost(url);

        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(READ_TIMEOUT).setConnectTimeout(CONNECT_TIMEOUT).build();
        httpPost.setConfig(requestConfig);

        StringEntity postEntity = new StringEntity(data, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.addHeader("User-Agent", USER_AGENT + " " + mchid);
        httpPost.setEntity(postEntity);

        HttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity();
        return EntityUtils.toString(httpEntity, "UTF-8");

    }


    public static String requestOnce(String url,String data,String mch_id,int cancert) throws Exception {
        BasicHttpClientConnectionManager connManager;
            // 证书
            char[] password = mch_id.toCharArray();
            String path = "D:\\rmfloz1irxc1p18hgxeu1rh7sbn.p12";
            KeyStore ks = KeyStore.getInstance("PKCS12");
//            String currentPath = Thread.currentThread().getContextClassLoader().getResource(path).getPath();
            FileInputStream inputStream = new FileInputStream(new File(path));
            ks.load(inputStream, password);

            // 实例化密钥库 & 初始化密钥工厂
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, password);

            // 创建 SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());

            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                    sslContext,
                    new String[]{"TLSv1"},
                    null,
                    new DefaultHostnameVerifier());

            connManager = new BasicHttpClientConnectionManager(
                    RegistryBuilder.<ConnectionSocketFactory>create()
                            .register("http", PlainConnectionSocketFactory.getSocketFactory())
                            .register("https", sslConnectionSocketFactory)
                            .build(),
                    null,
                    null,
                    null
            );

        HttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(connManager)
                .build();

        HttpPost httpPost = new HttpPost(url);

        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(READ_TIMEOUT).setConnectTimeout(CONNECT_TIMEOUT).build();
        httpPost.setConfig(requestConfig);

        StringEntity postEntity = new StringEntity(data, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.addHeader("User-Agent", USER_AGENT + " " + mch_id);
        httpPost.setEntity(postEntity);

        HttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity();
        return EntityUtils.toString(httpEntity, "UTF-8");

    }

}