package cn.itsite.abase.network.http;

import android.text.TextUtils;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cn.itsite.abase.common.Constants;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class HttpHelper {
    public static final String TAG = HttpHelper.class.getSimpleName();
    public static String BASE_URL = "http://www.aglhz.com:8090";
    private static OkHttpClient mOkHttpClient;
    private static Retrofit mRetrofit;
    private static final int TIMEOUT = 35;

    //构造方法私有
    private HttpHelper() {
        initRetrofit();
    }

    /**
     * 初始化OkHttp
     */
    private static void initOkHttpClient() {
        if (null == mOkHttpClient) {

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            if (builder.interceptors() != null) {
                builder.interceptors().clear();
            }

            File cacheFile = new File(Constants.PATH_NET_CACHE);
            Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);

            //设置缓存
            builder.cache(cache);
            //设置超时
            builder.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
            builder.readTimeout(TIMEOUT, TimeUnit.SECONDS);
            builder.writeTimeout(TIMEOUT, TimeUnit.SECONDS);
            //错误重连
            builder.retryOnConnectionFailure(true);

            builder.addInterceptor(new LogInterceptor());
            mOkHttpClient = builder.build();
        }
    }

    private static void initRetrofit() {
        if (null == mRetrofit) {
            initOkHttpClient();

            GsonBuilder gsonBuilder = new GsonBuilder();

            // Gson double类型转换, 避免空字符串解析出错
            final TypeAdapter<Number> DOUBLE = new TypeAdapter<Number>() {
                @Override
                public Number read(JsonReader in) throws IOException {
                    if (in.peek() == JsonToken.NULL) {
                        in.nextNull();
                        return null;
                    }
                    if (in.peek() == JsonToken.STRING) {
                        String tmp = in.nextString();
                        if (TextUtils.isEmpty(tmp)) tmp = "0";
                        return Double.parseDouble(tmp);
                    }
                    return in.nextDouble();
                }

                @Override
                public void write(JsonWriter out, Number value) throws IOException {
                    out.value(value);
                }
            };

            // Gson long类型转换, 避免空字符串解析出错
            final TypeAdapter<Number> LONG = new TypeAdapter<Number>() {
                @Override
                public Number read(JsonReader in) throws IOException {
                    if (in.peek() == JsonToken.NULL) {
                        in.nextNull();
                        return null;
                    }
                    if (in.peek() == JsonToken.STRING) {
                        String tmp = in.nextString();
                        if (TextUtils.isEmpty(tmp)) tmp = "0";
                        return Long.parseLong(tmp);
                    }
                    return in.nextLong();
                }

                @Override
                public void write(JsonWriter out, Number value) throws IOException {
                    out.value(value);
                }
            };

            // Gson int类型转换, 避免空字符串解析出错
            final TypeAdapter<Number> INT = new TypeAdapter<Number>() {
                @Override
                public Number read(JsonReader in) throws IOException {
                    if (in.peek() == JsonToken.NULL) {
                        in.nextNull();
                        return null;
                    }
                    if (in.peek() == JsonToken.STRING) {
                        String tmp = in.nextString();
                        if (TextUtils.isEmpty(tmp)) tmp = "0";
                        return Integer.parseInt(tmp);
                    }
                    return in.nextInt();
                }

                @Override
                public void write(JsonWriter out, Number value) throws IOException {
                    out.value(value);
                }
            };

            gsonBuilder.registerTypeAdapterFactory(TypeAdapters.newFactory(double.class, Double.class, DOUBLE));
            gsonBuilder.registerTypeAdapterFactory(TypeAdapters.newFactory(long.class, Long.class, LONG));
            gsonBuilder.registerTypeAdapterFactory(TypeAdapters.newFactory(int.class, Integer.class, INT));

            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(mOkHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
    }

    public static <T> T getService(Class<T> t) {
        if (mRetrofit == null) {
            initRetrofit();
        }

        return mRetrofit.create(t);
    }
}
