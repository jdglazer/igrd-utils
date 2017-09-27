package com.jdglazer.igrd.grid;

import java.io.Serializable;
import java.util.ArrayList;

import com.jdglazer.igrd.IGRDCommonDTO;

public class GridDataDTO extends IGRDCommonDTO implements Serializable {

	private GridDataHeaderDTO gridDataHeader;
	
	private ArrayList<GridDataLineDTO> gridDataLines = new ArrayList<GridDataLineDTO>();
	
	
	public GridDataDTO() {
		super(GridDataDTO.class);
	}
	
	public void addGridLine( GridDataLineDTO gridDataLine ) {
		gridDataLines.add( gridDataLine );
	}
	
	public void setGridDataHeader( GridDataHeaderDTO gridDataHeader ) {
		this.gridDataHeader = gridDataHeader;
	}
	
	public GridDataHeaderDTO getGridDataHeader() {
		return gridDataHeader;
	}
	
	public GridDataLineDTO getGridDataLine( int line ) {
		return gridDataLines.get(line);
	}
}
