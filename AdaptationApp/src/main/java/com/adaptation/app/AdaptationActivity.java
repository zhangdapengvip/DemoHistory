package com.adaptation.app;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adaptationapp.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

public class AdaptationActivity extends Activity {
    private TextView mInformationText;
    private TextView mNotice;
    private TextView mBtnCalculate;
    private TextView mBtnLook;
    private EditText mWidth;
    private EditText mWidthDpi;
    private EditText mHeight;
    private EditText mHeightDpi;
    private EditText mScreenDpi;
    private EditText mDpi;
    private TextView mReslut;
    private Activity mActivity;
    private int contentWidth;
    private int contentHeight;
    private int width;
    private int height;
    private float density;
    private int densityDpi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.acvt_adaptation);
        mActivity = this;
        initView();
    }

    public void obtainInfo() {
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        contentWidth = rect.width();
        contentHeight = rect.height();
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        width = metric.widthPixels;
        height = metric.heightPixels;
        density = metric.density;
        densityDpi = metric.densityDpi;
        StringBuilder sb = new StringBuilder();
        sb.append("基本信息:" + "\n");
        sb.append("屏幕宽度=" + width + "\n");
        sb.append("屏幕高度=" + height + "\n");
        sb.append("内容区域宽度=" + contentWidth + "\n");
        sb.append("内容区域高度=" + contentHeight + "\n");
        sb.append("屏幕密度=" + density + "\n");
        sb.append("屏幕DPI=" + densityDpi + "\n");
        sb.append("长度DPI=" + contentWidth + "/" + density);
        sb.append("=" + format(contentWidth / density, "#.0") + "\n");
        sb.append("高度DPI=" + contentHeight + "/" + density);
        sb.append("=" + format(contentHeight / density, "#.0") + "\n");
        float widthDpi = contentWidth / density;
        float heightDpi = contentHeight / density;
        mInformationText.setText(sb.toString());
        mWidth.setText(width + "");
        mWidthDpi.setText((int) widthDpi + "");
        mHeight.setText(contentHeight + "");
        mHeightDpi.setText((int) heightDpi + "");
        mDpi.setText(density + "");
        mScreenDpi.setText(densityDpi + "");
        mNotice.setText("根据不同分辨率的手机计算DIMENS仅可以转换dp,如果使用自己的DIMENS请将文件存放至："
                + getExternalStoragePath() + File.separator + "Download"
                + File.separator + "dimens" + File.separator + "dimens.xml");
    }

    public void adaptationDimens(int width, float density, int densityDpi,
                                 String outputPath) {
        try {
            InputStream inputStream = getAssets().open("dimens.xml");
            File srcFile = new File(getExternalStoragePath() + File.separator
                    + "Download" + File.separator + "dimens" + File.separator
                    + "dimens.xml");
            if (srcFile.exists()) {
                inputStream = new FileInputStream(srcFile);
            }
            BufferedReader readerDimens = new BufferedReader(
                    new InputStreamReader(inputStream));
            File file = createOutFile(outputPath);
            BufferedWriter writerDimens = new BufferedWriter(new FileWriter(
                    file));
            String line = null;
            while (((line = readerDimens.readLine()) != null)) {
                String writeValue;
                writeValue = line;
                if (line.contains("dimen")) {
                    int startIndex = line.indexOf(">") + 1;
                    int endIndex = line.indexOf("dp");
                    String startString = line.substring(0, startIndex);
                    String endString = line.substring(endIndex, line.length());
                    String dimenValue = line.substring(startIndex, endIndex);
                    Float dimenFloat = Float.valueOf(dimenValue);
                    Float value = (width * dimenFloat) / (density * 320);
                    writeValue = startString + format(value, "#.##")
                            + endString;
                }
                writerDimens.write(writeValue);
            }
            readerDimens.close();
            writerDimens.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String convertPath(int contentWidth, int contentHeight,
                              float density, int densityDpi) {
        // if (densityDpi >= 640) {// 4
        // result = "xxxhdpi";
        // }
        // if (densityDpi >= 480) {// 3
        // result = "xxhdpi";
        // }
        // if (densityDpi >= 320) {// 2
        // result = "xhdpi";
        // }
        // if (densityDpi >= 240) {// 1.5
        // result = "hdpi";
        // }
        // if (densityDpi >= 160) {// 1
        // result = "mdpi";
        // }
        // if (densityDpi >= 120) {// 0.75
        // result = "ldpi";
        // }
        // Android3.0：120dpi、160dpi，240dpi，320dpi
        // Android4.2：120dpi、160dpi，213dpi（TVDPI），240dpi，320dpi，480dpi
        // Android4.4：120dpi、160dpi，213dpi（TVDPI），240dpi，320dpi，400dpi，480dpi，640dpi
        // values-w360dp-h640dp-480dpi
        float widthDpi = contentWidth / density;
        float heightDpi = contentHeight / density;
        return getExternalStoragePath() + File.separator + "Download"
                + File.separator + "dimens" + File.separator + "values-w"
                + (int) widthDpi + "dp-h" + (int) heightDpi + "dp-"
                + densityDpi + "dpi" + File.separator;
    }

    private File createOutFile(String outputPath) throws IOException {
        File path = new File(outputPath);
        File file = new File(path, "dimens.xml");
        if (!path.exists()) {
            path.mkdirs();
        }
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        return file;
    }

    public void initView() {
        mInformationText = (TextView) findViewById(R.id.phone_information);
        mNotice = (TextView) findViewById(R.id.notice);
        mWidth = (EditText) findViewById(R.id.width);
        mWidthDpi = (EditText) findViewById(R.id.width_dpi);
        mHeight = (EditText) findViewById(R.id.height);
        mHeightDpi = (EditText) findViewById(R.id.height_dpi);
        mDpi = (EditText) findViewById(R.id.dpi);
        mScreenDpi = (EditText) findViewById(R.id.screen_dpi);
        mReslut = (TextView) findViewById(R.id.save_path);
        mBtnCalculate = (TextView) findViewById(R.id.btn_calculate);
        mBtnLook = (TextView) findViewById(R.id.btn_look);
        mBtnCalculate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String w = mWidth.getText().toString();
                String wDpi = mWidthDpi.getText().toString();
                String h = mHeight.getText().toString();
                String hDpi = mHeightDpi.getText().toString();
                String d = mDpi.getText().toString();
                String screend = mScreenDpi.getText().toString();
                if (TextUtils.isEmpty(w) || TextUtils.isEmpty(wDpi)
                        || TextUtils.isEmpty(h) || TextUtils.isEmpty(hDpi)
                        || TextUtils.isEmpty(d) || TextUtils.isEmpty(screend)) {
                    Toast.makeText(mActivity, "请输入有效参数", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                String convertPath = convertPath(Integer.valueOf(w),
                        Integer.valueOf(h), Float.valueOf(d),
                        Integer.valueOf(screend));
                adaptationDimens(Integer.valueOf(w), Float.valueOf(d),
                        Integer.valueOf(screend), convertPath);
                mReslut.setText("保存至" + convertPath + "dimens.xml");
            }
        });
        mBtnLook.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                obtainInfo();
            }
        });
    }

    public String format(Object value, String pattern) {
        DecimalFormat format = new DecimalFormat(pattern);
        return format.format(value);
    }

    public static String getExternalStoragePath() {
        String path = null;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File f = Environment.getExternalStorageDirectory();
            path = f.getPath();
        } else {
            path = getPath();
        }
        return path;
    }

    private static String getPath() {
        String path;
        String expath = null;
        String inpath = null;
        Runtime runtime = Runtime.getRuntime();
        Process proc;
        try {
            proc = runtime.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            String line;
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                if (line.contains("secure")) {
                    continue;
                }
                if (line.contains("asec")) {
                    continue;
                }

                if ((line.contains("fat") || line.contains("sdcardfs"))
                        && !line.contains("dmask")) {
                    String columns[] = line.split(" ");
                    if (columns != null && columns.length > 1) {
                        expath = columns[1];
                        System.out.println("this " + columns[1]
                                + " is external storage.");
                    }
                } else if (line.contains("fuse")) {
                    String columns[] = line.split(" ");
                    if (columns != null && columns.length > 1) {
                        inpath = columns[1];
                        System.out.println("this " + columns[1]
                                + " is internal storage.");
                    }
                }
            }
        } catch (IOException e) {
        }
        if (null != expath) {
            if (null == inpath) {
                inpath = Environment.getExternalStorageDirectory().getPath();
            }
            path = getProperPath(expath, inpath);
        } else {
            path = Environment.getExternalStorageDirectory().getPath();
        }

        return path;
    }

    public static String getProperPath(String pa, String pb) {
        File fa = new File(pa);
        File fb = new File(pb);
        if (!fa.exists()) {
            if (!fb.exists()) {
                return null;
            } else {
                return pb;
            }
        }
        if (!fb.exists()) {
            return pa;
        }

        StatFs sfa = new StatFs(pa);
        StatFs sfb = null;
        try {
            sfb = new StatFs(pb);
        } catch (Exception e) {
            return pa;
        }
        @SuppressWarnings("deprecation")
        long aa = (long) sfa.getAvailableBlocks() * (long) sfa.getBlockSize();
        @SuppressWarnings("deprecation")
        long ab = (long) sfb.getAvailableBlocks() * (long) sfb.getBlockSize();
        return (aa >= ab) ? pa : pb;

    }

}
