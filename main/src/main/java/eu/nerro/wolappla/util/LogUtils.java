package eu.nerro.wolappla.util;

import android.util.Log;

import eu.nerro.wolappla.BuildConfig;

/**
 * Helper methods that make logging more consistent throughout the app.
 */
public class LogUtils {
  private static final String LOG_PREFIX = "wolappla_";
  private static final int LOG_PREFIX_LENGTH = LOG_PREFIX.length();
  private static final int MAX_LOG_TAG_LENGTH = 25;

  private LogUtils() {
  }

  public static String makeLogTag(String name) {
    if (name.length() > MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH) {
      return LOG_PREFIX + name.substring(0, MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH - 1);
    }

    return LOG_PREFIX + name;
  }

  public static String makeLogTag(Class clazz) {
    return makeLogTag(clazz.getSimpleName());
  }

  public static void LOGD(final String tag, String message) {
    if (BuildConfig.DEBUG || Log.isLoggable(tag, Log.DEBUG)) {
      Log.d(tag, message);
    }
  }

  public static void LOGD(final String tag, String message, Throwable cause) {
    if (BuildConfig.DEBUG || Log.isLoggable(tag, Log.DEBUG)) {
      Log.d(tag, message, cause);
    }
  }

  public static void LOGV(final String tag, String message) {
    if (BuildConfig.DEBUG || Log.isLoggable(tag, Log.VERBOSE)) {
      Log.v(tag, message);
    }
  }

  public static void LOGV(final String tag, String message, Throwable cause) {
    if (BuildConfig.DEBUG || Log.isLoggable(tag, Log.VERBOSE)) {
      Log.v(tag, message, cause);
    }
  }

  public static void LOGW(final String tag, String message) {
    Log.w(tag, message);
  }

  public static void LOGW(final String tag, String message, Throwable cause) {
    Log.w(tag, message, cause);
  }
}
