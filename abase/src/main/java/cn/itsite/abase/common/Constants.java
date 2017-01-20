package cn.itsite.abase.common;


/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class Constants {
    private final String TAG = this.getClass().getSimpleName();
    /**
     * 不允许new
     */
    private Constants() {
        throw new Error("Do not need instantiate!");
    }
    //SD卡路径
//    public static final String PATH_DATA = DirectoryUtils.getDiskCacheDirectory(BaseApplication.mContext, "data").getAbsolutePath();
//    public static final String PATH_NET_CACHE = PATH_DATA + File.separator + "NetCache";
//    public static final String PATH_APK_CACHE = PATH_DATA + File.separator + "ApkCache";
    //基地址
    public static final String BASE_URL = "";
    //网络链接超时时间
    public static final int DEFAULT_TIMEOUT = 5;
}
