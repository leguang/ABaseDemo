package cn.itsite.abase.demo.common;


import java.io.File;

import cn.itsite.abase.demo.App;
import cn.itsite.abase.utils.DirectoryUtils;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class Constants {
    private final String TAG = Constants.class.getSimpleName();


    //不允许new
    private Constants() {
        throw new Error("Do not need instantiate!");
    }

    //SD卡路径
    public static final String PATH_DATA = DirectoryUtils.getDiskCacheDirectory(App.mContext, "data").getAbsolutePath();
    public static final String PATH_NET_CACHE = PATH_DATA + File.separator + "NetCache";
    public static final String PATH_APK_CACHE = PATH_DATA + File.separator + "ApkCache";

    //分类
    public static final int ARCHITECTURE = 0;
    public static final int BLUETOOTH = 1;
    public static final int CUSTOM_VIEW = 2;
    public static final int DESIGN_PATTERN = 3;
    public static final int UI = 4;
    public static final int OPTIMIZE = 5;
    public static final int MULTIMEDIA = 6;
    public static final int LBS = 7;
    public static final int PAYMENT = 8;
    public static final int QRCODE = 9;

    //蓝牙模块
    public static final String BLUETOOTH_TYPE = "bluetooth_type";
    public static final int BLUETOOTH_CLASSIC = 0;
    public static final int BLUETOOTH_LE = 1;
    public static final String BLUETOOTH_ADDRESS = "bluetooth_address";
}
