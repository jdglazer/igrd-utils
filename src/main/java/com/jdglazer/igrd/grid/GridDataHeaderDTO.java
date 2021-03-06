package com.jdglazer.igrd.grid;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import com.jdglazer.igrd.IGRDCommonDTO;

public class GridDataHeaderDTO extends IGRDCommonDTO implements Serializable {

	/**
	 * type of index identifier used for segments
	 */
	
	public static final short TYPE_BYTE = 1;
	public static final short TYPE_SHORT = 2;
	
	/**
	 * at grid data offset 0
	 */
	private double minimumLatitude;
	
	/**
	 * at grid data offset 8
	 */
	private double minimumLongitude;
	
	/**
	 * at grid data offset 16
	 */
	private double maximumLatitude;
	
	/**
	 * at grid data offset 24
	 */
	private double maximumLongitude;
	
	/**
	 * at grid data offset 32
	 */
	private double latitudeInterval;
	
	/**
	 * at grid data offset of 40
	 */
	private double longitudeInterval;
	
	/**
	 * at grid data offset 48
	 */
	private int latitudeLineCount;
	
	/**
	 * at grid data offset 52
	 */
	private short indexIdentifierType;
	
	/**
	 * at grid data offset 54
	 * 
	 * index of the line mapped to the offset position in the grid data
	 */
	private Map<Integer, Integer> lineStartPositions = new HashMap<Integer, Integer>();
	
	public GridDataHeaderDTO() {
		super();
	}
	
	/**
	 * @return the minimumLatitude
	 */
	public double getMinimumLatitude() {
		return minimumLatitude;
	}

	/**
	 * @param minimumLatitude the minimumLatitude to set
	 */
	public synchronized void setMinimumLatitude(double minimumLatitude) {
		this.minimumLatitude = minimumLatitude;
	}

	/**
	 * @return the minimumLongitude
	 */
	public synchronized double getMinimumLongitude() {
		return minimumLongitude;
	}

	/**
	 * @param minimumLongitude the minimumLongitude to set
	 */
	public void setMinimumLongitude(double minimumLongitude) {
		this.minimumLongitude = minimumLongitude;
	}

	/**
	 * @return the maximumLatitude
	 */
	public double getMaximumLatitude() {
		return maximumLatitude;
	}

	/**
	 * @param maximumLatitude the maximumLatitude to set
	 */
	public synchronized void setMaximumLatitude(double maximumLatitude) {
		this.maximumLatitude = maximumLatitude;
	}

	/**
	 * @return the maximumLongitude
	 */
	public double getMaximumLongitude() {
		return maximumLongitude;
	}

	/**
	 * @param maximumLongitude the maximumLongitude to set
	 */
	public synchronized void setMaximumLongitude(double maximumLongitude) {
		this.maximumLongitude = maximumLongitude;
	}

	/**
	 * @return the latitudeInterval
	 */
	public double getLatitudeInterval() {
		return latitudeInterval;
	}

	/**
	 * @param latitudeInterval the latitudeInterval to set
	 */
	public synchronized void setLatitudeInterval(double latitudeInterval) {
		this.latitudeInterval = latitudeInterval;
	}

	/**
	 * @return the longitudeInterval
	 */
	public double getLongitudeInterval() {
		return longitudeInterval;
	}

	/**
	 * @param longitudeInterval the longitudeInterval to set
	 */
	public synchronized void setLongitudeInterval(double longitudeInterval) {
		this.longitudeInterval = longitudeInterval;
	}

	/**
	 * @return the latitudeLineCount
	 */
	public int getLatitudeLineCount() {
		return latitudeLineCount;
	}

	/**
	 * @param latitudeLineCount the latitudeLineCount to set
	 */
	public synchronized void setLatitudeLineCount(int latitudeLineCount) {
		this.latitudeLineCount = latitudeLineCount;
	}

	/**
	 * @return the indexIdentifierType
	 */
	public short getIndexIdentifierType() {
		return indexIdentifierType;
	}

	/**
	 * @param indexIdentifierType the indexIdentifierType to set
	 */
	public synchronized void setIndexIdentifierType(short indexIdentifierType) {
		this.indexIdentifierType = indexIdentifierType;
	}
	
	public synchronized void setLineOffset( int lineIndex, int lineOffset ) {
		this.lineStartPositions.put( Integer.valueOf( lineIndex ) , Integer.valueOf( lineOffset ) );
	}
	
	public synchronized Integer getLineOffset( int lineIndex ) {
		return this.lineStartPositions.get( Integer.valueOf( lineIndex ) );
	}
	
	/**
	 * A function designed to make sure all sequential integer values for line indexes have a non-null
	 * integer offset value associated with them. This is done based on the current length of the lineOffset
	 * map. For instance of 4 line offsets have been registered this function will verify that for line 
	 * indices for 0 through 3, the offset values associated are not null.
	 * 
	 * @return
	 */
	public boolean isLineOffsetComplete( ) {
		int lineOffsetCount = this.lineStartPositions.size();
		
		for( int i = 0; i < lineOffsetCount; i++ ) {
			if( this.lineStartPositions.get(  i  ) == null )
				return false;
		}
		
		return true;
	}

	@Override
	public synchronized ByteBuffer getByteBuffer() {
		ByteBuffer buffer = ByteBuffer.allocate(getByteSize());
		
		buffer.putDouble(minimumLatitude);
		buffer.putDouble(minimumLongitude);
		buffer.putDouble(maximumLatitude);
		buffer.putDouble(maximumLongitude);
		buffer.putDouble(latitudeInterval);
		buffer.putDouble(longitudeInterval);
		buffer.putInt(latitudeLineCount);
		buffer.putShort(indexIdentifierType);
		
		lineStartPositions.forEach( (index,offset) -> buffer.position(54+index*4).putInt(offset) );
		
		return buffer;
	}

	@Override
	public int getByteSize() {
		return 54+(latitudeLineCount*4);
	}
}
