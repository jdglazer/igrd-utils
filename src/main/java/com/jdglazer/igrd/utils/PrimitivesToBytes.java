package com.jdglazer.igrd.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class PrimitivesToBytes {
	
	public static byte[] getBigEndianShort(short value) {
		return new byte[] {
				(byte)(value>>>8),
				(byte)(value)
		};
	}
	
	public static byte[] getBigEndianInt(int value) {
		return new byte[] {
				(byte)(value>>>24),
				(byte)(value>>>16),
				(byte)(value>>>8),
				(byte)(value)
		};
	}
	
	public static byte[] getBigEndianLong(long value) {
		return new byte[] {
				(byte)(value>>>56),
				(byte)(value>>>48),
				(byte)(value>>>40),
				(byte)(value>>>32),
				(byte)(value>>>24),
				(byte)(value>>>16),
				(byte)(value>>>8),
				(byte)(value)
		};
	}
	
	public static byte[] getFloat(float value, ByteOrder order) {
		return ByteBuffer.allocate(4).order(order).putFloat(value).array();
	}
	
	public static byte[] getDouble(double value, ByteOrder order) {
		return ByteBuffer.allocate(8).order(order).putDouble(value).array();
	}
}
