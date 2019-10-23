package com.mobiquityinc.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mobiquityinc.DTO.Item;
import com.mobiquityinc.DTO.Packet;
import com.mobiquityinc.exception.APIException;

public class HandlerFile implements HandlerLinePacket{

	private static final String WEIGHT_SEPARATOR_REGEX = " : ";
	private static final String SPACE_REGEX = "\\s+";
	private static final String RIGHT_PARENTH = ")";
	private static final String LEFT_PARENTH = "(";
	private static final String COMMA = ",";
	private static final String EURO = "€";
	private static final String EMPTY = "";

	/**
	 * Return all the file lines
	 * 
	 * @param filePath
	 * @return List lines
	 * @throws IOException
	 */
	public List<String> getLines(String filePath) throws IOException {
		Path path = Paths.get(filePath);
		List<String> fileLines = Files.lines(path).collect(Collectors.toList());
		
		return fileLines;
	}

	/**
	 * Return a packet based on the file line
	 * 
	 * @param line
	 * @return Packet DTO
	 * @throws APIException
	 * @throws NumberFormatException
	 * @throws IndexOutOfBoundsException
	 */
	public Packet getItemsPerPackage(String line) 
			throws NumberFormatException, IndexOutOfBoundsException, APIException {
		String[] elementsLine = line.split(WEIGHT_SEPARATOR_REGEX);
		double maxWeightPacket = Double.parseDouble(elementsLine[0]);
		
		List<Item> itemsPacket = getItemsValues(elementsLine[1]);

		return new Packet(maxWeightPacket, itemsPacket);
	}
	
	private List<Item> getItemsValues(String lineItems) throws APIException{
		List<Item> itemsPacket = new ArrayList<>();
		String[] eachItems = lineItems.split(SPACE_REGEX);
		
		for(int i = 0; i < eachItems.length; i++) {
	
			String[] itemValues = eachItems[i].replace(LEFT_PARENTH, EMPTY).replace(RIGHT_PARENTH, EMPTY).split(COMMA);
			
			if(itemValues.length != 3) {
				throw new APIException("There are wrong items in the file");
			}
			
			int index = Integer.valueOf(itemValues[0]);
			double weight = Double.valueOf(itemValues[1]);
			int price = Integer.valueOf(itemValues[2].replace(EURO, EMPTY));
			
			Item item = new Item(index, weight, price);
			
			itemsPacket.add(item);
		}
		
		return itemsPacket;
	}
}
