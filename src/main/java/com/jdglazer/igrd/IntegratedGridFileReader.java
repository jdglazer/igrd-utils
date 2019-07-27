package com.jdglazer.igrd;

import java.io.File;
import java.io.IOException;
import java.nio.ByteOrder;

import com.jdglazer.igrd.grid.GridDataReader;
import com.jdglazer.igrd.line.LineDataReader;
import com.jdglazer.igrd.point.PointDataReader;
import com.jdglazer.igrd.utils.file.LowVolumeFileReader;

/**
 * A class designed for intensive reading of static Integrated grid files. If the file 
 * changes on the system, this class must be reloaded. Reloading this can be rather 
 * expensive for the underlying operating system. Thus, it's suggested that the mapped 
 * file is held in a write locked state when using this.
 * 
 * @author jdglazer
 *
 */
public class IntegratedGridFileReader extends LowVolumeFileReader {
	
	public static final int FILE_CODE = 12739;
	
	private GridDataReader gridDataReader;
	
	private LineDataReader linearDataReader;
	
	private PointDataReader pointDataReader;

	public IntegratedGridFileReader(File igrdFile) throws IOException {
		super(igrdFile);
		
		boolean hasGridData = hasGridData();
		boolean hasLinearData = hasLinearData();
		boolean hasPointData = hasPointData();
		long fileLength = igrdFile.length();
		long startDataOffset = 16;
		long nextStartOffset = 16;
		
		if(hasGridData) {
			if(hasLinearData) {
				nextStartOffset = getLinearDataOffset();
			} else if(hasPointData) {
				nextStartOffset = getPointDataOffset();
			} else {
				nextStartOffset = fileLength;
			}
			
			gridDataReader = new GridDataReader(igrdFile,(int)startDataOffset,(int)(nextStartOffset-startDataOffset));
		}
		
		if(hasLinearData) {
			startDataOffset = nextStartOffset;
			if(hasPointData) {
				nextStartOffset = getPointDataOffset();
			} else {
				nextStartOffset = fileLength;
			}
			
		    linearDataReader = new LineDataReader(igrdFile,(int)startDataOffset,(int)(nextStartOffset-startDataOffset));
		}
		
		if(hasPointData) {
			startDataOffset = nextStartOffset;
		    pointDataReader = new PointDataReader(igrdFile,(int)startDataOffset, (int)(nextStartOffset-startDataOffset));
		}
	}

	public int getGridDataOffset( ) {
		return getIntFrom(ByteOrder.BIG_ENDIAN, 4);
	}
	
	public boolean hasGridData( ) {
		return 16 <= getGridDataOffset( );
	}
	
	public int getLinearDataOffset( ) {
		return getIntFrom(ByteOrder.BIG_ENDIAN, 8);
	}
	
	public boolean hasLinearData( ) {
		return 16 <= getLinearDataOffset( );
	}
	
	public int getPointDataOffset( ) {
		return getIntFrom(ByteOrder.BIG_ENDIAN, 12);
	}
	
	public boolean hasPointData( ) {
		return 16 <= getPointDataOffset( );
	}
	
	public GridDataReader getGridDataReader() {
		return gridDataReader;
	}
	
	public LineDataReader getLineDataReader() {
		return linearDataReader;
	}
	
	public PointDataReader getPointDataReader() {
		return pointDataReader;
	}
}
