package com.mobiquityinc.util;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.mobiquityinc.DTO.Packet;
import com.mobiquityinc.exception.APIException;

public class HandlerFileTest {

	private final static double EPSILON = 0.001;
	private HandlerLinePacket handlerFile;
	
	@Before
	public void setUp(){
		handlerFile = new HandlerFile();
	}
	
	/**
	 * Test case that throws an IOException
	 * when the file path is wrong o doesn't exist.
	 * @throws IOException
	 */
	@Test(expected = IOException.class)
	public void testThat_throwsIOException_when_filePathNotExist() throws IOException {
		//GIVEN
		//WHEN
		handlerFile.getLines("C:\\fakePath");
		//THEN
	}
	
	/**
	 * Test case that get the file lines
	 * correctly
	 * @throws IOException
	 */
	@Test
	public void testThat_getLines_when_filePathExist() throws IOException {
		//GIVEN
		final int linesExpected = 1;
		//WHEN
		List<String> lines = handlerFile.
				getLines("C:\\Users\\davidesteban.gomez\\Documents\\Personal\\Workspace\\package-challenge\\src\\test\\resources\\firstTest.txt");
		//THEN
		assertEquals(linesExpected, lines.size());
	}
	
	/**
	 * Test case that get the complete package
	 * according to a line
	 * @throws IOException
	 * @throws APIException 
	 * @throws IndexOutOfBoundsException 
	 * @throws NumberFormatException 
	 */
	@Test
	public void testThat_getItemsPerPackage_when_TheLineIsCorrect() 
			throws IOException, NumberFormatException, IndexOutOfBoundsException, APIException {
		//GIVEN
		String line = "81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)";
		//WHEN
		Packet packet = handlerFile.getItemsPerPackage(line);
		//THEN
		assertEquals(81, packet.getWeight(),EPSILON);
		assertEquals(6,packet.getItems().size());
	}
	
	/**
	 * Test case that throws an APIException
	 * when the file line is not correct according
	 * to the standard line
	 * @throws IOException
	 * @throws APIException 
	 * @throws IndexOutOfBoundsException 
	 * @throws NumberFormatException 
	 */
	@Test(expected = APIException.class)
	public void testThat_throwsAPIException_when_TheLineHasSomethingWrong() 
			throws IOException, NumberFormatException, IndexOutOfBoundsException, APIException {
		//GIVEN
		String line = "81 : (1,53.38,€45(2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)";
		//WHEN
		handlerFile.getItemsPerPackage(line);
		//THEN
	}
	
	/**
	 * Test case that throws an NumberFormatException
	 * when the file line is not correct according
	 * to the standard line
	 * @throws IOException
	 * @throws APIException 
	 * @throws IndexOutOfBoundsException 
	 * @throws NumberFormatException 
	 */
	@Test(expected = NumberFormatException.class)
	public void testThat_throwsNumberFormatException_when_TheLineHasSomethingWrong() 
			throws IOException, NumberFormatException, IndexOutOfBoundsException, APIException {
		//GIVEN
		String line = "81sa : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)";
		//WHEN
		handlerFile.getItemsPerPackage(line);
		//THEN
	}
}
