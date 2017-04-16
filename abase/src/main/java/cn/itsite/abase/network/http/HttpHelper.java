package cn.itsite.abase.network.http;

import android.text.TextUtils;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cn.itsite.abase.BuildConfig;
import cn.itsite.abase.common.Configuration;
import cn.itsite.abase.common.Constants;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class HttpHelper {
    private final String TAG = this.getClass().getSimpleName();
    public static final String BASE_URL = Constants.BASE_URL;
    private static OkHttpClient mOkHttpClient;
    private static Retrofit mRetrofit;
    private volatile static HttpHelper INSTANCE;

    //构造方法私有
    private HttpHelper() {
        initRetrofit();
    }

    //获取单例
    public static <T> T getInstance(Class<T> c) {
        if (INSTANCE == null) {
            synchronized (HttpHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpHelper();
                }
            }
        }
        return INSTANCE.initService(c);
    }

    /**
     * 初始化OkHttp
     */
    private void initOkHttpClient() {
        if (null == mOkHttpClient) {

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            if (builder.interceptors() != null) {
                builder.interceptors().clear();
            }

            //设置超时
            builder.connectTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            builder.readTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            builder.writeTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            //错误重连
            builder.retryOnConnectionFailure(true);

            //DEBUG模式下配Log拦截器
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(loggingInterceptor);
            }

//            builder.addNetworkInterceptor();

            if (Configuration.isShowNetworkParams()) {
                builder.addInterceptor(new LoggingInterceptor());
            }
            mOkHttpClient = builder.build();
        }
    }

    /**
     * 初始化Retrofit
     */
    private void initRetrofit() {
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
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
    }

    /**
     * 根据传入的接口类获取Retrofit的相应服务
     *
     * @param c   Retrofit的API接口
     * @param <T> 返回的服务类型
     * @return 返回的服务类型
     */
    public <T> T initService(Class<T> c) {
        return mRetrofit.create(c);
    }
}
