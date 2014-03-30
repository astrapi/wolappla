package eu.nerro.wolappla.entity;

import org.junit.Test;

public class MacAddressTest {
  @Test(expected = IllegalArgumentException.class)
  public void valueOf_shouldThrowIAEIfAddressIsNull() {
    MacAddress.valueOf(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void valueOf_shouldThrowIAEIfAddressArrayLengthIsNotSix() {
    byte[] invalidAddress = { 1, 2, 3, 4, 5 };
    MacAddress.valueOf(invalidAddress);
  }
}
