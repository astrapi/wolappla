package eu.nerro.wolappla.entity;

/**
 * The <i>magic packet</i> is a broadcast frame that is required to turn on computers through wake-on-lan technology.
 * A magic packet format look like this:
 * <table border="1">
 * <thead>
 * <tr>
 * <th>Synchronization Stream</th>
 * <th>Target MAC</th>
 * <th>Password (optional)</th>
 * </tr>
 * </thead>
 * <tbody>
 * <tr>
 * <td align="center">6</td>
 * <td align="center">96</td>
 * <td align="center">0, 4 or 6</td>
 * </tr>
 * </tbody>
 * </table>
 * <ul>
 * <li>Synchronization Stream is defined as 6 bytes of <code>0xff</code>.</li>
 * <li>Target MAC block contains 16 duplications of the IEEEaddress of the target, with no breaks or interruptions.</li>
 * <li>Password field is optional, but if present, contains either 4 bytes or 6 bytes.</li>
 * </ul>
 */
public class MagicPacket {
  private static final int SYNCHRONIZATION_STREAM_LENGTH = 6;
  private final byte[] frame = new byte[102];

  public MagicPacket(MacAddress macAddress) {
    byte[] macBytes = macAddress.getBytes();

    for (int i = 0; i < SYNCHRONIZATION_STREAM_LENGTH; i++) {
      frame[i] = (byte) 0xff;
    }

    for (int i = SYNCHRONIZATION_STREAM_LENGTH; i < frame.length; i += macBytes.length) {
      System.arraycopy(macBytes, 0, frame, i, macBytes.length);
    }
  }

  /**
   * Returns the broadcast frame that can be sent to turn on computers through wake-on-lan technology.
   *
   * @return the magic packet as byte array.
   */
  public byte[] getMagicBytes() {
    return frame;
  }
}
