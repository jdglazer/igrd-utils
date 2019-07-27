package com.jdglazer.igrd.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class LowVolumeFileReader extends FileReader {
	
	//Logger logger  = Logger.getLogger(getClass().getName());
	
	private FileInputStream fis;
	private ByteBuffer buffer;
	byte[] bytes;
	
	protected LowVolumeFileReader(File file) throws FileNotFoundException {
		this();
	    setFile(file);
	}
	
	protected LowVolumeFileReader() {
		buffer = ByteBuffer.allocate(0);
	    bytes = new byte[0];
	}
	
	@Override
	protected ByteBuffer getByteBuffer(ByteOrder endianness, int offset, int readLength) {
		try {
			// We allow for dynamic expansion of byte array and buffer
			if (readLength > bytes.length) {
				bytes = new byte[readLength];
				buffer = ByteBuffer.allocate(readLength);
			} else {
				// Reset position in buffer
				buffer.position(0);
			}
			
			//reset position in file
			fis.getChannel().position(offset);
			
			// read from file
			fis.read(bytes, 0, readLength);
		} catch (IOException e) {
			//logger.debug("Exception reading from the file "+fis.toString());
		}
		
		return buffer.put(bytes).order(endianness).position(0);
	}
	
	public void close() throws IOException {
		fis.close();
	}
	
	protected void setFile(File file) throws FileNotFoundException {
		this.fis = new FileInputStream(file);
	}

}
