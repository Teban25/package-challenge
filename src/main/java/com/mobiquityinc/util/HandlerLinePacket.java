package com.mobiquityinc.util;

import java.io.IOException;
import java.util.List;

import com.mobiquityinc.DTO.Packet;
import com.mobiquityinc.exception.APIException;

public interface HandlerLinePacket {

	public List<String> getLines(String filePath) throws IOException;
	public Packet getItemsPerPackage(String line) 
			throws NumberFormatException, IndexOutOfBoundsException, APIException;
}
