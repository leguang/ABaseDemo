package cn.itsite.abase.demo.common;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.widget.Toast;


import cn.itsite.abase.log.ALog;

import static android.content.Context.VIBRATOR_SERVICE;

/**
 * Created by leguang on 2017/7/7 0007.
 * Email：langmanleguang@qq.com
 */

public class ShakeHelper implements SensorEventListener {
    private static final String TAG = ShakeHelper.class.getSimpleName();
    private Context mContext;
    //传感器管理器
    private SensorManager mSensorManager;
    //传感器
    private Sensor mSensor;
    //速度阀值
    private final int SPEED = 3000;
    //时间间隔 
    private final int INTERVAL = 50;
    //上一次摇晃的时间
    private long lastTime;
    //上一次的x、y、z坐标
    private float lastX, lastY, lastZ;
    private float nowX, nowY, nowZ;
    private float deltaX, deltaY, deltaZ;
    private double nowSpeed;
    private long nowTime, deltaTime;
    private Vibrator vibrator;

    public ShakeHelper(Context mContext) {
        this.mContext = mContext;
        start();
    }

    public void start() {

        ALog.e("ShakeHelper--star-->" + "start");
        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager != null) {
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
        if (mSensor != null) {
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);
        }

        vibrator = (Vibrator) mContext.getSystemService(VIBRATOR_SERVICE);
    }

    public void Stop() {
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
//        if ((Math.abs(event.values[0]) > 17 || Math.abs(event.values[1]) > 17 || Math.abs(event.values[2]) > 17)) {//这个判断也可以
//            vibrator.vibrate(200);
//        }

        nowTime = System.currentTimeMillis();
        deltaTime = nowTime - lastTime;
        if (deltaTime < INTERVAL) {
            ALog.e("onSensor-->" + "return");
            return;
        }
        //将nowTime赋给lastTime
        lastTime = nowTime;
        //获取x,y,z
        nowX = event.values[0];
        nowY = event.values[1];
        nowZ = event.values[2];
        //计算x,y,z变化量
        deltaX = nowX - lastX;
        deltaY = nowY - lastY;
        deltaZ = nowZ - lastZ;
        //赋值
        lastX = nowX;
        lastY = nowY;
        lastZ = nowZ;
        //计算
        nowSpeed = (Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) / deltaTime) * 10000;
        ALog.e("onSensor-->" + "nowSpeed::" + nowSpeed);

        //判断
        if (nowSpeed >= SPEED) {
            vibrator.vibrate(200);
            Toast.makeText(mContext, "你摇晃了手机！", Toast.LENGTH_SHORT).show();
        }
    }
}
