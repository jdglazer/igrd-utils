package com.jdglazer.igrd.grid;

import static org.junit.Assert.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.junit.Before;
import org.junit.Test;

import com.jdglazer.igrd.utils.GridSegmentShortOverflowDTO;

public class GridDataBinaryGeneratorTest {
	
	private GridDataBinaryGenerator gridDataBinaryGenerator = new GridDataBinaryGenerator();
	private GridDataLineDTO gridDataLineDTO;
	private GridDataHeaderDTO gridDataHeaderDTO;
	
	@Before
	public void setUp() {
		
		//Setup for grid line test
		 gridDataLineDTO = new GridDataLineDTO( ( short ) 1 );
		 for( int i = 0 ; i < 3 ; i++ ) {
			 GridDataLinePartDTO part = new GridDataLinePartDTO(gridDataLineDTO.getSegmentIndexType());
			 part.setStartLongitude(-76.98f-((float)i*0.1f) );
			 for( int j = 0; j < 10; j++ ) {
				 GridDataLinePartSegmentDTO segment = new GridDataLinePartSegmentDTO(gridDataLineDTO.getSegmentIndexType());
				 segment.setSegmentIndex( (short) ( (i+1)*j ) );
				 segment.setSegmentLength( (short) ( (i+j)*(j) ) );
				 part.addSegment(segment);
			 }
			 gridDataLineDTO.addPart(part);
		 }
		 GridSegmentShortOverflowDTO overflow = new GridSegmentShortOverflowDTO();
		 overflow.setPartOffset(0);
		 overflow.setSegmentOffset(0);
		 overflow.setOverflowValue((short)10);
		 overflow.setOverflowIndex((short)1);
		 gridDataLineDTO.addShortOverflow(overflow);
		 
		 //Setup for grid header test
		 gridDataHeaderDTO = new GridDataHeaderDTO();
		 
		 gridDataHeaderDTO.setIndexIdentifierType( GridDataHeaderDTO.TYPE_BYTE );
		 gridDataHeaderDTO.setMinimumLongitude(-80.9876);
		 gridDataHeaderDTO.setMaximumLongitude(-74.63144);
		 gridDataHeaderDTO.setMinimumLatitude(39.78982);
		 gridDataHeaderDTO.setMaximumLatitude(42.134414);
		 gridDataHeaderDTO.setLatitudeInterval(.00018);
		 gridDataHeaderDTO.setLongitudeInterval(.00018);
		 gridDataHeaderDTO.setLatitudeLineCount(15238);
		 gridDataHeaderDTO.setLineOffset(234, 123431);
		 
	}
	
	@Test
	public void testConvertGridLine() {
		byte [] byteArray           = gridDataBinaryGenerator.convertGridLineDTO( gridDataLineDTO, (short) 1);
		ByteBuffer buffer           = ByteBuffer.wrap(byteArray).order(ByteOrder.BIG_ENDIAN);
		
		// Check all the header data 
		int BYTE_LENGTH             = buffer.getInt(),
			PART_COUNT              = buffer.getInt(),
			SHORT_OVERFLOW_COUNT    = buffer.getInt(),
			OVERFLOW_PART_OFFSET    = buffer.getInt(),
			OVERFLOW_SEGMENT_OFFSET = buffer.getInt();
		short OVERFLOW_INDEX        = buffer.getShort();
		int PART1_START             = buffer.getInt(),
			PART2_START             = buffer.getInt(),
			PART3_START             = buffer.getInt();
		
		//Selectively check parts and segments
		float PART2_START_LON         = buffer.getFloat(76);
		byte PART1_SEG5_INDEX         = buffer.get(58);
		short PART1_SEG5_SEGMENT_LEN  = buffer.getShort(59);

		byte PART3_SEG3_INDEX         = buffer.get(136);
		short PART3_SEG3_SEGMENT_LEN  = buffer.getShort(137);
		
		// run tests
		assertEquals( "Correct byte length",          160, BYTE_LENGTH );
		assertEquals( "Correct part count",             3, PART_COUNT );
		assertEquals( "Correct short overflow count",   1, SHORT_OVERFLOW_COUNT );
		assertEquals( "Correct overflow part offset",   0, OVERFLOW_PART_OFFSET );
		assertEquals( "Correct overflow segment offset", 0, OVERFLOW_SEGMENT_OFFSET );
		assertEquals( "Correct overflow index", (short)  1, OVERFLOW_INDEX );
		assertEquals( "Correct part 1 offset",          34, PART1_START );
		assertEquals( "Correct part 2 offset",          76, PART2_START );
		assertEquals( "Correct part 3 offset",         118, PART3_START );
		assertEquals(                              -77.08f, PART2_START_LON, .01 );
		assertEquals( "Correct part 1, segment 5 index", (byte)   4, PART1_SEG5_INDEX );
		assertEquals( "Correct part 1, segment 5 length", (short)16, PART1_SEG5_SEGMENT_LEN );
		assertEquals( "Correct part 3, segment 3 index", (byte)   6, PART3_SEG3_INDEX );
		assertEquals( "Correct part 3, segment 3 length", (short) 8, PART3_SEG3_SEGMENT_LEN );
	}
	
	@Test
	public void testConvertGridHeader() {
		byte [] byteArray           = gridDataBinaryGenerator.convertGridDataHeaderDTO( gridDataHeaderDTO);
		ByteBuffer buffer           = ByteBuffer.wrap(byteArray).order(ByteOrder.BIG_ENDIAN);
				
		double MIN_LAT         = buffer.getDouble(),
			   MIN_LON         = buffer.getDouble(),
			   MAX_LAT         = buffer.getDouble(),
			   MAX_LON         = buffer.getDouble(),
			   LAT_INTERVAL    = buffer.getDouble(),
			   LON_INTERVAL    = buffer.getDouble();
		int    LINE_COUNT      = buffer.getInt();
		short  IDENTIFIER_TYPE = buffer.getShort();
		int    LINE_234_OFFSET = buffer.getInt( 990 );
		
		// TODO: write all tests
		//assertEquals( 89.0, MIN_LAT, 0.0 );
		
	}

}
