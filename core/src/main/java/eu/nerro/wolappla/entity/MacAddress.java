package eu.nerro.wolappla.entity;

import java.util.Arrays;

/**
 * This class represents a MAC address according to the IEEE EUI-48 rule. <p> The 48-bit address space contains
 * potentially 2<sup>48</sup> or 281,474,976,710,656 possible MAC addresses. </p>
 */
public final class MacAddress {
  private static final int BYTE_ARRAY_LENGTH = 6;
  private static final String EUI_48 = "^(?i)(?:[0-9a-f]{2}[-:]?){5}[0-9a-f]{2}|(?:[0-9a-f]{4}\\.?){2}[0-9a-f]{4}$";

  private final byte[] address;

  private MacAddress(final byte[] address) {
    this.address = Arrays.copyOf(address, BYTE_ARRAY_LENGTH);
  }

  public static MacAddress valueOf(byte[] address) {
    if (address == null) {
      throw new IllegalArgumentException("MAC address bytes cannot be null.");
    }
    if (address.length != BYTE_ARRAY_LENGTH) {
      throw new IllegalArgumentException("MAC address byte array must be 6 bytes.");
    }

    return new MacAddress(address);
  }

  public static MacAddress valueOf(CharSequence address) {
    if (address == null) {
      throw new IllegalArgumentException("MAC address cannot be null.");
    }

    String addressAsString = address.toString();
    if (!addressAsString.matches(EUI_48)) {
      throw new IllegalArgumentException("Input string is not a valid MAC address.");
    }

    String cleanMacAddress = addressAsString.replaceAll("(?i)[^0-9a-f]", "");
    byte[] macBytes = new byte[BYTE_ARRAY_LENGTH];
    for (int i = 0; i < macBytes.length; i++) {
      macBytes[i] = (byte) Integer.parseInt(cleanMacAddress.substring(2 * i, 2 * i + 2), 16);
    }

    return new MacAddress(macBytes);
  }

  public static boolean validate(String address) {
    if (address == null) {
      throw new IllegalArgumentException("Input string cannot be null.");
    }

    return address.matches(EUI_48);
  }

  public byte[] getBytes() {
    return Arrays.copyOf(address, BYTE_ARRAY_LENGTH);
  }

  @Override
  public String toString() {
    return convertToString(Delimiter.COLON);
  }

  public String convertToString(Delimiter delimiter) {
    StringBuilder sb = new StringBuilder(17);
    String macAddress = macBytesToHex(address);

    int groupLength = delimiter.getGroupLength();
    for (int i = 0; i < macAddress.length() - groupLength; i = i + groupLength) {
      sb.append(macAddress.substring(i, i + groupLength));
      sb.append(delimiter.getDelimiterCharacter());
    }
    sb.append(macAddress.substring(macAddress.length() - groupLength, macAddress.length()));

    return sb.toString();
  }

  private String macBytesToHex(byte[] macBytes) {
    StringBuilder sb = new StringBuilder(12);
    for (byte b : macBytes) {
      int value = b & 0xff;
      if (value < 16) {
        sb.append("0");
      }
      sb.append(Integer.toHexString(value));
    }

    return sb.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof MacAddress)) {
      return false;
    }

    MacAddress ohter = (MacAddress) obj;

    return Arrays.equals(address, ohter.address);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(address);
  }

  public enum Delimiter {
    COLON(":", 2),
    HYPHEN("-", 2),
    NONE("", 1);

    private final String delimiterCharacter;
    private final int groupLength;

    private Delimiter(String delimiterCharacter, int groupLength) {
      this.delimiterCharacter = delimiterCharacter;
      this.groupLength = groupLength;
    }

    public String getDelimiterCharacter() {
      return delimiterCharacter;
    }

    public int getGroupLength() {
      return groupLength;
    }
  }
}
