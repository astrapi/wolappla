package eu.nerro.wolappla.entity;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class MagicPacketTest {
  @Test
  public void getSynchronizationStream_shouldNotBeNull() {
    MagicPacket magicPacket = new MagicPacket();
    byte[] synchronizationStream = magicPacket.getSynchronizationStream();

    assertThat(synchronizationStream, is(notNullValue()));
  }

  @Test
  public void getSynchronizationStream_arrayLengthShouldBeSix() {
    MagicPacket magicPacket = new MagicPacket();
    byte[] stream = magicPacket.getSynchronizationStream();

    assertThat(stream.length, is(6));
  }

  @Test
  public void getSynchronizationStream_shouldContainSixBytesOf0xFF() {
    MagicPacket magicPacket = new MagicPacket();
    byte[] actualStream = magicPacket.getSynchronizationStream();
    byte[] expectedStream = { -1, -1, -1, -1, -1, -1 };

    assertThat(actualStream, equalTo(expectedStream));
  }
}
