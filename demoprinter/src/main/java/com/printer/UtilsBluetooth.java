package com.printer;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class UtilsBluetooth {

    private static final String TAG = UtilsBluetooth.class.getSimpleName();
    public static final String DEVICE_NAME = "device_name";
    public static final String DEVICE_MAC = "device_mac";
    public static final String DEVICE_KH_100 = "KH-100";
    public static final String BLUETOOTH_BP = "Bluetooth BP";

    private List<Map<String, String>> mDeviceList = new ArrayList<Map<String, String>>();
    private Context mContext;
    private String mDeviceName;
    public BuletoothListener bluetoothListener;
    private boolean isDeviceConnected;
    private BluetoothSocket socket;
    private BluetoothDevice device;
    private ReadThread mReadThread;
    private ClientThread clientConnectThread;
    private BluetoothAdapter mBlueAdapter;

    private Handler mHandler;
    private static final int MSG_BASE = 9001;
    private static final int MSG_DEVICE_RESULT = MSG_BASE;
    private static final int MSG_DEVICE_CONNECTED = MSG_BASE + 1;
    private static final int MSG_DEVICE_DISCONNECTED = MSG_BASE + 2;
    private static final int MSG_DEVICE_FOUND = MSG_BASE + 3;
    private static final int MSG_DEVICE_FOUNDFINISH = MSG_BASE + 4;
    private static final int MSG_BLUETOOTH_CONNECTED = MSG_BASE + 5;

    private static UtilsBluetooth mBluetooth;
    private InputStream mInputStream;

    public static UtilsBluetooth getInstance(Context context) {
        if (null == mBluetooth) {
            mBluetooth = new UtilsBluetooth(context);
        }
        return mBluetooth;
    }

    private UtilsBluetooth(Context context) {
        mContext = context;
        mBlueAdapter = BluetoothAdapter.getDefaultAdapter();
        IntentFilter toothFilter = new IntentFilter();
        toothFilter.addAction(BluetoothDevice.ACTION_FOUND);
        toothFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        toothFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        mDeviceReceiver = new DeviceReceiver();
        mContext.registerReceiver(mDeviceReceiver, toothFilter);
        mHandler = new Handler(Looper.getMainLooper()) {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == MSG_DEVICE_RESULT) {
                    bluetoothListener.onBloodResult((byte[]) msg.obj);
                } else if (msg.what == MSG_DEVICE_CONNECTED) {
                    bluetoothListener.onDeviceConnnected();
                } else if (msg.what == MSG_DEVICE_DISCONNECTED) {
                    bluetoothListener.onDeviceDisconnnected();
                } else if (msg.what == MSG_DEVICE_FOUND) {
                    bluetoothListener.onDevicesFound();
                } else if (msg.what == MSG_DEVICE_FOUNDFINISH) {
                    bluetoothListener.onDevicesFoundFnish();
                } else if (msg.what == MSG_BLUETOOTH_CONNECTED) {
                    bluetoothListener.onBluetoothConnect();
                }
            }

            ;
        };
    }

    public static void checkBluetoothState(Activity activity) {
        if (!isBluetoothOpen()) {
            Intent enableBtIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivity(enableBtIntent);
        }
    }

    public static boolean isBluetoothOpen() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (null != adapter && adapter.isEnabled()) {
            return true;
        }
        return false;
    }

    private void handleMsg(final int what, final Object obj) {
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.what = what;
                msg.obj = obj;
                mHandler.handleMessage(msg);
            }
        });
    }

    public BuletoothListener getBluetoothListener() {
        return bluetoothListener;
    }

    public boolean isDeviceConnect() {
        return isDeviceConnected;
    }

    public void setBluetoothListener(BuletoothListener bluetoothListener) {
        this.bluetoothListener = bluetoothListener;
    }

    public boolean connectDevice() {
        if (!isBluetoothOpen()) {
            checkBluetoothState((Activity) mContext);
            return false;
        }
        if (isDeviceConnected) {
            handleMsg(MSG_DEVICE_CONNECTED, null);
            return true;
        }
        if (TextUtils.isEmpty(mDeviceName)) {
            return false;
        }
        String deviceMac = getDeviceMac(mDeviceList);
        if (TextUtils.isEmpty(deviceMac)) {
            return false;
        }
        stopSearchDevice();
        device = mBlueAdapter.getRemoteDevice(deviceMac);
        clientConnectThread = new ClientThread();
        clientConnectThread.start();
        return true;
    }

    private String getDeviceMac(List<Map<String, String>> deviceList) {
        String deviceMac = "";
        for (Map<String, String> deviceItem : deviceList) {
            if (deviceItem.get(DEVICE_NAME).contains(mDeviceName)) {
                deviceMac = deviceItem.get(DEVICE_MAC);
                break;
            }
        }
        return deviceMac;
    }

    private class ClientThread extends Thread {
        private ClientThread() {
        }

        public void run() {
            try {
                if (null == device) {
                    return;
                }
                socket = device.createRfcommSocketToServiceRecord(UUID
                        .fromString("00001101-0000-1000-8000-00805F9B34FB"));
                socket.connect();
                mReadThread = new UtilsBluetooth.ReadThread();
                mReadThread.start();
                isDeviceConnected = true;
                handleMsg(MSG_DEVICE_CONNECTED, null);
                return;
            } catch (IOException localIOException) {
                shutdownConnection();
            }
        }
    }

    private OutputStream mOutStream;

    public boolean write(byte[] paramByteArray) {
        if (this.mOutStream != null) {
            try {
                this.mOutStream.write(paramByteArray);
                return true;
            } catch (IOException localIOException) {
                localIOException.printStackTrace();
            }
        }
        return false;
    }

    private class ReadThread extends Thread {
        private ReadThread() {
        }

        public void run() {
            try {
                mInputStream = socket.getInputStream();
                mOutStream = socket.getOutputStream();
            } catch (Exception e) {
                shutdownConnection();
                Log.e(TAG, "socket连接异常");
                try {
                    mInputStream.close();
                    mOutStream.close();
                } catch (IOException inputException) {
                    inputException.printStackTrace();
                }
                return;
            }
            byte[] buffer = new byte[1024];
            int bytes;
            while (true) {
                try {
                    if (isInterrupted() || mInputStream.available() > 0) {
                        return;
                    }
                    if ((bytes = mInputStream.read(buffer)) > 0) {
                        byte[] buf_data = new byte[bytes];
                        for (int i = 0; i < bytes; i++) {
                            buf_data[i] = buffer[i];
                        }
                        handleMsg(MSG_DEVICE_RESULT, buf_data);
                    }
                } catch (IOException e) {
                    shutdownConnection();
                    try {
                        mInputStream.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;
                }
            }
        }
    }

    public void onActivityDestory() {
        if (null != mDeviceReceiver) {
            mContext.unregisterReceiver(mDeviceReceiver);
            mDeviceReceiver = null;
        }
        shutdownConnection();
        mBluetooth = null;
    }

    private void shutdownConnection() {
        isDeviceConnected = false;
        handleMsg(MSG_DEVICE_DISCONNECTED, null);
        if (clientConnectThread != null) {
            clientConnectThread.interrupt();
            clientConnectThread = null;
        }
        if (mReadThread != null) {
            mReadThread.interrupt();
            mReadThread = null;
        }
        if (socket != null) {
            try {
                socket.close();
                socket = null;
                return;
            } catch (IOException localIOException) {
                localIOException.printStackTrace();
            }
        }
    }

    public void stopSearchDevice() {
        if ((mBlueAdapter != null) && (mBlueAdapter.isDiscovering()))
            mBlueAdapter.cancelDiscovery();
    }

    public class DeviceReceiver extends BroadcastReceiver {
        public DeviceReceiver() {
        }

        public void onReceive(Context paramContext, Intent paramIntent) {
            String str = paramIntent.getAction();
            // 发现蓝牙设备
            if (BluetoothDevice.ACTION_FOUND.equals(str)) {
                BluetoothDevice device = (BluetoothDevice) paramIntent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // 未配对设备
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put(DEVICE_NAME, device.getName());
                    map.put(DEVICE_MAC, device.getAddress());
                    Log.v(TAG, "发现设备" + device.getName() + device.getAddress());
                    mDeviceList.add(map);
                    connectDevice();
                }
                // 完成设备的搜寻
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(str)) {
                handleMsg(MSG_DEVICE_FOUNDFINISH, null);
                // 连接状态监听
            } else if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(str)) {
                int state = paramIntent.getIntExtra(
                        BluetoothAdapter.EXTRA_STATE, 0);
                if (BluetoothAdapter.STATE_ON == state) {
                    handleMsg(MSG_BLUETOOTH_CONNECTED, null);
                } else if (BluetoothAdapter.STATE_OFF == state) {
                    shutdownConnection();
                }
            }
        }
    }

    private BroadcastReceiver mDeviceReceiver;

    public void initDeviceList(String deviceName) {
        mDeviceName = deviceName;
        if (null != mBlueAdapter) {
            mDeviceList.clear();
            Set<BluetoothDevice> pairedDevices = mBlueAdapter
                    .getBondedDevices();
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put(DEVICE_NAME, device.getName());
                    map.put(DEVICE_MAC, device.getAddress());
                    mDeviceList.add(map);
                }
            }
            if (isBluetoothOpen()
                    && TextUtils.isEmpty(getDeviceMac(mDeviceList))) {
                handleMsg(MSG_DEVICE_FOUND, null);
                mBlueAdapter.startDiscovery();
            }
        }
    }

    public List<Map<String, String>> getBoundedPrinters() {
        return mDeviceList;
    }

    public static abstract class BuletoothListener {
        public abstract void onBloodResult(byte[] result);

        public abstract void onDeviceConnnected();

        public abstract void onDeviceDisconnnected();

        public abstract void onBluetoothConnect();

        public void onDevicesFound() {
        }

        public void onDevicesFoundFnish() {
        }

    }

}
