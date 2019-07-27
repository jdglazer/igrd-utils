package com.jdglazer.igrd.utils.file;

import java.io.Closeable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class FileReader implements Closeable {
	
	protected byte getByteFrom(ByteOrder endianness, int offset) {
		return getByteBuffer(endianness,offset,1).get();
	}
	
	protected short getShortFrom(ByteOrder endianness, int offset) {
		return getByteBuffer(endianness,offset,2).getShort();
	}
	
	protected int getIntFrom(ByteOrder endianness, int offset) {
		return getByteBuffer(endianness,offset,4).getInt();
	}
	
    protected long getLongFrom(ByteOrder endianness, int offset) {
    	return getByteBuffer(endianness,offset,8).getLong();
    }
    
    protected float getFloatFrom(ByteOrder endianness, int offset) {
    	return getByteBuffer(endianness,offset,4).getFloat();
    }
    
    protected double getDoubleFrom(ByteOrder endianness, int offset) {
    	return getByteBuffer(endianness,offset,8).getDouble();
    }
    
    protected String getString(int offset, int length) {
    	ByteBuffer buffer = getByteBuffer(ByteOrder.BIG_ENDIAN, offset, length);
    	byte [] byteArr = new byte[length];
    	buffer.get(byteArr, buffer.position(), length);
    	return new String(byteArr);
    }
    
    protected abstract ByteBuffer getByteBuffer(ByteOrder endianness, int offset, int readLen);
    
    
}
