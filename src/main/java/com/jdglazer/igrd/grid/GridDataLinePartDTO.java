package com.jdglazer.igrd.grid;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import com.jdglazer.igrd.IGRDCommonDTO;

public class GridDataLinePartDTO extends IGRDCommonDTO {
    /**
     * line -> part offset 0
     */
	private float startLongitude;
	
    /**
     * line -> part offset 4
     */
	private int pointCount = 0;
	
    /**
     * line -> part offset 8
     */
	private int segmentCount = 0;
	
	private short segmentIndexSize;
	
	private ArrayList<GridDataLinePartSegmentDTO> segments = new ArrayList<GridDataLinePartSegmentDTO>();
	
	public GridDataLinePartDTO(short segmentIndexSize) {
		this.segmentIndexSize = segmentIndexSize;
	}
	
	/**
	 * @return the startLongitude
	 */
	public float getStartLongitude() {
		return startLongitude;
	}

	/**
	 * @param startLongitude the startLongitude to set
	 */
	public void setStartLongitude(float startLongitude) {
		this.startLongitude = startLongitude;
	}

	/**
	 * @return the pointCount
	 */
	public int getPointCount() {
		return pointCount;
	}

	/**
	 * @return the segmentCount
	 */
	public int getSegmentCount() {
		return segmentCount;
	}
	
	/**
	 * 
	 */
	public synchronized GridDataLinePartSegmentDTO getSegment( int index ) {
		return segments.get( index );
	}
	
	/**
	 * 
	 * @param segment
	 */
	public synchronized void addSegment( GridDataLinePartSegmentDTO segment ) {
		segments.add( segment );
		pointCount += segment.getSegmentLength();
		segmentCount++;
	}
	
	@Override
	public synchronized ByteBuffer getByteBuffer() {
		ByteBuffer buffer = ByteBuffer.allocate(getByteSize());
		
		buffer.putFloat(startLongitude);
		buffer.putInt(pointCount);
		buffer.putInt(segmentCount);
		
		for(GridDataLinePartSegmentDTO segment: segments) {
			buffer.put(segment.getByteBuffer());
		}
		
		return buffer;
	}

	@Override
	public int getByteSize() {
		return 12+segmentCount*(segmentIndexSize+2);
	}
}
