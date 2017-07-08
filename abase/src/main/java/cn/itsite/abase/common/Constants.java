package cn.itsite.abase.common;


import java.io.File;

import cn.itsite.abase.BaseApplication;
import cn.itsite.abase.utils.DirectoryUtils;

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

    //SD卡路径
    public static final String PATH_DATA = DirectoryUtils.getDiskCacheDirectory(BaseApplication.mContext, "data").getAbsolutePath();
    public static final String PATH_NET_CACHE = PATH_DATA + File.separator + "NetCache";
    public static final String PATH_APK_CACHE = PATH_DATA + File.separator + "ApkCache";
}
