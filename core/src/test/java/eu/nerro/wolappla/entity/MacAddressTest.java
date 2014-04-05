package eu.nerro.wolappla.entity;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MacAddressTest {
  @Test(expected = IllegalArgumentException.class)
  public void valueOf_shouldThrowIAEIfAddressByteArrayIsNull() {
    MacAddress.valueOf((byte[]) null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void valueOf_shouldThrowIAEIfAddressArrayLengthIsNotSix() {
    byte[] invalidAddress = { 1, 2, 3, 4, 5 };
    MacAddress.valueOf(invalidAddress);
  }

  @Test(expected = IllegalArgumentException.class)
  public void valueOf_shouldThrowIAEIfAddressCharSequenceIsNull() {
    MacAddress.valueOf((String) null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void valueOf_shouldThrowIAEIfAddressIsNotValid() {
    MacAddress.valueOf("This is not a MAC address.");
  }

  @Test
  public void valueOf_addressWithoutDelimiters() {
    byte[] actualMacBytes = MacAddress.valueOf("1a2b3c4d5eff").getBytes();
    byte[] expectedMacBytes = { 26, 43, 60, 77, 94, -1 };

    assertThat(actualMacBytes, equalTo(expectedMacBytes));
  }

  @Test
  public void valueOf_addressWithColonAsDelimiter() {
    byte[] actualMacBytes = MacAddress.valueOf("1a:2b:3c:4d:5e:ff").getBytes();
    byte[] expectedMacBytes = { 26, 43, 60, 77, 94, -1 };

    assertThat(actualMacBytes, equalTo(expectedMacBytes));
  }

  @Test
  public void valueOf_addressWithHyphenAsDelimiter() {
    byte[] actualMacBytes = MacAddress.valueOf("1a-2b-3c-4d-5e-ff").getBytes();
    byte[] expectedMacBytes = { 26, 43, 60, 77, 94, -1 };

    assertThat(actualMacBytes, equalTo(expectedMacBytes));
  }

  @Test
  public void valueOf_addressWithMixedDelimiters() {
    byte[] actualMacBytes = MacAddress.valueOf("1a-2b:3c-4d:5e-ff").getBytes();
    byte[] expectedMacBytes = { 26, 43, 60, 77, 94, -1 };

    assertThat(actualMacBytes, equalTo(expectedMacBytes));
  }

  @Test
  public void toString_shouldUseColonsAsDefaultDelimiter() {
    String address = MacAddress.valueOf("112233445566").toString();

    assertThat(address, equalTo("11:22:33:44:55:66"));
  }

  @Test
  public void convertToString_shouldUseColonsIfDelimiterIsCOLON() {
    String address = MacAddress.valueOf("112233445566").convertToString(MacAddress.Delimiter.COLON);

    assertThat(address, equalTo("11:22:33:44:55:66"));
  }

  @Test
  public void convertToString_shouldUseHyphensIfDelimiterIsHYPHEN() {
    String address = MacAddress.valueOf("112233445566").convertToString(MacAddress.Delimiter.HYPHEN);

    assertThat(address, equalTo("11-22-33-44-55-66"));
  }

  @Test
  public void convertToString_shouldUseNothingIfDelimiterIsNONE() {
    String address = MacAddress.valueOf("112233445566").convertToString(MacAddress.Delimiter.NONE);

    assertThat(address, equalTo("112233445566"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void validate_shouldThrowIAEIfAddressIsNull() {
    MacAddress.validate(null);
  }

  @Test
  public void validate_shouldBeTrueIfAddressIsValid() {
    boolean validationFinding = MacAddress.validate("1a-2b-3c-4d-5e-6f");

    assertThat(validationFinding, is(true));
  }

  @Test
  public void validate_shouldBeFalseIfAddressIsNotValid() {
    boolean validationFinding = MacAddress.validate("It cannot be a valid mac address.");

    assertThat(validationFinding, is(false));
  }

  @Test
  public void equals_shouldBeEqualIfAddressesAreSame() {
    MacAddress firstAddress = MacAddress.valueOf("11-22-33-44-55-66");
    MacAddress secondAddress = MacAddress.valueOf("11-22-33-44-55-66");

    boolean adressesAreSame = firstAddress.equals(secondAddress);

    assertThat(adressesAreSame, is(true));
  }

  @Test
  public void equals_shouldBeNotEqualIfAdressesAreNotSame() {
    MacAddress firstAddress = MacAddress.valueOf("11-22-33-44-55-66");
    MacAddress secondAddress = MacAddress.valueOf("66-55-44-33-22-11");

    boolean addressesAreSame = firstAddress.equals(secondAddress);

    assertThat(addressesAreSame, is(false));
  }
}
