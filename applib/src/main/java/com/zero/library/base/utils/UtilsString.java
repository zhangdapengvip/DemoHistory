package com.zero.library.base.utils;

import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.zero.library.base.AppToast;

public class UtilsString {
    public final static String UTF_8 = "utf-8";
    // 电话正则
    public final static String PHONEEGEX = "^[1][3578][0-9]{9}$";
    // 中文字符
    public final static String CHINESEREGEX = "[\u4e00-\u9fa5]";

    /**
     * 转换为小写
     *
     * @param str
     * @return
     */
    public static String getLowerCase(String str) {
        if (null == str) {
            return "";
        }
        return str.toLowerCase(Locale.getDefault());
    }

    /**
     * 转换手机号为123****4567
     *
     * @param phoneNum
     * @return
     */
    public static String getPhoneNum(String phoneNum) {
        if (!TextUtils.isEmpty(phoneNum) && phoneNum.length() > 8) {
            phoneNum = phoneNum.substring(0, 3) + "****"
                    + phoneNum.subSequence(7, phoneNum.length());
        }
        return phoneNum;
    }

    /**
     * 转换身份证号为123456****9876
     *
     * @param code
     * @return
     */
    public static String getCodeNumber(String code) {
        if (!TextUtils.isEmpty(code) && code.length() > 15) {
            code = code.substring(0, 4) + "****"
                    + code.subSequence(14, code.length());
        }
        return code;
    }

