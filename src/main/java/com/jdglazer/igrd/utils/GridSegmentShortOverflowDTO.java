/**
 * A class to allow us to short information on a short type variable that has overflowed 
 * the bounds of a short. It can return the short as an integer
 */
package com.jdglazer.igrd.utils;

import java.nio.ByteBuffer;

import com.jdglazer.igrd.IGRDCommonDTO;

public class GridSegmentShortOverflowDTO extends IGRDCommonDTO {
	
	private int partOffset;
	
	private int segmentOffset;
	
	private short overflowIndex;
	
	private short overflowValue;

	/**
	 * @return the segmentOffset
	 */
	public int getSegmentOffset() {
		return segmentOffset;
	}

	/**
	 * @param segmentOffset the segmentOffset to set
	 */
	public synchronized void setSegmentOffset(int segmentOffset) {
		this.segmentOffset = segmentOffset;
	}

	/**
	 * @return the overflowIndex
	 */
	public short getOverflowIndex() {
		return overflowIndex;
	}

	/**
	 * @param overflowIndex the overflowIndex to set
	 */
	public synchronized void setOverflowIndex(short overflowIndex) {
		this.overflowIndex = overflowIndex;
	}
	

	public int getPartOffset() {
		return partOffset;
	}

	public synchronized void setPartOffset(int partOffset) {
		this.partOffset = partOffset;
	}
	/**
	 * returns an overflow DTO for an integer or null if the integer is 
	 * sufficiently small
	 */
	public static GridSegmentShortOverflowDTO getOverflowDTO( int i, int segmentOffset, int partOffset ) {
		if( i > 32767 ) {
			GridSegmentShortOverflowDTO sodto = new GridSegmentShortOverflowDTO();
			sodto.setOverflowIndex((short) (i / 32767) );
			sodto.setOverflowValue((short) (i % 32767) );
			sodto.setSegmentOffset(segmentOffset);
			sodto.setPartOffset(partOffset);
			
			return sodto;
		}
		if( i < -32768 ) {
			GridSegmentShortOverflowDTO sodto = new GridSegmentShortOverflowDTO();
			sodto.setOverflowIndex((short) (i / -32768) );
			sodto.setOverflowValue((short) (i % -32768) );
			sodto.setSegmentOffset(segmentOffset);
			sodto.setPartOffset(partOffset);
			return sodto;
		}
		return null;
	}
	
	/**
	 * Return the integer from this short overflow
	 * @return
	 */
	public int getInt() {
		if( overflowIndex > 0 ) {
			return 32767 * (int) overflowIndex + (int) overflowValue;
		} else {
			return -32768 * (int) overflowIndex + (int) overflowValue;
		}
	}

	public short getOverflowValue() {
		return overflowValue;
	}

	public synchronized void setOverflowValue(short overflowValue) {
		this.overflowValue = overflowValue;
	}

	@Override
	public synchronized ByteBuffer getByteBuffer() {
		
		ByteBuffer buffer = ByteBuffer.allocate(getByteSize());
		
		buffer.putInt(partOffset);
		buffer.putInt(segmentOffset);
		buffer.putShort(overflowIndex);
		
		return buffer;
	}

	@Override
	public int getByteSize() {
		return 10;
	}
	
}
