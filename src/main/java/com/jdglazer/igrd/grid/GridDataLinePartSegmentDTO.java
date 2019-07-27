package com.jdglazer.igrd.grid;

import java.nio.ByteBuffer;

import com.jdglazer.igrd.IGRDCommonDTO;
import com.jdglazer.igrd.utils.PrimitivesToBytes;

public class GridDataLinePartSegmentDTO extends IGRDCommonDTO {
	
	private short segmentIndexSize;
	/**
	 * line -> part -> segment offset 0
	 */
	private short segmentIndex;
	
	/**
	 * line -> part -> segment offset 1 or 2
	 */
	private short segmentLength;
	
	public GridDataLinePartSegmentDTO(short segmentIndexSize) {
		this.segmentIndexSize = segmentIndexSize;
	}

	/**
	 * @return the segmentIndex
	 */
	public short getSegmentIndex() {
		return segmentIndex;
	}

	/**
	 * @param segmentIndex the segmentIndex to set
	 */
	public void setSegmentIndex(short segmentIndex) {
		this.segmentIndex = segmentIndex;
	}

	/**
	 * @return the segmentLength
	 */
	public short getSegmentLength() {
		return segmentLength;
	}

	/**
	 * @param segmentLength the segmentLength to set
	 */
	public void setSegmentLength(short segmentLength) {
		this.segmentLength = segmentLength;
	}

	@Override
	public synchronized ByteBuffer getByteBuffer() {
		ByteBuffer buffer = ByteBuffer.allocate(getByteSize());
		
		if(segmentIndexSize == 1) {
			buffer.put((byte)segmentIndex);
		} else {
			buffer.putShort(segmentIndex);
		}
		
		buffer.putShort(segmentLength);
		
		return buffer;
	}

	@Override
	public int getByteSize() {
		return segmentIndexSize + 2;
	}

}
