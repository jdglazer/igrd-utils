package com.jdglazer.igrd.point;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteOrder;

import com.jdglazer.igrd.utils.file.HighVolumeFileReader;

public class PointDataReader extends HighVolumeFileReader {

    public PointDataReader(File file, int startOffsetInFile, int endOffsetInFile) throws FileNotFoundException, IOException {
    	super(file,startOffsetInFile,endOffsetInFile);
    }

	private double dv( int offset ) throws IOException {
		return getDoubleFrom(ByteOrder.BIG_ENDIAN, offset);
	}
	
	private float fv( int offset ) {
		return getFloatFrom(ByteOrder.BIG_ENDIAN, offset);
	}
	
	private int iv( int offset ) {
		return getIntFrom(ByteOrder.BIG_ENDIAN, offset);
	}
	
	private short sv( int offset ) {
		return getShortFrom(ByteOrder.BIG_ENDIAN, offset);
	}
	
	public float getMinLat() throws IOException {
		return fv( 0 );
	}
	
	public float getMaxLat() throws IOException {
		return fv( 8 );
	}
	
	public float getMinLon() throws IOException {
		return fv( 4 );
	}		
	
	public float getMaxLon() throws IOException {
		return fv( 12 );
	}
	
	public int getPointCount() throws IOException {
		return iv( 16 );
	}
	
	public short getPointIndex( int recordOffsetIndex ) {
		return sv( 20+10*recordOffsetIndex );
	}
	
	public float [] getPoint( int recordOffsetIndex ) {
		return new float[] { iv( 22 + 10*recordOffsetIndex ),
							 iv( 26 + 10*recordOffsetIndex ) };
	}
}
