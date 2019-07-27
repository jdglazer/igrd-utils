package com.jdglazer.igrd.grid;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import com.jdglazer.igrd.IGRDCommonDTO;
import com.jdglazer.igrd.utils.GridSegmentShortOverflowDTO;

public class GridDataLineDTO extends IGRDCommonDTO implements Serializable {

	/**
 	* subject to parent grid data setting
 	*/
	private short segmentIndexType;
	
	/**
	 * The size of the line in bytes, grid line offset 0
	 */
	private int lineSize = 12;
	
	/**
	 * The number of geographically separated parts in the line, grid line offset 4
	 */
	private int numberParts = 0;
	
/**
 * The number of short overflows in the part, grid line offset 8
 */
	private int shortOverflowCount = 0;
	
/**
 * overflow segments where short value was insufficient, grid line offset 12
 */
	private ArrayList<GridSegmentShortOverflowDTO> shortOverflows = new ArrayList<GridSegmentShortOverflowDTO> ();
	
/**
 * The starting position in the grid line of each part
 */
	private ArrayList<Integer> partStarts = new ArrayList<Integer>();
	
/**
 * The actual part data
 */
	private ArrayList<GridDataLinePartDTO> parts = new ArrayList<GridDataLinePartDTO>();
	
	public GridDataLineDTO( short indexType ) {
		super();
		segmentIndexType = indexType <= 2 && indexType > 0 ? indexType : 2 ;
	}
	
	/**
	 * @return the lineSize
	 */
	public int getLineSize() {
		return lineSize;
	}

	/**
	 * @param lineSize the line size to set
	 */
	public synchronized void setLineSize(int lineSize) {
		this.lineSize = lineSize;
	}

	/**
	 * @return the numberParts
	 */
	public int getNumberParts() {
		return numberParts;
	}

	/**
	 * @param numberParts the numberParts to set
	 */
	public synchronized void setNumberParts(int numberParts) {
		this.numberParts = numberParts;
	}

	/**
	 * @return the shortOverflowCount
	 */
	public int getShortOverflowCount() {
		return shortOverflowCount;
	}

	/**
	 * @param shortOverflowCount the shortOverflowCount to set
	 */
	public synchronized void setShortOverflowCount(int shortOverflowCount) {
		this.shortOverflowCount = shortOverflowCount;
	}
	
	/**
	 * adds a part and updates part count, part start offset, and line byte length
	 * @param part
	 */
	public synchronized void addPart( GridDataLinePartDTO part ) {
		this.parts.add( part );
		numberParts++;
		partStarts.add( lineSize );
		lineSize += 16 + (2+segmentIndexType)*part.getSegmentCount();
	}
	
	public synchronized GridDataLinePartDTO getPart( int index ) {
		return this.parts.get( index );
	}

	public synchronized short getSegmentIndexType() {
		return segmentIndexType;
	}

	public synchronized void setSegmentIndexType(short segmentIndexType) {
		this.segmentIndexType = segmentIndexType;
	}

	public synchronized ArrayList<GridSegmentShortOverflowDTO> getShortOverflows() {
		return shortOverflows;
	}

	public synchronized void addShortOverflow( GridSegmentShortOverflowDTO overflow ) {
		this.shortOverflows.add( overflow );
		shortOverflowCount++;
		lineSize+= 10;
		for( int i = 0; i < partStarts.size(); i++ ) {
			partStarts.set(i, partStarts.get(i)+10 );
		}
	}
	
	public synchronized GridSegmentShortOverflowDTO getShortOverflow( int index ) {
		return shortOverflows.get(index);
	}

	@Override
	public synchronized ByteBuffer getByteBuffer() {
		ByteBuffer buffer = ByteBuffer.allocate(getByteSize());
		
		buffer.putInt(lineSize);
		buffer.putInt(numberParts);
		buffer.putInt(shortOverflowCount);
		
		for(GridSegmentShortOverflowDTO shortOverflow : shortOverflows) {
			buffer.put(shortOverflow.getByteBuffer());
		}
		
		for(int partStart : partStarts) {
			buffer.putInt(partStart);
		}
		
		for(GridDataLinePartDTO part : parts) {
			buffer.put(part.getByteBuffer());
		}
		
		return null;
	}

	@Override
	public int getByteSize() {
		return 12+(10*shortOverflowCount)+(numberParts*4);
	}
	
}
