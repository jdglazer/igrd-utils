package com.jdglazer.igrd.csv;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import com.jdglazer.igrd.IGRDCommonDTO;

public class CsvDataDTO extends IGRDCommonDTO implements Serializable {
	
	public static final String CSV_DATAPOINT_DELIMETER = "\\,";

	private HashMap<String,Integer> dataHeader = new HashMap<String,Integer>();
	// Maps the integer index to a list of strings
	private HashMap< Integer, ArrayList < String > > data = new HashMap< Integer, ArrayList < String > >();
	
	CsvDataDTO() {
		super(CsvDataDTO.class);
	}
	
	public void addKey( String name ) {
		dataHeader.put(name.trim(), dataHeader.size());
	}
	
	public void putData( Integer index, String name, String data_in) {
		data.putIfAbsent( index, new ArrayList<String>() );
		Integer offset = dataHeader.get(name);
		if( offset != null ) {
			data.get(index).add( dataHeader.get(name), data_in );
		} else {
			// log invalid data point
		}
	}
	
	public String getData( Integer index, String dataPoint ) {
		ArrayList<String> dataValues = data.get(index);
		if( dataValues != null ) {
			Integer point = dataHeader.get(dataPoint);
			return point == null ? null : dataValues.get(point);
		} else {
			//log invalid index error
			return null;
		}
	}
}
