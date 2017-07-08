package cn.itsite.abase.demo.bluetooth;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.itsite.abase.cache.SPCache;
import cn.itsite.abase.demo.AppApplication;
import cn.itsite.abase.demo.R;
import cn.itsite.abase.demo.common.Constants;
import cn.itsite.abase.demo.common.DialogHelper;
import cn.itsite.abase.mvp.view.base.BaseFragment;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 * 该界面留作调试指令用
 */
public class BluetoothFragment extends BaseFragment {
    private static final String TAG = BluetoothFragment.class.getSimpleName();
    @BindView(R.id.tv_disconnect)
    TextView tvDisconnect;
    @BindView(R.id.tv_scan)
    TextView tvScan;
    @BindView(R.id.tv_paired)
    TextView tvPaired;
    @BindView(R.id.toolbar_toolbar)
    Toolbar toolbar;
    @BindView(R.id.lv_responses)
    ListView lvResponses;
    @BindView(R.id.et_message)
    EditText etMessage;
    @BindView(R.id.cb_carrage)
    CheckBox cbCarrage;
    @BindView(R.id.bt_send)
    Button btSend;
    private ProgressDialog progressDialog;
    private List<String> listResponse = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;
    private AlertDialog.Builder deviceListBuilder;
    private ViewGroup viewGroup;
    private BluetoothManager mBluetoothManager;

    public static BluetoothFragment newInstance() {
        return new BluetoothFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bluetooth, container, false);
        ButterKnife.bind(this, view);
        initStateBar(toolbar);
        viewGroup = (ViewGroup) _mActivity.findViewById(android.R.id.content).getRootView();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData() {
        initToolbarBackNavigation(toolbar);
        toolbar.setTitle("蓝牙调试");
        mAdapter = new ArrayAdapter<>(_mActivity, android.R.layout.simple_list_item_1, listResponse);
        lvResponses.setAdapter(mAdapter);
        mBluetoothManager = BluetoothManager.newInstance(AppApplication.mContext);
        mBluetoothManager.open();
        mBluetoothManager.setListener(mListener);
    }

    @OnClick({R.id.tv_disconnect, R.id.tv_scan, R.id.tv_paired, R.id.bt_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_disconnect:
                mBluetoothManager.disconnect();
                listResponse.clear();
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_scan:
                mBluetoothManager.startScan();
                break;
            case R.id.tv_paired:
                mBluetoothManager.connectPaired(_mActivity);
                break;
            case R.id.bt_send:
                mBluetoothManager.sendData(etMessage.getText().toString().getBytes());
                etMessage.setText("");
                break;
        }
    }

    protected void initToolbarBackNavigation(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressedSupport();
            }
        });
    }

    private BluetoothListener mListener = new BluetoothListener() {
        @Override
        public void onBluetoothNotSupported() {
            DialogHelper.warningSnackbar(viewGroup, "未找到蓝牙设备");
        }

        @Override
        public void onBluetoothNotEnabled() {
            DialogHelper.warningSnackbar(viewGroup, "蓝牙未打开，请打开手机蓝牙");

        }

        @Override
        public void onConnecting(BluetoothDevice device) {
            DialogHelper.loadingSnackbar(viewGroup, "正连接:" + device.getName());
        }

        @Override
        public void onConnected(BluetoothDevice device) {
            SPCache.put(AppApplication.mContext, Constants.BLUETOOTH_ADDRESS, device.getAddress());
            DialogHelper.successSnackbar(viewGroup, "已连接:" + device.getName());
            tvPaired.setVisibility(View.GONE);
            tvScan.setVisibility(View.GONE);
            tvDisconnect.setVisibility(View.VISIBLE);
        }

        @Override
        public void onDisconnected() {
            DialogHelper.successSnackbar(viewGroup, "蓝牙连接已断开");
            tvPaired.setVisibility(View.VISIBLE);
            tvScan.setVisibility(View.VISIBLE);
            tvDisconnect.setVisibility(View.GONE);
        }

        @Override
        public void onConnectionFailed(BluetoothDevice device) {
            DialogHelper.warningSnackbar(viewGroup, "蓝牙连接失败");
        }

        @Override
        public void onDiscoveryStarted() {
            progressDialog = new ProgressDialog(_mActivity);
            progressDialog.setTitle("正在玩命查找设备...");
            progressDialog.setProgress(0);
            progressDialog.setMax(10);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        public void onDiscoveryFinished() {
            progressDialog.dismiss();
        }

        @Override
        public void onNoDevicesFound() {
            DialogHelper.warningSnackbar(viewGroup, "未发现蓝牙设备");
        }

        @Override
        public void onDevicesFound(final List<BluetoothDevice> deviceList) {
            String[] arrayDeviceInfo = new String[deviceList.size()];

            for (int i = 0; i < deviceList.size(); i++) {
                arrayDeviceInfo[i] = deviceList.get(i).getName() + "(" + deviceList.get(i).getAddress() + ")";
            }

            if (deviceListBuilder == null) {
                deviceListBuilder = new AlertDialog.Builder(_mActivity)
                        .setTitle(R.string.dialog_title_bluetooth)
                        .setItems(arrayDeviceInfo, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mBluetoothManager.connect(deviceList.get(which).getAddress());
                                dialog.dismiss();
                            }
                        }).setPositiveButton(R.string.dialog_negativeText, null)
                        .setNeutralButton(R.string.dialog_scan, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mBluetoothManager.startScan();
                            }
                        });
            } else {
                deviceListBuilder.setItems(arrayDeviceInfo, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mBluetoothManager.connect(deviceList.get(which).getAddress());
                        dialog.dismiss();
                    }
                });
            }
            deviceListBuilder.show();
        }

        @Override
        public void onDataReceived(String str) {
            if (str.length() > 0) {
                listResponse.add(0, str);
                mAdapter.notifyDataSetChanged();
            }

        }
    };
}
