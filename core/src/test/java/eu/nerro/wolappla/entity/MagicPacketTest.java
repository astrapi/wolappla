package eu.nerro.wolappla.entity;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MagicPacketTest {
  @Test
  public void synchronizationStreamShouldContainSixBytesOf0xFF() {
    MagicPacket magicPacket = new MagicPacket(MacAddress.valueOf("11-22-33-44-55-66"));
    byte[] magicBytes = magicPacket.getMagicBytes();
    byte[] actualSynchronizationStream = Arrays.copyOfRange(magicBytes, 0, 6);
    byte[] expectedSynchronizationStream = { -1, -1, -1, -1, -1, -1 };

    assertThat(actualSynchronizationStream, equalTo(expectedSynchronizationStream));
  }

  @Test
  public void getMagicBytes_arrayLengthShouldBe102() {
    MagicPacket magicPacket = new MagicPacket(MacAddress.valueOf("11-22-33-44-55-66"));
    byte[] magicBytes = magicPacket.getMagicBytes();

    assertThat(magicBytes.length, is(102));
  }

  @Test
  public void getMagicBytes_shouldReturnMagicPacketAsByteArray() {
    MagicPacket magicPacket = new MagicPacket(MacAddress.valueOf("11-22-33-44-55-66"));
    byte[] actualMagicBytes = magicPacket.getMagicBytes();
    byte[] expectedMagicBytes = {
        -1, -1, -1, -1, -1, -1,
        17, 34, 51, 68, 85, 102,
        17, 34, 51, 68, 85, 102,
        17, 34, 51, 68, 85, 102,
        17, 34, 51, 68, 85, 102,
        17, 34, 51, 68, 85, 102,
        17, 34, 51, 68, 85, 102,
        17, 34, 51, 68, 85, 102,
        17, 34, 51, 68, 85, 102,
        17, 34, 51, 68, 85, 102,
        17, 34, 51, 68, 85, 102,
        17, 34, 51, 68, 85, 102,
        17, 34, 51, 68, 85, 102,
        17, 34, 51, 68, 85, 102,
        17, 34, 51, 68, 85, 102,
        17, 34, 51, 68, 85, 102,
        17, 34, 51, 68, 85, 102
    };

    assertThat(actualMagicBytes.length, equalTo(expectedMagicBytes.length));
    assertThat(actualMagicBytes, equalTo(expectedMagicBytes));
  }
}
