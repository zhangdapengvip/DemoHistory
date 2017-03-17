package com.printer;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demoprinter.R;
import com.lvrenyang.pos.IO;
import com.lvrenyang.rwbt.BTRWThread;
import com.lvrenyang.rwusb.USBRWThread;
import com.lvrenyang.rwwifi.NETRWThread;
import com.lvrenyang.utils.DataUtils;
import com.lvrenyang.utils.FileUtils;
import com.printer.UtilsBluetooth.BuletoothListener;
import com.printer.bean.BloodPressureInfo;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public class MainTabActiviity extends Activity implements OnClickListener {

    private Activity mContext;
    private TextView btn;
    private UtilsBluetooth mBlue;
    private BloodPressureInfo info;
    // private BloodSugarInfo info = new BloodSugarInfo(result);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acvt_maintab);
        mContext = this;
        UtilsBluetooth.checkBluetoothState(this);
        BTRWThread.InitInstant().start();
        initView();
    }

    private void initView() {
        btn = (TextView) findViewById(R.id.connect);
        findViewById(R.id.connect).setOnClickListener(this);
        findViewById(R.id.print).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.connect:
                // connectBluetooth();
                connectBlood();
                // byte[] demo = { 103, 108, 118, 01, 66, 10 };
                // float convertHex = UtilsBluetooth.convertHex(demo);
                // Log.e("UtilsBluetooth", convertHex + "!!!");

                break;
            case R.id.print:
                // PrintTest();
                byte[] arrayOfByte = {-3, -3, -6, 5, 13, 10};
                mBlue.write(arrayOfByte);
                info = new BloodPressureInfo();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != mBlue){
            mBlue.onActivityDestory();
        }
    }

    private void connectBlood() {
        mBlue = UtilsBluetooth.getInstance(mContext);
        mBlue.setBluetoothListener(new BuletoothListener() {

            @Override
            public void onBluetoothConnect() {
                mBlue.initDeviceList(UtilsBluetooth.BLUETOOTH_BP);
            }

            @Override
            public void onDeviceDisconnnected() {
                Toast.makeText(mContext, "设备断开连接", Toast.LENGTH_SHORT).show();
                Log.e("UtilsBluetooth", "设备断开连接");
                btn.setText("设备断开连接");
            }

            @Override
            public void onDeviceConnnected() {
                Toast.makeText(mContext, "设备连接成功", Toast.LENGTH_SHORT).show();
                Log.e("UtilsBluetooth", "设备连接成功");
                btn.setText("设备连接成功");
            }

            @Override
            public void onBloodResult(byte[] result) {
                if (null != info) {
                    info.loadData(result);
                    String currentPressure = info.getCurrentPressure();
                    String highPressure = info.getHighPressure();
                    String lowPressure = info.getLowPressure();
                    String heartate = info.getHeartate();
                    String errorInfo = info.getErrorInfo();
                    if(!TextUtils.isEmpty(currentPressure))Log.e("Result", "当前：" + currentPressure);
                    if(!TextUtils.isEmpty(highPressure))Log.e("Result", "高压：" + highPressure + "低压：" + lowPressure + "心率：" + heartate);
                    if(!TextUtils.isEmpty(errorInfo))Log.e("Result", "错误信息：" + errorInfo);
                }
            }

            @Override
            public void onDevicesFound() {
                Toast.makeText(mContext, "开始搜索", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDevicesFoundFnish() {
                Toast.makeText(mContext, "结束搜索", Toast.LENGTH_SHORT).show();
            }

        });
        mBlue.initDeviceList(UtilsBluetooth.BLUETOOTH_BP);
        mBlue.connectDevice();

    }

    void PrintTest() {
        String str = "姓名：张大鹏\n" + "付款项目：签约\n" + "付款金额：40元\n" + "日期：2015-07-24";
        // 回车:0x0d—/r 换行:0x0A—/n
        byte[] space = {0x0A, 0x0A, 0x0A, 0x0A};
        byte[] buf = null;
        try {
            buf = DataUtils.byteArraysToBytes(new byte[][]{
                    str.getBytes("gbk"), space});
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (isConnected()) {
            if (IO.Write(buf, 0, buf.length) == buf.length) {
                Toast.makeText(this, "打印成功", Toast.LENGTH_SHORT).show();
            } else {
            }
        } else {
            connectBluetooth();
            Toast.makeText(this, "设备未连接请检查蓝牙状态", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isConnected() {
        if (BTRWThread.IsOpened() || NETRWThread.IsOpened()
                || USBRWThread.IsOpened())
            return true;
        else
            return false;
    }

    private void connectBluetooth() {
        IO.SetCurPort(IO.PORT_BT);
        UtilsBluetooth bluetooth = UtilsBluetooth.getInstance(mContext);
        String BTAddress = "";
        List<Map<String, String>> boundList = bluetooth.getBoundedPrinters();
        for (Map<String, String> boundItem : boundList) {
            if (boundItem.get(UtilsBluetooth.DEVICE_NAME).contains("MPT")) {
                BTAddress = boundItem.get(UtilsBluetooth.DEVICE_MAC);
                break;
            }
        }
        FileUtils.AddToFile("connecting bt:" + BTAddress + "\r\n",
                FileUtils.sdcard_dump_txt);
        // 连接蓝牙
        if (TextUtils.isEmpty(BTAddress)) {
            Toast.makeText(this, "请检查手机蓝牙是否连接打印机", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean result = BTRWThread.OpenOfficial(BTAddress);
        FileUtils.AddToFile("result:" + result + "\r\n",
                FileUtils.sdcard_dump_txt);
        Toast.makeText(this, result ? "连接成功" : "连接失败", Toast.LENGTH_SHORT)
                .show();

    }
}
