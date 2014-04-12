package eu.nerro.wolappla.provider;

import android.net.Uri;
import android.provider.BaseColumns;

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

    /** Build {@link Uri} for requested {@link #DEVICE_NAME}. */
    public static Uri buildDeviceUri(String deviceName) {
      return CONTENT_URI.buildUpon().appendPath(deviceName).build();
    }
  }
}
