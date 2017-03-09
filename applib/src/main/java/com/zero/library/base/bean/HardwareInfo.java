package com.zero.library.base.bean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.WindowManager;

public class HardwareInfo {
    Context mContext;

    /**
     * 手机传感器
     */
    private String[] mSensors;

    /**
     * 手机内存大小
     */
    private String ramSize;

    /**
     * rom大小
     */
    private String mRomSize;
    /**
     * CPU名字
     */
    private String mCUP;

    /**
     * CPU主频
     */
    private String mCUPFrequency;

    /**
     * 摄像头类
     */
    private String mIsCamera;

    /**
     * 是否支持GPS 0不支持，1支持
     */
    private String mGPS;

    private String mSDTSize;
    private String mSDASize;

    private TelephonyManager mTelMgr;

    public HardwareInfo(Context context) {
        this.mContext = context;
        mTelMgr = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
    }

    public String getSDTSize() {
        mSDTSize = String.valueOf(getSDSize()[0]);
        return mSDTSize;
    }

    public String getSDASize() {
        mSDASize = String.valueOf(getSDSize()[1]);

        return mSDASize;
    }

    @SuppressWarnings("deprecation")
    public long[] getSDSize() {
        long[] sdCardInfo = new long[2];
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long bSize = sf.getBlockSize();
            long bCount = sf.getBlockCount();
            long availBlocks = sf.getAvailableBlocks();

            sdCardInfo[0] = bSize * bCount;// 总大小
            sdCardInfo[1] = bSize * availBlocks;// 可用大小

        }

        return sdCardInfo;
    }

    /**
     * 窗口分辨率宽
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public String getScreenWidth() {
        WindowManager windowManager = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        return String.valueOf(display.getWidth());
    }

    /**
     * 窗口分辨率高
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public String getScreenHeigth() {
        WindowManager windowManager = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        return String.valueOf(display.getHeight());
    }

    /**
     * 手机型号
     *
     * @return
     */
    public static String getPhoneModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取当前sensor
     *
     * @return
     */
    public String[] getSensors() {
        SensorManager manager = (SensorManager) mContext
                .getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = manager.getSensorList(Sensor.TYPE_ALL);
        mSensors = new String[sensors.size()];
        for (int i = 0; i < sensors.size(); i++) {
            mSensors[i] = String.valueOf(sensors.get(i).getName());
        }

        return mSensors;
    }

    /**
     * 获取ram总大小
     *
     * @return
     */
    public String getRamSize() {
        String str1 = "/proc/meminfo";
        String str2 = "";
        BufferedReader localBufferedReader = null;
        try {
            FileReader fr = new FileReader(str1);
            localBufferedReader = new BufferedReader(fr, 8192);
            if ((str2 = localBufferedReader.readLine()) != null) {
                Pattern numbers = Pattern.compile("(\\d+)");
                Matcher matcher = numbers.matcher(str2);
                while (matcher.find()) {
                    ramSize = matcher.group();
                }

            }
        } catch (IOException e) {

        } finally {
            if (null != localBufferedReader) {
                try {
                    localBufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        BigDecimal decimal_1024 = new BigDecimal("1024");
        BigDecimal decimal_10 = new BigDecimal("10");
        Double r = Math.ceil(Double.valueOf(new BigDecimal(ramSize)
                .divide(decimal_1024).multiply(decimal_10).toString())) / 10;
        return r.toString() + "M";
    }

    /**
     * 剩余ram
     *
     * @return
     */
    public long getAvailMemory() {
        ActivityManager am = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return mi.availMem;
    }

    /**
     * 获取rom总大小
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public String getRomSize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long blockCount = stat.getBlockCount();
        mRomSize = String.valueOf(blockSize * blockCount);

        BigDecimal decimal_1024 = new BigDecimal("1024");
        BigDecimal decimal_ret = new BigDecimal(mRomSize).divide(decimal_1024)
                .divide(decimal_1024).divide(decimal_1024);
        Double r = Math.ceil(Double.valueOf(decimal_ret.toString()) * 100) / 100;
        return r.toString() + "G";
    }

    /**
     * rom size
     */
    @SuppressWarnings("deprecation")
    public long getFreeRomSize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long blockCount = stat.getAvailableBlocks();
        return blockSize * blockCount;
    }

    /**
     * CUP型号
     *
     * @return
     */
    public String getCUP() {
        mCUP = getCpuInfo()[0];
        return mCUP;
    }

    public String[] getCpuInfo() {
        String str1 = "/proc/cpuinfo";
        String str2 = "";
        String[] cpuInfo = {"", ""};
        String[] arrayOfString;
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            str2 = localBufferedReader.readLine();
            if (null != str2) {
                arrayOfString = str2.split("\\s+");
                for (int i = 2; i < arrayOfString.length; i++) {
                    cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
                }
            }
            str2 = localBufferedReader.readLine();
            if (null != str2) {
                arrayOfString = str2.split("\\s+");
                cpuInfo[1] += arrayOfString[2];
                localBufferedReader.close();
            }
        } catch (IOException e) {
        }
        return cpuInfo;
    }

    /**
     * CUP主频
     *
     * @return
     */
    public String getCUPFrequency() {
        mCUPFrequency = getCpuInfo()[1];
        return mCUPFrequency;
    }

    /**
     * 判断是否有Camera, 经测试getNumberOfCameras只可在2.3版本上可用
     *
     * @return
     */
    @SuppressLint("NewApi")
    public String getIsCamera() {
        mIsCamera = Camera.getNumberOfCameras() + "";
        return mIsCamera;
    }

    /**
     * 判断GPS是否可用(在模拟器上测试也未真)
     *
     * @return
     */
    public String getGPS() {
        LocationManager locationManager = (LocationManager) mContext
                .getSystemService(Context.LOCATION_SERVICE);

        List<String> accessibleProviders = locationManager.getProviders(true);
        mGPS = accessibleProviders != null && accessibleProviders.size() > 0 ? (0 + "")
                : (1 + "");
        return mGPS;
    }

    /**
     * 获取手机的IMEI
     */
    public String getIMEI() {
        return ((TelephonyManager) mContext
                .getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }

    /**
     * 获取手机Device ID
     */
    public String getDeviceID() {
        return mTelMgr.getDeviceId();
    }

    /**
     * 获取手机MAC地址
     */
    public String getOtherInfo() {
        String other = "";
        WifiManager wifiManager = (WifiManager) mContext
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo.getMacAddress() != null) {
            other = wifiInfo.getMacAddress();
        } else {
            other = "Fail";
        }
        return other;
    }

    /**
     * 获取手机号码
     */
    public String getPhoneNumber() {
        return mTelMgr.getLine1Number();
    }
}
