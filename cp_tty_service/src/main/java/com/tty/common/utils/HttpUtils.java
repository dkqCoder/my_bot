package com.tty.common.utils;

import com.alibaba.druid.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jdd.fm.core.log.LogExceptionStackTrace;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @author ln
 */
public class HttpUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * map转化
     *
     * @param srcPara
     * @return
     * @author wangmeng
     * @time 2014年12月16日下午4:17:12
     */
    @SuppressWarnings("rawtypes")
    public static Map getParameterMap(Map srcPara) {
        Map<String, Object> para = new HashMap<String, Object>();
        Iterator it = srcPara.keySet().iterator();
        String key;
        String[] strings;
        for (; it.hasNext(); ) {
            key = (String) it.next();
            strings = (String[]) srcPara.get(key);
            para.put(key, strings[0]);
        }
        return para;
    }

    /**
     * 输出json对象
     *
     * @param response
     * @param map
     * @author wangmeng
     * @time 2014年12月16日下午4:18:05
     */
    @SuppressWarnings("rawtypes")
    public static void responseMsg(HttpServletResponse response, Map map) {
        try {
            response.setContentType("text/html; charset=utf-8");
            PrintWriter out = response.getWriter();
            Gson gson = new GsonBuilder().serializeNulls().create();
            out.print(gson.toJson(map));
            out.flush();
            out.close();
        } catch (Exception e) { // NOSONAR
            e.printStackTrace(); // NOSONAR
        }
    }

    /**
     * tocken验证和签名验证
     *
     * @param paramsMap
     * @return
     * @author wangmeng
     * @time 2014年12月16日下午12:05:32
     */
    public static int validateParams(Map<String, Object> paramsMap) {
        int flag = 0;
        String moduleName = (String) paramsMap.get("mod");
        String actName = (String) paramsMap.get("act");
        String timestamp = (String) paramsMap.get("t");
        String sign = (String) paramsMap.get("sign");
        if (!StringUtils.isEmpty(moduleName) && !StringUtils.isEmpty(actName) && !StringUtils.isEmpty(timestamp)
                && !StringUtils.isEmpty(sign)) {// 验证参数是否存在

        } else {

        }
        return flag;
    }

    /**
     * 获取方法
     *
     * @param methodArray
     * @return
     */
    public static Method getMethod(Method[] methodArray, String act) {
        Method method = null;
        for (int i = 0; i < methodArray.length; i++) {
            if (methodArray[i].getName().equals(act)) {
                method = methodArray[i];
                break;
            }
        }
        return method;
    }

    /**
     * @param request
     * @return
     * @note 获取完整请求信息
     * @author wangmeng
     * @date 2016年2月19日 上午11:51:20
     */
    public static String getFullURL(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        if (request.getQueryString() != null) {
            url.append("?");
            url.append(request.getQueryString());
        }
        return url.toString();
    }

    /**
     * 移除url中的参数值
     *
     * @param url
     * @param params
     * @return
     * @date
     * @author yinjunlu
     */
    public static String removeParams(String url, String[] params) {
        if (StringUtils.isEmpty(url)) {
            return url;
        }
        String reg = null;
        StringBuffer ps = new StringBuffer();
        ps.append("(");
        for (int i = 0; i < params.length; i++) {
            ps.append(params[i]).append("|");
        }
        ps.deleteCharAt(ps.length() - 1);
        ps.append(")");
        reg = "(?<=[\\?&])" + ps.toString() + "=[^&]*&?";
        url = url.replaceAll(reg, "");
        url = url.replaceAll("(\\?|&+)$", "");
        return url;
    }

    /**
     * 解码前端id
     *
     * @param id
     * @return java.lang.Long
     * @author jiaofei
     */
    public static Long decodeId(String id) {
        String result = null;
        try {
            result = new String(new BASE64Decoder().decodeBuffer(id), "GB2312");
        } catch (IOException e) {
            logger.error("解码前端id异常！id={},stackTrace={}", id, LogExceptionStackTrace.erroStackTrace(e));
        }
        return Long.valueOf(result);
    }

    /**
     * 解码前端id
     *
     * @param id
     * @return java.lang.Long
     * @author jiaofei
     */
    public static String decode(String id) {
        String result = null;
        try {
            result = new String(new BASE64Decoder().decodeBuffer(id), "GB2312");
        } catch (IOException e) {
            logger.error("解码前端id异常！id={},stackTrace={}", id, LogExceptionStackTrace.erroStackTrace(e));
        }
        return result;
    }

    /**
     * @Author shenwei
     * @Date 2017/1/18 18:58
     * @Description 加码
     */
    public static String encodedId(Long id) {
        String result = null;
        try {
            result = new String(new BASE64Encoder().encode(id.toString().getBytes()));
        } catch (Exception e) {
            logger.error("编码前端id异常！id={},stackTrace={}", id, LogExceptionStackTrace.erroStackTrace(e));
        }
        return result;
    }

    public static String encodedId(String id) {
        String result = null;
        try {
            result = new String(new BASE64Encoder().encode(id.getBytes()));
        } catch (Exception e) {
            logger.error("编码异常 id={},stackTrace={}", id, LogExceptionStackTrace.erroStackTrace(e));
        }
        return result;
    }


    //用来获取每个URL请求的完全链接,附带请求参数
    public static String getUrl(HttpServletRequest request) {
        StringBuffer uri = request.getRequestURL();
        String url = uri.toString();
        //获取所有请求,返回Map集合,遍历
        Map<String, String[]> map = request.getParameterMap();
        Set<Map.Entry<String, String[]>> entry = map.entrySet();
        Iterator<Map.Entry<String, String[]>> iterator = entry.iterator();

        //遍历集合
        StringBuffer sb = new StringBuffer();
        while (iterator.hasNext()) {
            Map.Entry<String, String[]> item = iterator.next();
            //请求名
            String key = item.getKey();
            //请求值
            for (String value : item.getValue()) {
                //拼接每个请求参数   key=value&
                sb.append(key + "=" + value + "&");
            }
        }

        String string = sb.toString();
        //拼接URL,   URL?key=value&key=value&   并且去掉最后一个&
        url = url + "?" + string.substring(0, string.lastIndexOf("&"));
        return url;
    }


    /**
     * @Author shenwei
     * @Date 2017/1/18 15:49
     * @Description post请求
     */
    public static String doPost(String url, String userId, String action, String param) {

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("UserID", userId));
        nvps.add(new BasicNameValuePair("action", action));
        nvps.add(new BasicNameValuePair("params", param));
        return doPost(url, nvps);
    }

    /**
     * @Author zxh
     * @Date 2017/03/30
     * @Description post请求
     */
    public static String doPost(String url, List<NameValuePair> nvps) {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, Charset.forName("utf-8")));
        CloseableHttpResponse response2 = null;
        String result = "";
        try {
            response2 = httpclient.execute(httpPost);
            if (response2.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response2.getEntity();
                httpPost.releaseConnection();
                result = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            logger.error("请求URL= {}, 异常; StackTrace = {} ", url, LogExceptionStackTrace.erroStackTrace(e));
        } finally {
            if (response2 != null) {
                try {
                    response2.close();
                } catch (IOException e) {
                    logger.error("CloseableHttpResponse throw Exception error={}", LogExceptionStackTrace.erroStackTrace(e));
                }
            }
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    logger.error("CloseableHttpClient throw Exception error={}", LogExceptionStackTrace.erroStackTrace(e));
                }
            }
        }
        return result;
    }

    /**
     * @Author wzn
     * @Date 2017/5/3
     * @Description 上传文件
     */
    public static String upload(String url, Map<String, Object> params,String name,boolean isFile,int socketTimeOut,int connectTimeOut){
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClients.createDefault();


            // 把一个普通参数和文件上传给下面这个地址 是一个servlet
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeOut).setConnectTimeout(connectTimeOut).build();
            httpPost.setConfig(requestConfig);

            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();

            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (param.getKey().equals(name)){

                    if (isFile){
                        MultipartFile[] files = (MultipartFile[]) params.get(name);
                        for(MultipartFile myfile : files){
                            multipartEntityBuilder.addPart(name,new InputStreamBody(myfile.getInputStream(),myfile.getOriginalFilename()));
                        }
                    }else {
                        TuPuModel[] tuPuModels = (TuPuModel[]) param.getValue();
                        for (TuPuModel tuPuModel:tuPuModels){
                            multipartEntityBuilder.addPart(name,new InputStreamBody(tuPuModel.getInputStream(),tuPuModel.getFileName()));
                        }
                    }

                }else{
                    multipartEntityBuilder.addPart(param.getKey(),new StringBody(String.valueOf(param.getValue()), ContentType.create("text/plain", Consts.UTF_8)));
                }
            }

            HttpEntity reqEntity = multipartEntityBuilder.build();
            httpPost.setEntity(reqEntity);

            // 发起请求 并返回请求的响应
            response = httpClient.execute(httpPost);

            // 获取响应对象
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }

            // 销毁
            EntityUtils.consume(resEntity);
        }catch (Exception e){
            logger.error("upload URL= {}, 异常; StackTrace = {} ", url, LogExceptionStackTrace.erroStackTrace(e));
        }finally {
            try {
                if(response != null){
                    response.close();
                }
            } catch (IOException e) {
                logger.error("upload URL= {}, 异常; StackTrace = {} ", url, LogExceptionStackTrace.erroStackTrace(e));
            }

            try {
                if(httpClient != null){
                    httpClient.close();
                }
            } catch (IOException e) {
                logger.error("upload URL= {}, 异常; StackTrace = {} ", url, LogExceptionStackTrace.erroStackTrace(e));
            }
        }
        return null;
    }


    public static String getTraceId(HttpServletRequest request) {
        String traceId = "";
        Object objTraceId = request.getAttribute("traceId");
        if (objTraceId != null) {
            traceId = objTraceId.toString();
        }
        return traceId;
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param, int timeout,int readTimeout) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            if (org.apache.commons.lang.StringUtils.isNotBlank(param)) {
                urlNameString = url + "?" + param;
            }
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(readTimeout);
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }


}
