package com.jdglazer.igrd.utils.file;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
//import java.lang.reflect.Method;
import java.nio.ByteOrder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jdglazer.igrd.utils.file.HighVolumeFileReader;

public class HighVolumeFileReaderTest extends HighVolumeFileReader { 
	//private ArrayList<TestDTO> tests = new ArrayList<TestDTO>();
	
	public HighVolumeFileReaderTest() {
		super();
	}
	
	@Before
	public void setUp() throws IOException {
		// replace the place holder file given to super constructor
		String BINARY_FILE = getClass().getClassLoader().getResource("FileReaderSample.bin").getFile();
		File actualFile = new File(BINARY_FILE);
		setFile(actualFile,0,actualFile.length()-1);
		
		// set tests
		/*
		tests.add( new TestDTO<Double>("getDouble", Double.valueOf(30196578.34561), "Verify that we can get a double from specified offset", new Object[] { ByteOrder.LITTLE_ENDIAN, 0}));
		tests.add( new TestDTO<Integer>("getInt", Integer.valueOf(259873), "Verify that we can get an unsigned int from specified offset", new Object[] {ByteOrder.BIG_ENDIAN, 8}));
		*/
	};
	
/*	@Test
	public void testReadingFile() { 
		for(TestDTO testDTO : tests) {
			try {
				Class<?> [] argClasses = new Class<?>[testDTO.args.length];
				for( int i = 0; i < testDTO.args.length; i++) {
					argClasses[i] = testDTO.args[i].getClass();
				}
				Method method = getClass().getDeclaredMethod(testDTO.methodToCall,argClasses);
				Object actual = method.invoke(this, testDTO.args);
				assertEquals(testDTO.description,testDTO.expectedValue,actual);
			} catch (Exception e) {
				e.printStackTrace();
				//assertFail(testDTO.description);
			}
		}
	}
*/
	
	@Test
	public void testReadingFile() {
		assertEquals("verify we can get double",         3012132112.32134,  getDoubleFrom(ByteOrder.LITTLE_ENDIAN, 0), 0);
		assertEquals("verify we can get floating point", -123.43112f,       getFloatFrom(ByteOrder.BIG_ENDIAN, 8),     0);
		assertEquals("verify we can get integer",        2321435,           getIntFrom(ByteOrder.LITTLE_ENDIAN, 12));
		assertEquals("verify we can get short integer",  (short)30848,      getShortFrom(ByteOrder.BIG_ENDIAN, 16));
		assertEquals("verify we can get long integer",   34891849813491323l,getLongFrom(ByteOrder.LITTLE_ENDIAN, 18));
	}
/*
	private class TestDTO <T> {
		public String description;
		public String methodToCall;
		public T expectedValue;
		public Object[] args;
		
		public TestDTO(String methodToCall, T expectedValue, String description, Object...args) {
			this.methodToCall = methodToCall;
			this.args = args;
			this.expectedValue = expectedValue;
			this.description = description;
		}
	}
*/
	@After
	public void tearDown() {
		try {
			close();
		} catch (IOException e) {
		}
	}

}
