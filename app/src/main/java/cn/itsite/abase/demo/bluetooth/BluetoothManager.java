//package cn.itsite.abase.demo.bluetooth;
//
//import android.app.Activity;
//import android.bluetooth.BluetoothDevice;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.support.v7.app.AlertDialog;
//import android.text.TextUtils;
//import android.util.Log;
//
//import java.util.concurrent.TimeUnit;
//
//import cn.itsite.abase.cache.SPCache;
//import cn.itsite.abase.demo.AppApplication;
//import cn.itsite.abase.demo.R;
//import cn.itsite.abase.demo.bluetooth.classic.ClassicBluetooth;
//import cn.itsite.abase.demo.bluetooth.le.LeBluetooth;
//import cn.itsite.abase.demo.common.BaseConstants;
//import rx.android.schedulers.AndroidSchedulers;
//import rx.functions.Action1;
//
//
///**
// * Created by leguang on 2016/12/23 0023.
// * Email：langmanleguang@qq.com
// */
//public class BluetoothManager {
//    private static final String TAG = BluetoothManager.class.getSimpleName();
//    private static BluetoothManager INSTANCE;
//    private IBluetooth mBluetooth;
//    private BluetoothListener mListener;
//
//    private BluetoothManager(Context mContext) {
//        int intBluetoothType = (int) SPCache.get(mContext, BaseConstants.BLUETOOTH_TYPE, 0);
//        if (intBluetoothType == BaseConstants.BLUETOOTH_CLASSIC) {
//            mBluetooth = ClassicBluetooth.newInstance(mContext);
//        } else if (intBluetoothType == BaseConstants.BLUETOOTH_LE) {
//            mBluetooth = LeBluetooth.newInstance(mContext);
//        } else {
//            mBluetooth = ClassicBluetooth.newInstance(mContext);
//        }
//    }
//
//    public static BluetoothManager newInstance(Context mContext) {
//        if (INSTANCE == null) {
//            synchronized (ClassicBluetooth.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = new BluetoothManager(mContext);
//                }
//            }
//        }
//        return INSTANCE;
//    }
//
//    public IBluetooth getBluetooth() {
//        return mBluetooth;
//    }
//
//    public void switch2Classic() {
//        this.mBluetooth = ClassicBluetooth.newInstance(AppApplication.mContext);
//    }
//
//    public void switch2Le() {
//        this.mBluetooth = LeBluetooth.newInstance(AppApplication.mContext);
//    }
//
//    public void setListener(BluetoothListener mListener) {
//        this.mListener = mListener;
//        mBluetooth.setListener(mListener);
//    }
//
//    public void connectDefault() {
//        if (!mBluetooth.isAvailable()) {
//            if (mListener != null) {
//                mListener.onBluetoothNotSupported();
//            }
//            return;
//        }
//        if (!mBluetooth.isOpened()) {
//            mBluetooth.open();
//        }
//
//        final String strAddress = (String) SPCache.get(AppApplication.mContext, BaseConstants.BLUETOOTH_ADDRESS, "");
//        if (!TextUtils.isEmpty(strAddress)) {
//            Log.e(TAG,"连接:" + strAddress);
//            //要延迟连接，因为一进某个页面就打开蓝牙，蓝牙还没完全打开就连接会连接不上。
//            rx.Observable.timer(1500, TimeUnit.MILLISECONDS)
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Action1<Long>() {
//                        @Override
//                        public void call(Long aLong) {
//                            mBluetooth.connect(strAddress);
//                        }
//                    });
//        }
//    }
//
//    public void connectPaired(Activity mActivity) {
//        String[] arrayDeviceInfo = new String[mBluetooth.getBondedDevices().size()];
//        final String[] arrayAdress = new String[mBluetooth.getBondedDevices().size()];
//        int i = 0;
//        for (BluetoothDevice bluetoothDevice : mBluetooth.getBondedDevices()) {
//            arrayDeviceInfo[i] = bluetoothDevice.getName() + "(" + bluetoothDevice.getAddress() + ")";
//            arrayAdress[i] = bluetoothDevice.getAddress();
//            i++;
//        }
//        new AlertDialog.Builder(mActivity)
//                .setTitle(R.string.dialog_title_bluetooth)
//                .setItems(arrayDeviceInfo, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        mBluetooth.connect(arrayAdress[which]);
//                        dialog.dismiss();
//                    }
//                }).setPositiveButton(R.string.dialog_negativeText, null)
//                .setNeutralButton(R.string.dialog_scan, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        mBluetooth.startScan();
//                    }
//                }).show();
//    }
//
//    public void connect(String address) {
//        mBluetooth.connect(address);
//    }
//
//    public void disconnect() {
//        mBluetooth.disconnect();
//    }
//
//    public void startScan() {
//        mBluetooth.startScan();
//    }
//
//    public void sendData(byte[] data) {
//        mBluetooth.sendData(data);
//    }
//
//    public void onDestroy() {
//        mBluetooth.close();
//    }
//
//    public void open() {
//        mBluetooth.open();
//    }
//
//    public boolean isConnected() {
//        return mBluetooth.isConnected();
//    }
//}
