package com.jdglazer.igrd.line;

import java.io.Serializable;
import java.util.ArrayList;

public class LineDataDTO implements Serializable {
	
	private LineDataHeaderDTO dataHeader = new LineDataHeaderDTO();
	
	private ArrayList<LineDataRecordDTO> records = new ArrayList<LineDataRecordDTO>();

	public LineDataHeaderDTO getDataHeader() {
		return dataHeader;
	}

	public void addRecord( LineDataRecordDTO record ) {
		records.add(record);
		dataHeader.addRecordOffset( record.getRecordLength() );
	}
}
