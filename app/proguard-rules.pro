#jackson
 -keepattributes *Annotation*,EnclosingMethod,Signature
 -keepnames class com.fasterxml.jackson.** { *; }
  -dontwarn com.fasterxml.jackson.databind.**
  -keep class org.codehaus.** { *; }
  -keepclassmembers public final enum org.codehaus.jackson.annotate.JsonAutoDetect$Visibility {
  public static final org.codehaus.jackson.annotate.JsonAutoDetect$Visibility *; }
 -keep public class your.class.** {
   public void set*(***);
   public *** get*();
 }

 # Retrofit 2.X
 ## https://square.github.io/retrofit/ ##

 -dontwarn retrofit2.**
 -keep class retrofit2.** { *; }
 -keepattributes Signature
 -keepattributes Exceptions

 -keepclasseswithmembers class * {
     @retrofit2.http.* <methods>;
 }

 -dontwarn okhttp3.**
 -dontwarn okio.**

  -keepnames class jp.wasabeef.recyclerview.animators.** { *; }