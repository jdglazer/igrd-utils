package com.jdglazer.igrd.point;

public class PointDTO {
	
	private short index;
	private short latitude;
	private short longitude;
	
	public short getIndex() {
		return index;
	}
	public void setIndex(short index) {
		this.index = index;
	}
	public int getLatitude() {
		return (int) ( latitude + 32768 );
	}
	public void setLatitude(int latitude) {
		this.latitude = (short) ( latitude - 32768 );
	}
	public int getLongitude() {
		return (int) ( longitude + 32768 );
	}
	public void setLongitude(int longitude) {
		this.longitude = (short) ( longitude - 32768 );;
	}
}
