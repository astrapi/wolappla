package eu.nerro.wolappla.provider;

import android.net.Uri;

/**
 * Contract class for interacting with {@link DeviceProvider}.
 */
public class DeviceContract {
  public static final String CONTENT_AUTHORITY = "eu.nerro.wolappla";
  public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

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
}
