package eu.nerro.wolappla.provider;

import android.net.Uri;
import android.provider.BaseColumns;

import java.util.List;

/**
 * Contract class for interacting with {@link DeviceProvider}.
 */
public class DeviceContract {
  public static final String CONTENT_AUTHORITY = "eu.nerro.wolappla";
  public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
  public static final String PATH_DEVICES = "devices";

  interface DevicesColumns {
    /** Name describing this device. */
    String DEVICE_NAME = "device_name";
    /** MAC address of the network card of this device. */
    String DEVICE_MAC_ADDRESS = "device_mac_address";
    /** Network broadcast IP. */
    String DEVICE_IP_ADDRESS = "device_ip_address";
    /** Port on which this device is listening for magic packets. */
    String DEVICE_PORT = "device_port";
  }

  public static class Devices implements DevicesColumns, BaseColumns {
    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_DEVICES).build();
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.wolappla.device";
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.wolappla.device";

    /** Default "ORDER BY" clause. */
    public static final String DEFAULT_SORT = DevicesColumns.DEVICE_NAME + " ASC";

    /** Build {@link Uri} for requested device id. */
    public static Uri buildDeviceUri(String deviceId) {
      return CONTENT_URI.buildUpon().appendPath(deviceId).build();
    }

    /** Build {@link Uri} for requested device id. */
    public static Uri buildDeviceUri(Long deviceId) {
      return CONTENT_URI.buildUpon().appendPath(String.valueOf(deviceId)).build();
    }

    /** Read device id from {@link Devices} {@link Uri}. */
    public static String getDeviceId(Uri uri) {
      final List<String> pathSegments = uri.getPathSegments();
      return pathSegments != null ? pathSegments.get(1) : null;
    }
  }
}
