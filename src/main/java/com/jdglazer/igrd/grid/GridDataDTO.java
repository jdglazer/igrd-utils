package com.jdglazer.igrd.grid;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import com.jdglazer.igrd.IGRDCommonDTO;

public class GridDataDTO extends IGRDCommonDTO implements Serializable {

	private GridDataHeaderDTO gridDataHeader;
	
	private ArrayList<GridDataLineDTO> gridDataLines = new ArrayList<GridDataLineDTO>();
	
	
	public GridDataDTO() {
		super();
	}
	
	public synchronized void addGridLine( GridDataLineDTO gridDataLine ) {
		gridDataLines.add( gridDataLine );
	}
	
	public synchronized void addGridDataLines(ArrayList<GridDataLineDTO> lines ) {
		for( GridDataLineDTO gdl: lines ) {
			gridDataLines.add(gdl);
		}
	}
	
	public synchronized void setGridDataHeader( GridDataHeaderDTO gridDataHeader ) {
		this.gridDataHeader = gridDataHeader;
	}
	
	public GridDataHeaderDTO getGridDataHeader() {
		return gridDataHeader;
	}
	
	public GridDataLineDTO getGridDataLine( int line ) {
		return gridDataLines.get(line);
	}
	
	public ArrayList<GridDataLineDTO> getGridDataLines() {
		return gridDataLines;
	}

	@Override
	public synchronized ByteBuffer getByteBuffer() {
		ByteBuffer buffer = ByteBuffer.allocate(getByteSize());
		
		buffer.put(gridDataHeader.getByteBuffer());
		gridDataLines.forEach((line)->buffer.put(line.getByteBuffer()));
		
		return buffer;
	}

	@Override
	public int getByteSize() {
		int length = gridDataHeader.getByteSize();
		
		for( GridDataLineDTO line: gridDataLines) {
			length+=line.getByteSize();
		}
		
		return length;
	}
	
}
