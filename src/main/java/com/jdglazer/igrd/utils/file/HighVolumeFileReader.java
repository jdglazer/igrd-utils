package com.jdglazer.igrd.utils.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel.MapMode;

public class HighVolumeFileReader extends FileReader {
	private RandomAccessFile raf;
	private MappedByteBuffer mbb;
	
	protected HighVolumeFileReader(File file) throws FileNotFoundException, IOException {
		setFile(file, 0, file.length()-1);
	}
	
	protected HighVolumeFileReader(File file, int start, int size) throws FileNotFoundException, IOException {
		setFile(file, start, size);
	}
	
	// just for testing
	protected HighVolumeFileReader() {
		
	}
	
    protected void setFile(File file, long start, long end) throws IOException {
		this.raf = new RandomAccessFile(file, "r");
		mbb = raf.getChannel().map(MapMode.READ_ONLY, start, end);
    }

	@Override
	protected ByteBuffer getByteBuffer(ByteOrder endianness, int offset, int readLength) {
		mbb.order(endianness);
		mbb.position(offset);
		return mbb;
	}

	public void close() throws IOException {
		raf.close();
	}
	
}
