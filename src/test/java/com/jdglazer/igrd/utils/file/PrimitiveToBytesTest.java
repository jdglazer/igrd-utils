package com.jdglazer.igrd.utils.file;

import static org.junit.Assert.assertEquals;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.junit.Test;

import com.jdglazer.igrd.utils.PrimitivesToBytes;

public class PrimitiveToBytesTest {
	@Test
	public void testShort() {
		short expectedValue = (short)12345;
		ByteBuffer buffer = ByteBuffer.allocate(2).put(PrimitivesToBytes.getBigEndianShort(expectedValue)).position(0);
		assertEquals("",expectedValue,buffer.getShort());
	}
	
	@Test
	public void testDouble() {
		double expectedValue = 13480134803431.12312;
		ByteBuffer buffer = ByteBuffer.allocate(8).put(PrimitivesToBytes.getDouble(expectedValue,ByteOrder.BIG_ENDIAN)).position(0);
		assertEquals("",expectedValue,buffer.getDouble(), 0);
	}
}
