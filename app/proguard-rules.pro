# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable
# need to keep these classes and their methods because they are used by resampling code
-keep public class com.ps.vh.model.TargetArea { *; }
-keep public class com.ps.vh.model.Target { *; }
-keep public class com.ps.vh.model.TargetRequest { *; }
-keep public class com.ps.vh.http.HttpUtil { *; }
-keep public class com.ps.vh.http.*



# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-printusage c:/ps/temp/chproguardresults.txt