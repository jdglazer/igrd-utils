package com.jdglazer.igrd.line;

import java.io.Serializable;
import java.util.ArrayList;

public class LineDataHeaderDTO implements Serializable {
	
	private double minLat;
	
	private double maxLat;
	
	private double minLon;
	
	private double maxLon;
	
	private ArrayList<Integer> recordOffsets = new ArrayList<Integer>();

	public double getMinLat() {
		return minLat;
	}

	public void setMinLat(double minLat) {
		this.minLat = minLat;
	}

	public double getMaxLat() {
		return maxLat;
	}

	public void setMaxLat(double maxLat) {
		this.maxLat = maxLat;
	}

	public double getMinLon() {
		return minLon;
	}

	public void setMinLon(double minLon) {
		this.minLon = minLon;
	}

	public double getMaxLon() {
		return maxLon;
	}

	public void setMaxLon(double maxLon) {
		this.maxLon = maxLon;
	}
	
	public void addRecordOffset( int recordLength ) {
		for( int i = 0; i < recordOffsets.size(); i++ ) {
			int oldOffset = recordOffsets.get(i).intValue();
			recordOffsets.set( i, oldOffset + 4 );
		}
		
		int previousOffset = recordOffsets.get( recordOffsets.size() - 1 );
		
		recordOffsets.add( new Integer( previousOffset + recordLength ) );
	}
	
	public int getRecordOffset( int recordIndex ) {
		return recordOffsets.get(recordIndex);
	}
	
	public int getRecordCount() {
		return recordOffsets.size();
	}
}
