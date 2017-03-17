# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}

#gson
-keep class com.yijia.patient.ui.protocol.**{*;}
-keep class zero.library.base.bean.**{*;}
-keep class com.google.**{*;}
-dontwarn com.google.**
-keepclassmembers class * implements java.io.Serializable {
     static final long serialVersionUID;
     private static final java.io.ObjectStreamField[] serialPersistentFields;
     private void writeObject(java.io.ObjectOutputStream);
     private void readObject(java.io.ObjectInputStream);
     java.lang.Object writeReplace();
     java.lang.Object readResolve();
}

# Close Log
-keepclassmembers class * extends android.app.Activity {
public void *(android.view.View);
}

-keep class * implements android.os.Parcelable {
public static final android.os.Parcelable$Creator *;
}

-dontwarn android.support.**
-keepclassmembers class **.R$* {
public static *;
}

-assumenosideeffects class android.util.Log {
public static boolean isLoggable(java.lang.String,int);
public static int v(...);
public static int i(...);
public static int w(...);
public static int d(...);
public static int e(...);
}

#other
-dontwarn com.zero.library.**
-dontwarn com.yijia.patient.**
-dontwarn retrofit2.**
-dontwarn rx.**
-dontwarn junit.**
-dontwarn org.**
-dontwarn okio.**
-dontwarn okhttp3.**
-dontwarn com.squareup.**
-dontwarn cn.jpush.**

-keep class cn.jpush.**{*;}
-keep class org.**{*;}
-keep class rx.**{*;}
-keep class okio.**{*;}
-keep class junit.**{*;}
-keep class okhttp3.**{*;}
-keep class retrofit2.**{*;}
-keep class com.squareup.**{*;}
-keep class com.orhanobut.logger.**{*;}

#umeng
-dontshrink
-dontoptimize
-dontwarn com.google.android.maps.**
-dontwarn android.webkit.WebView
-dontwarn com.umeng.**
-dontwarn com.tencent.weibo.sdk.**
-dontwarn com.facebook.**

-keep enum com.facebook.**
-keepattributes Exceptions,InnerClasses,Signature
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable

-keep class com.umeng.**{*;}
-keep public interface com.facebook.**
-keep public interface com.tencent.**
-keep public interface com.umeng.socialize.**
-keep public interface com.umeng.socialize.sensor.**
-keep public interface com.umeng.scrshot.**
-keep public class com.umeng.socialize.* {*;}
-keep public class javax.**
-keep public class android.webkit.**
-keep class com.facebook.**
-keep class com.umeng.scrshot.**
-keep public class com.tencent.** {*;}
-keep class com.umeng.socialize.sensor.**
-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
-keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
-keep class im.yixin.sdk.api.YXMessage {*;}
-keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}

-keep public class [com.app.lib].R$*{
    public static final int *;
}
-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}
-keep public class [com.xinyi.patient].R$*{
   public static final int *;
}
-keepclassmembers enum * {
   public static **[] values();
   public static ** valueOf(java.lang.String);
}
