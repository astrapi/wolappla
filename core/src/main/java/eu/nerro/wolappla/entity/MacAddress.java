package eu.nerro.wolappla.entity;

import java.util.Arrays;

/**
 * This class represents a MAC address according to the IEEE EUI-48 rule. <p> The 48-bit address space contains
 * potentially 2<sup>48</sup> or 281,474,976,710,656 possible MAC addresses. </p>
 */
public final class MacAddress {
  private static final int BYTE_ARRAY_LENGTH = 6;

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

  @Override
  public String toString() {
    return convertToString(Delimiter.HYPHEN);
  }

  public String convertToString(Delimiter delimiter) {
    return "";
  }

  public enum Delimiter {
    COLON(":", 2),
    HYPHEN("-", 2),
    NONE("", 1);

    private final String delimiterCharacter;
    private final int characterNumber;

    private Delimiter(String delimiterCharacter, int characterNumber) {
      this.delimiterCharacter = delimiterCharacter;
      this.characterNumber = characterNumber;
    }

    public String getDelimiterCharacter() {
      return delimiterCharacter;
    }

    public int getCharacterNumber() {
      return characterNumber;
    }
  }
}
