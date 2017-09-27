package com.jdglazer.igrd.point;

import java.util.ArrayList;

public class PointDataDTO {
	
	private double minLat;
	private double maxLat;
	private double minLon;
	private double maxLon;
	
	private ArrayList<PointDTO> points = new ArrayList<PointDTO>();
	
	public void addPoint( PointDTO point ) {
		points.add(point);
	}
	
	public int getPointCount() {
		return points.size();
	}
	
	public double getPointLatitude( int offset) {
		return ( (double) points.get(offset).getLatitude() )*( maxLat - minLat )/65535.0;
	}
	
	public double getPointLongitude( int offset) {
		return ( (double) points.get(offset).getLongitude() )*( maxLon - minLon )/65535.0;
	}
	
	public short getPointIndex( int offset ) {
		return points.get(offset).getIndex();
	}
}
