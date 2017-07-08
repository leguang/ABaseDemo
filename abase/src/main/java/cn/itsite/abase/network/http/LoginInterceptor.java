package cn.itsite.abase.network.http;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;

import cn.itsite.abase.log.ALog;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class LoginInterceptor implements Interceptor {
    private static final String TAG = LoginInterceptor.class.getSimpleName();
    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

        ResponseBody responseBody = response.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(UTF8);
        }

        String bodyString = buffer.clone().readString(charset);

        ALog.e("body---------->" + bodyString);

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(buffer.readUtf8()).optJSONObject("other");
            String code = jsonObject.optString("code");
            if ("123".equals(code)) {
//                EventBus.getDefault().post(new LoginInterceptor());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (buffer != null) {
                buffer.close();
            }

            if (source != null) {
                source.close();
            }
        }


        return response;
    }
}
