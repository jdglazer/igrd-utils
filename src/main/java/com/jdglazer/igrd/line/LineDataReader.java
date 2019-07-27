package com.jdglazer.igrd.line;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteOrder;

import com.jdglazer.igrd.IndexOutOfBounds;
import com.jdglazer.igrd.utils.file.HighVolumeFileReader;

public class LineDataReader extends HighVolumeFileReader {
	
	public LineDataReader(File file, int startOffsetInFile, int endOffsetInFile) throws FileNotFoundException, IOException {
		super(file,startOffsetInFile,endOffsetInFile);
	}
	
	private double dv( int offset ) throws IOException {
		return getDoubleFrom(ByteOrder.BIG_ENDIAN,offset);
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
	
	public double getMinLat() throws IOException {
		return dv( 0 );
	}
	
	public double getMaxLat() throws IOException {
		return dv( 16 );
	}
	
	public double getMinLon() throws IOException {
		return dv( 8 );
	}		
	
	public double getMaxLon() throws IOException {
		return dv( 24 );
	}
	
	public int getLineCount() throws IOException {
		return iv( 32 );
	}
	
	public int getRecordOffset( int recordIndex ) throws IOException, IndexOutOfBounds {
		if( getLineCount() <= recordIndex || recordIndex < 0 )
			throw new IndexOutOfBounds( "Invalid linear data record index" );
		return iv( 36 + recordIndex*4 );
	}
	
	public short getRecordIndex( int recordOffsetIndex ) throws IOException, IndexOutOfBounds {
		return sv( getRecordOffset( recordOffsetIndex ) );
	}
	
	public float getLineMinLat( int recordOffsetIndex ) throws IOException, IndexOutOfBounds {
		return fv( getRecordOffset( recordOffsetIndex ) + 2 );
	}
	
	public float getLineMaxLat( int recordOffsetIndex ) throws IOException, IndexOutOfBounds {
		return fv( getRecordOffset( recordOffsetIndex ) + 6 );
	}
	
	public float getLineMinLon( int recordOffsetIndex ) throws IOException, IndexOutOfBounds {
		return fv( getRecordOffset( recordOffsetIndex ) + 10 );
	}		
	
	public float getLineMaxLon( int recordOffsetIndex ) throws IOException, IndexOutOfBounds {
		return fv( getRecordOffset( recordOffsetIndex ) + 14 );
	}
	
	public int getPointCount( int recordOffsetIndex ) throws IOException, IndexOutOfBounds {
		return iv( getRecordOffset( recordOffsetIndex ) + 18 );
	}
	
	public float [] getPoint( int recordOffsetIndex, int pointOffsetIndex ) throws IOException, IndexOutOfBounds {
		if( getPointCount( recordOffsetIndex ) <= pointOffsetIndex || pointOffsetIndex < 0 )
			throw new IndexOutOfBounds("Invalid point for record "+recordOffsetIndex);
		return new float[] { fv ( getRecordOffset( recordOffsetIndex ) + 18 + 8*pointOffsetIndex ), 
							 fv ( getRecordOffset( recordOffsetIndex ) + 22 + 8*pointOffsetIndex )
							}; 
	}
}
