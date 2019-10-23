package com.mobiquityinc.packer;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import com.mobiquityinc.DTO.Packet;
import com.mobiquityinc.business.RuleValidator;
import com.mobiquityinc.business.RuleValidatorImpl;
import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.exception.BusinessException;
import com.mobiquityinc.util.HandlerFile;
import com.mobiquityinc.util.HandlerLinePacket;

public class Packer {
	
	private static Logger logger = Logger.getLogger(Packer.class.getName());
	private static HandlerLinePacket handlerFile;
	private static RuleValidator ruleValidator;

	private Packer() {
	}

	public static String pack(String filePath) throws APIException {
		StringBuilder finalResult = new StringBuilder();
		try {
			List<String> lines = getHandlerFile().getLines(filePath);
			for(String line : lines) {
				Packet packet = getHandlerFile().getItemsPerPackage(line);
				getRuleValidator().validateConstraints(packet);
				finalResult.append(getRuleValidator().validatePacketGoal(packet)).append("\n");
			}
		} catch (IOException e) {
			logger.severe("There was a problem with the file path. The technical cause was: " + e.getMessage());
			throw new APIException("There was a problem with the file path or loading it, please check it.", e);
		} catch(NumberFormatException | IndexOutOfBoundsException e) {
			logger.severe("There was a problem loading some item. Technical cause: " + e.getMessage());
			throw new APIException("There was a problem loading some item, please check all of them are right.", e);
		} catch (BusinessException e) {
			logger.severe("There was a problem constrain with some item. the business cause: " + e.getMessage());
			throw new APIException("There was a problem constrain with some item. the business cause:" + e.getMessage(), e);
		}
		
		return finalResult.toString();
	}

	private static HandlerLinePacket getHandlerFile() {
		if(Packer.handlerFile == null) {
			Packer.handlerFile = new HandlerFile();
		}
		
		return Packer.handlerFile;
	}

	public static void setHandlerFile(HandlerLinePacket handlerLinePacket) {
		Packer.handlerFile = handlerLinePacket;
	}

	private static RuleValidator getRuleValidator() {
		if(Packer.ruleValidator == null) {
			Packer.ruleValidator = new RuleValidatorImpl();
		}
		return Packer.ruleValidator;
	}

	public static void setRuleValidator(RuleValidator ruleValidator) {
		Packer.ruleValidator = ruleValidator;
	}
}