    /**
     * 转换Url内容
     *
     * @param url 转换Url
     * @return
     */
    public static String getEncodeUrl(String indexStr, String url) {
        String newUrl = "";
        if (url.length() <= 0) return newUrl;
        try {
            int start = url.lastIndexOf(indexStr);
            newUrl = url.substring(0, start + 1) +
                    URLEncoder.encode(url.substring(start + 1, url.length()), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return newUrl;
        }
        return newUrl;
    }

    /**
     * 匹配是否包含正则内容
     *
     * @param regex 正则表达式
     * @param str   匹配字符串
     * @return 是否匹配
     */
    public static boolean matchString(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }


    /**
     * 检查是否为空，提示信息
     *
     * @param activity activity
     * @param view     检查的TextView
     * @return 是否为空
     */
    public static boolean checkEmpty(Activity activity, TextView view) {
        String text = view.getText().toString().trim();
        String hint = view.getHint().toString().trim();
        if (TextUtils.isEmpty(text)) {
            AppToast.show(activity, hint);
            return true;
        }
        return false;
    }

    /**
     * 检查是否为空，提示信息
     *
     * @param activity activity
     * @param info     检查字符串
     * @param toast    提示字符串
     * @return 是否为空
     */
    public static boolean checkEmpty(Activity activity, String info, String toast) {
        if (TextUtils.isEmpty(info)) {
            AppToast.show(activity, toast);
            return true;
        }
        return false;
    }

    /**
     * 检查是否为空，提示信息
     *
     * @param activity activity
     * @param info     检查字符串
     * @param toast    提示字符串Id
     * @return 是否为空
     */
    public static boolean checkEmpty(Activity activity, String info, int toast) {
        if (TextUtils.isEmpty(info)) {
            AppToast.show(activity, toast);
            return true;
        }
        return false;
    }

    /**
     * 检查正则表达式
     *
     * @param activity activity
     * @param info     检查字符串
     * @param regex    正则表达式
     * @param toast    提示字符串
     * @return 是否匹配
     */
    public static boolean checkRegex(Activity activity, String info, String regex, String toast) {
        boolean matches = !info.matches(regex);
        if (matches) AppToast.show(activity, toast);
        return matches;
    }

    /**
     * 检查正则表达式
     *
     * @param activity activity
     * @param info     检查字符串
     * @param regex    正则表达式
     * @param toast    提示字符串Id
     * @return 是否为空
     */
    public static boolean checkRegex(Activity activity, String info, String regex, int toast) {
        boolean matches = !info.matches(regex);
        if (matches) AppToast.show(activity, toast);
        return matches;
    }

    /**
     * 判断字符串是否有值，如果为null或者是空字符串或者只有空格或者为"null"字符串，则返回true，否则则返回false
     */
    public static boolean isEmpty(String value) {
        return !(value != null && !"".equalsIgnoreCase(value.trim())
                && !"null".equalsIgnoreCase(value.trim()));
    }

    /**
     * 判断多个字符串是否相等，如果其中有一个为空字符串或者null，则返回false，只有全相等才返回true
     */
    public static boolean isEquals(String... agrs) {
        String last = null;
        for (int i = 0; i < agrs.length; i++) {
            String str = agrs[i];
            if (isEmpty(str)) {
                return false;
            }
            if (last != null && !str.equalsIgnoreCase(last)) {
                return false;
            }
            last = str;
        }
        return true;
    }

    /**
     * 设置Edittext输入格式
     * InputFilter[] filters = new InputFilter[]{UtilsUi.getDecimalFilter(REGEX)};
     * editText.setFilters(filters);
     *
     * @param regex 输入内容正则表达式
     * @return
     */
    public static InputFilter getDecimalFilter(final String regex) {
        InputFilter lengthfilter = new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                String reslut = dest.subSequence(0, dstart).toString() + source
                        + dest.subSequence(dstart, dest.length()).toString();
                if (0 == end) {
                    reslut = dest.subSequence(0, dstart).toString() + source;
                    if (dstart + 1 <= dest.length()) {
                        reslut += dest.subSequence(dstart + 1, dest.length())
                                .toString();
                    }
                }
                if (!reslut.matches(regex)) {
                    if (0 == end) {
                        return dest.subSequence(dstart, dend);
                    }
                    return "";
                }
                return null;
            }
        };
        return lengthfilter;
    }

    /**
     * 返回一个高亮spannable
     *
     * @param content 文本内容
     * @param color   高亮颜色
     * @param start   起始位置
     * @param end     结束位置
     * @return 高亮spannable
     */
    public static CharSequence getHighLightText(String content, int color,
                                                int start, int end) {
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        start = start >= 0 ? start : 0;
        end = end <= content.length() ? end : content.length();
        SpannableString spannable = new SpannableString(content);
        CharacterStyle span = new ForegroundColorSpan(color);
        spannable.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    /**
     * 获取链接样式的字符串，即字符串下面有下划线
     *
     * @param resId 文字资源
     * @return 返回链接样式的字符串
     */
    public static Spanned getHtmlStyleString(int resId) {
        StringBuilder sb = new StringBuilder();
        sb.append("<a href=\"\"><u><b>").append(UtilsUi.getString(resId))
                .append(" </b></u></a>");
        return Html.fromHtml(sb.toString());
    }

    /**
     * 格式化文件大小，不保留末尾的0
     */
    public static String formatFileSize(long len) {
        return formatFileSize(len, false);
    }

    /**
     * 格式化文件大小，保留末尾的0，达到长度一致
     */
    public static String formatFileSize(long len, boolean keepZero) {
        String size;
        DecimalFormat formatKeepTwoZero = new DecimalFormat("#.00");
        DecimalFormat formatKeepOneZero = new DecimalFormat("#.0");
        if (len < 1024) {
            size = String.valueOf(len + "B");
        } else if (len < 10 * 1024) {
            // [0, 10KB)，保留两位小数
            size = String.valueOf(len * 100 / 1024 / (float) 100) + "KB";
        } else if (len < 100 * 1024) {
            // [10KB, 100KB)，保留一位小数
            size = String.valueOf(len * 10 / 1024 / (float) 10) + "KB";
        } else if (len < 1024 * 1024) {
            // [100KB, 1MB)，个位四舍五入
            size = String.valueOf(len / 1024) + "KB";
        } else if (len < 10 * 1024 * 1024) {
            // [1MB, 10MB)，保留两位小数
            if (keepZero) {
                size = String.valueOf(formatKeepTwoZero.format(len * 100 / 1024
                        / 1024 / (float) 100))
                        + "MB";
            } else {
                size = String.valueOf(len * 100 / 1024 / 1024 / (float) 100)
                        + "MB";
            }
        } else if (len < 100 * 1024 * 1024) {
            // [10MB, 100MB)，保留一位小数
            if (keepZero) {
                size = String.valueOf(formatKeepOneZero.format(len * 10 / 1024
                        / 1024 / (float) 10))
                        + "MB";
            } else {
                size = String.valueOf(len * 10 / 1024 / 1024 / (float) 10)
                        + "MB";
            }
        } else if (len < 1024 * 1024 * 1024) {
            // [100MB, 1GB)，个位四舍五入
            size = String.valueOf(len / 1024 / 1024) + "MB";
        } else {
            // [1GB, ...)，保留两位小数
            size = String.valueOf(len * 100 / 1024 / 1024 / 1024 / (float) 100)
                    + "GB";
        }
        return size;
    }
}
