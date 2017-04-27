package cn.itsite.abase.common;


/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class Constants {
    private final String TAG = Constants.class.getSimpleName();
    /**
     * 不允许new
     */
    private Constants() {
        throw new Error("Do not need instantiate!");
    }
    //基地址
    public static final String BASE_URL = "";
    //网络链接超时时间
    public static final int DEFAULT_TIMEOUT = 5;
}
