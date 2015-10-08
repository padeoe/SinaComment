/**
 * Created by padeoe on 2015/10/8.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;

/**
 * Created by padeoe on 2015/10/8.
 */
public class NetworkUtils {

    public static String postWithCookie(String postData, String cookie, String URL, int timeout) {
        try {
            byte[] postAsBytes = postData.getBytes("UTF-8");
            java.net.URL url = new URL(URL);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setConnectTimeout(timeout);
            //connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Accept-Encoding", "deflate, sdch");
            connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.6,en;q=0.4,zh-TW;q=0.2");

            connection.setRequestProperty("Connection", "keep-alive");
            connection.setRequestProperty("Host", "comment5.news.sina.com.cn");
            connection.setRequestProperty("Referer", "http://news.sina.com.cn/c/nd/2015-10-07/doc-ifximrxn8244692.shtml");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.93 Safari/537.36");
            connection.connect();

           /*          java 1.6 do not support
            try (OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(postAsBytes);
            }*/


            //读取10KB返回数据
            byte[] readData = new byte[10240000];
            int len = 0;
            int wholelen = 0;
/*          java 1.6 do not support
            try (InputStream inputStream = connection.getInputStream()) {
                len = inputStream.read(readData);
            }*/
            InputStream inputStream = null;
            try {
                inputStream = connection.getInputStream();
                while (true) {
                    len = inputStream.read(readData, wholelen, readData.length - wholelen);
                    if (len == -1 || readData.length - wholelen == len) {
                        break;
                    }
                    wholelen += len;

                }

            } finally {
                if (inputStream != null) {
                    {
                        inputStream.close();
                    }
                }
            }

            connection.disconnect();
            String data = new String(readData, 0, wholelen, "UTF-8");
            return data;
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        } catch (MalformedURLException malformedURLException) {
            return malformedURLException.getMessage();
        } catch (ProtocolException protocolException) {
            return protocolException.getMessage();
        } catch (IOException ioException) {
            return ioException.getMessage();

        }


    }






}

