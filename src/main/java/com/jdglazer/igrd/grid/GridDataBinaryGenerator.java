package com.jdglazer.igrd.grid;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

import com.jdglazer.igrd.grid.GridDataLineDTO.PartDTO;
import com.jdglazer.igrd.grid.GridDataLineDTO.SegmentDTO;
import com.jdglazer.igrd.utils.GridSegmentShortOverflowDTO;

public class GridDataBinaryGenerator {
	
	private ByteBuffer buffer2 = ByteBuffer.allocate(2).order(ByteOrder.BIG_ENDIAN);
	private ByteBuffer buffer4 = ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN);
	private ByteBuffer buffer8 = ByteBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN);
	
	public byte [] convertGridLineDTO( GridDataLineDTO gridDataLineDTO, int indexIdLength ) {
		
		ArrayList<Byte> out = new ArrayList<Byte>();
			
		append( getInt( gridDataLineDTO.getLineSize() ), out );
		append( getInt( gridDataLineDTO.getNumberParts() ), out );
		append( getInt( gridDataLineDTO.getShortOverflowCount() ), out );
		
		for( int i = 0; i < gridDataLineDTO.getShortOverflowCount(); i++ ) {
			GridSegmentShortOverflowDTO so = gridDataLineDTO.getShortOverflow(i);
			append( getInt( so.getPartOffset() ), out );
			append( getInt( so.getSegmentOffset() ), out );
			append( getShort( so.getOverflowIndex() ), out );
		}
		
		int offset = out.size() + gridDataLineDTO.getNumberParts()*4 ;
		for( int j = 0; j < gridDataLineDTO.getNumberParts() ; j++ ) {
			append( getInt( offset ), out );
			offset += partByteLength( gridDataLineDTO.getPart( j ), (int) gridDataLineDTO.getSegmentIndexType() );
		}
		
		for( int k = 0; k < gridDataLineDTO.getNumberParts() ; k++ ) {
			
			PartDTO part = gridDataLineDTO.getPart( k );
			append( getFloat( part.getStartLongitude() ), out );
			append( getInt( part.getPointCount() ), out );
			append( getInt( part.getSegmentCount() ), out );
			
			for( int l = 0; l < part.getSegmentCount() ; l++ ) {
				SegmentDTO segment = part.getSegment(l);
				if( gridDataLineDTO.getSegmentIndexType() == 2 ) {
					append( getShort( segment.getSegmentIndex() ), out );
				} else {
					out.add( (byte) segment.getSegmentIndex() );
				}
				append( getShort( segment.getSegmentLength() ), out );
			}
		}
	
		return toArray( out );

	}
	
	public byte [] convertGridDataHeaderDTO( GridDataHeaderDTO gridDataHeaderDTO ) {
		
		ArrayList<Byte> out = new ArrayList<Byte>();
		
		append( getDouble( gridDataHeaderDTO.getMinimumLatitude() ), out );
		append( getDouble( gridDataHeaderDTO.getMinimumLongitude() ), out );
		append( getDouble( gridDataHeaderDTO.getMaximumLatitude() ), out );
		append( getDouble( gridDataHeaderDTO.getMaximumLongitude() ), out );
		append( getDouble( gridDataHeaderDTO.getLatitudeInterval() ), out );
		append( getDouble( gridDataHeaderDTO.getLongitudeInterval() ), out );
		append( getInt( gridDataHeaderDTO.getLatitudeLineCount() ), out );
		append( getShort( gridDataHeaderDTO.getIndexIdentifierType() ), out );
		
		for( int i = 0; i < gridDataHeaderDTO.getLatitudeLineCount() ; i++ ) {
			Integer offset = gridDataHeaderDTO.getLineOffset( i );
			append( getInt( offset == null ? 0 : offset ), out );
		}
		
		return toArray( out );
	}
	
	private static void append( byte [] barray, ArrayList<Byte> list ) {
		
		if ( barray == null )
			return;
		
		for( byte b : barray ) {
			list.add(b);
		}
	}
	
	private static int partByteLength ( PartDTO part, int indexLength ) {
		return part.getSegmentCount()*(2 + indexLength) + 12;
	}
	
	private static byte [] toArray( ArrayList<Byte> al ) {
		byte [] out = new byte[al.size()];
		for(int i = 0; i < al.size() ; i++ ) {
			out[i] = al.get(i);
		}
		return out;
	}
	
	private byte [] getDouble( double d ) {
		synchronized( buffer8 ) {
			return ((ByteBuffer)buffer8.clear()).putDouble(d).array();	
		}
	}
	
	private byte [] getFloat( float f ) {
		synchronized( buffer4 ) {
			return ((ByteBuffer)buffer4.clear()).putFloat(f).array();	
		}
	}
	
	private byte [] getInt( int i ) {
		synchronized( buffer4 ) {
			return ((ByteBuffer)buffer4.clear()).putInt(i).array();
		}
	}
	
	private byte [] getShort( short s ) {
		synchronized( buffer2 ) {
			return ((ByteBuffer)buffer2.clear()).putShort(s).array();
		}
	}
}
