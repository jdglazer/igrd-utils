package com.jdglazer.igrd.line;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import com.jdglazer.igrd.IGRDCommonDTO;

public class LineDataRecordDTO extends IGRDCommonDTO implements Serializable  {
	
	private short id;
	
	private double minLat;
	
	private double maxLat;
	
	private double minLon;
	
	private double maxLon;
	
	private ArrayList<short[]> points = new ArrayList<short[]>();
	
	public LineDataRecordDTO( double minLat, double maxLat, double minLon, double maxLon ){
		super();
		minLat = this.minLat;
		maxLat = this.maxLat;
		minLon = this.minLon;
		maxLon = this.maxLon;
	}

	public double getMinLat() {
		return minLat;
	}

	public double getMaxLat() {
		return maxLat;
	}

	public double getMinLon() {
		return minLon;
	}

	public double getMaxLon() {
		return maxLon;
	}

	public short getId() {
		return id;
	}

	public void setId(short id) {
		this.id = id;
	}
	
	public void addPoint( double latitude, double longitude ) {
		short latShort = (short) ( Math.round( 65535.0*(latitude-minLat)/(maxLat-minLat) ) - 32768.0 );
		short lonShort = (short) ( Math.round( 65535.0*(longitude-minLon)/(maxLon-minLon) ) - 32768.0 );
		points.add( new short[] { latShort, lonShort } );
	}
	
	public double getLatitudePoint( int i ) {
		return ( ( (double) points.get(i)[0] + 32768.0 ) ) * ( maxLat - minLat ) / 65535.0;
	}
	
	public double getLongitudePoint( int i ) {
		return ( ( (double) points.get(i)[1] + 32768.0 ) ) * ( maxLon - minLon ) / 65535.0;
	}
	
	public int getPointCount() {
		return points.size();
	}
	
	public int getRecordLength() {
		return 36 + getPointCount() * 30;
	}

	@Override
	public ByteBuffer getByteBuffer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getByteSize() {
		// TODO Auto-generated method stub
		return 0;
	}

}
