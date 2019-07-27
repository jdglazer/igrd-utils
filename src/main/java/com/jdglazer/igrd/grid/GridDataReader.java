package com.jdglazer.igrd.grid;

import java.io.File;
import java.io.IOException;
import java.nio.ByteOrder;

import com.jdglazer.igrd.IndexOutOfBounds;
import com.jdglazer.igrd.utils.file.HighVolumeFileReader;

public class GridDataReader extends HighVolumeFileReader {	
	
	public GridDataReader(File file, int startOffsetInFile, int endOffsetInFile) throws IOException {
		super(file,startOffsetInFile,endOffsetInFile);
	}
	
	private double dv( int offset ) throws IOException {
		return getDoubleFrom(ByteOrder.BIG_ENDIAN, offset);
	}
	
	private float fv( int offset ) {
		return getFloatFrom(ByteOrder.BIG_ENDIAN,offset);
	}
	
	private int iv( int offset ) {
		return getIntFrom(ByteOrder.BIG_ENDIAN, offset);
	}
	
	private short sv( int offset ) {
		return getShortFrom(ByteOrder.BIG_ENDIAN, offset);
	}
	
	private int lo( int lineIndex ) throws IOException, IndexOutOfBounds {
		if( lineIndex < 0 || getLineCount() <= lineIndex )
			throw new IndexOutOfBounds( "Invalid line index provided" );
		return iv( 54 + lineIndex*4 );
	}
	
	private void validPart( int lineIndex, int partIndex ) throws IndexOutOfBounds, IOException {
		if( partIndex < 0 || getPartCount( lineIndex ) <= partIndex )
			throw new IndexOutOfBounds( "invalid part count for line "+lineIndex );
	}
	
	private void validSegment( int lineIndex, int partIndex, int segmentIndex ) throws IndexOutOfBounds, IOException {
		if( segmentIndex < 0 || segmentIndex >= getPartSegmentCount( lineIndex, partIndex) )
			throw new IndexOutOfBounds( "Segment "+segmentIndex+" is out bounds for line "+lineIndex+" part "+partIndex );
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
	
	public double getLatInterval() throws IOException {
		return dv( 32 );
	}
	
	public double getLonInterval() throws IOException {
		return dv( 40 );
	}
	
	public int getLineCount() throws IOException {
		return iv( 48 );
	}
	
	public short getIndexLength() throws IOException {
		return sv( 52 );
	}
	
	public int getLineSize( int lineIndex ) throws IOException, IndexOutOfBounds {
		return iv( lo( lineIndex ) );
	}
	
	public int getPartCount( int lineIndex ) throws IOException, IndexOutOfBounds {
		return iv( lo( lineIndex ) + 4  );
	}
	
	public int getShortOverflowCount( int lineIndex ) throws IOException, IndexOutOfBounds {
		return iv( lo( lineIndex ) + 8  );
	}
	
	public int getShortOverflowOffset( int lineIndex, int shortOverflowNum ) throws IOException, IndexOutOfBounds {
		if( shortOverflowNum < 0 || shortOverflowNum >=  getShortOverflowCount( lineIndex ) )
			throw new IndexOutOfBoundsException( "Invalid short overflow count provided" );
		return iv( lo( lineIndex ) + 12 + shortOverflowNum*6 );
	}
	
	public int getShortOverflowIndex( int lineIndex, int shortOverflowNum ) throws IOException, IndexOutOfBounds {
		if( shortOverflowNum < 0 || shortOverflowNum >=  getShortOverflowCount( lineIndex ) )
			throw new IndexOutOfBoundsException( "Invalid short overflow count provided" );
		return sv( lo( lineIndex ) + 16 + shortOverflowNum*6 );
	}
	
	public int getPartOffset( int lineIndex, int partIndex ) throws IOException, IndexOutOfBounds {
		validPart( lineIndex, partIndex );
		return iv( lo( lineIndex ) + 12 + getShortOverflowCount( lineIndex )*6 + partIndex*4 );
	}
	
	public float getPartStartLongitude( int lineIndex, int partIndex ) throws IOException, IndexOutOfBounds {
		return fv( lo( lineIndex ) + getPartOffset( lineIndex, partIndex ) );
	}
	
	public int getPartPointCount( int lineIndex, int partIndex ) throws IOException, IndexOutOfBounds {
		return iv( lo( lineIndex ) + getPartOffset( lineIndex, partIndex ) + 4 );
	}
	
	public int getPartSegmentCount( int lineIndex, int partIndex ) throws IOException, IndexOutOfBounds {
		return iv( lo( lineIndex ) + getPartOffset( lineIndex, partIndex ) + 8 );
	}
	
	public short getSegmentIndex( int lineIndex, int partIndex, int segmentNum ) throws IndexOutOfBounds, IOException {
		validSegment( lineIndex, partIndex, segmentNum );
		int iLen = getIndexLength(),
			sOff = 16 + lo( lineIndex ) + getPartOffset( lineIndex, partIndex ) + 12 + segmentNum*(4+iLen);
		return iLen == 1 
				? (short) getByteFrom(ByteOrder.BIG_ENDIAN, sOff)
				: sv( sOff ) ;
	}
	
	public int getSegmentPointCount( int lineIndex, int partIndex, int segmentNum ) throws IndexOutOfBounds, IOException {
		validSegment( lineIndex, partIndex, segmentNum );
		int iLen = getIndexLength();
		return iv( lo(lineIndex) + getPartOffset( lineIndex, partIndex ) + 12 + iLen + segmentNum*(iLen+4) );
	}
}
