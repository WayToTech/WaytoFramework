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
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#-----------------混淆配置设定------------------------------------------------------------------------
-optimizationpasses 5                                                       #指定代码压缩级别
-dontusemixedcaseclassnames                                                #混淆时不会产生形形色色的类名
-dontskipnonpubliclibraryclasses                                           #指定不忽略非公共类库
-dontpreverify                                                               #不预校验，如果需要预校验，是-dontoptimize
-ignorewarnings                                                              #屏蔽警告
-verbose                                                                      #混淆时记录日志
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*     #优化

#系统Library
-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
-dontwarn android.support.v7.**
-keep class android.support.v7.**{ *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment
-dontwarn com.google.auto.**
-dontwarn com.google.common.**
-dontwarn com.squareup.javapoet.**
-dontwarn jcifs.http.NetworkExplorer.**

#greenDao 代码混淆配制
-dontwarn org.greenrobot.greendao.**
-keep class org.greenrobot.greendao.**{*;}
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
     public static java.lang.String TABLENAME;
}
-keep class **$Properties

#Butter Knife 代码混淆配制
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-dontwarn butterknife.compiler.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

#Retrofit 代码混淆配制
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

#Glide 代码混淆配制
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#eventBus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.** { *; }
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

#ArcGis
-dontwarn com.esri.**
-keep class com.esri.** { *; }

#七牛服务 代码混淆配制
-keep class com.qiniu.** { *; }
-keep class com.qiniu.**{public <init>();}
-ignorewarnings

#Bugly混淆配制
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

-keep class com.yunwei.map.** { *; }
-keep class com.yunwei.frame.entity.** { *; }

#保持 Serializable 不被混淆并且enum 类也不被混淆
-keepclassmembers class * implements java.io.Serializable {
      static final long serialVersionUID;
        private static final java.io.ObjectStreamField[] serialPersistentFields;
        !static !transient <fields>;
        !private <fields>;
        !private <methods>;
        private void writeObject(java.io.ObjectOutputStream);
        private void readObject(java.io.ObjectInputStream);
        java.lang.Object writeReplace();
        java.lang.Object readResolve();
}
